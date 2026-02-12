package com.alt.lve_tmst_mgmt.controller;

import com.alt.lve_tmst_mgmt.dto.MonthlyEmployeeReportDto;
import com.alt.lve_tmst_mgmt.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

// Added for logging
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping
public class ReportsController {

    @Autowired
    ReportService reportService;

    @GetMapping("/public/reports/monthly")
    public Page<MonthlyEmployeeReportDto> getMonthlyReport(
            @RequestParam String sowId,
            @RequestParam YearMonth month,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        log.info("GET /public/reports/monthly - Request received for sowId={}, month={}, page={}, size={}",
                sowId, month, page, size);

        LocalDate start = month.atDay(1);
        LocalDate end = month.atEndOfMonth();

        log.info("Computed date range for monthly report: start={}, end={}", start, end);

        Page<MonthlyEmployeeReportDto> report =
                reportService.fetchReport(sowId, start, end, page, size);

        log.info("Monthly report generated for sowId={} with {} records",
                sowId, (report != null ? report.getTotalElements() : 0));

        return report;
    }

}