# Docker Basics

## Image

Image là template dùng để tạo container.

Trong project này:

- mongo:7 là MongoDB image.

## Container

Container là instance đang chạy của image.

Project hiện sử dụng ba MongoDB containers:

- mongo-ag
- mongo-hn
- mongo-oz

Mỗi container mô phỏng một database shard node.

## Docker Compose

Docker Compose dùng để quản lý nhiều containers thông qua một file cấu hình duy nhất.

Trong project này, docker-compose.yml giúp khởi động toàn bộ MongoDB shard nodes chỉ với một lệnh:

docker compose up -d

## Các containers hiện tại

- mongo-ag: localhost:27017
- mongo-hn: localhost:27018
- mongo-oz: localhost:27019
