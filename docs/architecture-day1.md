# Architecture Diagram - Day 1

```txt
+----------------------+
|   React Frontend     |
|   localhost:5173     |
+----------+-----------+
           |
           | HTTP REST
           v
+----------------------+
| Spring Boot Backend  |
| localhost:8080       |
| Shard Router Layer   |
+----+------------+----+
     |            |
     |            |
     v            v
+---------+   +---------+   +---------+
| mongo-ag|   | mongo-hn|   | mongo-oz|
| A-G     |   | H-N     |   | O-Z     |
| 27017   |   | 27018   |   | 27019   |
+---------+   +---------+   +---------+
```
