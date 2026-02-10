package com.alt.lve_tmst_mgmt.controller;

import com.alt.lve_tmst_mgmt.dto.HolidayResponse;
import com.alt.lve_tmst_mgmt.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HolidayController {

    @Autowired
    HolidayService holidayService;

    @GetMapping("/public/getAllHoliday")
    public List<HolidayResponse> getAllHoliday(){
        return holidayService.getAllHoliday();
    }


}
