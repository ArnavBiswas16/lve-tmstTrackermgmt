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

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.YearMonth;
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

        // Initialize a fully populated DTO
        sampleReport = new MonthlyEmployeeReportDto(
                "EMP001",                      // employeeId
                "John Doe",                     // name
                "john.doe@test.com",            // email
                "ROLE_USER",                    // role
                "SOE123",                       // soeId
                "New York",                     // location
                "SOW123",                       // sowId
                Date.valueOf(LocalDate.of(2026, 2, 1)), // assignmentStartDate
                "[{\"day\":1,\"hours\":8}]",    // timesheets
                "[{\"day\":2,\"hours\":8}]",    // leaves
                "[{\"day\":3,\"hours\":8}]",    // holidays
                BigDecimal.valueOf(160),        // totalHours
                2L,                             // numberOfLeaves
                1L,                             // numberOfHalfDays
                1L,                             // numberOfHolidays
                "[{\"week\":1,\"hours\":40}]",  // weeklyHours
                true,                           // ptsSaved
                false,                          // cofyUpdate
                true                            // citiTraining
        );
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

        MonthlyEmployeeReportDto dto = result.getContent().get(0);
        assertEquals("EMP001", dto.getEmployeeId());
        assertEquals("John Doe", dto.getName());
        assertEquals("john.doe@test.com", dto.getEmail());
        assertEquals("ROLE_USER", dto.getRole());
        assertEquals("SOW123", dto.getSowId());
        assertEquals(BigDecimal.valueOf(160), dto.getTotalHours());
        assertTrue(dto.getPtsSaved());
        assertFalse(dto.getCofyUpdate());
        assertTrue(dto.getCitiTraining());

        verify(reportService, times(1)).fetchReport(sowId, start, end, page, size);
    }
}