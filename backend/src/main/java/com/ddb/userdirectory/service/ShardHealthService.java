package com.ddb.userdirectory.service;

import com.ddb.userdirectory.dto.ClusterHealthResponse;
import com.ddb.userdirectory.dto.ShardHealthStatus;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShardHealthService {

    private final MongoTemplate agMongoTemplate;
    private final MongoTemplate hnMongoTemplate;
    private final MongoTemplate ozMongoTemplate;

    public ShardHealthService(
            @Qualifier("agMongoTemplate") MongoTemplate agMongoTemplate,
            @Qualifier("hnMongoTemplate") MongoTemplate hnMongoTemplate,
            @Qualifier("ozMongoTemplate") MongoTemplate ozMongoTemplate) {
        this.agMongoTemplate = agMongoTemplate;
        this.hnMongoTemplate = hnMongoTemplate;
        this.ozMongoTemplate = ozMongoTemplate;
    }

    public ClusterHealthResponse checkClusterHealth() {
        ShardHealthStatus ag = checkShard("AG", agMongoTemplate);
        ShardHealthStatus hn = checkShard("HN", hnMongoTemplate);
        ShardHealthStatus oz = checkShard("OZ", ozMongoTemplate);

        List<ShardHealthStatus> shards = List.of(ag, hn, oz);

        boolean clusterHealthy = shards.stream()
                .allMatch(shard -> shard.getStatus().equals("UP"));

        return new ClusterHealthResponse(clusterHealthy, shards);

    }

    private ShardHealthStatus checkShard(
            String shardName,
            MongoTemplate mongoTemplate) {
        try {
            mongoTemplate.getDb().runCommand(
                    new org.bson.Document("ping", 1));

            return new ShardHealthStatus(shardName, "UP", "Shard hoạt động bình thường");
        } catch (Exception ex) {
            return new ShardHealthStatus(shardName, "DOWN", "Không thể kết nối tới shard " + shardName);
        }
    }
}
