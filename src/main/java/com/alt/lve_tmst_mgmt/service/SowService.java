package com.alt.lve_tmst_mgmt.service;

import com.alt.lve_tmst_mgmt.dto.SowDto;

import java.util.List;

public interface SowService {
    List<SowDto> getAllSows();
    List<SowDto> getSowsByManagerId(String managerId);
    SowDto createSow(SowDto request);

}