package com.alt.lve_tmst_mgmt.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class MonthlyEmployeeReportDto {

    private String employeeId;
    private String name;
    private String email;
    private String role;
    private String soeId;
    private String location;
    private String sowId;
    private java.sql.Date assignmentStartDate;

    private String timesheets;          // JSON_ARRAYAGG(...) AS timesheets
    private String leaves;              // JSON_ARRAYAGG(...) AS leaves
    private String holidays;            // JSON_ARRAYAGG(...) AS holidays
    private String weeklyHours;         // JSON_ARRAYAGG(...) AS weeklyHours

    private BigDecimal totalHours;
    private Long numberOfLeaves;
    private Long numberOfHalfDays;
    private Long numberOfHolidays;

    private Boolean ptsSaved;
    private Boolean cofyUpdate;
    private Boolean citiTraining;

    public MonthlyEmployeeReportDto(
            String employeeId,
            String name,
            String email,
            String role,
            String soeId,
            String location,
            String sowId,
            java.sql.Date assignmentStartDate,
            String timesheets,
            String leaves,
            String holidays,
            BigDecimal totalHours,
            Long numberOfLeaves,
            Long numberOfHalfDays,
            Long numberOfHolidays,
            String weeklyHours,
            Boolean ptsSaved,
            Boolean cofyUpdate,
            Boolean citiTraining
    ) {
        this.employeeId = employeeId;
        this.name = name;
        this.email = email;
        this.role = role;
        this.soeId = soeId;
        this.location = location;
        this.sowId = sowId;
        this.assignmentStartDate = assignmentStartDate;
        this.timesheets = timesheets;
        this.leaves = leaves;
        this.holidays = holidays;
        this.totalHours = totalHours;
        this.numberOfLeaves = numberOfLeaves;
        this.numberOfHalfDays = numberOfHalfDays;
        this.numberOfHolidays = numberOfHolidays;
        this.weeklyHours = weeklyHours;
        this.ptsSaved = ptsSaved;
        this.cofyUpdate = cofyUpdate;
        this.citiTraining = citiTraining;
    }

    public java.sql.Date getAssignmentStartDate() {
        return assignmentStartDate;
    }

    public void setAssignmentStartDate(java.sql.Date assignmentStartDate) {
        this.assignmentStartDate = assignmentStartDate;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSoeId() {
        return soeId;
    }

    public void setSoeId(String soeId) {
        this.soeId = soeId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTimesheets() {
        return timesheets;
    }

    public void setTimesheets(String timesheets) {
        this.timesheets = timesheets;
    }

    public String getLeaves() {
        return leaves;
    }

    public void setLeaves(String leaves) {
        this.leaves = leaves;
    }

    public String getHolidays() {
        return holidays;
    }

    public void setHolidays(String holidays) {
        this.holidays = holidays;
    }

    public String getWeeklyHours() {
        return weeklyHours;
    }

    public void setWeeklyHours(String weeklyHours) {
        this.weeklyHours = weeklyHours;
    }

    public BigDecimal getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(BigDecimal totalHours) {
        this.totalHours = totalHours;
    }

    public Long getNumberOfLeaves() {
        return numberOfLeaves;
    }

    public void setNumberOfLeaves(Long numberOfLeaves) {
        this.numberOfLeaves = numberOfLeaves;
    }

    public Long getNumberOfHalfDays() {
        return numberOfHalfDays;
    }

    public void setNumberOfHalfDays(Long numberOfHalfDays) {
        this.numberOfHalfDays = numberOfHalfDays;
    }

    public Long getNumberOfHolidays() {
        return numberOfHolidays;
    }

    public void setNumberOfHolidays(Long numberOfHolidays) {
        this.numberOfHolidays = numberOfHolidays;
    }

    public Boolean getPtsSaved() {
        return ptsSaved;
    }

    public void setPtsSaved(Boolean ptsSaved) {
        this.ptsSaved = ptsSaved;
    }

    public Boolean getCofyUpdate() {
        return cofyUpdate;
    }

    public void setCofyUpdate(Boolean cofyUpdate) {
        this.cofyUpdate = cofyUpdate;
    }

    public Boolean getCitiTraining() {
        return citiTraining;
    }

    public void setCitiTraining(Boolean citiTraining) {
        this.citiTraining = citiTraining;
    }

    public String getSowId() {
        return sowId;
    }

    public void setSowId(String sowId) {
        this.sowId = sowId;
    }

    // getters and setters ...
}
