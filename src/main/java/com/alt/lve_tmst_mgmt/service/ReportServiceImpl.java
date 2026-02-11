package com.alt.lve_tmst_mgmt.service;

import com.alt.lve_tmst_mgmt.dto.MonthlyEmployeeReportDto;
import com.alt.lve_tmst_mgmt.repository.ReportRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional(readOnly = true)
public class ReportServiceImpl implements ReportService {

    private final ReportRepo reportRepository;

    public ReportServiceImpl(ReportRepo reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public Page<MonthlyEmployeeReportDto> fetchReport(
            String sowId,
            LocalDate monthStart,
            LocalDate monthEnd,
            int page,
            int size
    ) {

        validateInputs(sowId, monthStart, monthEnd);

        Pageable pageable = PageRequest.of(
                page,
                size
        );

        return reportRepository.fetchReport(
                sowId,
                monthStart,
                monthEnd,
                pageable
        );
    }

    private void validateInputs(String sowId, LocalDate start, LocalDate end) {
        if (sowId == null || sowId.isBlank()) {
            throw new IllegalArgumentException("sowId must not be null or empty");
        }
        if (start == null || end == null) {
            throw new IllegalArgumentException("Month start/end dates must not be null");
        }
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("Month end date cannot be before start date");
        }
    }
}
