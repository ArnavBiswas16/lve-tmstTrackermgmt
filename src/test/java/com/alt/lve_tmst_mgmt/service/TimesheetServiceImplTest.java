package com.alt.lve_tmst_mgmt.service;

import com.alt.lve_tmst_mgmt.dto.LeaveDayDTO;
import com.alt.lve_tmst_mgmt.dto.SaveTimesheetRequest;
import com.alt.lve_tmst_mgmt.dto.SaveTimesheetResponse;
import com.alt.lve_tmst_mgmt.dto.TimesheetDayDTO;
import com.alt.lve_tmst_mgmt.entity.Employee;
import com.alt.lve_tmst_mgmt.entity.LeaveForecast;
import com.alt.lve_tmst_mgmt.entity.Timesheet;
import com.alt.lve_tmst_mgmt.Exceptions.BusinessValidationException;
import com.alt.lve_tmst_mgmt.Exceptions.ResourceNotFoundException;
import com.alt.lve_tmst_mgmt.repository.EmployeeRepository;
import com.alt.lve_tmst_mgmt.repository.LeaveForecastRepository;
import com.alt.lve_tmst_mgmt.repository.TimesheetRepo;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TimesheetServiceImplTest {

    @Mock
    private TimesheetRepo timesheetRepo;

    @Mock
    private LeaveForecastRepository leaveForecastRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private TimesheetServiceImpl timesheetService;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = Employee.builder()
                .employeeId("634")
                .name("Abhay Kumar Singh")
                .email("abhay@test.com")
                .role("USER")
                .password("pass")
                .isActive(true)
                .build();
    }

    @Test
    void getAll_shouldReturnAllTimesheets() {
        Timesheet t1 = new Timesheet();
        Timesheet t2 = new Timesheet();

        when(timesheetRepo.findAll()).thenReturn(List.of(t1, t2));

        List<Timesheet> result = timesheetService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(timesheetRepo, times(1)).findAll();
    }

    @Test
    void create_shouldSaveAndReturnTimesheet() {
        Timesheet timesheet = new Timesheet();

        when(timesheetRepo.save(timesheet)).thenReturn(timesheet);

        Timesheet result = timesheetService.create(timesheet);

        assertNotNull(result);
        assertEquals(timesheet, result);
        verify(timesheetRepo, times(1)).save(timesheet);
    }

    @Test
    void save_validRequest_shouldReturnSuccess() {
        // Prepare request with one timesheet and one leave
        TimesheetDayDTO tsDay = new TimesheetDayDTO(LocalDate.of(2026, 2, 10), BigDecimal.valueOf(8));
        LeaveDayDTO leaveDay = new LeaveDayDTO(LocalDate.of(2026, 2, 12));

        SaveTimesheetRequest request = new SaveTimesheetRequest();
        request.setEmployeeId(employee.getEmployeeId());
        request.setTimesheet(List.of(tsDay));
        request.setLeaveForecast(List.of(leaveDay));

        // Mock employee existence
        when(employeeRepository.existsById(employee.getEmployeeId())).thenReturn(true);
        when(entityManager.getReference(Employee.class, employee.getEmployeeId())).thenReturn(employee);

        // Mock repository calls
        when(timesheetRepo.findByEmployeeAndWorkDateIn(any(Employee.class), anyList())).thenReturn(List.of());
        when(timesheetRepo.save(any(Timesheet.class))).thenAnswer(i -> i.getArgument(0));
        when(leaveForecastRepository.saveAll(anyList())).thenReturn(List.of());

        SaveTimesheetResponse response = timesheetService.save(request);

        assertNotNull(response);
        assertEquals(employee.getEmployeeId(), response.getEmp_id());
        assertEquals("success", response.getStatus());

        verify(timesheetRepo, times(1)).save(any(Timesheet.class));
        verify(leaveForecastRepository, times(1)).saveAll(anyList());
    }

    @Test
    void save_invalidEmployee_shouldThrowResourceNotFoundException() {
        SaveTimesheetRequest request = new SaveTimesheetRequest();
        request.setEmployeeId("unknown");

        when(employeeRepository.existsById("unknown")).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> timesheetService.save(request));
    }

    @Test
    void save_blankEmployeeId_shouldThrowBusinessValidationException() {
        SaveTimesheetRequest request = new SaveTimesheetRequest();
        request.setEmployeeId("   ");

        assertThrows(BusinessValidationException.class, () -> timesheetService.save(request));
    }

    @Test
    void getTotalForcastedHours_shouldReturnValue() {
        LocalDate start = LocalDate.of(2026, 2, 1);
        LocalDate end = LocalDate.of(2026, 2, 28);

        when(timesheetRepo.getTotalHoursForMonth("634", start, end)).thenReturn(BigDecimal.valueOf(160));

        BigDecimal total = timesheetService.getTotalForcastedHours("634", start, end);

        assertEquals(BigDecimal.valueOf(160), total);
        verify(timesheetRepo, times(1)).getTotalHoursForMonth("634", start, end);
    }
}