package com.alt.lve_tmst_mgmt.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "SOW",
        indexes = {
                @Index(name = "idx_sow_manager", columnList = "manager_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "manager")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Sow {

    @Id
    @Column(name = "sow_id", length = 36, nullable = false, updatable = false)
    @EqualsAndHashCode.Include
    private String sowId;

    @Column(name = "sow_name", length = 120, nullable = false)
    private String sowName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "manager_id",
            foreignKey = @ForeignKey(name = "fk_sow_manager")
    )
    private Employee manager;


}