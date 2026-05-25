package com.ddb.userdirectory.service;

import com.ddb.userdirectory.dto.CreateUserRequest;
import com.ddb.userdirectory.dto.ShardRoutingResult;
import com.ddb.userdirectory.dto.UserResponse;
import com.ddb.userdirectory.dto.UserSearchResponse;
import com.ddb.userdirectory.model.ShardName;
import com.ddb.userdirectory.model.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final MongoTemplate agMongoTemplage;
    private final MongoTemplate hnMongoTemplage;
    private final MongoTemplate ozMongoTemplage;
    private final ShardRouterService shardRouterService;
    private final ShardMetricsService shardMetricsService;

    public UserService(
            @Qualifier("agMongoTemplate") MongoTemplate agMongoTemplate,
            @Qualifier("hnMongoTemplate") MongoTemplate hnMongoTemplate,
            @Qualifier("ozMongoTemplate") MongoTemplate ozMongoTemplate,
            ShardRouterService shardRouterService,
            ShardMetricsService shardMetricsServicer) {
        this.agMongoTemplage = agMongoTemplate;
        this.hnMongoTemplage = hnMongoTemplate;
        this.ozMongoTemplage = ozMongoTemplate;
        this.shardRouterService = shardRouterService;
        this.shardMetricsService = shardMetricsServicer;
    }

    public UserResponse createUser(CreateUserRequest request) {
        ShardRoutingResult routingResult = shardRouterService.routeByUserName(request.getUsername());
        shardMetricsService.recordInsert(routingResult.getShardName());

        User user = new User(
                request.getUsername(),
                request.getEmail(),
                request.getCountry());

        MongoTemplate targetTemplate = getMongoTemplate(routingResult.getShardName());

        System.out.println("[Shard Routing] Đang route "
                + request.getUsername()
                + " tới "
                + routingResult.getDatabaseName());

        User savedUser = targetTemplate.save(user);

        return toResponse(savedUser, routingResult);
    }

    private MongoTemplate getMongoTemplate(ShardName shardName) {
        if (shardName == ShardName.AG) {
            return agMongoTemplage;
        }
        if (shardName == ShardName.HN) {
            return hnMongoTemplage;
        }
        if (shardName == ShardName.OZ) {
            return ozMongoTemplage;
        }
        throw new IllegalArgumentException("Shard không được hỗ trợ: " + shardName);

    }

    private UserResponse toResponse(User user, ShardRoutingResult routingResult) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCountry(),
                routingResult.getShardName().name(),
                routingResult.getDatabaseName(),
                routingResult.getRange(),
                routingResult.getReason()

        );
    }

    public UserSearchResponse searchUserByUsername(String username) {

        ShardRoutingResult routingResult = shardRouterService.routeByUserName(username);
        shardMetricsService.recordSearch(routingResult.getShardName());

        MongoTemplate targetTemplate = getMongoTemplate(routingResult.getShardName());

        System.out.println(
                "[Shard Search] Đang tìm user "
                        + username
                        + " trong "
                        + routingResult.getDatabaseName());

        Query query = new Query();

        query.addCriteria(
                Criteria.where("username").is(username));

        User user = targetTemplate.findOne(query, User.class);

        if (user == null) {

            return new UserSearchResponse(
                    null,
                    username,
                    null,
                    null,
                    routingResult.getShardName().name(),
                    routingResult.getDatabaseName(),
                    routingResult.getRange(),
                    false);
        }

        return new UserSearchResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCountry(),
                routingResult.getShardName().name(),
                routingResult.getDatabaseName(),
                routingResult.getRange(),
                true);
    }

    public void clearAllUsers() {
        agMongoTemplage.dropCollection(User.class);
        hnMongoTemplage.dropCollection(User.class);
        ozMongoTemplage.dropCollection(User.class);

        System.out.println("[Dataset] Đã xóa toàn bộ user trên tất cả shard");
    }

    public long countUsersInShard(ShardName shardName) {
        MongoTemplate mongoTemplate = getMongoTemplate(shardName);
        return mongoTemplate.getCollection("users").countDocuments();
    }

}
