package com.alt.lve_tmst_mgmt.repository;

import com.alt.lve_tmst_mgmt.entity.Holiday;
import com.alt.lve_tmst_mgmt.entity.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HolidayRepoTest {

    @Mock
    private HolidayRepo holidayRepo;

    private Location pune;
    private Location nyc;
    private Holiday diwali;
    private Holiday thanksgiving;

    @BeforeEach
    void setUp() {
        pune = Location.builder()
                .locationId(1)
                .country("India")
                .city("Pune")
                .build();

        nyc = Location.builder()
                .locationId(2)
                .country("USA")
                .city("New York")
                .build();

        diwali = Holiday.builder()
                .holidayId(101)
                .name("Diwali")
                .type("Public")
                .date(LocalDate.of(2025, 11, 1))
                .location(pune)
                .build();

        thanksgiving = Holiday.builder()
                .holidayId(102)
                .name("Thanksgiving")
                .type("Public")
                .date(LocalDate.of(2025, 11, 27))
                .location(nyc)
                .build();
    }

    @Test
    void findAllWithLocation_shouldReturnHolidaysWithLocations() {
        when(holidayRepo.findAllWithLocation()).thenReturn(List.of(diwali, thanksgiving));

        List<Holiday> result = holidayRepo.findAllWithLocation();

        assertNotNull(result);
        assertEquals(2, result.size());

        Holiday h1 = result.get(0);
        assertEquals("Diwali", h1.getName());
        assertNotNull(h1.getLocation());
        assertEquals("India", h1.getLocation().getCountry());
        assertEquals("Pune", h1.getLocation().getCity());

        Holiday h2 = result.get(1);
        assertEquals("Thanksgiving", h2.getName());
        assertNotNull(h2.getLocation());
        assertEquals("USA", h2.getLocation().getCountry());
        assertEquals("New York", h2.getLocation().getCity());

        verify(holidayRepo, times(1)).findAllWithLocation();
        verifyNoMoreInteractions(holidayRepo);
    }
}
