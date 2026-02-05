package com.alt.lve_tmst_mgmt.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(
        name = "EMPLOYEE_SOW_ASSIGNMENT",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_emp_sow", columnNames = {"employee_id", "sow_id"})
        },
        indexes = {
                @Index(name = "idx_esa_emp", columnList = "employee_id"),
                @Index(name = "idx_esa_sow", columnList = "sow_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"employee", "sow"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EmployeeSowAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_proj_id", nullable = false, updatable = false)
    @EqualsAndHashCode.Include
    private Integer employeeProjId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false, foreignKey = @ForeignKey(name = "fk_esa_employee"))
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sow_id", nullable = false, foreignKey = @ForeignKey(name = "fk_esa_sow"))
    private Sow sow;

    @Column(name = "project_startdate", nullable = false)
    private LocalDate projectStartdate;
}