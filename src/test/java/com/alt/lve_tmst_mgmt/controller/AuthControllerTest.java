/*
package com.alt.lve_tmst_mgmt.controller;

import com.alt.lve_tmst_mgmt.dto.LoginRequestDto;
import com.alt.lve_tmst_mgmt.dto.LoginResponseDto;
import com.alt.lve_tmst_mgmt.security.CustomUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthController authController;

    private LoginRequestDto loginRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        loginRequest = new LoginRequestDto("test@example.com", "password");
    }

    @Test
    void login_shouldReturnLoginResponseDto() {
        CustomUserDetails mockUserDetails = mock(CustomUserDetails.class);
        when(mockUserDetails.getEmpId()).thenReturn("EMP001");
        when(mockUserDetails.getEmployeeName()).thenReturn("John Doe");
        when(mockUserDetails.getUsername()).thenReturn("test@example.com");
        when(mockUserDetails.getAuthorities())
                .thenReturn(List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(mockUserDetails);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        LoginResponseDto response = authController.login(loginRequest);

        assertNotNull(response);
        assertEquals("EMP001", response.getUserId());
        assertEquals("John Doe", response.getName());
        assertEquals("test@example.com", response.getEmail());
        assertEquals("ROLE_ADMIN", response.getRole());
        assertEquals("Login successful", response.getMessage());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
}
*/
