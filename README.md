# Distributed User Directory System

Distributed database prototype sử dụng:

- Spring Boot
- MongoDB
- Range-based sharding
- Docker Compose

Project mô phỏng:

- shard routing
- hotspot detection
- re-sharding simulation
- distributed node failure
- cluster recovery

---

# System Architecture

Hệ thống gồm:

- ddb-backend
- mongo-ag
- mongo-hn
- mongo-oz

Backend Spring Boot đóng vai trò shard router và middleware.

MongoDB được chia thành nhiều shards theo username ranges.

---

# Shard Ranges

| Shard | Username Range |
| ----- | -------------- |
| AG    | A-G            |
| HN    | H-N            |
| OZ    | O-Z            |

---

# Technologies

| Thành phần       | Công nghệ     |
| ---------------- | ------------- |
| Backend          | Spring Boot   |
| Database         | MongoDB       |
| Containerization | Docker        |
| Build Tool       | Maven Wrapper |
| API Testing      | Postman       |

---

# Prerequisites

Cần cài đặt:

- Docker Desktop
- Git
- Java 17 (optional nếu chỉ chạy Docker)

---

# Setup Guide

## Clone project

```bash
git clone <repo-url>
```

---

## Start toàn bộ hệ thống

Từ root project:

```bash
docker compose up --build
```

Hoặc chạy background:

```bash
docker compose up --build -d
```

Docker Compose sẽ:

- build backend image
- tạo backend container
- tạo MongoDB shard containers
- start toàn bộ distributed system

---

## Stop toàn bộ hệ thống

```bash
docker compose down
```

---

## Verify containers

```bash
docker ps
```

Expected:

- ddb-backend
- mongo-ag
- mongo-hn
- mongo-oz

---

# APIs

## Backend Health

```http
GET /api/health
```

---

## Cluster Health

```http
GET /api/shards/health
```

---

## Generate Dataset

```http
POST /api/dataset/generate?size=10000&clearOldData=true
```

---

## Cluster Statistics

```http
GET /api/shards/stats
```

---

## Hotspot Analysis

```http
GET /api/shards/hotspots
```

---

## Re-sharding Plan

```http
GET /api/resharding-plan
```

---

## Re-sharding Simulation

```http
POST /api/resharding/simulate
```

---

# Failure Simulation

## Stop shard OZ

```bash
docker stop mongo-oz
```

## Verify cluster health

```http
GET /api/shards/health
```

Expected:

- OZ = DOWN
- clusterHealthy = false

## Recovery shard

```bash
docker start mongo-oz
```

---

# Distributed Database Concepts

Project mapping:

- horizontal fragmentation
- range-based sharding
- data localization
- hotspot detection
- re-sharding
- scale-out
- distributed node failure
- fault tolerance
- recovery

---

# Author

Distributed Database Final Project
