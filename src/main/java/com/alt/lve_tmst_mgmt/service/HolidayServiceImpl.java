package com.alt.lve_tmst_mgmt.service;

import com.alt.lve_tmst_mgmt.dto.HolidayResponse;
import com.alt.lve_tmst_mgmt.entity.Holiday;
import com.alt.lve_tmst_mgmt.repository.HolidayRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// Added for logging
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HolidayServiceImpl implements HolidayService {

    @Autowired
    HolidayRepo holidayRepository;

    @Override
    public List<HolidayResponse> getAllHoliday() {

        log.info("HolidayService.getAllHoliday() - fetching all holidays with locations");

        List<HolidayResponse> result = holidayRepository.findAllWithLocation()
                .stream()
                .map(h -> new HolidayResponse(
                        h.getHolidayId(),
                        h.getName(),
                        h.getType(),
                        h.getDate(),
                        h.getLocation() != null ? h.getLocation().getCity() : null
                ))
                .toList();

        log.info("HolidayService.getAllHoliday() - fetched {} holidays", (result != null ? result.size() : 0));

        return result;
    }
}