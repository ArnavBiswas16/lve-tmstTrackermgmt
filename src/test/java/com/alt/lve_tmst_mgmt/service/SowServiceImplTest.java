package com.alt.lve_tmst_mgmt.service;

import com.alt.lve_tmst_mgmt.dto.SowDto;
import com.alt.lve_tmst_mgmt.entity.Employee;
import com.alt.lve_tmst_mgmt.entity.Sow;
import com.alt.lve_tmst_mgmt.repository.EmployeeRepository;
import com.alt.lve_tmst_mgmt.repository.SowRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SowServiceImplTest {

    @Mock
    private SowRepository sowRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private SowServiceImpl sowService;

    private Employee manager;
    private Sow sow;

    @BeforeEach
    void setUp() {
        manager = Employee.builder()
                .employeeId("EMP001")
                .build();

        sow = Sow.builder()
                .sowId("SOW001")
                .sowName("Test SOW")
                .manager(manager)
                .build();
    }

    @Test
    void getAllSows_shouldReturnListOfDtos() {
        when(sowRepository.findAll()).thenReturn(List.of(sow));

        List<SowDto> result = sowService.getAllSows();

        assertEquals(1, result.size());
        assertEquals("SOW001", result.get(0).getSowId());
        assertEquals("EMP001", result.get(0).getManagerId());

        verify(sowRepository).findAll();
        verifyNoMoreInteractions(sowRepository, employeeRepository);
    }

    @Test
    void getSowsByManagerId_shouldReturnMatchingSows() {
        when(sowRepository.findByManager_EmployeeId("EMP001"))
                .thenReturn(List.of(sow));

        List<SowDto> result = sowService.getSowsByManagerId("EMP001");

        assertEquals(1, result.size());
        assertEquals("SOW001", result.get(0).getSowId());

        verify(sowRepository).findByManager_EmployeeId("EMP001");
        verifyNoMoreInteractions(sowRepository, employeeRepository);
    }

    @Test
    void createSow_shouldCreateNewSow_whenManagerExists() {
        SowDto request = SowDto.builder()
                .sowId("SOW001")
                .sowName("Test SOW")
                .managerId("EMP001")
                .build();

        when(employeeRepository.findById("EMP001"))
                .thenReturn(Optional.of(manager));

        when(sowRepository.save(any(Sow.class)))
                .thenReturn(sow);

        SowDto result = sowService.createSow(request);

        assertNotNull(result);
        assertEquals("SOW001", result.getSowId());
        assertEquals("EMP001", result.getManagerId());

        verify(employeeRepository).findById("EMP001");
        verify(sowRepository).save(any(Sow.class));
        verifyNoMoreInteractions(sowRepository, employeeRepository);
    }

    @Test
    void createSow_shouldThrowException_whenManagerNotFound() {
        SowDto request = SowDto.builder()
                .sowId("SOW001")
                .sowName("Test SOW")
                .managerId("EMP999")
                .build();

        when(employeeRepository.findById("EMP999"))
                .thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> sowService.createSow(request));

        assertTrue(ex.getMessage().contains("Manager not found"));

        verify(employeeRepository).findById("EMP999");
        verifyNoMoreInteractions(sowRepository, employeeRepository);
    }
}
