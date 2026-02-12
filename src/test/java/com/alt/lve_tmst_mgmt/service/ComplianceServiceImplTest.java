package com.alt.lve_tmst_mgmt.service;

import com.alt.lve_tmst_mgmt.Exceptions.ResourceNotFoundException;
import com.alt.lve_tmst_mgmt.dto.ComplianceId;
import com.alt.lve_tmst_mgmt.dto.SaveComplianceRequest;
import com.alt.lve_tmst_mgmt.dto.SaveComplianceResponse;
import com.alt.lve_tmst_mgmt.entity.MonthlyCompliance;
import com.alt.lve_tmst_mgmt.repository.EmployeeRepository;
import com.alt.lve_tmst_mgmt.repository.MonthlyComplianceRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ComplianceServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private MonthlyComplianceRepository mcRepository;

    @InjectMocks
    private ComplianceServiceImpl complianceService;

    private SaveComplianceRequest request;

    @BeforeEach
    void setup() {
        request = new SaveComplianceRequest();
        request.setUserId("EMP001");
        request.setMonth("2025-02");
        request.setPts(true);
        request.setCofy(false);
        request.setCitiTraining(true);
    }

    @Test
    void saveCompliance_shouldSaveSuccessfully() {
        when(employeeRepository.existsById("EMP001")).thenReturn(true);

        SaveComplianceResponse response = complianceService.saveCompliance(request);

        assertNotNull(response);
        assertEquals("EMP001", response.getUserId());
        assertEquals("2025-02", response.getMonth());
        assertTrue(response.isPts());
        assertFalse(response.isCofy());
        assertTrue(response.isCitiTraining());

        verify(mcRepository, times(1))
                .upsert(eq("EMP001"),
                        eq(LocalDate.of(2025, 2, 1)),
                        eq(true),
                        eq(false),
                        eq(true));
    }

    @Test
    void saveCompliance_shouldThrowException_whenEmployeeNotFound() {
        when(employeeRepository.existsById("EMP001")).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> complianceService.saveCompliance(request));

        verify(mcRepository, never())
                .upsert(any(), any(), anyBoolean(), anyBoolean(), anyBoolean());
    }

    @Test
    void getCompliance_shouldReturnComplianceSuccessfully() {
        when(employeeRepository.existsById("EMP001")).thenReturn(true);

        MonthlyCompliance mockEntity = mock(MonthlyCompliance.class);
        when(mockEntity.isPtsSaved()).thenReturn(true);
        when(mockEntity.isCofyUpdate()).thenReturn(false);
        when(mockEntity.isCitiTraining()).thenReturn(true);

        when(mcRepository.findById(any(ComplianceId.class)))
                .thenReturn(Optional.of(mockEntity));

        SaveComplianceResponse response =
                complianceService.getCompliance("EMP001", "2025-02");

        assertNotNull(response);
        assertEquals("EMP001", response.getUserId());
        assertEquals("2025-02", response.getMonth());
        assertTrue(response.isPts());
        assertFalse(response.isCofy());
        assertTrue(response.isCitiTraining());
    }

    @Test
    void getCompliance_shouldThrowException_whenEmployeeNotFound() {
        when(employeeRepository.existsById("EMP001")).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> complianceService.getCompliance("EMP001", "2025-02"));

        verify(mcRepository, never()).findById(any());
    }

    @Test
    void getCompliance_shouldThrowException_whenComplianceNotFound() {
        when(employeeRepository.existsById("EMP001")).thenReturn(true);

        when(mcRepository.findById(any(ComplianceId.class)))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> complianceService.getCompliance("EMP001", "2025-02"));
    }
}
