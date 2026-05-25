package com.ddb.userdirectory.controller;
import com.ddb.userdirectory.dto.HotspotAnalysisResponse;
import com.ddb.userdirectory.service.ShardMetricsService;
import org.springframework.web.bind.annotation.*;

/**
 * Controller cung cấp API phân tích hotspot theo request traffic.
 */
@RestController
@RequestMapping("/api/shards")
public class ShardHotspotController {

    private final ShardMetricsService shardMetricsService;

    public ShardHotspotController(ShardMetricsService shardMetricsService){
        this.shardMetricsService = shardMetricsService;
    }

    /**
     * API phân tích shard hotspot dựa trên insert/search request.
     */
    @GetMapping("/hotspots")
    public HotspotAnalysisResponse getHotspots(){
        return shardMetricsService.analyzeHotspots();
    }

    /**
    * API reset metrics để test lại hotspot từ đầu.
    */
    @PostMapping("/metrics/reset")
    public String resetMetrics(){
        shardMetricsService.resetMetrics();
        return "Đã reset shard metrics";
    }
}
