# Bài 6 – CI/CD Pipeline Optimization & Caching

## 1. Mục tiêu
Cấu hình Maven dependency caching trong GitHub Actions bằng `actions/setup-java@v4`
với `cache: maven`, sau đó so sánh lần chạy không cache và lần chạy có cache.

## 2. Cấu trúc thư mục
```text
Bai6/
├── pom.xml
├── README.md
├── src/
│   ├── main/java/MathUtils.java
│   └── test/java/MathUtilsTest.java
└── workflow-template/
    └── bai6-maven-cache.yml
```

## 3. Cài đặt workflow vào repository Week9
GitHub chỉ tự nhận workflow trong `.github/workflows` ở thư mục gốc repository.
Vì `Week9` là repository root, hãy sao chép file:

`Bai6/workflow-template/bai6-maven-cache.yml`

sang:

`Week9/.github/workflows/bai6-maven-cache.yml`

Không đặt workflow chỉ ở `Week9/Bai6/.github`, vì GitHub Actions sẽ không tự phát hiện vị trí đó.

Workflow sử dụng:
- `cache: maven`: bật Maven dependency cache.
- `cache-dependency-path: Bai6/pom.xml`: xác định POM để tạo cache key.
- `mvn -B -f Bai6/pom.xml clean verify`: chạy bài 6 từ repository root.

## 4. Cách đo trước và sau khi bật cache

Để so sánh công bằng, dùng cùng một runner (Ubuntu), cùng JDK 17, cùng POM
và cùng lệnh Maven.

### Lần A – Baseline, không bật cache
1. Trong workflow, tạm thời xóa hai dòng:
   `cache: maven`
   `cache-dependency-path: Bai6/pom.xml`
2. Commit và push:
   ```bash
   git add .
   git commit -m "Bai6: measure Maven build without cache"
   git push origin main
   ```
3. Mở GitHub → Actions → chọn lần chạy tương ứng.
4. Ghi lại thời gian tổng thể và thời gian bước Maven.

### Lần B – Bật cache
1. Khôi phục hai dòng cache trong workflow:
   ```yaml
   cache: maven
   cache-dependency-path: Bai6/pom.xml
   ```
2. Commit và push:
   ```bash
   git add .
   git commit -m "Bai6: enable Maven dependency cache"
   git push origin main
   ```
3. Lần chạy này có thể là cache miss và tạo cache mới.

### Lần C – Xác nhận cache hit
1. Thực hiện một thay đổi nhỏ không chỉnh sửa `Bai6/pom.xml`, ví dụ thêm comment vào `MathUtils.java`.
2. Commit và push:
   ```bash
   git add .
   git commit -m "Bai6: verify Maven cache reuse"
   git push origin main
   ```
3. Mở log bước `Set up JDK 17 and restore Maven cache`.
4. Kiểm tra thông báo cache restored/cache hit. Tên log cụ thể có thể khác theo phiên bản action.

Lưu ý: GitHub Actions có thể không hiện nguyên văn `Cache hit` ở mọi phiên bản.
Hãy xem log của bước setup-java để xác nhận cache được khôi phục thành công.
Lần B có thể là cache miss vì đó là lần đầu tạo cache sau khi bật.

## 5. Bảng ghi kết quả thực nghiệm

Điền thời gian thực tế lấy từ GitHub Actions, không điền số liệu ước lượng.

| Tiêu chí | Lần A: Không cache | Lần B: Cache miss | Lần C: Cache hit |
|---|---|---|---|
| Cấu hình cache | Tắt | Bật | Bật |
| Cache khôi phục | Không | Chưa có cache | Có, nếu key khớp |
| Tổng thời gian workflow | ... | ... | ... |
| Thời gian bước Maven | ... | ... | ... |
| Kết quả | ... | ... | ... |

Công thức tính phần trăm tiết kiệm thời gian (so sánh A với C):

`((Thời gian A - Thời gian C) / Thời gian A) x 100%`

Nếu thời gian C không thấp hơn A, hãy ghi nhận đúng kết quả. Thời gian còn phụ thuộc
vào runner, mạng, tải máy chủ và các bước khác ngoài tải dependency.

## 6. Ảnh chụp màn hình nên nộp
- Workflow YAML có `cache: maven`.
- Log lần A (không cache).
- Log lần C có thông báo khôi phục cache thành công.
- Trang Actions thể hiện thời gian chạy của các lần.
- Bảng so sánh đã điền số liệu thực tế.

## 7. Chạy thử cục bộ
Từ thư mục gốc `Week9`:
```bash
mvn -f Bai6/pom.xml clean verify
```
Nếu kiểm thử thành công, Maven hiển thị `BUILD SUCCESS`.
