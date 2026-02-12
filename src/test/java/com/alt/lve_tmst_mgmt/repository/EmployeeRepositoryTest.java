package com.alt.lve_tmst_mgmt.repository;

import com.alt.lve_tmst_mgmt.entity.Employee;
import com.alt.lve_tmst_mgmt.entity.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // manages mocks lifecycle
class EmployeeRepositoryTest {

    @Mock
    private EmployeeRepository employeeRepository;

    private Employee sampleEmployee;
    private Location sampleLocation;

    @BeforeEach
    void setUp() {
        sampleLocation = Location.builder()
                .locationId(1)          // Integer, not "LOC001"
                .country("USA")
                .city("New York")
                .build();

        sampleEmployee = Employee.builder()
                .employeeId("EMP001")
                .name("John Doe")
                .email("john@example.com")
                .password("hashedPassword")
                .role("ROLE_USER")
                .location(sampleLocation)
                .isActive(true)
                .build();
    }

    @Test
    void findByEmail_shouldReturnEmployee_whenExists() {
        when(employeeRepository.findByEmail("john@example.com"))
                .thenReturn(Optional.of(sampleEmployee));

        Optional<Employee> result = employeeRepository.findByEmail("john@example.com");

        assertTrue(result.isPresent());
        assertEquals("EMP001", result.get().getEmployeeId());
        assertEquals("John Doe", result.get().getName());
        assertEquals("ROLE_USER", result.get().getRole());
        assertEquals(1, result.get().getLocation().getLocationId()); // Integer 1

        verify(employeeRepository, times(1)).findByEmail("john@example.com");
    }

    @Test
    void findByEmail_shouldReturnEmpty_whenNotExists() {
        when(employeeRepository.findByEmail("nonexistent@example.com"))
                .thenReturn(Optional.empty());

        Optional<Employee> result = employeeRepository.findByEmail("nonexistent@example.com");

        assertTrue(result.isEmpty());
        verify(employeeRepository, times(1)).findByEmail("nonexistent@example.com");
    }
}