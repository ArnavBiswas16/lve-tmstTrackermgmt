package com.alt.lve_tmst_mgmt.controller;

import com.alt.lve_tmst_mgmt.entity.Timesheet;
import com.alt.lve_tmst_mgmt.service.TimesheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Added logging
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/timesheets")
public class TimesheetController {

    @Autowired
    TimesheetService timesheetService;

    @GetMapping()
    public List<Timesheet> getAllTimesheets() {
        log.info("GET timesheets - fetching all timesheets");
        List<Timesheet> list = timesheetService.getAll();
        log.info("GET timesheets - fetched {} timesheet records",
                (list != null ? list.size() : 0));

        return list;
    }

    @PostMapping
    public Timesheet createTimesheet(@RequestBody Timesheet timesheet) {
        log.info("POST timesheets - creating timesheet for employeeId={}, workDate={}",
                (timesheet != null && timesheet.getEmployee() != null
                        ? timesheet.getEmployee().getEmployeeId() : null),
                (timesheet != null ? timesheet.getWorkDate() : null));

        Timesheet created = timesheetService.create(timesheet);
        log.info("POST timesheets - created timesheet successfully");

        return created;
    }
}