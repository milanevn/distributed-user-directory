# Day 8 - Re-sharding Plan

## Mục tiêu

Sau khi phát hiện hotspot ở Day 7, hệ thống cần đề xuất một chiến lược chia shard mới để giảm mất cân bằng dữ liệu.

## Vấn đề hiện tại

Chiến lược ban đầu chia username theo 3 khoảng:

- A-G -> AG
- H-N -> HN
- O-Z -> OZ

Dataset được tạo theo yêu cầu đề bài, trong đó 70% username bắt đầu bằng M hoặc S.

Vì vậy:

- M thuộc shard HN
- S thuộc shard OZ

Kết quả là HN và OZ dễ bị quá tải hơn AG.

## Kế hoạch re-sharding đề xuất

Chiến lược mới:

- Shard 1: A-L
- Shard 2: M
- Shard 3: N-R
- Shard 4: S
- Shard 5: T-Z

## Lý do

M và S là hai nhóm dữ liệu lớn nhất trong dataset, nên cần tách riêng chúng ra shard riêng.

Cách này giúp:

- giảm tải cho HN và OZ cũ
- làm phân bố dữ liệu rõ ràng hơn
- dễ mở rộng nếu một nhóm username tiếp tục tăng
- chứng minh được cách xử lý hotspot trong range-based sharding

## Ghi chú

Ở Day 8, project chỉ đề xuất kế hoạch re-sharding, chưa thực hiện migrate dữ liệu thật.

Việc mô phỏng kết quả sau re-sharding sẽ được thực hiện ở Day 9.
