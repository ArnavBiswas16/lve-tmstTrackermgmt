package com.alt.lve_tmst_mgmt.controller;

import com.alt.lve_tmst_mgmt.dto.LoginRequestDto;
import com.alt.lve_tmst_mgmt.dto.LoginResponseDto;
import com.alt.lve_tmst_mgmt.security.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping(value = "/login", produces = "application/json")
    public LoginResponseDto login(
            @RequestBody LoginRequestDto request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        CustomUserDetails user =
                (CustomUserDetails) authentication.getPrincipal();
        return new LoginResponseDto(user.getUsername(), user.getAuthorities().iterator().next().getAuthority(), "Login successful");

    }
}
