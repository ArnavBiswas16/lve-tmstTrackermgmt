package com.alt.lve_tmst_mgmt.controller;

import com.alt.lve_tmst_mgmt.dto.MonthlyEmployeeReportDto;
import com.alt.lve_tmst_mgmt.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping
public class ReportsController {

    @Autowired
    ReportService reportService;

    @GetMapping("/public/reports/monthly")
    public List<MonthlyEmployeeReportDto> getMonthlyReport(
            @RequestParam String sowId,
            @RequestParam YearMonth month) {
        System.out.println("received");
        LocalDate start = month.atDay(1);
        LocalDate end = month.atEndOfMonth();

        return reportService.fetchReport(sowId, start, end);
    }

}
