package com.ddb.userdirectory.controller;

import com.ddb.userdirectory.dto.CreateUserRequest;
import com.ddb.userdirectory.model.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/test/shards")
public class TestShardController {

    private final MongoTemplate agMongoTemplate;
    private final MongoTemplate hnMongoTemplate;
    private final MongoTemplate ozMongoTemplate;

    public TestShardController(
            @Qualifier("agMongoTemplate") MongoTemplate agMongoTemplate,
            @Qualifier("hnMongoTemplate") MongoTemplate hnMongoTemplate,
            @Qualifier("ozMongoTemplate") MongoTemplate ozMongoTemplate) {
        this.agMongoTemplate = agMongoTemplate;
        this.hnMongoTemplate = hnMongoTemplate;
        this.ozMongoTemplate = ozMongoTemplate;
    }

    @PostMapping("/ag")
    public User insertToAg(
            @RequestBody CreateUserRequest request) {
        User user = new User(request.getUsername(), request.getEmail(), request.getCountry());
        return agMongoTemplate.save(user);
    }

    @PostMapping("/hn")
    public User insertToHn(
            @RequestBody CreateUserRequest request) {
        User user = new User(request.getUsername(), request.getEmail(), request.getCountry());
        return hnMongoTemplate.save(user);
    }

    @PostMapping("/oz")
    public User insertToOz(
            @RequestBody CreateUserRequest request) {
        User user = new User(request.getUsername(), request.getEmail(), request.getCountry());
        return ozMongoTemplate.save(user);
    }

    @GetMapping("/counts")
    public String counts() {
        long agCount = agMongoTemplate.getCollection("users").countDocuments();
        long hnCount = hnMongoTemplate.getCollection("users").countDocuments();
        long ozCount = ozMongoTemplate.getCollection("users").countDocuments();

        return "AG: " + agCount + ", HN: " + hnCount + ", OZ: " + ozCount;
    }

}
