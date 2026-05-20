package com.ddb.userdirectory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserSearchResponse {

    private String id;
    private String username;
    private String email;
    private String country;

    private String searchedShard;
    private String databaseName;
    private String shardRange;

    private boolean found;
}
