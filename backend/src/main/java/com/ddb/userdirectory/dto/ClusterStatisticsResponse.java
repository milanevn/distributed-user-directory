package com.ddb.userdirectory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Response thống kê toàn cluster.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClusterStatisticsResponse {

    private long totalUsers;

    private List<ShardStatistics> shards;

    private String mostLoadedShard;

    private String leastLoadedShard;

    private String message;
}
