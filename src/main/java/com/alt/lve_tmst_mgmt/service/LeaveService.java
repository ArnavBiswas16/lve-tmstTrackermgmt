package com.alt.lve_tmst_mgmt.service;

import com.alt.lve_tmst_mgmt.entity.Employee;
import com.alt.lve_tmst_mgmt.entity.LeaveForecast;
import com.alt.lve_tmst_mgmt.dto.LeaveForecastRequest;
import com.alt.lve_tmst_mgmt.dto.LeaveForecastResponse;
import com.alt.lve_tmst_mgmt.repository.EmployeeRepository;
import com.alt.lve_tmst_mgmt.repository.LeaveForecastRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LeaveService {

    private final EmployeeRepository employeeRepository;
    private final LeaveForecastRepo leaveForecastRepository;

    @Transactional
    public LeaveForecastResponse applyLeave(LeaveForecastRequest req) {
        Employee employee = employeeRepository.findById(req.getEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException("Employee not found: " + req.getEmployeeId()));

        LeaveForecast lf = LeaveForecast.builder()
                .employee(employee)
                .startDate(req.getStartDate().toLocalDate())
                .build();

        LeaveForecast saved = leaveForecastRepository.save(lf);

        return LeaveForecastResponse.builder()
                .leaveId(saved.getLeaveId())
                .employeeId(saved.getEmployee().getEmployeeId())
                .build();
    }
}

