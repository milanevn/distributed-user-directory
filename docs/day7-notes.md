# Day 7 Notes

## Mục tiêu

Phát hiện shard hotspot dựa trên request traffic.

Day 6 đo phân bố dữ liệu trên shard.

Day 7 đo phân bố request trên shard.

## Công việc đã hoàn thành

- Tạo ShardHotspotMetric DTO
- Tạo HotspotAnalysisResponse DTO
- Tạo ShardMetricsService
- Theo dõi insert request theo từng shard
- Theo dõi search request theo từng shard
- Tính total request từng shard
- Tính request percentage từng shard
- Tạo API GET /api/shards/hotspots
- Tạo API POST /api/shards/metrics/reset
- Test hotspot bằng search traffic tập trung vào HN hoặc OZ

## APIs

### Phân tích hotspot

GET /api/shards/hotspots

### Reset metrics

POST /api/shards/metrics/reset

## Hotspot Rule

Một shard được xem là hotspot nếu request percentage vượt threshold.

Trong project hiện tại:

threshold = 70%

## Vì sao có thể xảy ra hotspot?

Current shard ranges:

- A-G -> shard_ag
- H-N -> shard_hn
- O-Z -> shard_oz

Nếu nhiều request search username bắt đầu bằng S, phần lớn request sẽ được route vào shard_oz.

Nếu nhiều request search username bắt đầu bằng M, phần lớn request sẽ được route vào shard_hn.

Khi một shard nhận phần lớn request, shard đó trở thành hotspot.

## Liên hệ với Distributed Database Systems

Trong distributed database systems, dữ liệu và workload được phân bố trên nhiều node.

Nếu phân bố không đều, một số node có thể phải xử lý nhiều công việc hơn các node khác.

Điều này làm giảm hiệu quả load balancing và có thể làm tăng latency.

## Ý nghĩa

Day 7 giúp hệ thống không chỉ lưu và query dữ liệu phân tán, mà còn bắt đầu quan sát workload của từng shard.

Đây là nền tảng để Day 8 đề xuất re-sharding logic.
