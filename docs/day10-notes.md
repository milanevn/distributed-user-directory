# Day 10 Notes

## Mục tiêu

Mô phỏng shard/node failure và kiểm tra khả năng chịu lỗi của hệ thống distributed database.

## Công việc đã hoàn thành

- Tạo ShardHealthStatus DTO
- Tạo ClusterHealthResponse DTO
- Tạo ErrorResponse DTO
- Tạo ShardUnavailableException
- Tạo GlobalExceptionHandler
- Tạo ShardHealthService
- Tạo ShardHealthController
- Thêm API GET /api/shards/health
- Thêm MongoDB health check bằng ping command
- Thêm MongoDB timeout configuration
- Thêm failure handling trong UserService
- Test shard failure bằng docker stop
- Test shard recovery bằng docker start

## APIs

### Kiểm tra trạng thái cluster

GET /api/shards/health

## Failure Simulation Flow

### Bước 1

Kiểm tra cluster ban đầu:

GET /api/shards/health

Expected:

- AG = UP
- HN = UP
- OZ = UP
- clusterHealthy = true

## Bước 2

Stop shard OZ:

```bash
docker stop mongo-oz
```

## Bước 3

Kiểm tra lại cluster health:

GET /api/shards/health

Expected:

- AG = UP
- HN = UP
- OZ = DOWN
- clusterHealthy = false

## Bước 4

Test partial failure:

GET /api/users/SUser00001

Vì username bắt đầu bằng S nên request sẽ được route tới shard OZ.

Khi OZ DOWN:

- backend không crash toàn bộ
- request fail đúng shard liên quan
- hệ thống trả lỗi rõ ràng

Expected:

```json
{
  "status": 503,
  "error": "Service Unavailable",
  "message": "Shard OZ unavailable"
}
```

## Bước 5

Recovery shard:

```bash
docker start mongo-oz
```

## Bước 6

Kiểm tra lại cluster health:

GET /api/shards/health

Expected:

- AG = UP
- HN = UP
- OZ = UP
- clusterHealthy = true

## MongoDB Health Check

Project sử dụng:

```java
runCommand(new Document("ping", 1))
```

để kiểm tra trạng thái hoạt động của từng MongoDB shard.

Nếu shard phản hồi:

- status = UP

Nếu shard không phản hồi:

- status = DOWN

## Failure Handling Logic

Khi shard unavailable:

- MongoDB driver throw exception
- UserService catch exception
- throw ShardUnavailableException
- GlobalExceptionHandler trả response lỗi chuẩn

Điều này giúp backend không crash toàn bộ khi một shard bị lỗi.

## Distributed Database Concepts

### Distributed Node Failure

Trong distributed systems, node/database có thể bị stop hoặc mất kết nối bất cứ lúc nào.

Project mô phỏng điều này bằng:

```bash
docker stop mongo-oz
```

### Failure Detection

System hiện có khả năng phát hiện shard nào đang DOWN thông qua health check API.

### Partial Failure

OZ có thể DOWN trong khi AG và HN vẫn hoạt động.

Đây là đặc trưng của distributed systems.

### Fault Tolerance

Backend không crash toàn bộ khi một shard lỗi.

### Graceful Degradation

Chỉ các request liên quan tới shard lỗi mới bị ảnh hưởng.

Các shard còn lại vẫn usable.

### Recovery

Sau khi shard hoạt động lại:

```bash
docker start mongo-oz
```

cluster có thể recover và tiếp tục hoạt động bình thường.

## Ý nghĩa

Day 10 giúp project không chỉ hỗ trợ:

- sharding
- routing
- hotspot analysis
- re-sharding simulation

mà còn hỗ trợ:

- node failure detection
- partial failure handling
- cluster recovery

Điều này giúp hệ thống gần hơn với distributed database behavior thực tế.
