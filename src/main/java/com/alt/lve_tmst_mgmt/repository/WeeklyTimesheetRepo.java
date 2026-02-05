package com.alt.lve_tmst_mgmt.repository;

import com.alt.lve_tmst_mgmt.entity.WeeklyTimesheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeeklyTimesheetRepo extends JpaRepository<WeeklyTimesheet, Long> {
}