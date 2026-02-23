package com.alt.lve_tmst_mgmt.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeeklyTimesheetRequestDTO {

    private String employeeId;

    // Format: yyyy-MM (Ex: 2026-02)
    private String month;

    // Optional fields
    private Boolean pts;
    private Boolean cofy;
    private Boolean citiTraining;
    private Boolean complianceSubmit;

    private List<WeeklyEntryDTO> timeSheet;
}
