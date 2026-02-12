package com.alt.lve_tmst_mgmt.service;

import com.alt.lve_tmst_mgmt.entity.HistoryTimesheet;
import com.alt.lve_tmst_mgmt.repository.HistoryTimeSheetRepo;
import com.alt.lve_tmst_mgmt.service.HistoryTimeSheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// Added for logging
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HistoryTimeSheetServiceImpl implements HistoryTimeSheetService {

    @Autowired
    HistoryTimeSheetRepo repository;

    @Override
    public List<HistoryTimesheet> getAll() {
        log.info("HistoryTimeSheetService.getAll() - fetching all history timesheets");
        List<HistoryTimesheet> list = repository.findAll();
        log.info("HistoryTimeSheetService.getAll() - fetched {} records", (list != null ? list.size() : 0));
        return list;
    }

    @Override
    public HistoryTimesheet create(HistoryTimesheet historyTimeSheet) {
        log.info("HistoryTimeSheetService.create() - creating history timesheet (employeeId={}, workDate={})",
                (historyTimeSheet != null && historyTimeSheet.getEmployee() != null
                        ? historyTimeSheet.getEmployee().getEmployeeId() : null),
                (historyTimeSheet != null ? historyTimeSheet.getWorkDate() : null));

        HistoryTimesheet saved = repository.save(historyTimeSheet);

        log.info("HistoryTimeSheetService.create() - created history timesheet successfully");
        return saved;
    }
}