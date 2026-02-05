package com.alt.lve_tmst_mgmt.controller;

import com.alt.lve_tmst_mgmt.entity.WeeklyTimesheet;
import com.alt.lve_tmst_mgmt.service.WeeklyTimesheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weekly-timesheets")
public class WeeklyTimesheetController {
@Autowired
    WeeklyTimesheetService service;


    @GetMapping
    public List<WeeklyTimesheet> getAllWeeklyTimesheets() {
        return service.getAll();
    }

    @PostMapping
    public WeeklyTimesheet createWeeklyTimesheet(@RequestBody WeeklyTimesheet weeklyTimesheet) {
        return service.create(weeklyTimesheet);
    }
}