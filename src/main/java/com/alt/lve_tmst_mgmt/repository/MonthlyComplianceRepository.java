package com.alt.lve_tmst_mgmt.repository;

import com.alt.lve_tmst_mgmt.dto.ComplianceId;
import com.alt.lve_tmst_mgmt.entity.MonthlyCompliance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

public interface MonthlyComplianceRepository
        extends JpaRepository<MonthlyCompliance, ComplianceId> {

    @Transactional
    default MonthlyCompliance upsert(String employeeId,
                                     LocalDate month,
                                     boolean pts,
                                     boolean cofy,
                                     boolean citi) {

        ComplianceId id = new ComplianceId(employeeId, month);

        Optional<MonthlyCompliance> optional = findById(id);

        MonthlyCompliance entity = optional.orElseGet(() -> {
            MonthlyCompliance mc = new MonthlyCompliance();
            mc.setId(id);
            return mc;
        });

        entity.setPtsSaved(pts);
        entity.setCofyUpdate(cofy);
        entity.setCitiTraining(citi);

        return save(entity);
    }
}
