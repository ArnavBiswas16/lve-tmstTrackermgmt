package com.alt.lve_tmst_mgmt.service;

import com.alt.lve_tmst_mgmt.entity.HistoryTimesheet;
import com.alt.lve_tmst_mgmt.repository.HistoryTimeSheetRepo;
import com.alt.lve_tmst_mgmt.service.HistoryTimeSheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryTimeSheetServiceImpl implements HistoryTimeSheetService {
@Autowired
    HistoryTimeSheetRepo  repository;



    @Override
    public List<HistoryTimesheet> getAll() {
        return repository.findAll();
    }

    @Override
    public HistoryTimesheet create(HistoryTimesheet historyTimeSheet) {
        return repository.save(historyTimeSheet);
    }
}
