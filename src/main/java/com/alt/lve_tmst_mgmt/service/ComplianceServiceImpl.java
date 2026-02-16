package com.alt.lve_tmst_mgmt.service;

import com.alt.lve_tmst_mgmt.Exceptions.ResourceNotFoundException;
import com.alt.lve_tmst_mgmt.dto.ComplianceId;
import com.alt.lve_tmst_mgmt.dto.SaveComplianceRequest;
import com.alt.lve_tmst_mgmt.dto.SaveComplianceResponse;
import com.alt.lve_tmst_mgmt.repository.EmployeeRepository;
import com.alt.lve_tmst_mgmt.repository.MonthlyComplianceRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;

@Slf4j
@Service
@RequiredArgsConstructor
public class ComplianceServiceImpl implements ComplianceService {

    private final EmployeeRepository employeeRepository;
    private final MonthlyComplianceRepository mcRepository;

    @Override
    @Transactional
    public SaveComplianceResponse saveCompliance(SaveComplianceRequest req) {

        final String userId = req.getUserId();
        log.info("saveCompliance() invoked for userId={} month={} (pts={}, cofy={}, citiTraining={})",
                userId, req.getMonth(), req.getPts(), req.getCofy(), req.getCitiTraining());

        if (!employeeRepository.existsById(userId)) {
            log.warn("saveCompliance() - userId not found: {}", userId);
            throw new ResourceNotFoundException("userId not found: " + userId);
        }

        YearMonth ym = YearMonth.parse(req.getMonth());
        LocalDate monthDate = ym.atDay(1);
        log.debug("saveCompliance() - Parsed YearMonth={}, computed monthDate={}", ym, monthDate);

        mcRepository.upsert(
                userId,
                monthDate,
                req.getPts(),
                req.getCofy(),
                req.getCitiTraining()
        );
        log.info("saveCompliance() - Upserted compliance for userId={} monthDate={}", userId, monthDate);

        SaveComplianceResponse response = SaveComplianceResponse.builder()
                .userId(userId)
                .month(req.getMonth())
                .pts(req.getPts())
                .cofy(req.getCofy())
                .citiTraining(req.getCitiTraining())
                .build();

        log.info("saveCompliance() - Returning response for userId={} month={}", userId, req.getMonth());
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public SaveComplianceResponse getCompliance(String userId, String month) {

        log.info("getCompliance() invoked for userId={} month={}", userId, month);

        if (!employeeRepository.existsById(userId)) {
            log.warn("getCompliance() - userId not found: {}", userId);
            throw new ResourceNotFoundException("userId not found: " + userId);
        }

        YearMonth ym = YearMonth.parse(month);
        LocalDate monthDate = ym.atDay(1);
        log.debug("getCompliance() - Parsed YearMonth={}, computed monthDate={}", ym, monthDate);

        ComplianceId id = new ComplianceId(userId, monthDate);

        var compliance = mcRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Compliance not found for userId: " + userId + " and month: " + month));
        log.info("getCompliance() - Found compliance record for userId={} monthDate={}", userId, monthDate);

        SaveComplianceResponse response = SaveComplianceResponse.builder()
                .userId(userId)
                .month(month)
                .pts(compliance.isPtsSaved())
                .cofy(compliance.isCofyUpdate())
                .citiTraining(compliance.isCitiTraining())
                .build();

        log.info("getCompliance() - Returning response for userId={} month={}", userId, month);
        return response;
    }
}