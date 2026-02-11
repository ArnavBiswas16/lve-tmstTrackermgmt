package com.alt.lve_tmst_mgmt.dto;

public class UserDashBoardDto {

    private String employeeId;
    private String sowId;
    private String timesheets;
    private String leaves;
    private String holidays;

    public UserDashBoardDto(
            String employeeId,
            String sowId,
            String timesheets,
            String leaves,
            String holidays
    ) {
        this.employeeId = employeeId;
        this.sowId = sowId;
        this.timesheets = timesheets;
        this.leaves = leaves;
        this.holidays = holidays;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getSowId() {
        return sowId;
    }

    public String getTimesheets() {
        return timesheets;
    }

    public String getLeaves() {
        return leaves;
    }

    public String getHolidays() {
        return holidays;
    }


}
