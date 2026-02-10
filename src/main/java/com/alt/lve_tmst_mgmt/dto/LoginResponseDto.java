package com.alt.lve_tmst_mgmt.dto;

public class LoginResponseDto {

    private String name;
    private String userId;
    private String email;
    private String role;
    private String message;

    public LoginResponseDto(String userId, String name, String email, String role, String message) {

        this.name = name;
        this.userId = userId;
        this.email = email;
        this.role = role;
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
