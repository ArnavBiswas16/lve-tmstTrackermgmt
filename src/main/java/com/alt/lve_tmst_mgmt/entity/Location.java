package com.alt.lve_tmst_mgmt.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "LOCATION",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_location_country_city",
                        columnNames = {"country", "city"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id", nullable = false, updatable = false)
    @EqualsAndHashCode.Include
    private Integer locationId;

    @Column(name = "country", nullable = false, length = 60)
    private String country;

    @Column(name = "city", nullable = false, length = 60)
    private String city;
}
