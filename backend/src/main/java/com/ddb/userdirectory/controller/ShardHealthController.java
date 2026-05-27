package com.ddb.userdirectory.controller;

import com.ddb.userdirectory.dto.ClusterHealthResponse;
import com.ddb.userdirectory.service.ShardHealthService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shards")
public class ShardHealthController {
    private final ShardHealthService shardHealthService;

    public ShardHealthController(
            ShardHealthService shardHealthService) {
        this.shardHealthService = shardHealthService;
    }

    @GetMapping("/health")
    public ClusterHealthResponse getClusterHealth() {
        return shardHealthService.checkClusterHealth();
    }
}
