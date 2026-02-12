package com.alt.lve_tmst_mgmt.controller;

import com.alt.lve_tmst_mgmt.dto.SaveComplianceRequest;
import com.alt.lve_tmst_mgmt.dto.SaveComplianceResponse;
import com.alt.lve_tmst_mgmt.service.ComplianceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ComplianceControllerTest {

    @Mock
    private ComplianceService complianceService;

    @InjectMocks
    private ComplianceController complianceController;

    private SaveComplianceRequest request;
    private SaveComplianceResponse response;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        request = new SaveComplianceRequest();
        response = new SaveComplianceResponse();
    }

    @Test
    void saveCompliance_shouldReturnResponseEntity() {
        when(complianceService.saveCompliance(request)).thenReturn(response);

        ResponseEntity<SaveComplianceResponse> result = complianceController.saveCompliance(request);

        assertNotNull(result);
        assertEquals(response, result.getBody());
        assertEquals(200, result.getStatusCodeValue());

        verify(complianceService, times(1)).saveCompliance(request);
    }

    @Test
    void getCompliance_shouldReturnResponseEntity() {
        String userId = "USER001";
        String month = "2026-02";

        when(complianceService.getCompliance(userId, month)).thenReturn(response);

        ResponseEntity<SaveComplianceResponse> result = complianceController.getCompliance(userId, month);

        assertNotNull(result);
        assertEquals(response, result.getBody());
        assertEquals(200, result.getStatusCodeValue());

        verify(complianceService, times(1)).getCompliance(userId, month);
    }
}
