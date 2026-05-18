# Day 2 Notes

## Goal

Set up MongoDB shard nodes and connect the Spring Boot backend to each shard.

## Completed Work

- Verified three MongoDB containers are running
- Added MongoDB shard URIs in application.properties
- Created User model
- Created CreateUserRequest DTO
- Created MongoShardConfig
- Created three MongoTemplate beans
- Created TestShardController
- Tested insert into each shard
- Verified data in MongoDB Compass
- Verified shard counts API

## MongoDB Shard Nodes

| Shard | Container | Host Port | Database |
| ----- | --------- | --------: | -------- |
| A-G   | mongo-ag  |     27017 | shard_ag |
| H-N   | mongo-hn  |     27018 | shard_hn |
| O-Z   | mongo-oz  |     27019 | shard_oz |

## User Document

Fields:

- id
- username
- email
- country

## Current APIs

### Health Check

GET /api/health

### Insert Test User Into AG Shard

POST /api/test/shards/ag

### Insert Test User Into HN Shard

POST /api/test/shards/hn

### Insert Test User Into OZ Shard

POST /api/test/shards/oz

### Count Users In All Shards

GET /api/test/shards/counts

## Current Limitation

Routing is still manual through test endpoints.

Automatic range-based routing will be implemented next.
