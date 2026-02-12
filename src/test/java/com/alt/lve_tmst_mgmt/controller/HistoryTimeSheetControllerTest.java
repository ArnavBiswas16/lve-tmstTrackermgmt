package com.alt.lve_tmst_mgmt.controller;

import com.alt.lve_tmst_mgmt.entity.HistoryTimesheet;
import com.alt.lve_tmst_mgmt.service.HistoryTimeSheetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HistoryTimeSheetControllerTest {

    @Mock
    private HistoryTimeSheetService service;

    @InjectMocks
    private HistoryTimeSheetController controller;

    private HistoryTimesheet sampleTimesheet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleTimesheet = new HistoryTimesheet();
    }

    @Test
    void getAllHistoryTimeSheets_shouldReturnList() {
        List<HistoryTimesheet> timesheets = List.of(sampleTimesheet);
        when(service.getAll()).thenReturn(timesheets);

        List<HistoryTimesheet> result = controller.getAllHistoryTimeSheets();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(sampleTimesheet, result.get(0));

        verify(service, times(1)).getAll();
    }

    @Test
    void createHistoryTimeSheet_shouldReturnCreatedTimesheet() {
        when(service.create(sampleTimesheet)).thenReturn(sampleTimesheet);

        HistoryTimesheet result = controller.createHistoryTimeSheet(sampleTimesheet);

        assertNotNull(result);
        assertEquals(sampleTimesheet, result);

        verify(service, times(1)).create(sampleTimesheet);
    }
}
