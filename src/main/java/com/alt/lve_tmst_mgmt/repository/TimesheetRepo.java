package com.alt.lve_tmst_mgmt.repository;

import com.alt.lve_tmst_mgmt.entity.Employee;
import com.alt.lve_tmst_mgmt.entity.Timesheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface TimesheetRepo extends JpaRepository<Timesheet, Integer> {

    Optional<Timesheet> findByEmployeeAndWorkDate(Employee employee, LocalDate workDate);

    List<Timesheet> findByEmployeeAndWorkDateIn(Employee employee, Collection<LocalDate> dates);

}