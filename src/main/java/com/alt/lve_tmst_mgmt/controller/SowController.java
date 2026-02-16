package com.alt.lve_tmst_mgmt.controller;

import com.alt.lve_tmst_mgmt.dto.SowDto;
import com.alt.lve_tmst_mgmt.service.SowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/public/sows")
@RequiredArgsConstructor
public class SowController {

    private final SowService sowService;

    @GetMapping
    public ResponseEntity<List<SowDto>> getAll() {
        log.info("GET /sows - fetching all SOWs");
        List<SowDto> data = sowService.getAllSows();
        log.info("GET /sows - fetched {} SOWs", (data != null ? data.size() : 0));
        return ResponseEntity.ok(data);
    }

    @GetMapping("/manager/{managerId}")
    public ResponseEntity<List<SowDto>> getByManager(@PathVariable String managerId) {
        log.info("GET /sows/manager/{} - fetching SOWs for manager", managerId);
        List<SowDto> data = sowService.getSowsByManagerId(managerId);
        log.info("GET /sows/manager/{} - fetched {} SOWs", managerId, (data != null ? data.size() : 0));
        return ResponseEntity.ok(data);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<SowDto> createSow(@RequestBody SowDto request) {
        log.info("POST /sows - creating new SOW with name={}", request != null ? request.getSowName() : null);
        SowDto created = sowService.createSow(request);
        log.info("POST /sows - SOW created successfully with id={}", created != null ? created.getSowId() : null);
        return ResponseEntity.ok(created);
    }
}