package com.alt.lve_tmst_mgmt.controller;

import com.alt.lve_tmst_mgmt.dto.LoginRequestDto;
import com.alt.lve_tmst_mgmt.dto.LoginResponseDto;
import com.alt.lve_tmst_mgmt.security.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto request) {

        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            CustomUserDetails user =
                    (CustomUserDetails) authentication.getPrincipal();

            LoginResponseDto response = new LoginResponseDto(
                    user.getEmpId(),
                    user.getEmployeeName(),
                    user.getUsername(),
                    user.getAuthorities().iterator().next().getAuthority(),
                    "Login successful"
            );

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException ex) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid username or password"));
        }
    }
}
