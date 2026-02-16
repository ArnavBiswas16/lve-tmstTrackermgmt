package com.alt.lve_tmst_mgmt.controller;

import com.alt.lve_tmst_mgmt.dto.HolidayResponse;
import com.alt.lve_tmst_mgmt.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// Added for logging
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class HolidayController {

    @Autowired
    HolidayService holidayService;

    @GetMapping("/public/getAllHoliday")
    public List<HolidayResponse> getAllHoliday() {

        log.info("GET /public/getAllHoliday - fetching all holidays");

        List<HolidayResponse> holidays = holidayService.getAllHoliday();

        log.info("GET /public/getAllHoliday - fetched {} holidays",
                (holidays != null ? holidays.size() : 0));

        return holidays;
    }

}