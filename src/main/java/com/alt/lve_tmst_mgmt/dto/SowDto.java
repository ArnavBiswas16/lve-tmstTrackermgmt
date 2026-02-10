package com.alt.lve_tmst_mgmt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SowDto {
    private String sowId;
    private String sowName;
    private String managerId;
}
