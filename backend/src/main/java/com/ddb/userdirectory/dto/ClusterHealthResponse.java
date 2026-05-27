package com.ddb.userdirectory.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ClusterHealthResponse {

    private boolean clusterHealthy;
    private List<ShardHealthStatus> shards;
}
