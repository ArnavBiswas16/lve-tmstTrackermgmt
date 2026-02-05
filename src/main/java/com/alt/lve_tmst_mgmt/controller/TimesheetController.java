package com.alt.lve_tmst_mgmt.controller;

import com.alt.lve_tmst_mgmt.entity.Timesheet;
import com.alt.lve_tmst_mgmt.service.TimesheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/timesheets")
public class TimesheetController {
@Autowired
     TimesheetService timesheetService;



    // GET all
    @GetMapping()
    public List<Timesheet> getAllTimesheets() {
        return timesheetService.getAll();
    }

    // POST create
    @PostMapping
    public Timesheet createTimesheet(@RequestBody Timesheet timesheet) {
        return timesheetService.create(timesheet);
    }
}
