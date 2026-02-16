package com.alt.lve_tmst_mgmt.service;

import com.alt.lve_tmst_mgmt.dto.LeaveForecastRequest;
import com.alt.lve_tmst_mgmt.dto.LeaveForecastResponse;
import com.alt.lve_tmst_mgmt.entity.Employee;
import com.alt.lve_tmst_mgmt.entity.LeaveForecast;
import com.alt.lve_tmst_mgmt.repository.EmployeeRepository;
import com.alt.lve_tmst_mgmt.repository.LeaveForecastRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LeaveServiceTest {

    @Mock
    private EmployeeRepository employeeRepo;

    @Mock
    private LeaveForecastRepo leaveForecastRepo;

    @InjectMocks
    private LeaveService leaveService;

    private Employee mockEmployee;
    private LeaveForecastRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockEmployee = Employee.builder()
                .employeeId("EMP001")
                .name("John Doe")
                .email("john@example.com")
                .build();

        request = LeaveForecastRequest.builder()
                .employeeId("EMP001")
                .leaveTypeId(1)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(2))
                .comments("Personal leave")
                .build();
    }

    @Test
    void applyLeave_shouldReturnLeaveForecastResponse() {
        LeaveForecast savedLeave = LeaveForecast.builder()
                .leaveId(100)
                .employee(mockEmployee)
                .build();

        when(employeeRepo.findById("EMP001")).thenReturn(Optional.of(mockEmployee));
        when(leaveForecastRepo.save(any(LeaveForecast.class))).thenReturn(savedLeave);

        LeaveForecastResponse response = leaveService.applyLeave(request);

        assertNotNull(response);
        assertEquals(100, response.getLeaveId());
        assertEquals("EMP001", response.getEmployeeId());

        verify(employeeRepo, times(1)).findById("EMP001");
        verify(leaveForecastRepo, times(1)).save(any(LeaveForecast.class));
    }

    @Test
    void applyLeave_shouldThrowExceptionWhenEmployeeNotFound() {
        when(employeeRepo.findById("EMP001")).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> leaveService.applyLeave(request)
        );

        assertEquals("Employee not found: EMP001", exception.getMessage());

        verify(employeeRepo, times(1)).findById("EMP001");
        verify(leaveForecastRepo, never()).save(any());
    }
}
