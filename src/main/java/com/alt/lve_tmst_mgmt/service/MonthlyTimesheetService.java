package com.alt.lve_tmst_mgmt.service;
import com.alt.lve_tmst_mgmt.dto.*;
import com.alt.lve_tmst_mgmt.entity.*;
import com.alt.lve_tmst_mgmt.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MonthlyTimesheetService {

    private final WeeklyTimesheetRepository weeklyRepo;
    private final MonthlyComplianceRepository complianceRepo;
    private final EmployeeRepository employeeRepo;

    @Transactional
    public WeeklyTimesheetResponseDTO saveTimesheet(WeeklyTimesheetRequestDTO request) {

        Employee employee = employeeRepo.findById(request.getEmployeeId())
                .orElseThrow(() ->
                        new RuntimeException("Employee not found with id: "
                                + request.getEmployeeId()));

        List<WeeklyEntryDTO> savedWeeklyEntries = new ArrayList<>();

        if (request.getTimeSheet() != null) {

            for (WeeklyEntryDTO week : request.getTimeSheet()) {

                WeeklyTimesheet weekly = weeklyRepo
                        .findByEmployeeEmployeeIdAndWeekStartDate(
                                request.getEmployeeId(),
                                week.getWeekStartDate()
                        )
                        .orElse(WeeklyTimesheet.builder()
                                .employee(employee)
                                .weekStartDate(week.getWeekStartDate())
                                .build());

                weekly.setWeekEndDate(week.getWeekEndDate());
                weekly.setTotalHours(week.getTotalHours());
                weekly.setExpectedHours(BigDecimal.valueOf(40.00));

                WeeklyTimesheet savedWeekly = weeklyRepo.save(weekly);

                savedWeeklyEntries.add(
                        WeeklyEntryDTO.builder()
                                .weekStartDate(savedWeekly.getWeekStartDate())
                                .weekEndDate(savedWeekly.getWeekEndDate())
                                .totalHours(savedWeekly.getTotalHours())
                                .build()
                );
            }
        }


        Boolean pts = null;
        Boolean cofy = null;
        Boolean citiTraining = null;
        Boolean complianceSubmit = null;

        if (request.getMonth() != null &&
                (request.getPts() != null ||
                        request.getCofy() != null ||
                        request.getCitiTraining() != null ||
                        request.getComplianceSubmit() != null)) {

            YearMonth yearMonth = YearMonth.parse(request.getMonth());
            LocalDate monthDate = yearMonth.atDay(1);

            ComplianceId id = new ComplianceId(
                    request.getEmployeeId(),
                    monthDate
            );

            MonthlyCompliance compliance = complianceRepo
                    .findById(id)
                    .orElse(MonthlyCompliance.builder()
                            .id(id)
                            .ptsSaved(false)
                            .cofyUpdate(false)
                            .citiTraining(false)
                            .complianceSubmit(false)
                            .build());

            if (request.getPts() != null)
                compliance.setPtsSaved(request.getPts());

            if (request.getCofy() != null)
                compliance.setCofyUpdate(request.getCofy());

            if (request.getCitiTraining() != null)
                compliance.setCitiTraining(request.getCitiTraining());

            if (request.getComplianceSubmit() != null)
                compliance.setComplianceSubmit(request.getComplianceSubmit());

            MonthlyCompliance savedCompliance = complianceRepo.save(compliance);

            pts = savedCompliance.isPtsSaved();
            cofy = savedCompliance.isCofyUpdate();
            citiTraining = savedCompliance.isCitiTraining();
            complianceSubmit = savedCompliance.getComplianceSubmit();
        }

        return WeeklyTimesheetResponseDTO.builder()
                .employeeId(employee.getEmployeeId())
                .employeeName(employee.getName())
                .timeSheet(savedWeeklyEntries)
                .pts(pts)
                .cofy(cofy)
                .citiTraining(citiTraining)
                .complianceSubmit(complianceSubmit)
                .build();
    }
    @Transactional(readOnly = true)
    public WeeklyTimesheetResponseDTO getMonthlyData(String employeeId, String month) {

        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() ->
                        new RuntimeException("Employee not found with id: " + employeeId));

        YearMonth ym = YearMonth.parse(month);
        LocalDate startOfMonth = ym.atDay(1);
        LocalDate endOfMonth = ym.atEndOfMonth();
        LocalDate monthDate = ym.atDay(1);

        List<WeeklyTimesheet> weeklyList =
                weeklyRepo.findByEmployeeEmployeeIdAndWeekStartDateBetween(
                        employeeId, startOfMonth, endOfMonth);

        List<WeeklyEntryDTO> weeklyDTO = weeklyList.stream()
                .map(w -> WeeklyEntryDTO.builder()
                        .weekStartDate(w.getWeekStartDate())
                        .weekEndDate(w.getWeekEndDate())
                        .totalHours(w.getTotalHours())
                        .build())
                .toList();


        ComplianceId id = new ComplianceId(employeeId, monthDate);

        MonthlyCompliance compliance =
                complianceRepo.findById(id).orElse(null);

        Boolean pts = null;
        Boolean cofy = null;
        Boolean citiTraining = null;
        Boolean complianceSubmit = null;

        if (compliance != null) {
            pts = compliance.isPtsSaved();
            cofy = compliance.isCofyUpdate();
            citiTraining = compliance.isCitiTraining();
            complianceSubmit = compliance.getComplianceSubmit();
        }

        return WeeklyTimesheetResponseDTO.builder()
                .employeeId(employee.getEmployeeId())
                .employeeName(employee.getName())
                .timeSheet(weeklyDTO)
                .pts(pts)
                .cofy(cofy)
                .citiTraining(citiTraining)
                .complianceSubmit(complianceSubmit)
                .build();
    }

}
