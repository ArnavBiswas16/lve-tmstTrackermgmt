package com.alt.lve_tmst_mgmt.controller;

import com.alt.lve_tmst_mgmt.dto.SowDto;
import com.alt.lve_tmst_mgmt.service.SowService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SowControllerTest {

    @Mock
    private SowService sowService;

    @InjectMocks
    private SowController sowController;

    private SowDto sampleSow;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleSow = new SowDto();
    }

    @Test
    void getAll_shouldReturnListOfSows() {
        List<SowDto> sows = List.of(sampleSow);
        when(sowService.getAllSows()).thenReturn(sows);

        ResponseEntity<List<SowDto>> result = sowController.getAll();

        assertNotNull(result);
        assertEquals(200, result.getStatusCodeValue());
        assertEquals(1, result.getBody().size());
        assertEquals(sampleSow, result.getBody().get(0));

        verify(sowService, times(1)).getAllSows();
    }

    @Test
    void getByManager_shouldReturnListOfSows() {
        String managerId = "MGR001";
        List<SowDto> sows = List.of(sampleSow);
        when(sowService.getSowsByManagerId(managerId)).thenReturn(sows);

        ResponseEntity<List<SowDto>> result = sowController.getByManager(managerId);

        assertNotNull(result);
        assertEquals(200, result.getStatusCodeValue());
        assertEquals(1, result.getBody().size());
        assertEquals(sampleSow, result.getBody().get(0));

        verify(sowService, times(1)).getSowsByManagerId(managerId);
    }

    @Test
    void createSow_shouldReturnCreatedSow() {
        when(sowService.createSow(sampleSow)).thenReturn(sampleSow);

        ResponseEntity<SowDto> result = sowController.createSow(sampleSow);

        assertNotNull(result);
        assertEquals(200, result.getStatusCodeValue());
        assertEquals(sampleSow, result.getBody());

        verify(sowService, times(1)).createSow(sampleSow);
    }
}
