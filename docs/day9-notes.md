# Day 9 - Re-sharding Simulation

## Mục tiêu

Mô phỏng kết quả phân bố dữ liệu nếu áp dụng chiến lược re-sharding mới.

Day 8 chỉ đề xuất cách chia shard mới. Day 9 kiểm tra xem nếu áp dụng chiến lược đó thì dữ liệu hiện tại sẽ được phân bố lại như thế nào.

Hệ thống chỉ simulation logic, chưa migrate dữ liệu thật.

---

## Dataset hiện tại

Dataset gồm 10,000 users.

Trong đó:

- khoảng 35% username bắt đầu bằng M
- khoảng 35% username bắt đầu bằng S
- khoảng 30% còn lại phân bố random A-Z

Range sharding hiện tại:

- AG: A-G
- HN: H-N
- OZ: O-Z

Do M thuộc H-N và S thuộc O-Z nên dữ liệu bị lệch mạnh về HN và OZ.

---

## Data distribution trước re-sharding

| Shard | Range | User Count | Percentage |
| ----- | ----- | ---------- | ---------- |
| AG    | A-G   | 877        | 8.77%      |
| HN    | H-N   | 4215       | 42.15%     |
| OZ    | O-Z   | 4908       | 49.08%     |

Kết quả cho thấy:

- HN và OZ vượt ngưỡng hotspot 40%
- AG chứa rất ít dữ liệu
- cluster bị mất cân bằng dữ liệu rõ rệt

---

## Re-sharding plan

Chiến lược mới:

| New Shard | Range |
| --------- | ----- |
| SHARD_1   | A-L   |
| SHARD_2   | M     |
| SHARD_3   | N-R   |
| SHARD_4   | S     |
| SHARD_5   | T-Z   |

Mục tiêu:

- tách M riêng
- tách S riêng
- giảm hotspot trên HN và OZ

---

## Simulation process

Hệ thống:

1. đọc toàn bộ users từ AG, HN, OZ
2. xác định ký tự đầu username
3. tính shard mới tương ứng theo range mới
4. đếm số lượng user trên từng shard mới

Simulation không di chuyển dữ liệu thật trong MongoDB.

---

## Simulated distribution

| New Shard | Range | User Count | Percentage |
| --------- | ----- | ---------- | ---------- |
| SHARD_1   | A-L   | 1508       | 15.08%     |
| SHARD_2   | M     | 3447       | 34.47%     |
| SHARD_3   | N-R   | 616        | 6.16%      |
| SHARD_4   | S     | 3553       | 35.53%     |
| SHARD_5   | T-Z   | 876        | 8.76%      |

---

## Imbalance comparison

### Before re-sharding

```text
49.08% - 8.77% = 40.31%
```
