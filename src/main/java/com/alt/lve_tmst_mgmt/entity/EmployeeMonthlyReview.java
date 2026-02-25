package com.alt.lve_tmst_mgmt.entity;
import com.alt.lve_tmst_mgmt.dto.ComplianceId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "employee_monthly_review")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmployeeMonthlyReview {

    @EmbeddedId
    private ComplianceId id;
    @Column(name = "what_went_well", columnDefinition = "TEXT")
    private String whatWentWell;

    @Column(name = "improvements_needed", columnDefinition = "TEXT")
    private String improvementsNeeded;

    @Column(name = "blockers_challenges", columnDefinition = "TEXT")
    private String blockersChallenges;

    @Column(name = "things_to_try", columnDefinition = "TEXT")
    private String thingsToTry;

    @Column(name = "client_appreciation", columnDefinition = "TEXT")
    private String clientAppreciation;

    @Column(name = "key_achievements", columnDefinition = "TEXT")
    private String keyAchievements;

    @Column(name = "updated_at", nullable = false, insertable = false, updatable = false)
    private LocalDateTime updatedAt;
}
