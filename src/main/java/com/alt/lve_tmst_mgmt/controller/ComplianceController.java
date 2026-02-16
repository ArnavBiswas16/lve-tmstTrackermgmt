package com.alt.lve_tmst_mgmt.controller;

import com.alt.lve_tmst_mgmt.dto.SaveComplianceRequest;
import com.alt.lve_tmst_mgmt.dto.SaveComplianceResponse;
import com.alt.lve_tmst_mgmt.service.ComplianceService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/public/compliance")
@RequiredArgsConstructor
public class ComplianceController {

    private final ComplianceService service;

    @PostMapping
    public ResponseEntity<SaveComplianceResponse> saveCompliance(
            @Valid @RequestBody SaveComplianceRequest request) {

        log.info("Received save compliance request for userId={}", request.getUserId());
        SaveComplianceResponse response = service.saveCompliance(request);
        log.info("Successfully saved compliance details for userId={}", request.getUserId());

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<SaveComplianceResponse> getCompliance(
            @RequestParam String userId,
            @RequestParam String month) {

        log.info("Fetching compliance for userId={} and month={}", userId, month);
        SaveComplianceResponse response = service.getCompliance(userId, month);
        log.info("Returning compliance data for userId={} and month={}", userId, month);

        return ResponseEntity.ok(response);
    }
}