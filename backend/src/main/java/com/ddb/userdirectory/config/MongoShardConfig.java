package com.ddb.userdirectory.config;

import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.context.annotation.Primary;

@Configuration
public class MongoShardConfig {

    @Value("${mongodb.shards.ag.uri}")
    private String agUri;

    @Value("${mongodb.shards.hn.uri}")
    private String hnUri;

    @Value("${mongodb.shards.oz.uri}")
    private String ozUri;

    @Primary
    @Bean(name = "agMongoTemplate")
    public MongoTemplate agMongoTemplate() {
        return new MongoTemplate(MongoClients.create(agUri), "shard_ag");
    }

    @Bean(name = "hnMongoTemplate")
    public MongoTemplate hnMongoTemplate() {
        return new MongoTemplate(MongoClients.create(hnUri), "shard_hn");
    }

    @Bean(name = "ozMongoTemplate")
    public MongoTemplate ozMongoTemplate() {
        return new MongoTemplate(MongoClients.create(ozUri), "shard_oz");
    }

}
