package com.ddb.userdirectory.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.TimeUnit;

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
        return new MongoTemplate(MongoClients.create(buildSettings(agUri)), "shard_ag");
    }

    @Bean(name = "hnMongoTemplate")
    public MongoTemplate hnMongoTemplate() {
        return new MongoTemplate(MongoClients.create(buildSettings(hnUri)), "shard_hn");
    }

    @Bean(name = "ozMongoTemplate")
    public MongoTemplate ozMongoTemplate() {
        return new MongoTemplate(MongoClients.create(buildSettings(ozUri)), "shard_oz");
    }

    private MongoClientSettings buildSettings(String uri) {
        return MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(uri))
                .applyToClusterSettings(builder -> builder.serverSelectionTimeout(3, TimeUnit.SECONDS))
                .applyToSocketSettings(builder -> builder.connectTimeout(3, TimeUnit.SECONDS)
                        .readTimeout(3, TimeUnit.SECONDS))
                .build();
    }

}
