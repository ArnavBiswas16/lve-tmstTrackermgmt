package com.alt.lve_tmst_mgmt.service;

import com.alt.lve_tmst_mgmt.dto.HolidayResponse;
import com.alt.lve_tmst_mgmt.entity.Holiday;
import com.alt.lve_tmst_mgmt.entity.Location;
import com.alt.lve_tmst_mgmt.repository.HolidayRepo;
import org.junit.jupiter.api.BeforeEach;
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
class HolidayServiceImplTest {

    @Mock
    private HolidayRepo holidayRepository;

    @InjectMocks
    private HolidayServiceImpl holidayService;

    private Holiday h1;
    private Holiday h2;

    @BeforeEach
    void setup() {
        Location loc1 = Location.builder()
                .locationId(1)
                .country("India")
                .city("Pune")
                .build();

        Location loc2 = Location.builder()
                .locationId(2)
                .country("USA")
                .city("New York")
                .build();

        h1 = Holiday.builder()
                .holidayId(10)
                .name("Diwali")
                .type("Festival")
                .date(LocalDate.of(2025, 11, 1))
                .location(loc1)
                .build();

        h2 = Holiday.builder()
                .holidayId(20)
                .name("Christmas")
                .type("National")
                .date(LocalDate.of(2025, 12, 25))
                .location(loc2)
                .build();
    }

    @Test
    void getAllHoliday_shouldReturnMappedHolidayResponses() {
        when(holidayRepository.findAllWithLocation()).thenReturn(List.of(h1, h2));

        List<HolidayResponse> result = holidayService.getAllHoliday();

        assertNotNull(result);
        assertEquals(2, result.size());

        HolidayResponse r1 = result.get(0);
        assertEquals(10, r1.getHolidayId());
        assertEquals("Diwali", r1.getName());
        assertEquals("Festival", r1.getType());
        assertEquals(LocalDate.of(2025, 11, 1), r1.getDate());
        assertEquals("Pune", r1.getLocation());

        HolidayResponse r2 = result.get(1);
        assertEquals(20, r2.getHolidayId());
        assertEquals("Christmas", r2.getName());
        assertEquals("National", r2.getType());
        assertEquals(LocalDate.of(2025, 12, 25), r2.getDate());
        assertEquals("New York", r2.getLocation());

        verify(holidayRepository, times(1)).findAllWithLocation();
    }
}