package com.alt.lve_tmst_mgmt.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "HISTORY_TIMESHEET")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"timesheet", "employee"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class HistoryTimesheet {

    @Id
    @Column(name = "timesheet_id", nullable = false, updatable = false)
    @EqualsAndHashCode.Include
    private Integer timesheetId;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false, foreignKey = @ForeignKey(name = "fk_hist_ts_employee"))
    private Employee employee;

    @Column(name = "work_date", nullable = false)
    private LocalDate workDate;

    @Column(name = "hours_logged", nullable = false, precision = 5, scale = 2)
    private BigDecimal hoursLogged;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
