package com.alt.lve_tmst_mgmt.service;

import com.alt.lve_tmst_mgmt.dto.HolidayResponse;
import com.alt.lve_tmst_mgmt.entity.Holiday;
import com.alt.lve_tmst_mgmt.repository.HolidayRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HolidayServiceImpl implements HolidayService {

    @Autowired
    HolidayRepo holidayRepository;

    @Override
    public List<HolidayResponse> getAllHoliday() {

            return holidayRepository.findAllWithLocation()
                    .stream()
                    .map(h -> new HolidayResponse(
                            h.getHolidayId(),
                            h.getName(),
                            h.getType(),
                            h.getDate(),
                            h.getLocation().getCity()
                    ))
                    .toList();

    }
}
