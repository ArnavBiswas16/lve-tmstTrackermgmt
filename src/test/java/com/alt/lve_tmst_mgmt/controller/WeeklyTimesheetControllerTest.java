package com.alt.lve_tmst_mgmt.controller;

import com.alt.lve_tmst_mgmt.entity.WeeklyTimesheet;
import com.alt.lve_tmst_mgmt.service.WeeklyTimesheetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WeeklyTimesheetControllerTest {

    @Mock
    private WeeklyTimesheetService service;

    @InjectMocks
    private WeeklyTimesheetController controller;

    private WeeklyTimesheet sampleWeeklyTimesheet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleWeeklyTimesheet = new WeeklyTimesheet();
    }


    @Test
    void createWeeklyTimesheet_shouldReturnCreatedTimesheet() {
        when(service.create(sampleWeeklyTimesheet)).thenReturn(sampleWeeklyTimesheet);

        WeeklyTimesheet result = controller.createWeeklyTimesheet(sampleWeeklyTimesheet);

        assertNotNull(result);
        assertEquals(sampleWeeklyTimesheet, result);

        verify(service, times(1)).create(sampleWeeklyTimesheet);
    }
}
