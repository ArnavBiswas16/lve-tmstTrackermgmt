package com.alt.lve_tmst_mgmt.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "WEEKLY_TIMESHEET",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_weekly_timesheet_emp_start", columnNames = {"employee_id", "week_start_date"})
        },
        indexes = {
                @Index(name = "idx_wt_employee", columnList = "employee_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "employee")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class WeeklyTimesheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "weekly_timesheet_id", nullable = false, updatable = false)
    @EqualsAndHashCode.Include
    private Integer weeklyTimesheetId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false, foreignKey = @ForeignKey(name = "fk_wt_employee"))
    private Employee employee;

    @Column(name = "week_start_date", nullable = false)
    private LocalDate weekStartDate;

    @Column(name = "week_end_date", nullable = false)
    private LocalDate weekEndDate;

    @Column(name = "total_hours", nullable = false, precision = 6, scale = 2)
    private BigDecimal totalHours;

    @Column(name = "expected_hours", nullable = false, precision = 6, scale = 2)
    private BigDecimal expectedHours;

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;
}
