package com.ddb.userdirectory.dto;

import com.ddb.userdirectory.model.ShardName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShardRoutingResult {

    private ShardName shardName;
    private String databaseName;
    private String range;
    private String reason;

}
