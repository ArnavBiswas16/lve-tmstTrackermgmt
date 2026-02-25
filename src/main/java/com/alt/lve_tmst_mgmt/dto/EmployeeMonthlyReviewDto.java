package com.alt.lve_tmst_mgmt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeMonthlyReviewDto {
    private String employeeId;
    private LocalDate month;
    private String whatWentWell;
    private String improvementsNeeded;
    private String blockersChallenges;
    private String thingsToTry;
    private String clientAppreciation;
    private String keyAchievements;
}
