package com.alt.lve_tmst_mgmt.controller;

import com.alt.lve_tmst_mgmt.entity.Location;
import com.alt.lve_tmst_mgmt.service.LocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LocationControllerTest {

    @Mock
    private LocationService locationService;

    @InjectMocks
    private LocationController locationController;

    private Location sampleLocation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleLocation = new Location();

    }

    @Test
    void getAllLocation_shouldReturnListOfLocations() {
        List<Location> locations = List.of(sampleLocation);
        when(locationService.getAllLocation()).thenReturn(locations);

        List<Location> result = locationController.getAllLocation();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(sampleLocation, result.get(0));

        verify(locationService, times(1)).getAllLocation();
    }
}
