package com.ddb.userdirectory.controller;

import com.ddb.userdirectory.dto.ReshardingPlanResponse;
import com.ddb.userdirectory.service.ReshardingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ReshardingController {

    private final ReshardingService reshardingService;

    public ReshardingController(ReshardingService reshardingService){
        this.reshardingService = reshardingService;
    }

    @GetMapping("/resharding-plan")
    public ResponseEntity<ReshardingPlanResponse> getReshardingPlan(){
        return ResponseEntity.ok(reshardingService.getReshardingPlan());
    }
}
