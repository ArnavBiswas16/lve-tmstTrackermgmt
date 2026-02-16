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

// Added logging
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("public/timesheets")
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

    // POST create

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.OK)
    public SaveTimesheetResponse save(@Valid @RequestBody SaveTimesheetRequest request) {
        return timesheetService.save(request);
    }

}
