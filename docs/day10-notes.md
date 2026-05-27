# Day 11 Notes

## Mục tiêu

Đóng gói backend Spring Boot vào Docker và chạy toàn bộ hệ thống distributed database bằng Docker Compose.

## Công việc đã hoàn thành

- Tạo Dockerfile cho backend
- Tạo .dockerignore cho backend
- Tạo application-docker.properties
- Cập nhật docker-compose.yml
- Thêm backend service vào Docker Compose
- Cấu hình Spring profile docker
- Backend trong Docker connect tới MongoDB bằng service name
- Test docker compose up --build
- Verify backend health API
- Verify shard health API
- Verify dataset generation
- Verify shard statistics
- Test shard failure sau khi dockerized

## Kiến trúc sau Day 11

Hệ thống hiện gồm:

- ddb-backend container
- mongo-ag container
- mongo-hn container
- mongo-oz container

Backend Spring Boot đóng vai trò shard router và middleware.

Ba MongoDB containers đóng vai trò distributed shard nodes.

## Docker Concepts

### Docker Image

Image là template hoặc blueprint dùng để tạo container.

Ví dụ trong project:

- mongo:7 image
- backend image build từ Dockerfile

### Docker Container

Container là instance đang chạy của image.

Ví dụ:

- mongo-ag
- mongo-hn
- mongo-oz
- ddb-backend

Ba MongoDB containers đều được tạo từ cùng một MongoDB image.

### Dockerfile

Dockerfile là file mô tả cách build backend image.

Project sử dụng:

- Java 17
- Maven Wrapper (mvnw)
- multi-stage build

### Docker Compose

Docker Compose giúp chạy toàn bộ hệ thống bằng một lệnh:

```bash
docker compose up --build
```

Compose sẽ:

- build backend image
- tạo backend container
- tạo MongoDB containers
- tạo Docker network
- start toàn bộ services

## Vì sao cần application-docker.properties?

Khi backend chạy local bằng IntelliJ:

```text
mongodb://localhost:27017
```

hoạt động bình thường.

Nhưng khi backend chạy trong Docker container:

```text
localhost
```

là chính container backend.

Vì vậy backend phải connect bằng Docker service name:

```text
mongodb://mongo-ag:27017
mongodb://mongo-hn:27017
mongodb://mongo-oz:27017
```

## Docker Commands

### Build và start toàn bộ hệ thống

```bash
docker compose up --build
```

### Chạy background mode

```bash
docker compose up --build -d
```

### Stop và remove toàn bộ containers

```bash
docker compose down
```

### Xem containers đang chạy

```bash
docker ps
```

### Stop một shard để test failure

```bash
docker stop mongo-oz
```

### Start lại shard

```bash
docker start mongo-oz
```

## APIs kiểm thử

### Backend health

GET /api/health

### Cluster health

GET /api/shards/health

### Generate dataset

POST /api/dataset/generate?size=10000&clearOldData=true

### Shard statistics

GET /api/shards/stats

## Kiểm thử

### Test backend trong Docker

Sau khi chạy:

```bash
docker compose up --build
```

backend container hoạt động bình thường và expose port 8080.

### Test cluster health

GET /api/shards/health

Expected:

- AG = UP
- HN = UP
- OZ = UP
- clusterHealthy = true

### Test generate dataset

Generate 10000 skewed users thành công.

### Test hotspot analysis

Shard HN và OZ tiếp tục nhận lượng data lớn hơn AG.

### Test failure handling

Stop shard OZ:

```bash
docker stop mongo-oz
```

Expected:

- OZ = DOWN
- clusterHealthy = false

Recovery:

```bash
docker start mongo-oz
```

Expected:

- clusterHealthy = true

## Distributed Database Concepts

### Distributed Nodes

Mỗi MongoDB container đại diện cho một shard node trong distributed database.

### Middleware

Backend Spring Boot đóng vai trò middleware để route request tới đúng shard.

### Service Isolation

Mỗi thành phần của hệ thống chạy trong container riêng biệt.

### Deployment Environment

Docker Compose giúp tái tạo distributed environment ổn định và dễ demo hơn.

### Containerized Distributed System

Project hiện đã được containerized hoàn toàn:

- backend container
- distributed MongoDB shard containers

Điều này giúp project gần hơn với deployment environment thực tế.

## Ý nghĩa

Day 11 giúp project chuyển từ:

- chạy local bằng IDE

sang:

- chạy bằng Docker Compose
- containerized backend
- distributed deployment environment

Đây là bước quan trọng trước khi hoàn thiện proposal, analysis report và final demo.
