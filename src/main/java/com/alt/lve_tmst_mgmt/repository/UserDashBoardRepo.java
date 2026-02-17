package com.alt.lve_tmst_mgmt.repository;


import com.alt.lve_tmst_mgmt.dto.UserDashBoardDto;
import com.alt.lve_tmst_mgmt.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface UserDashBoardRepo extends JpaRepository<Employee, String> {

    @Query(value = """
            SELECT
                  e.employee_id,
                  e.sow_id,
                  COALESCE(ts.timesheets, JSON_ARRAY()) AS timesheets,
                  COALESCE(lf.leaves, JSON_ARRAY()) AS leaves,
                  COALESCE(h.holidays, JSON_ARRAY()) AS holidays
    
                  FROM employee e
        
                  LEFT JOIN (
                      SELECT
                          employee_id,
                          JSON_ARRAYAGG(
                              JSON_OBJECT(
                                  'workDate', work_date,
                                  'hoursLogged', hours_logged
                              )
                          ) AS timesheets
                      FROM timesheet
                      WHERE work_date BETWEEN :monthStart AND :monthEnd
                      GROUP BY employee_id
                  ) ts ON ts.employee_id = e.employee_id
        
                  LEFT JOIN (
                      SELECT
                          employee_id,
                          JSON_ARRAYAGG(
                              JSON_OBJECT(
                                  'startDate', start_date,
                                  'leaveTypeId', leave_type_id,
                                  'comments', comments
                              )
                          ) AS leaves
                      FROM leave_forecast
                      WHERE start_date BETWEEN :monthStart AND :monthEnd
                      GROUP BY employee_id
                  ) lf ON lf.employee_id = e.employee_id
        
                  LEFT JOIN (
                      SELECT
                          location_id,
                          JSON_ARRAYAGG(
                              JSON_OBJECT(
                                  'date', date,
                                  'name', name,
                                  'type', type
                              )
                          ) AS holidays
                      FROM holiday
                      WHERE date BETWEEN :monthStart AND :monthEnd
                      GROUP BY location_id
                  ) h ON h.location_id = e.location_id
        
                  WHERE e.employee_id = :userId
                    AND e.is_active = 1
        
                  ORDER BY e.name;
            
            
       """, nativeQuery = true)
    UserDashBoardDto fetchDashBoard(
            @Param("userId") String userId,
            @Param("monthStart") LocalDate monthStart,
            @Param("monthEnd") LocalDate monthEnd
    );
}



