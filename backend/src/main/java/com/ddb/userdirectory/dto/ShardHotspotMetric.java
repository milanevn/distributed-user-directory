package com.ddb.userdirectory.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO biểu diễn metrics request của một shard.
 *
 * Dùng để phân tích shard nào đang nhận nhiều request hơn các shard khác.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShardHotspotMetric {

    private String shardName;
    private long insertRequests;
    private long searchRequests;
    private long totalRequests;
    private double requestPercentage;
    private boolean hotspotStatus;
    private String reason;
}
