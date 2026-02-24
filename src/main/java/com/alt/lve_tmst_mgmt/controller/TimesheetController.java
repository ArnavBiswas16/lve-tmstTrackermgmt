package com.alt.lve_tmst_mgmt.controller;

import com.alt.lve_tmst_mgmt.dto.SaveTimesheetRequest;
import com.alt.lve_tmst_mgmt.dto.SaveTimesheetResponse;

import com.alt.lve_tmst_mgmt.entity.Timesheet;
import com.alt.lve_tmst_mgmt.service.MonthlyTimesheetService;
import com.alt.lve_tmst_mgmt.service.TimesheetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

// Added logging
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("public/timesheets")
public class TimesheetController {
     @Autowired
     TimesheetService timesheetService;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.OK)
    public SaveTimesheetResponse save(@Valid @RequestBody SaveTimesheetRequest request) {
        return timesheetService.save(request);
    }

    @GetMapping()
    public List<Timesheet> getAllTimesheets() {
        log.info("GET timesheets - fetching all timesheets");
        List<Timesheet> list = timesheetService.getAll();
        log.info("GET timesheets - fetched {} timesheet records",
                (list != null ? list.size() : 0));

        return list;
    }

    @GetMapping("/getTotalForecastedHours")
    public ResponseEntity<Map<String, BigDecimal>> getTotalForecastedHours(
            @RequestParam String userId,
            @RequestParam YearMonth month) {

        LocalDate start = month.atDay(1);
        LocalDate end = month.atEndOfMonth();

        BigDecimal total = timesheetService
                .getTotalForcastedHours(userId, start, end);

        return ResponseEntity.ok(
                Map.of("totalHours", total)
        );
    }
}
