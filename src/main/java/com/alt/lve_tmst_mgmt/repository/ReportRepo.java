package com.alt.lve_tmst_mgmt.repository;

import com.alt.lve_tmst_mgmt.dto.MonthlyEmployeeReportDto;
import com.alt.lve_tmst_mgmt.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReportRepo extends JpaRepository<Employee, String>{

    @Query(value = """
            SELECT
                                      e.employee_id,
                                      e.name,
                                      e.email,
                                      e.role,
                                      e.soe_id,
                                      loc.city AS location,
                                      esa.project_startdate AS assignmentStartDate,
                                      (
                                          SELECT COALESCE(
                                              JSON_ARRAYAGG(
                                                  JSON_OBJECT(
                                                      'workDate', ts.work_date,
                                                      'hoursLogged', ts.hours_logged
                                                  )
                                              ),
                                              JSON_ARRAY()
                                          )
                                          FROM timesheet ts
                                          WHERE ts.employee_id = e.employee_id
                                            AND ts.work_date BETWEEN :monthStart AND :monthEnd
                                      ) AS timesheets,
            
                                      (
                                          SELECT COALESCE(
                                              JSON_ARRAYAGG(
                                                  JSON_OBJECT(
                                                      'startDate', lf.start_date,
                                                      'leaveTypeId', lf.leave_type_id,
                                                      'comments', lf.comments
                                                  )
                                              ),
                                              JSON_ARRAY()
                                          )
                                          FROM leave_forecast lf
                                          WHERE lf.employee_id = e.employee_id
                                            AND lf.start_date BETWEEN :monthStart AND :monthEnd
                                      ) AS leaves,
            
                                      (
                                          SELECT COALESCE(
                                              JSON_ARRAYAGG(
                                                  JSON_OBJECT(
                                                      'date', h.date,
                                                      'name', h.name,
                                                      'type', h.type
                                                  )
                                              ),
                                              JSON_ARRAY()
                                          )
                                          FROM holiday h
                                          WHERE h.location_id = e.location_id
                                            AND h.date BETWEEN :monthStart AND :monthEnd
                                      ) AS holidays,
            
                                      (
                                          SELECT COALESCE(SUM(ts.hours_logged), 0)
                                          FROM timesheet ts
                                          WHERE ts.employee_id = e.employee_id
                                            AND ts.work_date BETWEEN :monthStart AND :monthEnd
                                      ) AS totalHours,
            
                                      (
                                          SELECT COUNT(*)
                                          FROM leave_forecast lf
                                          WHERE lf.employee_id = e.employee_id
                                            AND lf.start_date BETWEEN :monthStart AND :monthEnd
                                      ) AS numberOfLeaves,
            
                                      (
                                          SELECT COUNT(*)
                                          FROM timesheet ts
                                          WHERE ts.employee_id = e.employee_id
                                            AND ts.work_date BETWEEN :monthStart AND :monthEnd
                                            AND ts.hours_logged <= 4
                                      ) AS numberOfHalfDays,
            
                                      (
                                          SELECT COUNT(*)
                                          FROM holiday h
                                          WHERE h.location_id = e.location_id
                                            AND h.date BETWEEN :monthStart AND :monthEnd
                                      ) AS numberOfHolidays,
            
                                      (
                                          SELECT COALESCE(
                                              JSON_ARRAYAGG(
                                                  JSON_OBJECT(
                                                      'weekStart', wt.week_start_date,
                                                      'hours', wt.total_hours  -- fixed column name from weekly_timesheet
                                                  )
                                              ),
                                              JSON_ARRAY()
                                          )
                                          FROM weekly_timesheet wt
                                          WHERE wt.employee_id = e.employee_id
                                            AND wt.week_start_date BETWEEN :monthStart AND :monthEnd
                                      ) AS weeklyHours,
            
                                      mc.pts_saved,
                                      mc.cofy_update,
                                      mc.citi_training
            
                                  FROM employee e
                                  JOIN location loc
                                      ON loc.location_id = e.location_id
            
                                  LEFT JOIN EMPLOYEE_SOW_ASSIGNMENT esa
                                      ON esa.employee_id = e.employee_id  -- assignment table join
            
                                  LEFT JOIN monthly_compliance mc
                                      ON mc.employee_id = e.employee_id
                                     AND mc.month = :monthStart
            
                                  WHERE e.sow_id = :sowId
                                    AND e.is_active = 1
            
                                  ORDER BY e.name;
            
            
       """, nativeQuery = true)
    List<MonthlyEmployeeReportDto> fetchReport(
            @Param("sowId") String sowId,
            @Param("monthStart") LocalDate monthStart,
            @Param("monthEnd") LocalDate monthEnd
    );
}
