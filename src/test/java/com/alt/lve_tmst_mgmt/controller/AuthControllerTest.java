/*
package com.alt.lve_tmst_mgmt.controller;

import com.alt.lve_tmst_mgmt.dto.LoginRequestDto;
import com.alt.lve_tmst_mgmt.dto.LoginResponseDto;
import com.alt.lve_tmst_mgmt.security.CustomUserDetails;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collection;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false) // disables security filters for the test
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthenticationManager authenticationManager;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public AuthenticationManager authenticationManager() {
            return Mockito.mock(AuthenticationManager.class);
        }
    }

    @Test
    void login_success() throws Exception {

        // Mock CustomUserDetails
        CustomUserDetails userDetails = Mockito.mock(CustomUserDetails.class);
        Mockito.when(userDetails.getEmpId()).thenReturn("634");
        Mockito.when(userDetails.getEmployeeName()).thenReturn("Abhay Kumar Singh");
        Mockito.when(userDetails.getUsername()).thenReturn("abhay@test.com");

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        // Mock Authentication object returned by AuthenticationManager
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
        );

        Mockito.when(authenticationManager.authenticate(any())).thenReturn(authentication);

        // JSON request
        String requestJson = """
                {
                  "email": "abhay@test.com",
                  "password": "password123"
                }
                """;

        // Perform POST /auth/login and verify response
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.empId").value("634"))
                .andExpect(jsonPath("$.employeeName").value("Abhay Kumar Singh"))
                .andExpect(jsonPath("$.username").value("abhay@test.com"))
                .andExpect(jsonPath("$.role").value("ROLE_USER"))
                .andExpect(jsonPath("$.message").value("Login successful"));
    }
}*/
