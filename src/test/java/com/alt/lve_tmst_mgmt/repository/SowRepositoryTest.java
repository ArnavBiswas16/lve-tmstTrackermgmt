package com.alt.lve_tmst_mgmt.repository;

import com.alt.lve_tmst_mgmt.entity.Employee;
import com.alt.lve_tmst_mgmt.entity.Sow;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SowRepositoryTest {

    @Mock
    private SowRepository sowRepository;

    @Test
    void findByManagerEmployeeId_shouldReturnSows() {
        String managerId = "MGR001";

        Employee manager = Employee.builder()
                .employeeId(managerId)
                .name("Manager One")
                .build();

        Sow sow1 = Sow.builder()
                .sowId("SOW001")
                .sowName("Project Alpha")
                .manager(manager)
                .build();

        Sow sow2 = Sow.builder()
                .sowId("SOW002")
                .sowName("Project Beta")
                .manager(manager)
                .build();

        when(sowRepository.findByManager_EmployeeId(managerId))
                .thenReturn(List.of(sow1, sow2));

        List<Sow> result = sowRepository.findByManager_EmployeeId(managerId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("SOW001", result.get(0).getSowId());
        assertEquals("SOW002", result.get(1).getSowId());
        assertEquals(managerId, result.get(0).getManager().getEmployeeId());

        verify(sowRepository).findByManager_EmployeeId(managerId);
        verifyNoMoreInteractions(sowRepository);
    }
}
