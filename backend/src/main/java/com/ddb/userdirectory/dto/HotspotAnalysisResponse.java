package com.ddb.userdirectory.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
/**
 * Response tổng cho API hotspot analysis.
 *
 * Chứa toàn bộ request metrics của cluster và kết luận hotspot.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotspotAnalysisResponse {

    private long totalRequests;
    private double thresholdPercentage;
    private boolean hasHotspot;
    private List<ShardHotspotMetric> shards;
    private String message;
}
