package com.alt.lve_tmst_mgmt.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class WeeklyTimesheetResponseDTO {

    private String employeeId;
    private String employeeName;
    private List<WeeklyEntryDTO> timeSheet;
    private Boolean pts;
    private Boolean cofy;
    private Boolean citiTraining;
    private Boolean complianceSubmit;
}

