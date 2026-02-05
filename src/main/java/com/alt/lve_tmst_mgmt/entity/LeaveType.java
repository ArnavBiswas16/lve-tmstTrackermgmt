package com.alt.lve_tmst_mgmt.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "LEAVE_TYPE",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_leave_type", columnNames = "leave_type")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class LeaveType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "leave_type_id", nullable = false, updatable = false)
    @EqualsAndHashCode.Include
    private Integer leaveTypeId;

    @Column(name = "leave_type", nullable = false, length = 60)
    private String leaveType;
}
