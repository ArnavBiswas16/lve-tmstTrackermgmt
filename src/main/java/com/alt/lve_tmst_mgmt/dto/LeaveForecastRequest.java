package com.alt.lve_tmst_mgmt.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveForecastRequest {

    @NotBlank
    private String employeeId;

    @NotNull
    private Integer leaveTypeId;

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime endDate;

    @Size(max = 255)
    private String comments;
}