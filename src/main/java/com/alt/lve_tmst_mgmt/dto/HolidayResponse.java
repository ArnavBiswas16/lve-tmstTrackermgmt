package com.alt.lve_tmst_mgmt.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
// @Schema(name = "HolidayResponse", description = "Holiday details with only the location city")
public class HolidayResponse {
        Integer holidayId;
        String name;
        String type;
        LocalDate date;
        String location;
}
