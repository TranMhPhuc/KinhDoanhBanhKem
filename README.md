# Thông tin chung

Đồ án Nhập môn Công nghệ phần mềm năm 2018.
Tên đồ án: Quản lý cửa hàng kinh doanh bánh kem

# Mô tả yêu cầu phần mềm

Mục tiêu là xây dựng, phát triển phần mềm quản lý bán bánh kem dành cho cửa hàng kinh doanh với qui mô nhỏ. Đối tượng sử dụng là người quản trị hệ thống phần mềm, các nhân viên làm việc trong cửa hàng (gồm nhân viên quản lý, kế toán và nhân viên bán hàng).

Các thành phần của chương trình bao gồm giao diện tương tác người dùng, truy cập cơ sở dữ liệu, các bản mô tả kĩ thuật và kịch bản kiểm thử phần mềm.

Kết quả của phần mềm phải đảm bảo các tiêu chuẩn:

- [ ] Tính đúng đắn:
  - Sử dụng đúng thuật toán.
  - Kiểm thử thuật toán.
- [ ] Tính tiến hóa:

  - Tùy biến theo cách sử dụng của người dùng.

    Ví dụ: Nhân viên quản lý có thể đồng thời xem dữ liệu các hóa đơn và dữ liệu doanh thu trong 2 cửa sổ và đặt vị trí các cửa sổ một cách tùy ý.

  - Dễ mở rộng chức năng về sau.

- Tính hiệu quả:
  - [ ] Tốc độ xử lý phải tối ưu.
  - [ ] Có kiểm thử tốc độ thực thi.
  - [ ] Sử dụng tốt tài nguyên máy tính.
- [ ] Tính tiện dụng:
  - [ ] Giao diện dễ dùng.
  - [ ] Cung cấp đầy đủ chức năng cần thiết cho mỗi user.
- [ ] Tính tương thích:
  - Nhập/xuất dữ liệu sang các định dạng:
    - [ ] Excel: dữ liệu hàng hóa.
    - [ ] PDF: report thống kê, hóa đơn.
  - Cập nhật thông tin từ website nhà cung cấp:
    - [ ] Cập nhật giá thành hàng hóa.
- Tính tái sử dụng:
  - XXX (chưa rõ)

# Mô tả chức năng

- [x] Giao diện người dùng: Swing java library.
- [x] Cơ sở dữ liệu: MS SQL Database.
- [x] Mô hình kiến trúc: MVC.

1. Login, logout (mọi nhân viên).
1. Nhân viên được quyền **xem và thay đổ**i thông tin cá nhân (ngoài trừ mã nhân viên).
1. Chương trình gồm 3 chức năng chính:
   - Chức năng bán hàng:
     - Tạo, xuất hóa đơn.
   - Chức năng quản lý:
     - Quản lý dữ liệu các nhân viên.
     - Quản lý dữ liệu hàng trong kho.
     - Quản lý dữ liệu nguyên liệu chế biến.
     - Quản lý dữ liệu hóa đơn.
     - Quản lý dữ liệu nhà cung cấp.
   - Chức năng thống kê:
     - Thống kê doanh thu.

# Mô tả dữ liệu và phân quyền.

### Chương trình gồm các user (vai trò theo thứ tự giảm dần):

1. Admin quản trị
2. Quản lý
3. Kế toán
4. Bán hàng

### Phân quyền trên CSDL:

1. Admin quản trị có **toàn quyền hệ thống**.
1. Dữ liệu nhân viên:
   - Quản lý: toàn quyền.
   - Kế toán: không.
   - Bán hàng: không.
1. Dữ liệu hàng trong kho:
   - Quản lý: toàn quyền.
   - Kế toán: xem.
   - Bán hàng: không.
1. Dữ liệu nguyên liệu chế biến:
   - Quản lý: toàn quyền.
   - Kế toán: xem.
   - Bán hàng: không.
1. Dữ liệu hóa đơn:
   - Quản lý: toàn quyền.
   - Kế toán: toàn quyền.
   - Bán hàng: không.
1. Dữ liệu nhà cung cấp:
   - Quản lý: toàn quyền.
   - Kế toán: không.
   - Bán hàng: không.
1. Dữ liệu doanh thu:
   - Quản lý: toàn quyền.
   - Kế toán: không.
   - Bán hàng: không.

# TODO List

- Viết mô tả cơ bản về phần mềm.
  - [x] Mô tả yêu cầu người dùng.
  - [x] Mô tả chức năng.
  - [x] Mô tả cơ sở dữ liệu.
  - [ ] Mô tả kiến trúc và giải thuật.
  - [ ] Mô tả các bước thực hiện và thời hạn hoàn thành.
- Thiết kế cơ sở dữ liệu.
  - [ ] Tạo cơ sở dữ liệu.
  - [ ] Chuẩn hóa cơ sở dữ liệu.
  - [ ] Phân quyền các user.
  - [ ] Tạo Views, Store Procedures (thực hiện khi xây dựng mã nguồn).
- [ ] Tạo prototype giao diện.
- Các kịch bản Test thực thi tự động cần viết:
  - [ ] Functional Test.
  - [ ] Unit Test.
  - [ ] Security Test.
  - [ ] Performance Test.
- Xây dựng mã nguồn theo kịch bản test.
