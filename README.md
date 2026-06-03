# Distributed User Directory System

## Overview

Distributed User Directory System là một prototype mô phỏng cơ sở dữ liệu phân tán sử dụng kỹ thuật Range-Based Sharding để phân chia dữ liệu người dùng trên nhiều MongoDB instances.

Hệ thống được xây dựng nhằm minh họa các khái niệm quan trọng trong môn Cơ sở dữ liệu phân tán như Horizontal Fragmentation, Distributed Query Routing, Data Localization, Data Skew, Shard Hotspot, Re-sharding và Fault Tolerance.

Dữ liệu người dùng được phân chia dựa trên ký tự đầu của Username. Spring Boot Backend đóng vai trò shard router, chịu trách nhiệm xác định vị trí lưu trữ dữ liệu và chuyển tiếp truy vấn đến MongoDB instance phù hợp.

---

## System Architecture

Hệ thống áp dụng mô hình Application-Level Sharding. Toàn bộ logic phân mảnh và định tuyến dữ liệu được thực hiện bởi Spring Boot Backend thay vì MongoDB Native Sharding.

```
Client
  ↓
Spring Boot Backend
  ↓
Shard Router
  ↓
MongoDB Instances (AG / HN / OZ)
```

| Shard | Database | Container |
| ----- | -------- | --------- |
| AG    | shard_ag | mongo-ag  |
| HN    | shard_hn | mongo-hn  |
| OZ    | shard_oz | mongo-oz  |

---

## Data Fragmentation Strategy

Hệ thống áp dụng Horizontal Fragmentation kết hợp với Range-Based Sharding. Shard key là ký tự đầu của Username.

| Shard | Username Range |
| ----- | -------------- |
| AG    | A – G          |
| HN    | H – N          |
| OZ    | O – Z          |

Mỗi người dùng chỉ được lưu trữ trên một shard duy nhất. Khi một yêu cầu được gửi đến hệ thống, backend xác định shard đích dựa trên ký tự đầu của Username và chỉ thực hiện truy vấn trên MongoDB instance tương ứng.

Ví dụ:

| Username | Destination Shard |
| -------- | ----------------- |
| Alice    | AG                |
| Michael  | HN                |
| Sophia   | OZ                |

---

## Dataset Characteristics

Hệ thống hỗ trợ sinh dữ liệu tự động phục vụ quá trình kiểm thử và phân tích.

Dataset mặc định:

- 10.000 users
- Thuộc tính: `username`, `email`, `country`

Để mô phỏng hiện tượng Data Skew, khoảng 70% Username được sinh với ký tự đầu là M hoặc S (tỷ lệ M và S được chọn ngẫu nhiên, kỳ vọng xấp xỉ 35% mỗi loại). Điều này tạo ra sự mất cân bằng dữ liệu giữa các shards và là cơ sở cho các phân tích Hotspot và Re-sharding.

---

## Technologies

| Thành phần       | Công nghệ           |
| ---------------- | ------------------- |
| Backend          | Spring Boot         |
| Database         | MongoDB             |
| Data Access      | Spring Data MongoDB |
| Containerization | Docker              |
| Build Tool       | Maven Wrapper       |
| API Testing      | Postman             |

---

## Prerequisites

- Docker Desktop
- Git

---

## Setup Guide

### Clone Repository

```bash
git clone <repository-url>
cd <project-folder>
```

### Start System

```bash
docker compose up --build
```

Chạy nền:

```bash
docker compose up --build -d
```

Docker Compose sẽ khởi tạo backend container, ba MongoDB instances, Docker Network và toàn bộ hệ thống.

### Stop System

```bash
docker compose down
```

### Verify Running Containers

```bash
docker ps
```

Kết quả mong đợi: `ddb-backend`, `mongo-ag`, `mongo-hn`, `mongo-oz`.

---

## Available APIs

### Backend Health Check

```http
GET /api/health
```

Kiểm tra trạng thái hoạt động của backend. Trả về plain text `"Backend running"`.

---

### Cluster Health Check

```http
GET /api/shards/health
```

Kiểm tra trạng thái từng MongoDB shard trong cluster.

Response:

```json
{
  "clusterHealthy": true,
  "shards": [
    { "shardName": "AG", "status": "UP", "message": "..." },
    { "shardName": "HN", "status": "UP", "message": "..." },
    { "shardName": "OZ", "status": "UP", "message": "..." }
  ]
}
```

---

### Create User

```http
POST /api/users
```

Tạo người dùng mới. Backend tự động định tuyến đến shard phù hợp dựa trên ký tự đầu của Username.

Request body:

```json
{
  "username": "Alice",
  "email": "alice@example.com",
  "country": "Vietnam"
}
```

Response:

```json
{
  "id": "...",
  "username": "Alice",
  "email": "alice@example.com",
  "country": "Vietnam",
  "selectedShard": "AG",
  "databaseName": "shard_ag",
  "shardRange": "A-G",
  "routingReason": "..."
}
```

---

### Search User

```http
GET /api/users/{username}
```

Tìm kiếm người dùng theo Username. Backend định tuyến truy vấn trực tiếp đến shard chứa dữ liệu.

Response:

```json
{
  "username": "Alice",
  "found": true,
  "searchedShard": "AG",
  "databaseName": "shard_ag",
  "shardRange": "A-G"
}
```

