package com.alt.lve_tmst_mgmt.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class LeaveDayDTO {

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}