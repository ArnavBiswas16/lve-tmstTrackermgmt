package com.alt.lve_tmst_mgmt.controller;

import com.alt.lve_tmst_mgmt.dto.WeeklyEntryDTO;
import com.alt.lve_tmst_mgmt.dto.WeeklyTimesheetRequestDTO;
import com.alt.lve_tmst_mgmt.dto.WeeklyTimesheetResponseDTO;
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
    public ResponseEntity<WeeklyTimesheetResponseDTO> saveTimesheet(
            @RequestBody WeeklyTimesheetRequestDTO request) {

        WeeklyTimesheetResponseDTO response = timesheetService.saveTimesheet(request);
        return ResponseEntity.ok(response);
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
    @GetMapping
    public ResponseEntity<WeeklyTimesheetResponseDTO> getWeeklyTimesheets(
            @RequestParam("userId") String userId,
            @RequestParam(value = "month", required = false) String month) {

        List<WeeklyTimesheet> timesheets;

        if (month != null && !month.isEmpty()) {
            timesheets = service.getByEmployeeIdAndMonth(userId, month);
        } else {
            timesheets = service.getByEmployeeId(userId);
        }

        // ✅ Return empty response instead of 404
        if (timesheets == null || timesheets.isEmpty()) {
            return ResponseEntity.ok(
                    WeeklyTimesheetResponseDTO.builder()
                            .employeeId(userId)
                            .employeeName(null)
                            .timeSheet(List.of())
                            .build()
            );
        }

        WeeklyTimesheet first = timesheets.get(0);

        List<WeeklyEntryDTO> weeklyEntries = timesheets.stream()
                .map(ts -> WeeklyEntryDTO.builder()
                        .weekStartDate(ts.getWeekStartDate())
                        .weekEndDate(ts.getWeekEndDate())
                        .totalHours(ts.getTotalHours())
                        .build())
                .toList();

        return ResponseEntity.ok(
                WeeklyTimesheetResponseDTO.builder()
                        .employeeId(first.getEmployee().getEmployeeId())
                        .employeeName(first.getEmployee().getName())
                        .timeSheet(weeklyEntries)
                        .build()
        );
    }
}