package com.alt.lve_tmst_mgmt.dto;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SaveComplianceResponse {
    private String userId;
    private String month;     // YYYY-MM (echo back)
    private boolean pts;
    private boolean cofy;
    private boolean citiTraining;
    private boolean complianceSubmit;
}