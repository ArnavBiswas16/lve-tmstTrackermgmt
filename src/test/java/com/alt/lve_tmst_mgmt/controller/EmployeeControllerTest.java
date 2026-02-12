package com.alt.lve_tmst_mgmt.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeControllerTest {

    private EmployeeController employeeController;

    @BeforeEach
    void setUp() {
        employeeController = new EmployeeController();
    }

    @Test
    void publicHello_shouldReturnPublicAccess() {
        String result = employeeController.publicHello();
        assertEquals("Public access", result);
    }

    @Test
    void userHello_shouldReturnHelloWithUserName() {
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("testUser");

        String result = employeeController.userHello(auth);
        assertEquals("Hello testUser", result);

        verify(auth, times(1)).getName();
    }

    @Test
    void adminHello_shouldReturnAdminAccess() {
        String result = employeeController.adminHello();
        assertEquals("Admin access", result);
    }
}
