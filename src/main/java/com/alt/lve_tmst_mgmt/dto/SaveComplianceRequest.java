package com.alt.lve_tmst_mgmt.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SaveComplianceRequest {

    @NotBlank
    private String userId;

    @NotBlank
    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])$", message = "month must be YYYY-MM")
    private String month;

    @NotNull
    private Boolean pts;

    @NotNull
    private Boolean cofy;

    @NotNull
    private Boolean citiTraining;

    @NotNull
    private Boolean complianceSubmit;

}