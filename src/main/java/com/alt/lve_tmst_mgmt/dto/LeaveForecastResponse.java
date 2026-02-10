package com.alt.lve_tmst_mgmt.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveForecastResponse {

    private Integer leaveId;
    private String employeeId;
    private Integer leaveTypeId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String comments;
}