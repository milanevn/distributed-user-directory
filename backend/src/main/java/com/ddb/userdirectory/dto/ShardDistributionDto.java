package com.ddb.userdirectory.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ShardDistributionDto {

    private String shardName;
    private String range;
    private long count;
    private double percentage;
}
