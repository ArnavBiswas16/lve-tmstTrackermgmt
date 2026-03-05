package com.alt.lve_tmst_mgmt.controller;

import com.alt.lve_tmst_mgmt.Exceptions.ResourceNotFoundException;
import com.alt.lve_tmst_mgmt.dto.EmployeeMonthlyReviewDto;
import com.alt.lve_tmst_mgmt.service.EmployeeMonthlyReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/public/monthly_review")
public class EmployeeMonthlyReviewController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeMonthlyReviewController.class);

    @Autowired
    private EmployeeMonthlyReviewService service;

    @GetMapping
    public ResponseEntity<EmployeeMonthlyReviewDto> getReview(
            @RequestParam("userId") String userId,
            @RequestParam("month") @DateTimeFormat(pattern = "yyyy-MM") LocalDate month
    ) {

        logger.info("Received request to fetch monthly review for userId: {} and month: {}", userId, month);

        EmployeeMonthlyReviewDto review = service.getReviewByUserAndMonth(userId, month);

        if (review == null) {
            logger.warn("Monthly review NOT found | userId={} | month={}", userId, month);

            throw new ResourceNotFoundException(
                    "Monthly review not found for userId: " + userId + " and month: " + month
            );
        }

        return ResponseEntity.ok(review);
    }


    @PostMapping
    public ResponseEntity<String> saveReview(@RequestBody EmployeeMonthlyReviewDto dto) {

        logger.info("Received request to save monthly review for userId: {} and month: {}", dto.getEmployeeId(), dto.getMonth());

        try {
            service.saveReview(dto);
            logger.info("Monthly review saved successfully for userId: {}", dto.getEmployeeId());
            return ResponseEntity.ok("Review saved successfully.");
        } catch (Exception e) {
            logger.error("Error while saving monthly review for userId: {}", dto.getEmployeeId(), e);
            throw e;
        }
    }
}