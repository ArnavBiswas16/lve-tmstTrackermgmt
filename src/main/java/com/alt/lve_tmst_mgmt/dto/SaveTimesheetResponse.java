package com.alt.lve_tmst_mgmt.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class SaveTimesheetResponse {

    private String emp_id;
    private String status;

    public SaveTimesheetResponse(String emp_id,
                                 String status){
        this.emp_id = emp_id;
        this.status = status;
    }

}
