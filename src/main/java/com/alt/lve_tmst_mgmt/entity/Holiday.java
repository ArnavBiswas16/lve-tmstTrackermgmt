package com.alt.lve_tmst_mgmt.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(
        name = "HOLIDAY",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_holiday_date_location", columnNames = {"date", "location_id"})
        },
        indexes = {
                @Index(name = "idx_holiday_location", columnList = "location_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "location")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Holiday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "holiday_id", nullable = false, updatable = false)
    @EqualsAndHashCode.Include
    private Integer holidayId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "`type`", nullable = false, length = 40)
    private String type;

    @Column(name = "`date`", nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "location_id", nullable = false, foreignKey = @ForeignKey(name = "fk_holiday_location"))
    private Location location;
}
