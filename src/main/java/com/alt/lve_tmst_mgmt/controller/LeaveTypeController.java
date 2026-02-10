package com.alt.lve_tmst_mgmt.controller;

import com.alt.lve_tmst_mgmt.entity.LeaveType;
import com.alt.lve_tmst_mgmt.service.LeaveTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/public/api")
public class LeaveTypeController {

    @Autowired
    LeaveTypeService leaveTypeService;

    @GetMapping("/getAllLeaveType")
    public List<LeaveType> getAllLocation(){
        return leaveTypeService.getAllLeaveType();
    }

}
