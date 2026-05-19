# Architecture Diagram - Backend Only

+-----------------------------+
| Postman / curl / Browser |
| API Testing Client |
+--------------+--------------+
|
| HTTP REST
v
+-----------------------------+
| Spring Boot Backend |
| |
| - UserController |
| - UserService |
| - ShardRouterService |
| - MongoShardConfig |
+--------------+--------------+
|
+--------+---------+
| | |
v v v
+----------+ +----------+ +----------+
| mongo-ag | | mongo-hn | | mongo-oz |
| shard_ag | | shard_hn | | shard_oz |
| A-G | | H-N | | O-Z |
| :27017 | | :27018 | | :27019 |
+----------+ +----------+ +----------+

# Explanation

The system is backend-only.

The client sends HTTP requests to the Spring Boot backend.

The backend acts as a shard router. It chooses the correct MongoDB shard based on username range.

Each MongoDB container represents an independent database node.

```

```