---

### Generate Dataset

```http
POST /api/dataset/generate?size=10000&clearOldData=true
```

Sinh dữ liệu người dùng phục vụ kiểm thử. Dataset được cấu hình để tạo hiện tượng Data Skew với khoảng 70% Username bắt đầu bằng M hoặc S.

Response:

```json
{
  "totalGenerated": 10000,
  "countM": 3500,
  "countS": 3500,
  "countOther": 3000,
  "insertedPerShard": { "AG": 830, "HN": 4298, "OZ": 4872 },
  "clearedBeforeGenerate": true,
  "message": "..."
}
```

---

### Cluster Statistics

```http
GET /api/shards/stats
```

Thống kê phân bố dữ liệu trên từng shard.

Response:

```json
{
  "totalUsers": 10000,
  "shards": [
    {
      "shardName": "AG",
      "userCount": 830,
      "percentage": 8.3,
      "largest": false,
      "smallest": true
    },
    {
      "shardName": "HN",
      "userCount": 4298,
      "percentage": 42.98,
      "largest": false,
      "smallest": false
    },
    {
      "shardName": "OZ",
      "userCount": 4872,
      "percentage": 48.72,
      "largest": true,
      "smallest": false
    }
  ],
  "mostLoadedShard": "OZ",
  "leastLoadedShard": "AG",
  "message": "..."
}
```

---

### Data Hotspot Analysis

```http
GET /api/shards/hotspots
```

Phân tích hiện tượng Data Hotspot dựa trên phân bố dữ liệu giữa các shards. Một shard được xem là hotspot khi tỷ lệ dữ liệu vượt quá 40% tổng số bản ghi.

Response:

```json
{
  "totalUsers": 10000,
  "thresholdPercentage": 40.0,
  "hasHotspot": true,
  "shards": [
    { "shardName": "AG", "percentage": 8.3, "hotspot": false },
    { "shardName": "HN", "percentage": 42.98, "hotspot": true },
    { "shardName": "OZ", "percentage": 48.72, "hotspot": true }
  ],
  "message": "..."
}
```

---

### Re-sharding Plan

```http
GET /api/resharding-plan
```

Hiển thị chiến lược phân mảnh mới nhằm giảm mất cân bằng dữ liệu. Chiến lược đề xuất tách riêng các nhóm dữ liệu nóng (M và S) thành các shards logic độc lập.

---

### Re-sharding Simulation

```http
POST /api/resharding/simulate
```

Mô phỏng phân bố dữ liệu sau khi áp dụng chiến lược re-sharding mới gồm 5 shards logic.

| Shard   | Range |
| ------- | ----- |
| SHARD_1 | A–L   |
| SHARD_2 | M     |
| SHARD_3 | N–R   |
| SHARD_4 | S     |
| SHARD_5 | T–Z   |

Response bao gồm phân bố hiện tại, phân bố sau mô phỏng và chỉ số mất cân bằng trước/sau.

**Lưu ý:** API này chỉ thực hiện phân tích và mô phỏng. Dữ liệu thực tế không được di chuyển giữa các shards.

---

## Failure Simulation

Hệ thống hỗ trợ mô phỏng lỗi node trong quá trình vận hành.

### Stop Shard

```bash
docker stop mongo-oz
```

### Verify Failure Detection

```http
GET /api/shards/health
```

Kết quả mong đợi: `OZ = DOWN`, `clusterHealthy = false`. Backend vẫn tiếp tục phục vụ các request thuộc shard AG và HN. Request thuộc shard OZ sẽ nhận phản hồi `HTTP 503 Service Unavailable`.

### Recovery

```bash
docker start mongo-oz
```

Sau khi shard được khởi động lại, hệ thống trở về trạng thái bình thường. Dữ liệu được giữ nguyên nhờ Docker Volume.

---

## Theory Mapping (Özsu & Valduriez)

| Khái niệm lý thuyết        | Triển khai trong project                                             |
| -------------------------- | -------------------------------------------------------------------- |
| Horizontal Fragmentation   | Phân chia dữ liệu theo Username ranges                               |
| Range Partitioning         | Shard key là ký tự đầu Username, phân chia theo khoảng A-G, H-N, O-Z |
| Data Localization          | Truy vấn chỉ được thực hiện trên shard chứa dữ liệu liên quan        |
| Distributed Query Routing  | ShardRouterService định tuyến mọi request đến đúng shard             |
| Application-Level Sharding | Logic sharding được quản lý tại tầng ứng dụng (Spring Boot)          |
| Data Skew                  | 70% Username bắt đầu bằng M hoặc S                                   |
| Data Hotspot               | Hotspot Analysis API phát hiện shard vượt ngưỡng 40%                 |
| Load Balancing             | Re-sharding Simulation đánh giá cải thiện cân bằng tải               |
| Fault Detection            | Cluster Health Monitoring kiểm tra trạng thái từng shard             |
| Distributed Failure        | Mô phỏng bằng cách dừng một MongoDB container                        |
| Fault Tolerance            | Backend tiếp tục hoạt động khi một shard gặp sự cố                   |
| Recovery                   | Hệ thống phục hồi sau khi node được khởi động lại                    |

---
