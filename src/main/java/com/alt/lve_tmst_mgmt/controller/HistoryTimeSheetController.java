package com.alt.lve_tmst_mgmt.controller;

import com.alt.lve_tmst_mgmt.entity.HistoryTimesheet;
import com.alt.lve_tmst_mgmt.service.HistoryTimeSheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// --- Logging (added) ---
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/history-timesheets")
public class HistoryTimeSheetController {

    @Autowired
    HistoryTimeSheetService service;

    @GetMapping
    public List<HistoryTimesheet> getAllHistoryTimeSheets() {
        log.info("history-timesheets - fetching all history timesheets");
        List<HistoryTimesheet> result = service.getAll();
        log.info("history-timesheets - fetched {} records", (result != null ? result.size() : 0));
        return result;
    }

    @PostMapping
    public HistoryTimesheet createHistoryTimeSheet(@RequestBody HistoryTimesheet historyTimeSheet) {
        log.info("history-timesheets - creating history timesheet (employeeId={}, workDate={})",
                (historyTimeSheet != null && historyTimeSheet.getEmployee() != null
                        ? historyTimeSheet.getEmployee().getEmployeeId() : null),
                (historyTimeSheet != null ? historyTimeSheet.getWorkDate() : null));

        HistoryTimesheet created = service.create(historyTimeSheet);

        log.info("history-timesheets - created history timesheet successfully");
        return created;
    }
}