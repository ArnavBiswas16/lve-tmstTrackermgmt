package com.alt.lve_tmst_mgmt.service;

import com.alt.lve_tmst_mgmt.entity.WeeklyTimesheet;
import com.alt.lve_tmst_mgmt.repository.WeeklyTimesheetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeeklyTimesheetServiceImplTest {

    @Mock
    private WeeklyTimesheetRepository weeklyTimesheetRepo;

    @InjectMocks
    private WeeklyTimesheetServiceImpl weeklyTimesheetService;

    @Test
    void getAll_shouldReturnAllWeeklyTimesheets() {
        WeeklyTimesheet w1 = new WeeklyTimesheet();
        WeeklyTimesheet w2 = new WeeklyTimesheet();

        when(weeklyTimesheetRepo.findAll()).thenReturn(List.of(w1, w2));

        List<WeeklyTimesheet> result = weeklyTimesheetService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(weeklyTimesheetRepo, times(1)).findAll();
    }

    @Test
    void create_shouldSaveAndReturnWeeklyTimesheet() {
        WeeklyTimesheet weeklyTimesheet = new WeeklyTimesheet();

        when(weeklyTimesheetRepo.save(weeklyTimesheet)).thenReturn(weeklyTimesheet);

        WeeklyTimesheet result = weeklyTimesheetService.create(weeklyTimesheet);

        assertNotNull(result);
        assertEquals(weeklyTimesheet, result);
        verify(weeklyTimesheetRepo, times(1)).save(weeklyTimesheet);
    }




}