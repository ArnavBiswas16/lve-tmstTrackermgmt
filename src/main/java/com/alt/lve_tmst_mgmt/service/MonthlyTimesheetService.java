package com.alt.lve_tmst_mgmt.service;


import com.alt.lve_tmst_mgmt.dto.*;
import com.alt.lve_tmst_mgmt.entity.*;
import com.alt.lve_tmst_mgmt.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;

@Service
@RequiredArgsConstructor
public class MonthlyTimesheetService {

    private final WeeklyTimesheetRepository weeklyRepo;
    private final MonthlyComplianceRepository complianceRepo;
    private final EmployeeRepository employeeRepo;

    @Transactional
    public void saveTimesheet(TimesheetRequestDTO request) {

        Employee employee = employeeRepo.findById(request.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        /*
         * ==========================
         * 1️⃣ SAVE WEEKLY TIMESHEET
         * ==========================
         */
        request.getTimeSheet().forEach(week -> {

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

            weeklyRepo.save(weekly);
        });

        /*
         * ==========================
         * 2️⃣ SAVE MONTHLY COMPLIANCE
         * ==========================
         */

        if (request.getPts() != null ||
                request.getCofy() != null ||
                request.getCitiTraining() != null) {

            YearMonth yearMonth = YearMonth.parse(request.getMonth());
            LocalDate monthDate = yearMonth.atDay(1);  // convert to LocalDate

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
                            .build());

            if (request.getPts() != null)
                compliance.setPtsSaved(request.getPts());

            if (request.getCofy() != null)
                compliance.setCofyUpdate(request.getCofy());

            if (request.getCitiTraining() != null)
                compliance.setCitiTraining(request.getCitiTraining());

            complianceRepo.save(compliance);
        }
    }
}