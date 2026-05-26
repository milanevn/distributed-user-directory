package com.ddb.userdirectory.controller;

import com.ddb.userdirectory.dto.ClusterStatisticsResponse;
import com.ddb.userdirectory.service.StatisticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller cung cấp APIs thống kê cluster.
 */

@RestController
@RequestMapping("/api/shards")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(
            StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    /**
     * API phân tích phân bố dữ liệu trên cluster.
     */
    @GetMapping("/stats")
    public ClusterStatisticsResponse getShardStatistics() {
        return statisticsService.getClusterStatistics();
    }
}
