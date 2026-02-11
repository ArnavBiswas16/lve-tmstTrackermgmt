package com.alt.lve_tmst_mgmt.controller;
import com.alt.lve_tmst_mgmt.dto.SaveComplianceRequest;
import com.alt.lve_tmst_mgmt.dto.SaveComplianceResponse;
import com.alt.lve_tmst_mgmt.service.ComplianceService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/compliance")
@RequiredArgsConstructor
public class ComplianceController {

    private final ComplianceService service;

    @PostMapping
    public ResponseEntity<SaveComplianceResponse> saveCompliance(
            @Valid @RequestBody SaveComplianceRequest request) {
        return ResponseEntity.ok(service.saveCompliance(request));
    }

    @GetMapping
    public ResponseEntity<SaveComplianceResponse> getCompliance(
            @RequestParam String userId,
            @RequestParam String month) {

        return ResponseEntity.ok(service.getCompliance(userId, month));
    }
}