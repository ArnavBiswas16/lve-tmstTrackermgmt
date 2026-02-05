package com.alt.lve_tmst_mgmt.service;

import com.alt.lve_tmst_mgmt.entity.Timesheet;
import java.util.List;

public interface TimesheetService {
    List<Timesheet> getAll();
    Timesheet create(Timesheet timesheet);
}
