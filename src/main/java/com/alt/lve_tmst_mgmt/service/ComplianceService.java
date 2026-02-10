package com.alt.lve_tmst_mgmt.service;


import com.alt.lve_tmst_mgmt.dto.SaveComplianceRequest;
import com.alt.lve_tmst_mgmt.dto.SaveComplianceResponse;

public interface ComplianceService {
    SaveComplianceResponse saveCompliance(SaveComplianceRequest req);
}