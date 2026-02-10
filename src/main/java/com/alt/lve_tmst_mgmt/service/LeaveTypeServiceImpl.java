package com.alt.lve_tmst_mgmt.service;

import com.alt.lve_tmst_mgmt.entity.LeaveType;
import com.alt.lve_tmst_mgmt.repository.LeaveTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveTypeServiceImpl implements  LeaveTypeService {

    @Autowired
    LeaveTypeRepo leaveTypeRepo;

    @Override
    public List<LeaveType> getAllLeaveType() {
        return leaveTypeRepo.findAll();
    }
}
