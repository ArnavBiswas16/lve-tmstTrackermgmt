package com.alt.lve_tmst_mgmt.service;

import com.alt.lve_tmst_mgmt.dto.MonthlyEmployeeReportDto;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {

    List<MonthlyEmployeeReportDto> fetchReport(String sowId, LocalDate start, LocalDate end);
}
