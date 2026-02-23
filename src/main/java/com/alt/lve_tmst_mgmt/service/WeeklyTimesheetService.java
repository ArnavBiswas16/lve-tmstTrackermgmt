package com.alt.lve_tmst_mgmt.service;

import com.alt.lve_tmst_mgmt.entity.WeeklyTimesheet;
import java.util.List;

public interface WeeklyTimesheetService {
    List<WeeklyTimesheet> getAll();
    WeeklyTimesheet create(WeeklyTimesheet weeklyTimesheet);
    List<WeeklyTimesheet> getByEmployeeId(String employeeId);
    List<WeeklyTimesheet> getByEmployeeIdAndMonth(String employeeId, String month);
}