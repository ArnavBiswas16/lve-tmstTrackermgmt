package com.alt.lve_tmst_mgmt.controller;

import com.alt.lve_tmst_mgmt.dto.SaveTimesheetRequest;
import com.alt.lve_tmst_mgmt.dto.SaveTimesheetResponse;
import com.alt.lve_tmst_mgmt.entity.Timesheet;
import com.alt.lve_tmst_mgmt.service.TimesheetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("public/timesheets")
public class TimesheetController {
     @Autowired
     TimesheetService timesheetService;



    // GET all
    @GetMapping()
    public List<Timesheet> getAllTimesheets() {
        return timesheetService.getAll();
    }

    // POST create

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.OK)
    public SaveTimesheetResponse save(@Valid @RequestBody SaveTimesheetRequest request) {
        return timesheetService.save(request);
    }

}
