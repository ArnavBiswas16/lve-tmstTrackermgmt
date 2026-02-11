package com.alt.lve_tmst_mgmt.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "LEAVE_FORECAST",
        indexes = {
                @Index(name = "idx_lf_employee", columnList = "employee_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"employee"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class LeaveForecast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "leave_id", nullable = false, updatable = false)
    @EqualsAndHashCode.Include
    private Integer leaveId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false, foreignKey = @ForeignKey(name = "fk_lf_employee"))
    private Employee employee;

    @Column(name = "leave_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "last_updated_at", nullable = false, insertable = false, updatable = false)
    private LocalDateTime lastUpdatedAt;
}
