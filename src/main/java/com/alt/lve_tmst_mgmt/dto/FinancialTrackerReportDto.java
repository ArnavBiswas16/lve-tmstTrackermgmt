package com.alt.lve_tmst_mgmt.dto;

import java.math.BigDecimal;

public class FinancialTrackerReportDto {


    private String sowName;
    private String sowId;
    private String soeId;
    private String employeeId;
    private String name;
    private BigDecimal ptsTotalMonthlyHours;
    private Long totalLeaves;

    public FinancialTrackerReportDto(String sowName, String sowId, String soeId, String employeeId, String name, BigDecimal ptsTotalMonthlyHours, Long totalLeaves) {
        this.sowName = sowName;
        this.sowId = sowId;
        this.soeId = soeId;
        this.employeeId = employeeId;
        this.name = name;
        this.ptsTotalMonthlyHours = ptsTotalMonthlyHours;
        this.totalLeaves = totalLeaves;
    }

    public String getSowName() {
        return sowName;
    }

    public void setSowName(String sowName) {
        this.sowName = sowName;
    }

    public String getSowId() {
        return sowId;
    }

    public void setSowId(String sowId) {
        this.sowId = sowId;
    }

    public String getSoeId() {
        return soeId;
    }

    public void setSoeId(String soeId) {
        this.soeId = soeId;
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

    public BigDecimal getPtsTotalMonthlyHours() {
        return ptsTotalMonthlyHours;
    }

    public void setPtsTotalMonthlyHours(BigDecimal ptsTotalMonthlyHours) {
        this.ptsTotalMonthlyHours = ptsTotalMonthlyHours;
    }

    public Long getTotalLeaves() {
        return totalLeaves;
    }

    public void setTotalLeaves(Long totalLeaves) {
        this.totalLeaves = totalLeaves;
    }
}
