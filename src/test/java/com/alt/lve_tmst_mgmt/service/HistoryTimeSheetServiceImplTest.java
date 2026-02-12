package com.alt.lve_tmst_mgmt.service;

import com.alt.lve_tmst_mgmt.entity.HistoryTimesheet;
import com.alt.lve_tmst_mgmt.repository.HistoryTimeSheetRepo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HistoryTimeSheetServiceImplTest {

    @Mock
    private HistoryTimeSheetRepo repository;

    @InjectMocks
    private HistoryTimeSheetServiceImpl service;

    @Test
    void getAll_shouldReturnAllRecords() {
        HistoryTimesheet h1 = new HistoryTimesheet();
        HistoryTimesheet h2 = new HistoryTimesheet();

        when(repository.findAll()).thenReturn(Arrays.asList(h1, h2));

        List<HistoryTimesheet> result = service.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void create_shouldSaveAndReturnEntity() {
        HistoryTimesheet history = new HistoryTimesheet();

        when(repository.save(history)).thenReturn(history);

        HistoryTimesheet result = service.create(history);

        assertNotNull(result);
        assertEquals(history, result);
        verify(repository, times(1)).save(history);
    }
}
