package com.ddb.userdirectory.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ShardHealthStatus {

    private String shardName;
    private String status;
    private String message;
}
