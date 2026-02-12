package com.alt.lve_tmst_mgmt.controller;

import com.alt.lve_tmst_mgmt.entity.WeeklyTimesheet;
import com.alt.lve_tmst_mgmt.service.WeeklyTimesheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Added for logging
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/weekly-timesheets")
public class WeeklyTimesheetController {

    @Autowired
    WeeklyTimesheetService service;

    @GetMapping
    public List<WeeklyTimesheet> getAllWeeklyTimesheets() {
        log.info("GET /weekly-timesheets - fetching all weekly timesheets");
        List<WeeklyTimesheet> list = service.getAll();
        log.info("GET /weekly-timesheets - fetched {} weekly timesheets",
                (list != null ? list.size() : 0));

        return list;
    }

    @PostMapping
    public WeeklyTimesheet createWeeklyTimesheet(@RequestBody WeeklyTimesheet weeklyTimesheet) {

        log.info("POST /weekly-timesheets - creating weekly timesheet for employeeId={}, weekStart={}",
                (weeklyTimesheet != null && weeklyTimesheet.getEmployee() != null
                        ? weeklyTimesheet.getEmployee().getEmployeeId() : null),
                (weeklyTimesheet != null ? weeklyTimesheet.getWeekStartDate() : null));

        WeeklyTimesheet created = service.create(weeklyTimesheet);
        log.info("POST /weekly-timesheets - weekly timesheet created successfully");

        return created;
    }
}