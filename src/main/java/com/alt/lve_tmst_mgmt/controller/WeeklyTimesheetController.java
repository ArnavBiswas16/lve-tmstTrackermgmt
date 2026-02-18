package com.alt.lve_tmst_mgmt.controller;

import com.alt.lve_tmst_mgmt.dto.TimesheetRequestDTO;
import com.alt.lve_tmst_mgmt.dto.TimesheetResponseDTO;
import com.alt.lve_tmst_mgmt.entity.WeeklyTimesheet;
import com.alt.lve_tmst_mgmt.service.MonthlyTimesheetService;
import com.alt.lve_tmst_mgmt.service.WeeklyTimesheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Added for logging
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/public/weekly-timesheets")
public class WeeklyTimesheetController {

    @Autowired
    WeeklyTimesheetService service;
    @Autowired
    private MonthlyTimesheetService timesheetService;
    @PostMapping("/save")
    public ResponseEntity<TimesheetResponseDTO> saveTimesheet(
            @RequestBody TimesheetRequestDTO request) {

        TimesheetResponseDTO response = timesheetService.saveTimesheet(request);
        return ResponseEntity.ok(response);
    }
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