package com.alt.lve_tmst_mgmt.controller;

import com.alt.lve_tmst_mgmt.dto.MonthlyEmployeeReportDto;
import com.alt.lve_tmst_mgmt.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.YearMonth;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReportsControllerTest {

    @Mock
    private ReportService reportService;

    @InjectMocks
    private ReportsController reportsController;

    private MonthlyEmployeeReportDto sampleReport;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleReport = new MonthlyEmployeeReportDto();
        // Set fields if needed, e.g., sampleReport.setEmployeeName("John Doe");
    }

    @Test
    void getMonthlyReport_shouldReturnPagedReports() {
        String sowId = "SOW123";
        YearMonth month = YearMonth.of(2026, 2);
        int page = 0;
        int size = 10;

        LocalDate start = month.atDay(1);
        LocalDate end = month.atEndOfMonth();

        Page<MonthlyEmployeeReportDto> mockPage = new PageImpl<>(List.of(sampleReport));

        when(reportService.fetchReport(sowId, start, end, page, size)).thenReturn(mockPage);

        Page<MonthlyEmployeeReportDto> result = reportsController.getMonthlyReport(sowId, month, page, size);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(sampleReport, result.getContent().get(0));

        verify(reportService, times(1)).fetchReport(sowId, start, end, page, size);
    }
}
