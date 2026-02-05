package com.alt.lve_tmst_mgmt.service;

import com.alt.lve_tmst_mgmt.entity.HistoryTimesheet;
import java.util.List;

public interface HistoryTimeSheetService {
    List<HistoryTimesheet> getAll();
    HistoryTimesheet create(HistoryTimesheet historyTimesheet);
}
