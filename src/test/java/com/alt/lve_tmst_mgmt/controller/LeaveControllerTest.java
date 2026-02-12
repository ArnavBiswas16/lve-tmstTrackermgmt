package com.alt.lve_tmst_mgmt.controller;

import com.alt.lve_tmst_mgmt.dto.LeaveForecastRequest;
import com.alt.lve_tmst_mgmt.dto.LeaveForecastResponse;
import com.alt.lve_tmst_mgmt.service.LeaveService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LeaveControllerTest {

    @Mock
    private LeaveService leaveService;

    @InjectMocks
    private LeaveController leaveController;

    private LeaveForecastRequest request;
    private LeaveForecastResponse response;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        request = LeaveForecastRequest.builder()
                .employeeId("EMP001")
                .leaveTypeId(1)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(2))
                .comments("Personal leave")
                .build();

        response = new LeaveForecastResponse();
        response.setLeaveId(100);
    }

    @Test
    void applyLeave_shouldReturnCreatedResponse() {
        when(leaveService.applyLeave(request)).thenReturn(response);

        ResponseEntity<LeaveForecastResponse> result = leaveController.applyLeave(request);

        assertNotNull(result);
        assertEquals(201, result.getStatusCodeValue());
        assertEquals(response, result.getBody());
        assertEquals("/api/leaves/100", result.getHeaders().getLocation().getPath());

        verify(leaveService, times(1)).applyLeave(request);
    }
}
