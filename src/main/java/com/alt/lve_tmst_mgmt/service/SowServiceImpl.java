package com.alt.lve_tmst_mgmt.service;

import com.alt.lve_tmst_mgmt.dto.SowDto;
import com.alt.lve_tmst_mgmt.entity.Employee;
import com.alt.lve_tmst_mgmt.entity.Sow;
import com.alt.lve_tmst_mgmt.repository.EmployeeRepository;
import com.alt.lve_tmst_mgmt.repository.SowRepository;
import lombok.RequiredArgsConstructor;
// Added for logging
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SowServiceImpl implements SowService {

    private final SowRepository sowRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<SowDto> getAllSows() {
        log.info("SowService.getAllSows() - fetching all SOWs");
        List<SowDto> list = sowRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        log.info("SowService.getAllSows() - fetched {} SOWs", (list != null ? list.size() : 0));
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SowDto> getSowsByManagerId(String managerId) {
        log.info("SowService.getSowsByManagerId() - fetching SOWs for managerId={}", managerId);
        List<SowDto> list = sowRepository.findByManager_EmployeeId(managerId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        log.info("SowService.getSowsByManagerId() - fetched {} SOWs for managerId={}", (list != null ? list.size() : 0), managerId);
        return list;
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

    @Override
    @Transactional
    public SowDto createSow(SowDto request) {
        log.info("SowService.createSow() - creating SOW (sowId={}, sowName={}, managerId={})",
                (request != null ? request.getSowId() : null),
                (request != null ? request.getSowName() : null),
                (request != null ? request.getManagerId() : null));

        Employee manager = employeeRepository.findById(request.getManagerId())
                .orElseThrow(() ->
                        new RuntimeException("Manager not found: " + request.getManagerId()));

        Sow sow = Sow.builder()
                .sowId(request.getSowId())
                .sowName(request.getSowName())
                .manager(manager)
                .build();

        Sow saved = sowRepository.save(sow);

        SowDto response = toDto(saved);
        log.info("SowService.createSow() - SOW created successfully (sowId={}, managerId={})",
                response != null ? response.getSowId() : null,
                response != null ? response.getManagerId() : null);

        return response;
    }
}