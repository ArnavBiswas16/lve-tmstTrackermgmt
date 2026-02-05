package com.alt.lve_tmst_mgmt.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "EMPLOYEE",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_employee_email", columnNames = "email")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"manager"}) // avoid deep recursion
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Employee {

    @Id
    @Column(name = "employee_id", length = 36, nullable = false, updatable = false)
    @EqualsAndHashCode.Include
    private String employeeId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "email", nullable = false, length = 128)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "location_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_employee_location")
    )
    private Location location;

    @Column(name = "role", nullable = false, length = 60)
    private String role;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "manager_id",
            foreignKey = @ForeignKey(name = "fk_employee_manager")
    )
    private Employee manager;

    @Column(name = "sow_id", length = 36)
    private String sowId;

    @Column(name = "soe_id", length = 36)
    private String soeId;

    @Column(name = "password", nullable = false, length = 255)
    private String password; // store salted/hashed only

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true; // maps TINYINT(1) to Boolean

    @CreationTimestamp
    @Column(name = "createdOn", nullable = false, updatable = false)
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @Column(name = "updatedOn", nullable = false)
    private LocalDateTime updatedOn;
}