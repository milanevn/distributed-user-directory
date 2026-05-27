# Day 11 Notes

## Mục tiêu

Đóng gói backend Spring Boot vào Docker và chạy toàn bộ hệ thống bằng Docker Compose.

## Công việc đã hoàn thành

- Tạo Dockerfile cho backend
- Tạo .dockerignore cho backend
- Tạo application-docker.properties
- Cập nhật docker-compose.yml
- Thêm backend service vào Docker Compose
- Cấu hình backend chạy với Spring profile docker
- Cấu hình backend trong Docker connect tới MongoDB bằng service name
- Test docker compose up --build
- Verify backend health check
- Verify shard health check
- Verify dataset generation
- Verify shard statistics

## Kiến trúc sau Day 11

Hệ thống gồm bốn containers:

- ddb-backend
- mongo-ag
- mongo-hn
- mongo-oz

Backend Spring Boot đóng vai trò shard router.

Ba MongoDB containers đóng vai trò shard nodes.

## Vì sao cần application-docker.properties?

Khi backend chạy ngoài Docker, backend connect tới MongoDB bằng localhost:

```text
mongodb://localhost:27017
```
