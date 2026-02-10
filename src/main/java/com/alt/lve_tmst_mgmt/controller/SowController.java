package com.alt.lve_tmst_mgmt.controller;
import com.alt.lve_tmst_mgmt.dto.SowDto;
import com.alt.lve_tmst_mgmt.service.SowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sows")
@RequiredArgsConstructor
public class SowController {

    private final SowService sowService;

    @GetMapping
    public ResponseEntity<List<SowDto>> getAll() {
        return ResponseEntity.ok(sowService.getAllSows());
    }

    @GetMapping("/manager/{managerId}")
    public ResponseEntity<List<SowDto>> getByManager(@PathVariable String managerId) {
        return ResponseEntity.ok(sowService.getSowsByManagerId(managerId));
    }
}