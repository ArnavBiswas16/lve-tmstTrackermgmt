package com.alt.lve_tmst_mgmt.entity;

import com.alt.lve_tmst_mgmt.dto.ComplianceId;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "MONTHLY_COMPLIANCE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Access(AccessType.FIELD)
public class MonthlyCompliance {

    @EmbeddedId
    private ComplianceId id;

    @Column(name = "pts_saved", nullable = false)
    private boolean ptsSaved;

    @Column(name = "cofy_update", nullable = false)
    private boolean cofyUpdate;

    @Column(name = "citi_training", nullable = false)
    private boolean citiTraining;

    @Column(name = "compliance_submit", nullable = false)
    private Boolean complianceSubmit;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;

}
