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

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TimesheetServiceImpl implements TimesheetService {

    private static final Logger log = LoggerFactory.getLogger(TimesheetServiceImpl.class);

    @Autowired
    private TimesheetRepo timesheetRepo;

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
    @Transactional
    public SaveTimesheetResponse save(SaveTimesheetRequest req) {

        String empId = req.getEmployeeId();
        log.info("SaveTimesheet() invoked for employeeId={}", empId);

        if (!employeeRepository.existsById(empId)) {
            log.warn("Employee not found: {}", empId);
            throw new EntityNotFoundException("Employee not found: " + empId);
        }

        Employee employeeRef = em.getReference(Employee.class, empId);

        List<TimesheetDayDTO> tsItems = Optional.ofNullable(req.getTimesheet())
                .orElseGet(Collections::emptyList);

        List<LeaveDayDTO> lfItems = Optional.ofNullable(req.getLeaveForecast())
                .orElseGet(Collections::emptyList);

        log.debug("Timesheet items count={}, LeaveForecast items count={}",
                tsItems.size(), lfItems.size());

        // Deduplicate by date
        Map<LocalDate, TimesheetDayDTO> timeSheetByDate = new LinkedHashMap<>();
        for (TimesheetDayDTO dto : tsItems) {
            timeSheetByDate.put(dto.getDate(), dto);
        }

        Set<LocalDate> lfDates = lfItems.stream()
                .map(LeaveDayDTO::getDate)
                .collect(Collectors.toSet());

        LocalDateTime now = LocalDateTime.now();

        // --- TIMESHEET UPSERT ---
        if (!timeSheetByDate.isEmpty()) {

            List<LocalDate> reqDates = new ArrayList<>(timeSheetByDate.keySet());

            Map<LocalDate, Timesheet> existingByDate = timesheetRepo
                    .findByEmployeeAndWorkDateIn(employeeRef, reqDates)
                    .stream()
                    .collect(Collectors.toMap(Timesheet::getWorkDate, t -> t));

            for (Map.Entry<LocalDate, TimesheetDayDTO> e : timeSheetByDate.entrySet()) {

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
        }

        // --- LEAVE FORECAST REPLACE ---
        if (!lfDates.isEmpty()) {

            log.debug("Replacing leave forecast entries count={}", lfDates.size());

            leaveForecastRepository.deleteByEmployeeAndStartDateIn(employeeRef, lfDates);

            List<LeaveForecast> inserts = lfDates.stream()
                    .map(d -> LeaveForecast.builder()
                            .employee(employeeRef)
                            .startDate(d)
                            .build())
                    .collect(Collectors.toList());

            leaveForecastRepository.saveAll(inserts);
        }

        log.info("Timesheet save completed successfully for employeeId={}", empId);

        return new SaveTimesheetResponse(empId, "success");
    }

    @Override
    public BigDecimal getTotalForcastedHours(String userId, LocalDate start, LocalDate end) {


        return timesheetRepo.getTotalHoursForMonth(userId, start,end);
    }
}