package com.ddb.userdirectory.controller;

import com.ddb.userdirectory.dto.ReshardingSimulationResponse;
import com.ddb.userdirectory.service.ReshardingSimulationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resharding")
@RequiredArgsConstructor
public class ReshardingSimulationController {

    private final ReshardingSimulationService reshardingSimulationService;

    @PostMapping("/simulate")
    public ResponseEntity<ReshardingSimulationResponse> simulateResharding() {
        return ResponseEntity.ok(reshardingSimulationService.simulate());
    }
}
