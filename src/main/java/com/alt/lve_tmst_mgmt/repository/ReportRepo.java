package com.alt.lve_tmst_mgmt.repository;

import com.alt.lve_tmst_mgmt.dto.FinancialTrackerReportDto;
import com.alt.lve_tmst_mgmt.dto.MonthlyEmployeeReportDto;
import com.alt.lve_tmst_mgmt.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReportRepo extends JpaRepository<Employee, String> {

    // BASE QUERY (USED FOR BOTH PAGINATION & EXPORT)
    String BASE_REPORT_QUERY = """
        SELECT
            e.employee_id,
            e.name,
            e.email,
            e.role,
            e.soe_id,
            loc.city AS location,
            e.sow_id,
            esa.project_startdate AS assignmentStartDate,

            -- ================= Timesheets =================
            (
                SELECT COALESCE(
                    JSON_ARRAYAGG(
                        JSON_OBJECT(
                            'workDate', ts.work_date,
                            'hoursLogged', ts.hours_logged
                        )
                        ORDER BY ts.work_date
                    ),
                    JSON_ARRAY()
                )
                FROM timesheet ts
                WHERE ts.employee_id = e.employee_id
                  AND ts.work_date BETWEEN :monthStart AND :monthEnd
            ) AS timesheets,

            -- ================= Leaves =================
            (
                SELECT COALESCE(
                    JSON_ARRAYAGG(
                        JSON_OBJECT(
                            'startDate', lf.leave_date
                        )
                        ORDER BY lf.leave_date
                    ),
                    JSON_ARRAY()
                )
                FROM leave_forecast lf
                WHERE lf.employee_id = e.employee_id
                  AND lf.leave_date BETWEEN :monthStart AND :monthEnd
            ) AS leaves,

            -- ================= Holidays =================
            (
                SELECT COALESCE(
                    JSON_ARRAYAGG(
                        JSON_OBJECT(
                            'date', h.date,
                            'name', h.name,
                            'type', h.type
                        )
                        ORDER BY h.date
                    ),
                    JSON_ARRAY()
                )
                FROM holiday h
                WHERE h.location_id = e.location_id
                  AND h.date BETWEEN :monthStart AND :monthEnd
            ) AS holidays,

            -- ================= Totals =================
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
                  AND lf.leave_date BETWEEN :monthStart AND :monthEnd
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

            -- ================= Weekly Hours =================
            (
                SELECT COALESCE(
                    JSON_ARRAYAGG(
                        JSON_OBJECT(
                            'weekStart', wt.week_start_date,
                            'hours', wt.total_hours
                        )
                        ORDER BY wt.week_start_date
                    ),
                    JSON_ARRAY()
                )
                FROM weekly_timesheet wt
                WHERE wt.employee_id = e.employee_id
                  AND wt.week_start_date BETWEEN :monthStart AND :monthEnd
            ) AS weeklyHours,

            -- ================= Compliance =================
            mc.pts_saved,
            mc.cofy_update,
            mc.citi_training

        FROM employee e

        JOIN location loc
            ON loc.location_id = e.location_id

        LEFT JOIN employee_sow_assignment esa
            ON esa.employee_id = e.employee_id

        LEFT JOIN monthly_compliance mc
            ON mc.employee_id = e.employee_id
           AND mc.month = :monthStart

        WHERE e.sow_id = :sowId
          AND e.is_active = 1
    """;


    // PAGINATED REPORT
    @Query(
            value = BASE_REPORT_QUERY + " ORDER BY e.name",
            countQuery = """
            SELECT COUNT(*)
            FROM employee e
            WHERE e.sow_id = :sowId
              AND e.is_active = 1
        """,
            nativeQuery = true
    )
    Page<MonthlyEmployeeReportDto> fetchReport(
            @Param("sowId") String sowId,
            @Param("monthStart") LocalDate monthStart,
            @Param("monthEnd") LocalDate monthEnd,
            Pageable pageable
    );


    // EXPORT (NO PAGINATION)
    @Query(
            value = BASE_REPORT_QUERY + " ORDER BY e.name",
            nativeQuery = true
    )
    List<MonthlyEmployeeReportDto> exportCompleteMonthlySOWReport(
            @Param("sowId") String sowId,
            @Param("monthStart") LocalDate monthStart,
            @Param("monthEnd") LocalDate monthEnd
    );

    String FINANCIAL_TRACKER_BASE_QUERY ="""
    SELECT
        s.sow_name         AS sowName,
        s.sow_id           AS sowId,
        e.soe_id           AS soeId,
        e.employee_id      AS employeeId,
        e.name             AS name,
        COALESCE(SUM(wt.total_hours), 0) AS ptsTotalMonthlyHours,
        COALESCE(COUNT(DISTINCT lf.leave_date), 0) AS totalLeaves

        FROM employee e
    
        JOIN sow s
            ON s.sow_id = e.sow_id
    
        LEFT JOIN weekly_timesheet wt
            ON wt.employee_id = e.employee_id
           AND wt.week_start_date BETWEEN :monthStart AND :monthEnd
    
        LEFT JOIN leave_forecast lf
            ON lf.employee_id = e.employee_id
           AND lf.leave_date BETWEEN :monthStart AND :monthEnd
    
        
        """;


    @Query(value = FINANCIAL_TRACKER_BASE_QUERY + """
        WHERE e.sow_id = :sowId
          AND e.is_active = 1
    
        GROUP BY
            s.sow_name,
            s.sow_id,
            e.soe_id,
            e.employee_id,
            e.name
    
        ORDER BY e.name
    """, nativeQuery = true)
    List<FinancialTrackerReportDto> fetchMonthlyFinancialTrackerBySOW(
            @Param("sowId") String sowId,
            @Param("monthStart") LocalDate monthStart,
            @Param("monthEnd") LocalDate monthEnd
    );

    @Query(value = FINANCIAL_TRACKER_BASE_QUERY + """
        WHERE e.is_active = 1
    
        GROUP BY
            s.sow_name,
            s.sow_id,
            e.soe_id,
            e.employee_id,
            e.name
    
        ORDER BY s.sow_id, e.name
    """, nativeQuery = true)
    List<FinancialTrackerReportDto> fetchMonthlyFinancialTracker(
            @Param("monthStart") LocalDate monthStart,
            @Param("monthEnd") LocalDate monthEnd
    );
}