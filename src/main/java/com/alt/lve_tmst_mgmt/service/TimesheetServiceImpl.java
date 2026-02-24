package com.alt.lve_tmst_mgmt.service;

import com.alt.lve_tmst_mgmt.dto.LeaveDayDTO;
import com.alt.lve_tmst_mgmt.dto.SaveTimesheetRequest;
import com.alt.lve_tmst_mgmt.dto.SaveTimesheetResponse;
import com.alt.lve_tmst_mgmt.dto.TimesheetDayDTO;
import com.alt.lve_tmst_mgmt.entity.Employee;
import com.alt.lve_tmst_mgmt.entity.LeaveForecast;
import com.alt.lve_tmst_mgmt.entity.Timesheet;
import com.alt.lve_tmst_mgmt.repository.EmployeeRepository;
import com.alt.lve_tmst_mgmt.repository.LeaveForecastRepository;
import com.alt.lve_tmst_mgmt.repository.TimesheetRepo;
import com.alt.lve_tmst_mgmt.Exceptions.BusinessValidationException;
import com.alt.lve_tmst_mgmt.Exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TimesheetServiceImpl implements TimesheetService {

    private static final Logger log = LoggerFactory.getLogger(TimesheetServiceImpl.class);

    private final TimesheetRepo timesheetRepo;
    private final LeaveForecastRepository leaveForecastRepository;
    private final EmployeeRepository employeeRepository;
    private final EntityManager em;

    public TimesheetServiceImpl(TimesheetRepo timesheetRepository,
                                LeaveForecastRepository leaveForecastRepository,
                                EmployeeRepository employeeRepository,
                                EntityManager em) {
        this.timesheetRepo = timesheetRepository;
        this.leaveForecastRepository = leaveForecastRepository;
        this.employeeRepository = employeeRepository;
        this.em = em;
    }

    @Override
    public List<Timesheet> getAll() {
        log.info("Fetching all timesheets");
        return timesheetRepo.findAll();
    }

    @Override
    public Timesheet create(Timesheet timesheet) {
        log.info("Creating timesheet for employee={}, date={}",
                timesheet.getEmployee() != null ? timesheet.getEmployee().getEmployeeId() : null,
                timesheet.getWorkDate());
        return timesheetRepo.save(timesheet);
    }

    @Override
    @Transactional // runtime exceptions will rollback by default
    public SaveTimesheetResponse save(SaveTimesheetRequest req) {

        // capture for logs in catch blocks
        String empIdForLog = (req != null ? req.getEmployeeId() : null);

        try {
            // ---- Input Validation (Business Rules) ----
            if (req == null) {
                throw new BusinessValidationException("Request body is required.");
            }

            String empId = req.getEmployeeId();
            if (empId != null) {
                empId = empId.trim();
            }
            if (empId == null || empId.isBlank()) {
                // Match the exact format you wanted in GlobalExceptionHandler: "employeeId must not be blank"
                throw new BusinessValidationException("employeeId must not be blank");
            }

            // Validate timesheet items (list may be null or empty; both are allowed)
            if (req.getTimesheet() != null) {
                for (TimesheetDayDTO t : req.getTimesheet()) {
                    if (t == null) {
                        throw new BusinessValidationException("Timesheet entry cannot be null.");
                    }
                    if (t.getDate() == null) {
                        throw new BusinessValidationException("Timesheet date cannot be null.");
                    }
                    if (t.getHours() == null || t.getHours().compareTo(BigDecimal.ZERO) < 0) {
                        // still allow zero here; negative is invalid
                        throw new BusinessValidationException("Timesheet hours must be non-negative.");
                    }
                }
            }

            // Validate leave forecast items (list may be null or empty; both are allowed)
            if (req.getLeaveForecast() != null) {
                for (LeaveDayDTO l : req.getLeaveForecast()) {
                    if (l == null) {
                        throw new BusinessValidationException("Leave forecast entry cannot be null.");
                    }
                    if (l.getDate() == null) {
                        throw new BusinessValidationException("Leave forecast date cannot be null.");
                    }
                }
            }

            log.info("SaveTimesheet() invoked for employeeId={}", empId);

            // ---- Existence Check ----
            if (!employeeRepository.existsById(empId)) {
                log.warn("Employee not found: {}", empId);
                throw new ResourceNotFoundException("Employee not found: " + empId);
            }

            Employee employeeRef = em.getReference(Employee.class, empId);

            List<TimesheetDayDTO> tsItems = Optional.ofNullable(req.getTimesheet())
                    .orElseGet(Collections::emptyList);

            List<LeaveDayDTO> lfItems = Optional.ofNullable(req.getLeaveForecast())
                    .orElseGet(Collections::emptyList);

            log.debug("Timesheet items count={}, LeaveForecast items count={}",
                    tsItems.size(), lfItems.size());

            // Deduplicate timesheet by date (last wins)
            Map<LocalDate, TimesheetDayDTO> timeSheetByDate = new LinkedHashMap<>();
            for (TimesheetDayDTO dto : tsItems) {
                timeSheetByDate.put(dto.getDate(), dto);
            }

            // Collect unique leave forecast dates
            Set<LocalDate> lfDates = lfItems.stream()
                    .map(LeaveDayDTO::getDate)
                    .collect(Collectors.toCollection(LinkedHashSet::new));

            // Collect timesheet dates – but split into two sets:
            //   - tsUpsertDates: hours > 0 (we upsert these)
            //   - tsZeroHourDates: hours == 0 (we delete these)
            Set<LocalDate> tsUpsertDates = new LinkedHashSet<>();
            Set<LocalDate> tsZeroHourDates = new LinkedHashSet<>();
            for (TimesheetDayDTO dto : tsItems) {
                if (dto.getHours() != null && dto.getHours().compareTo(BigDecimal.ZERO) == 0) {
                    tsZeroHourDates.add(dto.getDate());
                } else {
                    tsUpsertDates.add(dto.getDate());
                }
            }

            // Affected dates in this request (used for targeted replacement)
            Set<LocalDate> affectedDates = new LinkedHashSet<>();
            affectedDates.addAll(tsUpsertDates);
            affectedDates.addAll(lfDates);
            // Note: we purposely DO NOT add tsZeroHourDates to affectedDates for leave replacement;
            // zero-hour means "delete timesheet", not "timesheet wins".

            LocalDateTime now = LocalDateTime.now();

            // ---- TIMESHEET UPSERT ----
            if (!timeSheetByDate.isEmpty()) {

                // First handle DELETEs for zero-hour timesheet dates
                if (!tsZeroHourDates.isEmpty()) {
                    log.debug("Removing timesheet entries for zero-hour dates count={}", tsZeroHourDates.size());
                    // requires TimesheetRepo.deleteByEmployeeAndWorkDateIn(...)
                    timesheetRepo.deleteByEmployeeAndWorkDateIn(employeeRef, tsZeroHourDates);
                }

                // Now upsert only the > 0 hour entries
                Map<LocalDate, TimesheetDayDTO> upsertMap = new LinkedHashMap<>();
                for (Map.Entry<LocalDate, TimesheetDayDTO> entry : timeSheetByDate.entrySet()) {
                    LocalDate d = entry.getKey();
                    TimesheetDayDTO v = entry.getValue();
                    if (v.getHours() != null && v.getHours().compareTo(BigDecimal.ZERO) > 0) {
                        upsertMap.put(d, v);
                    }
                }

                if (!upsertMap.isEmpty()) {
                    List<LocalDate> reqDates = new ArrayList<>(upsertMap.keySet());

                    Map<LocalDate, Timesheet> existingByDate = timesheetRepo
                            .findByEmployeeAndWorkDateIn(employeeRef, reqDates)
                            .stream()
                            .collect(Collectors.toMap(Timesheet::getWorkDate, t -> t));

                    for (Map.Entry<LocalDate, TimesheetDayDTO> e : upsertMap.entrySet()) {
                        LocalDate date = e.getKey();
                        TimesheetDayDTO dto = e.getValue();
                        Timesheet existing = existingByDate.get(date);

                        if (existing == null) {
                            log.debug("Inserting new timesheet for date={}", date);

                            Timesheet newTs = Timesheet.builder()
                                    .employee(employeeRef)
                                    .workDate(date)
                                    .hoursLogged(dto.getHours())
                                    .build();

                            timesheetRepo.save(newTs);

                        } else {
                            log.debug("Updating existing timesheet for date={}", date);

                            existing.setHoursLogged(dto.getHours());
                            existing.setUpdatedAt(now);
                            timesheetRepo.save(existing);
                        }
                    }

                    // **Conflict rule:** when a timesheet exists for a date, remove any leave on that date
                    if (!tsUpsertDates.isEmpty()) {
                        log.debug("Removing leave forecast for dates with timesheet (conflict resolution), count={}", tsUpsertDates.size());
                        leaveForecastRepository.deleteByEmployeeAndStartDateIn(employeeRef, tsUpsertDates);
                    }
                }
            }

            // ---- LEAVE FORECAST REPLACE ----
            // If lfDates is empty, we do nothing (we don't delete existing leaves implicitly)
            // Enhanced behavior: when leave is in the request, ensure no timesheet remains on those dates unless a timesheet for that date is also provided.
            if (req.getLeaveForecast() != null) {
                // Remove any existing timesheet entries for leave-only dates (dates that are in leave payload but not in timesheet payload with hours > 0)
                Set<LocalDate> leaveOnlyDates = lfDates.stream()
                        .filter(d -> !tsUpsertDates.contains(d))
                        .collect(Collectors.toCollection(LinkedHashSet::new));
                if (!leaveOnlyDates.isEmpty()) {
                    log.debug("Removing timesheet entries for leave-only dates count={}", leaveOnlyDates.size());
                    timesheetRepo.deleteByEmployeeAndWorkDateIn(employeeRef, leaveOnlyDates);
                }

                if (!affectedDates.isEmpty()) {
                    log.debug("Replacing leave forecast entries count={}", affectedDates.size());

                    // Clean slate for affected dates (includes removing any old leave that might conflict)
                    leaveForecastRepository.deleteByEmployeeAndStartDateIn(employeeRef, affectedDates);
                }

                // Insert new leaves only for dates that are NOT present in timesheet payload with hours > 0 (timesheet wins on same-day collisions)
                Set<LocalDate> lfToInsert = lfDates.stream()
                        .filter(d -> !tsUpsertDates.contains(d))
                        .collect(Collectors.toCollection(LinkedHashSet::new));

                if (!lfToInsert.isEmpty()) {
                    List<LeaveForecast> inserts = lfToInsert.stream()
                            .map(d -> LeaveForecast.builder()
                                    .employee(employeeRef)
                                    .startDate(d)
                                    .build())
                            .collect(Collectors.toList());

                    leaveForecastRepository.saveAll(inserts);
                    log.debug("Inserted leave forecast rows count={}", inserts.size());
                }
            } else {
                // If leaveForecast is absent (null), do nothing more: we already removed conflicts for tsDates above.
                log.debug("leaveForecast not present in request; skipped replacement beyond conflict removal.");
            }

            log.info("Timesheet save completed successfully for employeeId={}", empId);

            // Keep your original response contract
            return new SaveTimesheetResponse(empId, "success");

        } catch (BusinessValidationException | ResourceNotFoundException e) {
            // known business errors — log and rethrow to be handled by GlobalExceptionHandler
            log.warn("Business error in SaveTimesheet for employeeId={}: {}", empIdForLog, e.getMessage());
            throw e;
        } catch (DataIntegrityViolationException e) {
            // DB constraint issues
            log.error("Data integrity violation in SaveTimesheet for employeeId={}", empIdForLog, e);
            throw e;
        } catch (TransactionSystemException e) {
            // Transaction failures
            log.error("Transaction error in SaveTimesheet for employeeId={}", empIdForLog, e);
            throw e;
        } catch (Exception e) {
            // Any unexpected runtime error
            log.error("Unexpected error in SaveTimesheet for employeeId={}", empIdForLog, e);
            throw e;
        }
    }

    @Override
    public BigDecimal getTotalForcastedHours(String userId, LocalDate start, LocalDate end) {
        return timesheetRepo.getTotalHoursForMonth(userId, start, end);
    }
}
