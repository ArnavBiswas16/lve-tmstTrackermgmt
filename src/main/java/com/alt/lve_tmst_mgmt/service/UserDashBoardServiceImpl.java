package com.alt.lve_tmst_mgmt.service;

import com.alt.lve_tmst_mgmt.dto.UserDashBoardDto;
import com.alt.lve_tmst_mgmt.repository.UserDashBoardRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserDashBoardServiceImpl implements UserDashBoardService{

    private final UserDashBoardRepo userDashBoardRepo;

    public UserDashBoardServiceImpl(UserDashBoardRepo userDashBoardRepo) {
        this.userDashBoardRepo = userDashBoardRepo;
    }

    @Override
    public UserDashBoardDto fetchDashBoard(String userId, LocalDate monthStart, LocalDate monthEnd) {


        validateInputs(userId, monthStart, monthEnd);

        return userDashBoardRepo.fetchDashBoard(
                userId,
                monthStart,
                monthEnd
        );
    }

    private void validateInputs(String sowId, LocalDate start, LocalDate end) {
        if (sowId == null || sowId.isBlank()) {
            throw new IllegalArgumentException("sowId must not be null or empty");
        }
        if (start == null || end == null) {
            throw new IllegalArgumentException("Month start/end dates must not be null");
        }
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("Month end date cannot be before start date");
        }
    }
}
