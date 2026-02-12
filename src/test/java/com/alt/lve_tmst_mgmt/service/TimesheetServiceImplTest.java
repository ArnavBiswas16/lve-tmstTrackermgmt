package com.alt.lve_tmst_mgmt.service;

import com.alt.lve_tmst_mgmt.entity.Timesheet;
import com.alt.lve_tmst_mgmt.repository.TimesheetRepo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TimesheetServiceImplTest {

    @Mock
    private TimesheetRepo timesheetRepo;

    @InjectMocks
    private TimesheetServiceImpl timesheetService;

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
}
