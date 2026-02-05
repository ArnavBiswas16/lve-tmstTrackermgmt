package com.alt.lve_tmst_mgmt.entity;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "MONTHLY_COMPLIANCE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "employee")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MonthlyCompliance {

    @Id
    @Column(name = "employee_id", length = 36, nullable = false, updatable = false)
    @EqualsAndHashCode.Include
    private String employeeId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    @JoinColumn(name = "employee_id", nullable = false, foreignKey = @ForeignKey(name = "fk_mc_employee"))
    private Employee employee;

    @Column(name = "pts_saved", nullable = false)
    private Boolean ptsSaved;

    @Column(name = "cofy_update", nullable = false)
    private Boolean cofyUpdate;

    @Column(name = "citi_training", nullable = false)
    private Boolean citiTraining;

    @Column(name = "updated_at", nullable = false, insertable = false, updatable = false)
    private LocalDateTime updatedAt;
}
