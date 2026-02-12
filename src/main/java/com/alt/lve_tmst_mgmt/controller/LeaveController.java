package com.alt.lve_tmst_mgmt.controller;

import com.alt.lve_tmst_mgmt.service.LeaveService;
import com.alt.lve_tmst_mgmt.dto.LeaveForecastRequest;
import com.alt.lve_tmst_mgmt.dto.LeaveForecastResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/public/api/leaves")
@RequiredArgsConstructor
public class LeaveController {

    private final LeaveService leaveService;

    @PostMapping("/apply")
    public ResponseEntity<LeaveForecastResponse> applyLeave(@Valid @RequestBody LeaveForecastRequest request) {
        LeaveForecastResponse created = leaveService.applyLeave(request);
        return ResponseEntity
                .created(URI.create("/api/leaves/" + created.getLeaveId()))
                .body(created);
    }
}
