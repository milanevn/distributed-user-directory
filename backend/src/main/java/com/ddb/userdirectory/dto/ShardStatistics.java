package com.ddb.userdirectory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Thống kê dữ liệu của một shard.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShardStatistics {

    private String shardName;

    private long userCount;

    private double percentage;

    private boolean largestShard;

    private boolean smallestShard;
}
