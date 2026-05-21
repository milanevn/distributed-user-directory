# Day 4 Notes

## Mục tiêu

Triển khai distributed user search bằng shard routing.

## Công việc đã hoàn thành

- Tạo UserSearchResponse DTO
- Thêm method searchUserByUsername
- Thêm MongoDB query theo username
- Thêm API GET /api/users/{username}
- Triển khai distributed query routing
- Verify search đúng shard bằng MongoDB Compass

## Search Flow

1. Client gửi username
2. Backend xác định shard range
3. Backend chọn MongoTemplate phù hợp
4. Backend query đúng shard
5. Backend trả kết quả

## Routing Rules

- A-G -> shard_ag
- H-N -> shard_hn
- O-Z -> shard_oz

## Ý nghĩa

Hệ thống hiện đã hỗ trợ distributed query routing.

Client không biết dữ liệu đang nằm ở shard nào.

Backend tự động localize query tới đúng database node phù hợp.

## Distributed Database Concepts

### Distributed Query Routing

Distributed query routing là quá trình xác định node phù hợp trước khi thực hiện query.

System hiện không cần scan toàn bộ cluster.

### Data Localization

Data localization là kỹ thuật query đúng fragment chứa dữ liệu thay vì query toàn bộ shards.

Ví dụ:

- Alice chỉ query shard_ag
- Mike chỉ query shard_hn
- Sarah chỉ query shard_oz

### Query Optimization

Localized query giúp:

- giảm network traffic
- tăng performance
- giảm tải cho cluster
- hỗ trợ horizontal scaling
