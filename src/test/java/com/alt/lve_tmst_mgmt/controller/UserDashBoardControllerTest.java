package com.alt.lve_tmst_mgmt.controller;

import com.alt.lve_tmst_mgmt.dto.UserDashBoardDto;
import com.alt.lve_tmst_mgmt.service.UserDashBoardService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.YearMonth;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

@WebMvcTest(UserDashBoardController.class)
@AutoConfigureMockMvc(addFilters = false)   // ✅ disables security filters
@Import(UserDashBoardControllerTest.TestConfig.class)
class UserDashBoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserDashBoardService userDashBoardService;

    @TestConfiguration
    static class TestConfig {

        @Bean
        public UserDashBoardService userDashBoardService() {
            return Mockito.mock(UserDashBoardService.class);
        }
    }

    @Test
    void testUserDashBoard_Success() throws Exception {

        String userId = "634";
        YearMonth month = YearMonth.of(2026, 2);

        LocalDate start = month.atDay(1);
        LocalDate end = month.atEndOfMonth();

        UserDashBoardDto mockResponse = new UserDashBoardDto();

        Mockito.when(userDashBoardService.fetchDashBoard(
                eq(userId),
                eq(start),
                eq(end)
        )).thenReturn(mockResponse);

        mockMvc.perform(get("/public/userDashBoard")
                        .param("userId", userId)
                        .param("month", "2026-02")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());
    }
}