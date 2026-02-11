package com.alt.lve_tmst_mgmt.service;
import com.alt.lve_tmst_mgmt.Exceptions.ResourceNotFoundException;
import com.alt.lve_tmst_mgmt.dto.ComplianceId;
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


        if (!employeeRepository.existsById(userId)) {
            throw new ResourceNotFoundException("userId not found: " + userId);
        }


        YearMonth ym = YearMonth.parse(req.getMonth());
        LocalDate monthDate = ym.atDay(1);


        mcRepository.upsert(
                userId,
                monthDate,
                req.getPts(),
                req.getCofy(),
                req.getCitiTraining()
        );

        return SaveComplianceResponse.builder()
                .userId(userId)
                .month(req.getMonth())
                .pts(req.getPts())
                .cofy(req.getCofy())
                .citiTraining(req.getCitiTraining())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public SaveComplianceResponse getCompliance(String userId, String month) {


        if (!employeeRepository.existsById(userId)) {
            throw new ResourceNotFoundException("userId not found: " + userId);
        }


        YearMonth ym = YearMonth.parse(month);
        LocalDate monthDate = ym.atDay(1);

        ComplianceId id = new ComplianceId(userId, monthDate);

        var compliance = mcRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Compliance not found for userId: " + userId + " and month: " + month));

        return SaveComplianceResponse.builder()
                .userId(userId)
                .month(month)
                .pts(compliance.isPtsSaved())
                .cofy(compliance.isCofyUpdate())
                .citiTraining(compliance.isCitiTraining())
                .build();
    }
}
