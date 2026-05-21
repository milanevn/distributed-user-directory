# Day 1 Notes

## Mục tiêu

Thiết lập nền tảng project và kiến trúc hệ thống.

## Công việc đã hoàn thành

- Tạo repository local
- Tạo repository GitHub
- Tạo backend Spring Boot
- Tạo frontend React Vite
- Tạo file Docker Compose
- Khởi động ba MongoDB containers
- Tạo cấu trúc project ban đầu

## Khái niệm Distributed Database

Distributed database là hệ cơ sở dữ liệu được nhìn như một hệ thống logic thống nhất, nhưng dữ liệu thực tế được phân tán trên nhiều node khác nhau.

Trong project này, hệ thống User Directory được xem như một hệ thống logic duy nhất, trong khi dữ liệu user được lưu trên nhiều MongoDB shard nodes.

## Horizontal Fragmentation

Horizontal fragmentation là kỹ thuật chia dữ liệu theo hàng hoặc document.

Trong project này, user documents được phân bố dựa trên range username.

## Kiến trúc ban đầu

Frontend React giao tiếp với backend Spring Boot.

Spring Boot đóng vai trò shard router và giao tiếp với ba MongoDB shard nodes:

- mongo-ag
- mongo-hn
- mongo-oz

## Điều chỉnh phạm vi project

Frontend React được tạo trong giai đoạn setup ban đầu, nhưng phạm vi project đã được đổi sang backend-only.

Demo cuối kỳ sẽ sử dụng:

- Postman hoặc curl
- Spring Boot APIs
- MongoDB Compass
- Docker logs
- Terminal output

Điều này giúp project tập trung vào kiến thức distributed database thay vì frontend implementation.
