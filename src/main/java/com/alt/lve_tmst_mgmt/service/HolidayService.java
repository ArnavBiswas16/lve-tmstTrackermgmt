package com.alt.lve_tmst_mgmt.service;

import com.alt.lve_tmst_mgmt.dto.HolidayResponse;
import com.alt.lve_tmst_mgmt.entity.Holiday;

import java.util.List;



public interface HolidayService {

    public List<HolidayResponse> getAllHoliday();
}
