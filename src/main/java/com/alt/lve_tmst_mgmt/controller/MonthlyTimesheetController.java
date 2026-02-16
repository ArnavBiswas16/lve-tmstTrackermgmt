package com.alt.lve_tmst_mgmt.controller;

import com.alt.lve_tmst_mgmt.dto.TimesheetRequestDTO;
import com.alt.lve_tmst_mgmt.service.MonthlyTimesheetService;
import com.alt.lve_tmst_mgmt.service.TimesheetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/timesheets")
@RequiredArgsConstructor
public class MonthlyTimesheetController {

    private final MonthlyTimesheetService service;

    @PostMapping
    public ResponseEntity<String> saveTimesheet(
            @RequestBody TimesheetRequestDTO request) {

        service.saveTimesheet(request);

        return ResponseEntity.ok("Timesheet saved successfully");
    }
}
