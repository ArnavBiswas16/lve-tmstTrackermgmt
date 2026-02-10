package com.alt.lve_tmst_mgmt.dto;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Access(AccessType.FIELD)
public class ComplianceId implements Serializable {

    @Column(name = "employee_id", nullable = false, length = 36)
    private String employeeId;

    @Column(name = "month", nullable = false)
    private LocalDate month;
}