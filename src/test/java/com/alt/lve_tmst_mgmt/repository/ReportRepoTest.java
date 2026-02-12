package com.alt.lve_tmst_mgmt.repository;

import com.alt.lve_tmst_mgmt.dto.MonthlyEmployeeReportDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportRepoTest {

    @Mock
    private ReportRepo reportRepo;

    @Test
    void fetchReport_shouldReturnPagedResult_withValidDto() {
        String sowId = "SOW123";
        LocalDate monthStart = LocalDate.of(2025, 1, 1);
        LocalDate monthEnd = LocalDate.of(2025, 1, 31);
        Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());

        MonthlyEmployeeReportDto dto = new MonthlyEmployeeReportDto(
                "EMP001",
                "John Doe",
                "john@example.com",
                "ROLE_USER",
                "SOE001",
                "Pune",
                "SOW123",
                Date.valueOf(LocalDate.of(2024, 12, 1)),
                "[]",
                "[]",
                "[]",
                new BigDecimal("40.0"),
                1L,
                0L,
                2L,
                "[]",
                true,
                true,
                false
        );

        Page<MonthlyEmployeeReportDto> page = new PageImpl<>(List.of(dto), pageable, 1);

        when(reportRepo.fetchReport(sowId, monthStart, monthEnd, pageable)).thenReturn(page);

        Page<MonthlyEmployeeReportDto> result = reportRepo.fetchReport(sowId, monthStart, monthEnd, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        MonthlyEmployeeReportDto first = result.getContent().get(0);

        assertEquals("EMP001", first.getEmployeeId());
        assertEquals("John Doe", first.getName());
        assertEquals("john@example.com", first.getEmail());
        assertEquals("ROLE_USER", first.getRole());
        assertEquals("SOE001", first.getSoeId());
        assertEquals("Pune", first.getLocation());
        assertEquals("SOW123", first.getSowId());
        assertEquals(Date.valueOf(LocalDate.of(2024, 12, 1)), first.getAssignmentStartDate());
        assertEquals("[]", first.getTimesheets());
        assertEquals("[]", first.getLeaves());
        assertEquals("[]", first.getHolidays());
        assertEquals(new BigDecimal("40.0"), first.getTotalHours());
        assertEquals(1L, first.getNumberOfLeaves());
        assertEquals(0L, first.getNumberOfHalfDays());
        assertEquals(2L, first.getNumberOfHolidays());
        assertEquals("[]", first.getWeeklyHours());
        assertEquals(true, first.getPtsSaved());
        assertEquals(true, first.getCofyUpdate());
        assertEquals(false, first.getCitiTraining());

        verify(reportRepo).fetchReport(sowId, monthStart, monthEnd, pageable);
        verifyNoMoreInteractions(reportRepo);
    }
}