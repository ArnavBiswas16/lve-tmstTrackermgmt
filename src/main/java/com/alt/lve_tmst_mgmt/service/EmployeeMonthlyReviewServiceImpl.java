package com.alt.lve_tmst_mgmt.service;
import com.alt.lve_tmst_mgmt.dto.ComplianceId;
import com.alt.lve_tmst_mgmt.dto.EmployeeMonthlyReviewDto;
import com.alt.lve_tmst_mgmt.entity.EmployeeMonthlyReview;
import com.alt.lve_tmst_mgmt.repository.EmployeeMonthlyReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class EmployeeMonthlyReviewServiceImpl implements EmployeeMonthlyReviewService {

    @Autowired
    private EmployeeMonthlyReviewRepository repository;

    public void saveReview(EmployeeMonthlyReviewDto dto) {
        ComplianceId id = ComplianceId.builder()
                .employeeId(dto.getEmployeeId())
                .month(dto.getMonth())
                .build();

        EmployeeMonthlyReview review = repository.findById(id)
                .orElse(EmployeeMonthlyReview.builder().id(id).build());

        review.setWhatWentWell(dto.getWhatWentWell());
        review.setImprovementsNeeded(dto.getImprovementsNeeded());
        review.setBlockersChallenges(dto.getBlockersChallenges());
        review.setThingsToTry(dto.getThingsToTry());
        review.setClientAppreciation(dto.getClientAppreciation());
        review.setKeyAchievements(dto.getKeyAchievements());

        repository.save(review);
    }

    public EmployeeMonthlyReviewDto getReviewByUserAndMonth(String userId, LocalDate month) {
        ComplianceId id = ComplianceId.builder()
                .employeeId(userId)
                .month(month)
                .build();

        Optional<EmployeeMonthlyReview> reviewOpt = repository.findById(id);

        return reviewOpt.map(this::convertToDto).orElse(null);
    }

    private EmployeeMonthlyReviewDto convertToDto(EmployeeMonthlyReview review) {
        EmployeeMonthlyReviewDto dto = new EmployeeMonthlyReviewDto();
        dto.setEmployeeId(review.getId().getEmployeeId());
        dto.setMonth(review.getId().getMonth());
        dto.setWhatWentWell(review.getWhatWentWell());
        dto.setImprovementsNeeded(review.getImprovementsNeeded());
        dto.setBlockersChallenges(review.getBlockersChallenges());
        dto.setThingsToTry(review.getThingsToTry());
        dto.setClientAppreciation(review.getClientAppreciation());
        dto.setKeyAchievements(review.getKeyAchievements());
        return dto;
    }
}