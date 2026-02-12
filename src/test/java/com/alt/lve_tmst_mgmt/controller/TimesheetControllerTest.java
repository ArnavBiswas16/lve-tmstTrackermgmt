package com.alt.lve_tmst_mgmt.controller;

import com.alt.lve_tmst_mgmt.entity.Timesheet;
import com.alt.lve_tmst_mgmt.service.TimesheetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TimesheetControllerTest {

    @Mock
    private TimesheetService timesheetService;

    @InjectMocks
    private TimesheetController timesheetController;

    private Timesheet sampleTimesheet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleTimesheet = new Timesheet();
    }

    @Test
    void getAllTimesheets_shouldReturnList() {
        List<Timesheet> timesheets = List.of(sampleTimesheet);
        when(timesheetService.getAll()).thenReturn(timesheets);

        List<Timesheet> result = timesheetController.getAllTimesheets();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(sampleTimesheet, result.get(0));

        verify(timesheetService, times(1)).getAll();
    }

    @Test
    void createTimesheet_shouldReturnCreatedTimesheet() {
        when(timesheetService.create(sampleTimesheet)).thenReturn(sampleTimesheet);

        Timesheet result = timesheetController.createTimesheet(sampleTimesheet);

        assertNotNull(result);
        assertEquals(sampleTimesheet, result);

        verify(timesheetService, times(1)).create(sampleTimesheet);
    }
}
