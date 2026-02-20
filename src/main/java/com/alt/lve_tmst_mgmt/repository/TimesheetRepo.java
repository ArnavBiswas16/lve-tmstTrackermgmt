package com.alt.lve_tmst_mgmt.repository;

import com.alt.lve_tmst_mgmt.entity.Employee;
import com.alt.lve_tmst_mgmt.entity.Timesheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface TimesheetRepo extends JpaRepository<Timesheet, Integer> {

    Optional<Timesheet> findByEmployeeAndWorkDate(Employee employee, LocalDate workDate);

    List<Timesheet> findByEmployeeAndWorkDateIn(Employee employee, Collection<LocalDate> dates);

    @Query("""
        SELECT COALESCE(SUM(t.hoursLogged), 0)
        FROM Timesheet t
        WHERE t.employee.employeeId = :employeeId
        AND t.workDate BETWEEN :startDate AND :endDate
    """)
    BigDecimal getTotalHoursForMonth(
            @Param("employeeId") String employeeId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    void deleteByEmployeeAndWorkDateIn(Employee employee, Collection<LocalDate> dates);

}