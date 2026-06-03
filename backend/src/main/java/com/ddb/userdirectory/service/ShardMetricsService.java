package com.ddb.userdirectory.service;

import com.ddb.userdirectory.dto.HotspotAnalysisResponse;
import com.ddb.userdirectory.dto.ShardHotspotMetric;
import com.ddb.userdirectory.model.ShardName;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Service theo dõi request traffic theo từng shard.
 *
 * Metrics này dùng để phát hiện hotspot dựa trên số lượng request,
 * không chỉ dựa trên số lượng dữ liệu đang lưu trong shard.
 */
@Service
public class ShardMetricsService {

    private static final double DEFAULT_HOTSPOT_THRESHOLD = 70.0;

    private final Map<ShardName, AtomicLong> insertCounters = new EnumMap<>(ShardName.class);

    private final Map<ShardName, AtomicLong> searchCounters = new EnumMap<>(ShardName.class);

    public ShardMetricsService() {
        for (ShardName shardName : ShardName.values()) {
            insertCounters.put(shardName, new AtomicLong(0));
            searchCounters.put(shardName, new AtomicLong(0));
        }
    }

    /**
     * Ghi nhận một insert request được route tới shard.
     */
    public void recordInsert(ShardName shardName) {
        insertCounters.get(shardName).incrementAndGet();
    }

    /**
     * Ghi nhận một search request được route tới shard.
     */
    public void recordSearch(ShardName shardName) {
        searchCounters.get(shardName).incrementAndGet();
    }

    /**
     * Phân tích hotspot dựa trên tổng request của từng shard.
     */
    public HotspotAnalysisResponse analyzeHotspots() {
        long totalRequests = calculateTotalRequests();

        List<ShardHotspotMetric> shardMetrics = new ArrayList<>();

        boolean hasHotspot = false;

        for (ShardName shardName : ShardName.values()) {
            long insertRequests = insertCounters.get(shardName).get();
            long searchRequests = searchCounters.get(shardName).get();
            long totalShardRequests = insertRequests + searchRequests;

            double requestPercentage = calculatePercentage(totalShardRequests, totalRequests);

            boolean hotspotStatus = requestPercentage > DEFAULT_HOTSPOT_THRESHOLD;

            if (hotspotStatus) {
                hasHotspot = true;
            }

            shardMetrics.add(
                    new ShardHotspotMetric(
                            shardName.name(),
                            insertRequests,
                            searchRequests,
                            totalShardRequests,
                            requestPercentage,
                            hotspotStatus,
                            buildReason(shardName, requestPercentage, hotspotStatus)));
        }

        return new HotspotAnalysisResponse(
                totalRequests,
                DEFAULT_HOTSPOT_THRESHOLD,
                hasHotspot,
                shardMetrics,
                buildSummaryMessage(hasHotspot));
    }

    /**
     * Reset toàn bộ metrics để test lại từ đầu.
     */
    public void resetMetrics() {
        for (ShardName shardName : ShardName.values()) {
            insertCounters.get(shardName).set(0);
            searchCounters.get(shardName).set(0);
        }
    }

    private long calculateTotalRequests() {
        long total = 0;

        for (ShardName shardName : ShardName.values()) {
            total += insertCounters.get(shardName).get();
            total += searchCounters.get(shardName).get();
        }

        return total;
    }

    private double calculatePercentage(long shardRequests, long totalRequests) {
        if (totalRequests == 0) {
            return 0;
        }

        return Math.round(
                ((double) shardRequests / totalRequests) * 10000) / 100.0;
    }

    private String buildReason(
            ShardName shardName,
            double requestPercentage,
            boolean hotspotStatus) {
        if (hotspotStatus) {
            return "Shard "
                    + shardName.name()
                    + " đang nhận "
                    + requestPercentage
                    + "% tổng request, vượt ngưỡng hotspot";
        }
        return "Shard "
                + shardName.name()
                + " chưa vượt ngưỡng hotspot";
    }

    private String buildSummaryMessage(boolean hasHotspot) {
        if (hasHotspot) {
            return "Phát hiện hotspot trong cluster";
        }

        return "Chưa phát hiện hotspot theo ngưỡng hiện tại";
    }

}
