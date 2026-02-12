/*
package com.alt.lve_tmst_mgmt.repository;

import com.alt.lve_tmst_mgmt.dto.ComplianceId;
import com.alt.lve_tmst_mgmt.entity.MonthlyCompliance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Answers;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MonthlyComplianceRepositoryTest {

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private MonthlyComplianceRepository monthlyComplianceRepository;

    @Captor
    private ArgumentCaptor<MonthlyCompliance> complianceCaptor;

    private String employeeId;
    private LocalDate month;

    @BeforeEach
    void setUp() {
        employeeId = "EMP001";
        month = LocalDate.of(2025, 1, 1);
    }

    @Test
    void upsert_shouldCreateNewRecord_whenNotExists() {
        ComplianceId id = new ComplianceId(employeeId, month);

        doReturn(Optional.empty())
                .when(monthlyComplianceRepository).findById(id);

        MonthlyCompliance savedEntity = MonthlyCompliance.builder()
                .id(id)
                .ptsSaved(true)
                .cofyUpdate(false)
                .citiTraining(true)
                .build();

        doReturn(savedEntity)
                .when(monthlyComplianceRepository).save(any(MonthlyCompliance.class));

        MonthlyCompliance result = monthlyComplianceRepository.upsert(
                employeeId, month, true, false, true);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertTrue(result.isPtsSaved());
        assertFalse(result.isCofyUpdate());
        assertTrue(result.isCitiTraining());

        verify(monthlyComplianceRepository).findById(id);
        verify(monthlyComplianceRepository).save(complianceCaptor.capture());
        MonthlyCompliance captured = complianceCaptor.getValue();
        assertEquals(id, captured.getId());
        assertTrue(captured.isPtsSaved());
        assertFalse(captured.isCofyUpdate());
        assertTrue(captured.isCitiTraining());

        verifyNoMoreInteractions(monthlyComplianceRepository);
    }

    @Test
    void upsert_shouldUpdateExistingRecord_whenExists() {
        ComplianceId id = new ComplianceId(employeeId, month);

        MonthlyCompliance existing = MonthlyCompliance.builder()
                .id(id)
                .ptsSaved(false)
                .cofyUpdate(false)
                .citiTraining(false)
                .build();

        doReturn(Optional.of(existing))
                .when(monthlyComplianceRepository).findById(id);

        doReturn(existing)
                .when(monthlyComplianceRepository).save(existing);

        MonthlyCompliance result = monthlyComplianceRepository.upsert(
                employeeId, month, true, true, false);

        assertNotNull(result);
        assertTrue(result.isPtsSaved());
        assertTrue(result.isCofyUpdate());
        assertFalse(result.isCitiTraining());

        verify(monthlyComplianceRepository).findById(id);
        verify(monthlyComplianceRepository).save(existing);

        verifyNoMoreInteractions(monthlyComplianceRepository);
    }
}*/
