package com.alt.lve_tmst_mgmt.controller;

import com.alt.lve_tmst_mgmt.entity.HistoryTimesheet;
import com.alt.lve_tmst_mgmt.service.HistoryTimeSheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/history-timesheets")
public class HistoryTimeSheetController {
    @Autowired
    HistoryTimeSheetService service;

    @GetMapping
    public List<HistoryTimesheet> getAllHistoryTimeSheets() {
        return service.getAll();
    }

    @PostMapping
    public HistoryTimesheet createHistoryTimeSheet(@RequestBody HistoryTimesheet historyTimeSheet) {
        return service.create(historyTimeSheet);
    }
}
