# Day 6 Notes

## Mục tiêu

Phân tích phân bố dữ liệu trên cluster để phát hiện data skew.

## Công việc đã hoàn thành

- Tạo ShardStatistic DTO
- Tạo ClusterStatisticsResponse DTO
- Tạo StatisticsService
- Tạo StatisticsController
- Thêm API GET /api/statistics/shards
- Tính số lượng user trên từng shard
- Tính phần trăm dữ liệu từng shard
- Xác định shard lớn nhất và nhỏ nhất
- Verify skew bằng dataset 10000 users

## API

GET /api/statistics/shards

## Ý nghĩa

System hiện đã có khả năng monitoring cluster distribution.

Backend có thể:

- đo số lượng dữ liệu trên từng shard
- phát hiện shard imbalance
- xác định hotspot candidates

## Data Skew

Kết quả cho thấy:

- shard_hn và shard_oz chứa nhiều dữ liệu hơn
- shard_ag chứa ít dữ liệu hơn

Điều này xảy ra do dataset skewed:

- 70% username bắt đầu bằng M hoặc S

## Distributed Database Concepts

### Cluster Statistics

Cluster statistics giúp theo dõi trạng thái phân bố dữ liệu trên distributed system.

### Data Skew Detection

System hiện có khả năng phát hiện phân bố dữ liệu không đều giữa các shards.

### Hotspot Candidate

Shard chứa quá nhiều dữ liệu có nguy cơ trở thành hotspot khi traffic tăng cao.
