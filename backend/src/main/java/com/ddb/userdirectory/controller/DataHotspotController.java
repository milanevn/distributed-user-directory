package com.ddb.userdirectory.controller;

import com.ddb.userdirectory.dto.DataHotspotResponse;
import com.ddb.userdirectory.service.DataHotspotService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller phân tích hotspot dựa trên phân bố dữ liệu.
 */
@RestController
@RequestMapping("/api/shards")
public class DataHotspotController {

    private final DataHotspotService dataHotspotService;

    public DataHotspotController(DataHotspotService dataHotspotService) {
        this.dataHotspotService = dataHotspotService;
    }

    /**
     * API phân tích hotspot dựa trên data distribution.
     */
    @GetMapping("/hotspots")
    public DataHotspotResponse getDataHotspots() {
        return dataHotspotService.analyzeDataHotspots();
    }
}
