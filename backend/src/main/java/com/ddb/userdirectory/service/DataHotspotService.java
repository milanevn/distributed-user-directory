package com.ddb.userdirectory.service;

import com.ddb.userdirectory.dto.DataHotspotMetric;
import com.ddb.userdirectory.dto.DataHotspotResponse;
import com.ddb.userdirectory.model.ShardName;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataHotspotService {

    private static final double DATA_HOTSPOT_THRESHOLD = 40.0;

    private final UserService userService;

    public DataHotspotService(UserService userService) {
        this.userService = userService;
    }

    public DataHotspotResponse analyzeDataHotspots() {
        long agCount = userService.countUsersInShard(ShardName.AG);
        long hnCount = userService.countUsersInShard(ShardName.HN);
        long ozCount = userService.countUsersInShard(ShardName.OZ);

        long totalUsers = agCount + hnCount + ozCount;

        List<DataHotspotMetric> shards = List.of(
                buildMetric("AG", "A-G", agCount, totalUsers),
                buildMetric("HN", "H-N", hnCount, totalUsers),
                buildMetric("OZ", "O-Z", ozCount, totalUsers));

        boolean hasHotspot = shards.stream()
                .anyMatch(DataHotspotMetric::isHotspotStatus);

        return new DataHotspotResponse(
                totalUsers,
                DATA_HOTSPOT_THRESHOLD,
                hasHotspot,
                shards,
                hasHotspot
                        ? "Phát hiện data hotspot do dữ liệu phân bố không đều"
                        : "Chưa phát hiện data hotspot");
    }

    private DataHotspotMetric buildMetric(String shardName, String range, long userCount, long totalUsers) {
        double dataPercentage = calculatePercentage(userCount, totalUsers);
        boolean hotspotStatus = dataPercentage > DATA_HOTSPOT_THRESHOLD;

        String reason = hotspotStatus
                ? "Shard " + shardName + " chứa " + dataPercentage +
                        " % tổng dữ liệu, vượt ngưỡng " + DATA_HOTSPOT_THRESHOLD + "%"
                : "Shard " + shardName + " chưa vượt ngưỡng data hotspot";

        return new DataHotspotMetric(shardName, range, userCount, dataPercentage, hotspotStatus, reason);
    }

    private double calculatePercentage(long shardCount, long totalUsers) {
        if (totalUsers == 0) {
            return 0;
        }

        double percentage = (shardCount * 100.0) / totalUsers;
        return Math.round(percentage * 100.0) / 100.0;
    }
}
