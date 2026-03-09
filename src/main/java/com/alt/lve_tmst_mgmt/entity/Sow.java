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


    public String getSowId() {
        return sowId;
    }

    public void setSowId(String sowId) {
        this.sowId = sowId;
    }

    public String getSowName() {
        return sowName;
    }

    public void setSowName(String sowName) {
        this.sowName = sowName;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }
}