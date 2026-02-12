package com.alt.lve_tmst_mgmt.controller;

import com.alt.lve_tmst_mgmt.dto.HolidayResponse;
import com.alt.lve_tmst_mgmt.service.HolidayService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HolidayControllerTest {

    @Mock
    private HolidayService holidayService;

    @InjectMocks
    private HolidayController holidayController;

    private HolidayResponse holiday1;
    private HolidayResponse holiday2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        holiday1 = new HolidayResponse();
        holiday2 = new HolidayResponse();
    }

    @Test
    void getAllHoliday_shouldReturnHolidayList() {
        List<HolidayResponse> mockList = List.of(holiday1, holiday2);

        when(holidayService.getAllHoliday()).thenReturn(mockList);

        List<HolidayResponse> result = holidayController.getAllHoliday();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(mockList, result);

        verify(holidayService, times(1)).getAllHoliday();
    }
}
