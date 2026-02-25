package com.alt.lve_tmst_mgmt.repository;

import com.alt.lve_tmst_mgmt.dto.ComplianceId;
import com.alt.lve_tmst_mgmt.entity.EmployeeMonthlyReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeMonthlyReviewRepository extends JpaRepository<EmployeeMonthlyReview, ComplianceId> {
    List<EmployeeMonthlyReview> findByIdEmployeeId(String employeeId);
    List<EmployeeMonthlyReview> findByIdMonth(LocalDate month);

}
