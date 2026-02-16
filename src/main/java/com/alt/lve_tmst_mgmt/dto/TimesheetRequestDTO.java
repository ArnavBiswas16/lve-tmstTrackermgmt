package com.alt.lve_tmst_mgmt.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimesheetRequestDTO {

    private String employeeId;

    // Format: yyyy-MM (Ex: 2026-02)
    private String month;

    // Optional fields
    private Boolean pts;
    private Boolean cofy;
    private Boolean citiTraining;

    private List<WeeklyEntryDTO> timeSheet;
}
