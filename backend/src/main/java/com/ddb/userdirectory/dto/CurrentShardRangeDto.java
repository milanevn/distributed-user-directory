package com.ddb.userdirectory.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CurrentShardRangeDto {

    private String shardName;
    private String range;
    private String problem;
}
