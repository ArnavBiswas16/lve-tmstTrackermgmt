package com.alt.lve_tmst_mgmt.service;

import com.alt.lve_tmst_mgmt.entity.Timesheet;
import com.alt.lve_tmst_mgmt.repository.TimesheetRepo;
import com.alt.lve_tmst_mgmt.service.TimesheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimesheetServiceImpl implements TimesheetService {
@Autowired
     TimesheetRepo timesheetRepo;

    @Override
    public List<Timesheet> getAll() {
        return timesheetRepo.findAll();
    }

    @Override
    public Timesheet create(Timesheet timesheet) {
        return timesheetRepo.save(timesheet);
    }
}
