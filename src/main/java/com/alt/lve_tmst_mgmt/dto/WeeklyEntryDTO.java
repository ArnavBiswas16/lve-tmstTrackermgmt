package com.alt.lve_tmst_mgmt.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeeklyEntryDTO {

    private LocalDate weekStartDate;
    private LocalDate weekEndDate;
    private BigDecimal totalHours;
}
