package com.alt.lve_tmst_mgmt.repository;
import com.alt.lve_tmst_mgmt.entity.Sow;
import io.micrometer.common.KeyValues;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SowRepository extends JpaRepository<Sow, String> {
    List<Sow> findByManager_EmployeeId(String managerId);

}
