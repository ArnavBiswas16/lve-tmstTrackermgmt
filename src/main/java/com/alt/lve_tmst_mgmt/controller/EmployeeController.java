package com.alt.lve_tmst_mgmt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;


@RestController
@RequestMapping
public class EmployeeController {

    @GetMapping("/public/hello")
    public String publicHello() {
        return "Public access";
    }

    @GetMapping("/user/hello")
    public String userHello(Authentication auth) {
        return "Hello " + auth.getName();
    }

    @GetMapping("/admin/hello")
    public String adminHello() {
        return "Admin access";
    }
}

