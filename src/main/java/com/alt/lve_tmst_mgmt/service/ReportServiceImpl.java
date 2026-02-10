
package com.alt.lve_tmst_mgmt.service;

import com.alt.lve_tmst_mgmt.dto.MonthlyEmployeeReportDto;
import com.alt.lve_tmst_mgmt.repository.ReportRepo;
import com.alt.lve_tmst_mgmt.service.ReportService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ReportServiceImpl implements ReportService {

    private final ReportRepo reportRepository;

    public ReportServiceImpl(ReportRepo reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public List<MonthlyEmployeeReportDto> fetchReport(
            String sowId,
            LocalDate monthStart,
            LocalDate monthEnd) {

        validateInputs(sowId, monthStart, monthEnd);

        return reportRepository.fetchReport(
                sowId,
                monthStart,
                monthEnd
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
