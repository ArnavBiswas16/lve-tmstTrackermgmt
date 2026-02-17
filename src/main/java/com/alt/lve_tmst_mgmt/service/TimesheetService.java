package com.alt.lve_tmst_mgmt.service;

import com.alt.lve_tmst_mgmt.dto.SaveTimesheetRequest;
import com.alt.lve_tmst_mgmt.dto.SaveTimesheetResponse;
import com.alt.lve_tmst_mgmt.entity.Timesheet;

import javax.swing.plaf.basic.BasicIconFactory;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TimesheetService {
    List<Timesheet> getAll();
    Timesheet create(Timesheet timesheet);
    SaveTimesheetResponse save(SaveTimesheetRequest req);
    BigDecimal getTotalForcastedHours(String userId, LocalDate start, LocalDate end);
}
