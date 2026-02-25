package com.alt.lve_tmst_mgmt.controller;
import com.alt.lve_tmst_mgmt.dto.EmployeeMonthlyReviewDto;
import com.alt.lve_tmst_mgmt.service.EmployeeMonthlyReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
@RestController
@RequestMapping("/public/monthly_review")
public class EmployeeMonthlyReviewController {

        @Autowired
        private EmployeeMonthlyReviewService service;
        @GetMapping
        public ResponseEntity<EmployeeMonthlyReviewDto> getReview(
                @RequestParam("userId") String userId,
                @RequestParam("month") @DateTimeFormat(pattern = "yyyy-MM") LocalDate month
        ) {
            EmployeeMonthlyReviewDto review = service.getReviewByUserAndMonth(userId, month);
            if (review != null) {
                return ResponseEntity.ok(review);
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        @PostMapping
        public ResponseEntity<String> saveReview(@RequestBody EmployeeMonthlyReviewDto dto) {
            service.saveReview(dto);
            return ResponseEntity.ok("Review saved successfully.");
        }
    }