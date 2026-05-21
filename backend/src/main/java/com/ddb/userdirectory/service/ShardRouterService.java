package com.ddb.userdirectory.service;

import org.springframework.stereotype.Service;

import com.ddb.userdirectory.dto.ShardRoutingResult;
import com.ddb.userdirectory.model.ShardName;

@Service
public class ShardRouterService {

    public ShardRoutingResult routeByUserName(String username) {
        validateUsername(username);

        char firstChar = Character.toUpperCase(username.trim().charAt(0));

        if (firstChar >= 'A' && firstChar <= 'G') {
            return new ShardRoutingResult(
                    ShardName.AG,
                    "shard_ag",
                    "A-G",
                    "Username bắt đầu bằng " + firstChar + ", nên thuộc range A-G");
        }

        if (firstChar >= 'H' && firstChar <= 'N') {
            return new ShardRoutingResult(
                    ShardName.HN,
                    "shard_hn",
                    "H-N",
                    "Username bắt đầu bằng " + firstChar + ", nên thuộc range H-N");
        }

        if (firstChar >= 'O' && firstChar <= 'Z') {
            return new ShardRoutingResult(
                    ShardName.OZ,
                    "shard_oz",
                    "O-Z",
                    "Username bắt đầu bằng " + firstChar + ", nên thuộc range O-Z");
        }

        throw new IllegalArgumentException("Username phải bắt đầu bằng chữ cái từ A đến Z");
    }

    private void validateUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username không được để trống");
        }
    }
}
