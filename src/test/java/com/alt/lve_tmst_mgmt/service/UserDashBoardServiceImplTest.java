package com.alt.lve_tmst_mgmt.service;

import com.alt.lve_tmst_mgmt.dto.UserDashBoardDto;
import com.alt.lve_tmst_mgmt.repository.UserDashBoardRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserDashBoardServiceImplTest {

    private UserDashBoardRepo userDashBoardRepo;
    private UserDashBoardServiceImpl userDashBoardService;

    @BeforeEach
    void setUp() {
        userDashBoardRepo = Mockito.mock(UserDashBoardRepo.class);
        userDashBoardService = new UserDashBoardServiceImpl(userDashBoardRepo);
    }

    @Test
    void fetchDashBoard_success() {
        // Prepare inputs
        String employeeId = "634";
        String sowId = "SOW123";
        LocalDate start = LocalDate.of(2026, 2, 1);
        LocalDate end = LocalDate.of(2026, 2, 28);

        // Mock repository response
        UserDashBoardDto mockDto = new UserDashBoardDto(
                employeeId,
                sowId,
                "10 hours",
                "2 leaves",
                "1 holiday"
        );
        when(userDashBoardRepo.fetchDashBoard(eq(employeeId), eq(start), eq(end)))
                .thenReturn(mockDto);

        // Call service method
        UserDashBoardDto result = userDashBoardService.fetchDashBoard(employeeId, start, end);

        // Verify
        assertNotNull(result);
        assertEquals(employeeId, result.getEmployeeId());
        assertEquals(sowId, result.getSowId());
        assertEquals("10 hours", result.getTimesheets());
        assertEquals("2 leaves", result.getLeaves());
        assertEquals("1 holiday", result.getHolidays());

        // Verify repository call
        verify(userDashBoardRepo, times(1)).fetchDashBoard(eq(employeeId), eq(start), eq(end));
    }

    @Test
    void fetchDashBoard_invalidEmployeeId_throwsException() {
        LocalDate start = LocalDate.of(2026, 2, 1);
        LocalDate end = LocalDate.of(2026, 2, 28);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                userDashBoardService.fetchDashBoard(" ", start, end));

        assertEquals("sowId must not be null or empty", exception.getMessage());
    }

    @Test
    void fetchDashBoard_nullDates_throwsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                userDashBoardService.fetchDashBoard("634", null, LocalDate.of(2026, 2, 28)));

        assertEquals("Month start/end dates must not be null", exception.getMessage());
    }

    @Test
    void fetchDashBoard_endBeforeStart_throwsException() {
        LocalDate start = LocalDate.of(2026, 2, 28);
        LocalDate end = LocalDate.of(2026, 2, 1);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                userDashBoardService.fetchDashBoard("634", start, end));

        assertEquals("Month end date cannot be before start date", exception.getMessage());
    }
}