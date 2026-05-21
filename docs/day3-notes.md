# Day 3 Notes

## Mục tiêu

Triển khai automatic shard routing dựa trên range username.

## Công việc đã hoàn thành

- Refactor DTOs và model bằng Lombok annotations
- Tạo ShardName enum
- Tạo ShardRoutingResult DTO
- Tạo UserResponse DTO
- Tạo ShardRouterService
- Tạo UserService
- Tạo UserController
- Thêm API POST /api/users

## Routing Rules

- A-G -> shard_ag
- H-N -> shard_hn
- O-Z -> shard_oz

## Test Cases

| Username | Expected Shard |
| -------- | -------------- |
| Alice    | shard_ag       |
| Mike     | shard_hn       |
| Sarah    | shard_oz       |

## Ý nghĩa

Client không còn phải chọn shard thủ công.

Backend hiện đóng vai trò shard router và tự động xác định database node phù hợp dựa trên ký tự đầu của username.

Điều này mô phỏng range-based horizontal fragmentation trong distributed database systems.

## Distributed Database Concepts

### Shard Routing

Shard routing là quá trình xác định shard phù hợp để lưu hoặc truy vấn dữ liệu.

Trong project này, routing được thực hiện dựa trên ký tự đầu của username.

### Horizontal Fragmentation

Horizontal fragmentation là kỹ thuật chia dữ liệu theo rows hoặc documents.

User data hiện được phân bố thành ba fragments:

- A-G
- H-N
- O-Z

### Data Localization

System bắt đầu biết chính xác dữ liệu thuộc shard nào thay vì lưu ngẫu nhiên.
