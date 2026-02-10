package com.alt.lve_tmst_mgmt.service;

import com.alt.lve_tmst_mgmt.dto.MonthlyEmployeeReportDto;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface ReportService {

    Page<MonthlyEmployeeReportDto> fetchReport(
            String sowId,
            LocalDate monthStart,
            LocalDate monthEnd,
            int page,
            int size
    );
}
