package com.alt.lve_tmst_mgmt.service;

import com.alt.lve_tmst_mgmt.dto.EmployeeMonthlyReviewDto;

import java.time.LocalDate;

public interface EmployeeMonthlyReviewService {
    void saveReview(EmployeeMonthlyReviewDto dto);
    EmployeeMonthlyReviewDto getReviewByUserAndMonth(String employeeId, LocalDate month);


}
