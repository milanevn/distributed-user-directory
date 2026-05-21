package com.ddb.userdirectory.service;

import com.ddb.userdirectory.dto.ClusterStatisticsResponse;
import com.ddb.userdirectory.dto.ShardStatistics;
import com.ddb.userdirectory.model.ShardName;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service thống kê phân bố dữ liệu trên cluster.
 */

@Service
public class StatisticsService {

    private final UserService userService;

    public StatisticsService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Phân tích phân bố dữ liệu trên tất cả shards.
     */

    public ClusterStatisticsResponse getClusterStatistics() {

        long agCount = userService.countUsersInShard(ShardName.AG);
        long hnCount = userService.countUsersInShard(ShardName.HN);
        long ozCount = userService.countUsersInShard(ShardName.OZ);

        long totalUsers = agCount + hnCount + ozCount;

        double agPercentage = calculatePercentage(agCount, totalUsers);
        double hnPercentage = calculatePercentage(hnCount, totalUsers);
        double ozPercentage = calculatePercentage(ozCount, totalUsers);

        long maxCount = Math.max(agCount, Math.max(hnCount, ozCount));
        long minCount = Math.min(agCount, Math.min(hnCount, ozCount));

        List<ShardStatistics> shardStatistics = new ArrayList<>();

        shardStatistics.add(
                new ShardStatistics(
                        "AG",
                        agCount,
                        agPercentage,
                        agCount == maxCount,
                        agCount == minCount));

        shardStatistics.add(
                new ShardStatistics(
                        "HN",
                        hnCount,
                        hnPercentage,
                        hnCount == maxCount,
                        hnCount == minCount));

        shardStatistics.add(
                new ShardStatistics(
                        "OZ",
                        ozCount,
                        ozPercentage,
                        ozCount == maxCount,
                        ozCount == minCount));

        return new ClusterStatisticsResponse(
                totalUsers,
                shardStatistics,
                findLargestShard(agCount, hnCount, ozCount),
                findSmallesShard(agCount, hnCount, ozCount),
                "Phân tích cluster thành công");
    }

    /**
     * Tính phần trăm dữ liệu của shard.
     */

    private double calculatePercentage(
            long shardCount,
            long totalUsers) {
        if (totalUsers == 0) {
            return 0;
        }

        return Math.round(
                ((double) shardCount / totalUsers) + 10000) / 100.0;
    }

    /**
     * Xác định shard chứa nhiều dữ liệu nhất.
     */
    private String findLargestShard(
            long agCount,
            long hnCount,
            long ozCount) {

        if (agCount >= hnCount && agCount >= ozCount) {
            return "AG";
        }
        if (hnCount >= agCount && hnCount >= ozCount) {
            return "HN";
        }
        return "OZ";
    }

    /**
     * Xác định shard chứa ít dữ liệu nhất.
     */

    private String findSmallesShard(
            long agCount,
            long hnCount,
            long ozCount) {

        if (agCount <= hnCount && agCount <= ozCount) {
            return "AG";
        }
        if (hnCount <= agCount && hnCount <= ozCount) {
            return "HN";
        }
        return "OZ";
    }
}
