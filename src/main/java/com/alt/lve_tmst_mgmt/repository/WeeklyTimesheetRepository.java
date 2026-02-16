package com.alt.lve_tmst_mgmt.repository;

import com.alt.lve_tmst_mgmt.entity.WeeklyTimesheet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface WeeklyTimesheetRepository
        extends JpaRepository<WeeklyTimesheet, Integer> {

    Optional<WeeklyTimesheet> findByEmployeeEmployeeIdAndWeekStartDate(
            String employeeId, LocalDate weekStartDate);
}
