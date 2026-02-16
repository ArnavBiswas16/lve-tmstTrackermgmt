
package com.alt.lve_tmst_mgmt.service;

import com.alt.lve_tmst_mgmt.entity.WeeklyTimesheet;
import com.alt.lve_tmst_mgmt.repository.WeeklyTimesheetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeeklyTimesheetServiceImpl implements WeeklyTimesheetService {
    @Autowired
    WeeklyTimesheetRepository weeklyTimesheetRepo;



    @Override
    public List<WeeklyTimesheet> getAll() {
        return weeklyTimesheetRepo.findAll();
    }

    @Override
    public WeeklyTimesheet create(WeeklyTimesheet weeklyTimesheet) {
        return weeklyTimesheetRepo.save(weeklyTimesheet);
    }
}