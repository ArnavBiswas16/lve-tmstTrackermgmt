package com.alt.lve_tmst_mgmt.repository;

import com.alt.lve_tmst_mgmt.dto.ComplianceId;
import com.alt.lve_tmst_mgmt.entity.MonthlyCompliance;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

public interface MonthlyComplianceRepository extends JpaRepository<MonthlyCompliance, ComplianceId> {

    @Modifying
    @Transactional
    @Query(value = """
       INSERT INTO MONTHLY_COMPLIANCE (
         employee_id, `month`, pts_saved, cofy_update, citi_training
       ) VALUES (
         :employeeId, :month, :pts, :cofy, :citi
       )
       ON DUPLICATE KEY UPDATE
         pts_saved = VALUES(pts_saved),
         cofy_update = VALUES(cofy_update),
         citi_training = VALUES(citi_training),
         updated_at = CURRENT_TIMESTAMP
       """, nativeQuery = true)
    int upsert(@Param("employeeId") String employeeId,
               @Param("month") LocalDate month,
               @Param("pts") boolean pts,
               @Param("cofy") boolean cofy,
               @Param("citi") boolean citi);
}