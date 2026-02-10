package com.alt.lve_tmst_mgmt.service;

import com.alt.lve_tmst_mgmt.entity.Employee;
import com.alt.lve_tmst_mgmt.entity.LeaveForecast;
import com.alt.lve_tmst_mgmt.entity.LeaveType;
import com.alt.lve_tmst_mgmt.dto.LeaveForecastRequest;
import com.alt.lve_tmst_mgmt.dto.LeaveForecastResponse;
import com.alt.lve_tmst_mgmt.repository.EmployeeRepo;
import com.alt.lve_tmst_mgmt.repository.LeaveForecastRepo;
import com.alt.lve_tmst_mgmt.repository.LeaveTypeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LeaveService {

    private final EmployeeRepo employeeRepository;
    private final LeaveTypeRepo leaveTypeRepository;
    private final LeaveForecastRepo leaveForecastRepository;

    @Transactional
    public LeaveForecastResponse applyLeave(LeaveForecastRequest req) {
        Employee employee = employeeRepository.findById(req.getEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException("Employee not found: " + req.getEmployeeId()));

        LeaveType leaveType = leaveTypeRepository.findById(req.getLeaveTypeId())
                .orElseThrow(() -> new IllegalArgumentException("LeaveType not found: " + req.getLeaveTypeId()));

        LeaveForecast lf = LeaveForecast.builder()
                .employee(employee)
                .leaveType(leaveType)
                .startDate(req.getStartDate())
                .endDate(req.getEndDate())
                .comments(req.getComments())
                .build();

        LeaveForecast saved = leaveForecastRepository.save(lf);

        return LeaveForecastResponse.builder()
                .leaveId(saved.getLeaveId())
                .employeeId(saved.getEmployee().getEmployeeId())
                .leaveTypeId(saved.getLeaveType().getLeaveTypeId())
                .startDate(saved.getStartDate())
                .endDate(saved.getEndDate())
                .comments(saved.getComments())
                .build();
    }
}

