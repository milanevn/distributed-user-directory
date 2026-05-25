# Day 5 Notes

## Mục tiêu

Generate dataset lệch theo đúng yêu cầu đề tài.

## Yêu cầu dataset

Dataset phải có:

- 70% username bắt đầu bằng M hoặc S
- 30% username bắt đầu bằng các chữ cái khác

## Công việc đã hoàn thành

- Tạo DatasetGenerationResponse DTO
- Thêm clearAllUsers trong UserService
- Thêm countUsersInShard trong UserService
- Tạo DatasetGeneratorService
- Tạo DatasetController
- Thêm API POST /api/dataset/generate
- Test generate dataset
- Verify phân bố dữ liệu bằng MongoDB Compass

## API

POST /api/dataset/generate?size=10000&clear=true

## Dataset Distribution Logic

- 70% username bắt đầu bằng M hoặc S
- 30% username bắt đầu bằng random letters khác

## Vì sao M và S quan trọng?

Current shard ranges:

- A-G -> shard_ag
- H-N -> shard_hn
- O-Z -> shard_oz

M thuộc H-N.

S thuộc O-Z.

Do đó, khi generate nhiều username bắt đầu bằng M và S, dữ liệu sẽ bị lệch về shard_hn và shard_oz.

## Quy tắc implementation quan trọng

Dataset generator không insert trực tiếp vào MongoDB.

Generator luôn gọi:

UserService.createUser(...)

để dữ liệu vẫn đi qua shard routing logic thật của hệ thống.

Điều này chứng minh routing layer vẫn hoạt động đúng khi dataset lớn hơn.

## Distributed Database Concepts

### Data Skew

Data skew xảy ra khi dữ liệu phân bố không đều giữa các shards.

Trong dataset hiện tại:

- shard_hn nhận nhiều username bắt đầu bằng M
- shard_oz nhận nhiều username bắt đầu bằng S

### Hotspot Preparation

Data skew là nguyên nhân dẫn tới hotspot.

Một số shards sẽ phải xử lý nhiều dữ liệu và requests hơn các shard khác.

Dataset hiện tại được thiết kế để chuẩn bị cho hotspot analysis ở các bước tiếp theo.

### Distributed Dataset Simulation

Dataset generator hiện mô phỏng cách dữ liệu thực tế có thể phân bố lệch trong distributed systems.
