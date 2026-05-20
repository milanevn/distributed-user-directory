package com.ddb.userdirectory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private String id;
    private String username;
    private String email;
    private String country;

    private String selectedShard;
    private String databaseName;
    private String shardRange;
    private String routingReason;

}
