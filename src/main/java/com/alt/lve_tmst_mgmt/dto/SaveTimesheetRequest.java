package com.alt.lve_tmst_mgmt.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SaveTimesheetRequest {

    @NotBlank
    private String employeeId;

    @NotNull
    @Valid
    private List<TimesheetDayDTO> timesheet;

    @NotNull
    @Valid
    private List<LeaveDayDTO> leaveForecast;

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public List<TimesheetDayDTO> getTimesheet() { return timesheet; }
    public void setTimesheet(List<TimesheetDayDTO> timesheet) { this.timesheet = timesheet; }

    public List<LeaveDayDTO> getLeaveForecast() { return leaveForecast; }
    public void setLeaveForecast(List<LeaveDayDTO> leaveForecast) { this.leaveForecast = leaveForecast; }
}