package com.alt.lve_tmst_mgmt.service;

import com.alt.lve_tmst_mgmt.dto.MonthlyEmployeeReportDto;
import com.alt.lve_tmst_mgmt.repository.ReportRepo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportServiceImplTest {

    @Mock
    private ReportRepo reportRepository;

    @InjectMocks
    private ReportServiceImpl reportService;

    @Test
    void fetchReport_shouldReturnPageSuccessfully() {
        String sowId = "SOW001";
        LocalDate start = LocalDate.of(2025, 2, 1);
        LocalDate end = LocalDate.of(2025, 2, 28);

        MonthlyEmployeeReportDto dto = mock(MonthlyEmployeeReportDto.class);
        Page<MonthlyEmployeeReportDto> pageResult =
                new PageImpl<>(List.of(dto));

        when(reportRepository.fetchReport(
                eq(sowId),
                eq(start),
                eq(end),
                any(Pageable.class)
        )).thenReturn(pageResult);

        Page<MonthlyEmployeeReportDto> result =
                reportService.fetchReport(sowId, start, end, 0, 10);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(reportRepository, times(1))
                .fetchReport(eq(sowId), eq(start), eq(end), any(Pageable.class));
    }

    @Test
    void fetchReport_shouldThrowException_whenSowIdIsNull() {
        LocalDate start = LocalDate.of(2025, 2, 1);
        LocalDate end = LocalDate.of(2025, 2, 28);

        assertThrows(IllegalArgumentException.class,
                () -> reportService.fetchReport(null, start, end, 0, 10));

        verify(reportRepository, never())
                .fetchReport(any(), any(), any(), any());
    }

    @Test
    void fetchReport_shouldThrowException_whenDatesAreNull() {
        assertThrows(IllegalArgumentException.class,
                () -> reportService.fetchReport("SOW001", null, null, 0, 10));

        verify(reportRepository, never())
                .fetchReport(any(), any(), any(), any());
    }

    @Test
    void fetchReport_shouldThrowException_whenEndBeforeStart() {
        LocalDate start = LocalDate.of(2025, 2, 28);
        LocalDate end = LocalDate.of(2025, 2, 1);

        assertThrows(IllegalArgumentException.class,
                () -> reportService.fetchReport("SOW001", start, end, 0, 10));

        verify(reportRepository, never())
                .fetchReport(any(), any(), any(), any());
    }
}
