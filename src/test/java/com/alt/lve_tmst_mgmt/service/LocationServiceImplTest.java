package com.alt.lve_tmst_mgmt.service;

import com.alt.lve_tmst_mgmt.entity.Location;
import com.alt.lve_tmst_mgmt.repository.LocationRepo;

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
class LocationServiceImplTest {

    @Mock
    private LocationRepo locationRepo;

    @InjectMocks
    private LocationServiceImpl locationService;

    @Test
    void getAllLocation_shouldReturnAllLocations() {
        Location l1 = new Location();
        Location l2 = new Location();

        when(locationRepo.findAll()).thenReturn(Arrays.asList(l1, l2));

        List<Location> result = locationService.getAllLocation();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(locationRepo, times(1)).findAll();
    }

    @Test
    void getAllLocation_shouldReturnEmptyList() {
        when(locationRepo.findAll()).thenReturn(List.of());

        List<Location> result = locationService.getAllLocation();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(locationRepo, times(1)).findAll();
    }
}
