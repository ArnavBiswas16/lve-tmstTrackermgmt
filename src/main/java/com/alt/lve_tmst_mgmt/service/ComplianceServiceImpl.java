package com.alt.lve_tmst_mgmt.service;

import com.alt.lve_tmst_mgmt.Exceptions.ResourceNotFoundException;
import com.alt.lve_tmst_mgmt.dto.SaveComplianceRequest;
import com.alt.lve_tmst_mgmt.dto.SaveComplianceResponse;
import com.alt.lve_tmst_mgmt.repository.EmployeeRepository;
import com.alt.lve_tmst_mgmt.repository.MonthlyComplianceRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;

@Service
@RequiredArgsConstructor
public class ComplianceServiceImpl implements ComplianceService {

    private final EmployeeRepository employeeRepository;
    private final MonthlyComplianceRepository mcRepository;

    @Override
    @Transactional
    public SaveComplianceResponse saveCompliance(SaveComplianceRequest req) {
        final String userId = req.getUserId();

        // Clean 404 if employee doesn't exist (FK would otherwise throw)
        if (!employeeRepository.existsById(userId)) {
            throw new ResourceNotFoundException("userId not found: " + userId);
        }

        // Normalize YYYY-MM -> first day of month
        YearMonth ym = YearMonth.parse(req.getMonth()); // throws if invalid (we already validated)
        LocalDate monthDate = ym.atDay(1);

        // Idempotent UPSERT for (employee_id, month)
        mcRepository.upsert(
                userId,
                monthDate,
                req.getPts(),
                req.getCofy(),
                req.getCitiTraining()
        );

        // Echo back the requested shape
        return SaveComplianceResponse.builder()
                .userId(userId)
                .month(req.getMonth())
                .pts(req.getPts())
                .cofy(req.getCofy())
                .citiTraining(req.getCitiTraining())
                .build();
    }
}