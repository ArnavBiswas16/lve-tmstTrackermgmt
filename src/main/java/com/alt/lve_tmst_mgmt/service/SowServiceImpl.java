package com.alt.lve_tmst_mgmt.service;

import com.alt.lve_tmst_mgmt.dto.SowDto;
import com.alt.lve_tmst_mgmt.entity.Employee;
import com.alt.lve_tmst_mgmt.entity.Sow;
import com.alt.lve_tmst_mgmt.repository.SowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SowServiceImpl implements SowService {

    private final SowRepository sowRepository;

    @Override
    @Transactional(readOnly = true)
    public List<SowDto> getAllSows() {
        return sowRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SowDto> getSowsByManagerId(String managerId) {
        return sowRepository.findByManager_EmployeeId(managerId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private SowDto toDto(Sow e) {
        String mgrId = null;
        Employee mgr = e.getManager();
        if (mgr != null) {
            mgrId = mgr.getEmployeeId();
        }
        return SowDto.builder()
                .sowId(e.getSowId())
                .sowName(e.getSowName())
                .managerId(mgrId)
                .build();
    }
}