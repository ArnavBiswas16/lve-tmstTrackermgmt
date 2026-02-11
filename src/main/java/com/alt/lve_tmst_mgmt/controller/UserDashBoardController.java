package com.alt.lve_tmst_mgmt.controller;


import com.alt.lve_tmst_mgmt.dto.UserDashBoardDto;
import com.alt.lve_tmst_mgmt.service.UserDashBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.YearMonth;

@RestController
public class UserDashBoardController {

    @Autowired
    UserDashBoardService userDashBoardService;

    @GetMapping("/public/userDashBoard")
    public Page<UserDashBoardDto> userDashBoard (
            @RequestParam String userId,
            @RequestParam YearMonth month,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        LocalDate start = month.atDay(1);
        LocalDate end = month.atEndOfMonth();

        return userDashBoardService.fetchDashBoard(userId, start, end, page, size);


    }
}
