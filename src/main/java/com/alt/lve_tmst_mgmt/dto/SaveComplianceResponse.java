package com.alt.lve_tmst_mgmt.dto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaveComplianceResponse {
    private String userId;
    private String month;     // YYYY-MM (echo back)
    private boolean pts;
    private boolean cofy;
    private boolean citiTraining;
}