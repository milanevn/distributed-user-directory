# Day 2 Notes

## Mục tiêu

Thiết lập các MongoDB shard nodes và kết nối backend Spring Boot tới từng shard.

## Công việc đã hoàn thành

- Kiểm tra ba MongoDB containers đang hoạt động
- Thêm MongoDB shard URIs vào application.properties
- Tạo User model
- Tạo CreateUserRequest DTO
- Tạo MongoShardConfig
- Tạo ba MongoTemplate beans
- Tạo TestShardController
- Test insert vào từng shard
- Verify dữ liệu bằng MongoDB Compass
- Verify API đếm số lượng user trên shard

## MongoDB Shard Nodes

| Shard | Container | Host Port | Database |
| ----- | --------- | --------: | -------- |
| A-G   | mongo-ag  |     27017 | shard_ag |
| H-N   | mongo-hn  |     27018 | shard_hn |
| O-Z   | mongo-oz  |     27019 | shard_oz |

## User Document

Các field hiện tại:

- id
- username
- email
- country

## APIs hiện tại

### Health Check

GET /api/health

### Insert test user vào AG shard

POST /api/test/shards/ag

### Insert test user vào HN shard

POST /api/test/shards/hn

### Insert test user vào OZ shard

POST /api/test/shards/oz

### Đếm số lượng user trên tất cả shards

GET /api/test/shards/counts

## Giới hạn hiện tại

Routing vẫn đang thực hiện thủ công thông qua các test endpoints.

Automatic range-based routing sẽ được triển khai ở bước tiếp theo.
