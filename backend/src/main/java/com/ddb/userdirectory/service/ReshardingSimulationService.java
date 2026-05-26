package com.ddb.userdirectory.service;

import com.ddb.userdirectory.dto.ReshardingSimulationResponse;
import com.ddb.userdirectory.dto.ShardDistributionDto;
import com.ddb.userdirectory.model.User;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReshardingSimulationService {

    private final MongoTemplate agMongoTemplate;
    private final MongoTemplate hnMongoTemplate;
    private final MongoTemplate ozMongoTemplate;

    public ReshardingSimulationService(
            @Qualifier("agMongoTemplate") MongoTemplate agMongoTemplate,
            @Qualifier("hnMongoTemplate") MongoTemplate hnMongoTemplate,
            @Qualifier("ozMongoTemplate") MongoTemplate ozMongoTemplate) {
        this.agMongoTemplate = agMongoTemplate;
        this.hnMongoTemplate = hnMongoTemplate;
        this.ozMongoTemplate = ozMongoTemplate;
    }

    public ReshardingSimulationResponse simulate() {
        List<User> agUsers = agMongoTemplate.findAll(User.class);
        List<User> hnUsers = hnMongoTemplate.findAll(User.class);
        List<User> ozUsers = ozMongoTemplate.findAll(User.class);

        long agCount = agUsers.size();
        long hnCount = hnUsers.size();
        long ozCount = ozUsers.size();

        List<User> allUsers = new ArrayList<>();
        allUsers.addAll(agUsers);
        allUsers.addAll(hnUsers);
        allUsers.addAll(ozUsers);

        long totalUsers = allUsers.size();

        List<ShardDistributionDto> currentDistribution = List.of(
                buildDistribution("AG", "A-G", agCount, totalUsers),
                buildDistribution("HN", "H-N", hnCount, totalUsers),
                buildDistribution("OZ", "O-Z", ozCount, totalUsers));

        long shard1Count = 0; // A-L
        long shard2Count = 0; // M
        long shard3Count = 0; // N-R
        long shard4Count = 0; // S
        long shard5Count = 0; // T-Z

        for (User user : allUsers) {
            String username = user.getUsername();

            if (username == null || username.isBlank()) {
                continue;
            }

            char firstChar = Character.toUpperCase(username.charAt(0));

            if (firstChar >= 'A' && firstChar <= 'L') {
                shard1Count++;
            } else if (firstChar == 'M') {
                shard2Count++;
            } else if (firstChar >= 'N' && firstChar <= 'R') {
                shard3Count++;
            } else if (firstChar == 'S') {
                shard4Count++;
            } else if (firstChar >= 'T' && firstChar <= 'Z') {
                shard5Count++;
            }
        }

        List<ShardDistributionDto> simulatedDistribution = List.of(
                buildDistribution("SHARD_1", "A-L", shard1Count, totalUsers),
                buildDistribution("SHARD_2", "M", shard2Count, totalUsers),
                buildDistribution("SHARD_3", "N-R", shard3Count, totalUsers),
                buildDistribution("SHARD_4", "S", shard4Count, totalUsers),
                buildDistribution("SHARD_5", "T-Z", shard5Count, totalUsers));

        double imbalanceBefore = calculateImbalance(currentDistribution);
        double imbalanceAfter = calculateImbalance(simulatedDistribution);

        String conclusion = imbalanceAfter < imbalanceBefore
                ? "Kế hoạch re-sharding giúp giảm mức độ mất cân bằng dữ liệu"
                : "Kế hoạch re-sharding chưa cải thiện rõ ràng, cần xem lại cách chia range";

        return new ReshardingSimulationResponse(
                totalUsers,
                currentDistribution,
                simulatedDistribution,
                imbalanceBefore,
                imbalanceAfter,
                conclusion);

    }

    private ShardDistributionDto buildDistribution(String shardName, String range, long count, long total) {
        double percentage = total == 0 ? 0 : (count * 100.0) / total;
        percentage = Math.round(percentage * 100.0) / 100.0;

        return new ShardDistributionDto(
                shardName,
                range,
                count,
                percentage);
    }

    private double calculateImbalance(List<ShardDistributionDto> distributions) {
        if (distributions == null || distributions.isEmpty()) {
            return 0;
        }

        double max = distributions.stream()
                .mapToDouble(ShardDistributionDto::getPercentage)
                .max()
                .orElse(0);

        double min = distributions.stream()
                .mapToDouble(ShardDistributionDto::getPercentage)
                .min()
                .orElse(0);

        double imbalance = max - min;
        return Math.round(imbalance * 100.0) / 100.0;
    }

}
