package com.alt.lve_tmst_mgmt.service;

import com.alt.lve_tmst_mgmt.dto.MonthlyEmployeeReportDto;
import com.alt.lve_tmst_mgmt.dto.UserDashBoardDto;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface UserDashBoardService {

   UserDashBoardDto fetchDashBoard(
            String userId,
            LocalDate monthStart,
            LocalDate monthEnd
    );
}
