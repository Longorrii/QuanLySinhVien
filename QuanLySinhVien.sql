-- Tạo cơ sở dữ liệu
CREATE DATABASE QuanLySinhVien;
USE QuanLySinhVien;
SHOW DATABASES;

-- Tạo cấu trúc bảng SINHVIEN
CREATE TABLE SINHVIEN(
  MSSV char(8) ,
  HoTen varchar(40),
  GioiTinh char(3),
  NgaySinh date,
  QueQuan varchar(40),
  MaKhoa char(6),
  MaNganh char(4),
  KhoaHoc int(4),
  CONSTRAINT PK_SINHVIEN PRIMARY KEY(MSSV)
);

-- Tạo cấu trúc bảng KHOA
CREATE TABLE KHOA (
  MaKhoa char(6),
  TenKhoa varchar(40),
  CONSTRAINT PK_KHOA PRIMARY KEY(MaKhoa)
);

-- Tạo cấu trúc bảng NGANH
CREATE TABLE NGANH (
  MaNganh char(4),
  MaKhoa char(6),
  TenNganh varchar(40) NOT NULL,
  CONSTRAINT PK_NGANH PRIMARY KEY(MaNganh)
);

-- Tạo cấu trúc bảng HOCPHAN
CREATE TABLE HOCPHAN (
  MaHP char(5) NOT NULL,
  TenHP varchar(40) NOT NULL,
  MaKhoa char(6) NOT NULL,
  SoTinChi int(11) NOT NULL,
  CONSTRAINT PK_HOCPHAN PRIMARY KEY(MaHP)
);

-- Tạo cấu trúc bảng KETQUA
CREATE TABLE KETQUA (
  MaHP char(5) NOT NULL,
  MSSV char(8) NOT NULL,
  HocKy int(11) NOT NULL,
  NamHoc int(11) NOT NULL,
  Diem int(11) DEFAULT NULL,
  CONSTRAINT PK_KETQUA PRIMARY KEY(MaHP,MSSV)
);

-- Tạo cấu trúc bảng TAIKHOAN
CREATE TABLE TAIKHOAN (
  MSSV char(8) NOT NULL,
  MatKhau varchar(40) DEFAULT NULL,
  Loai varchar(2) DEFAULT NULL,
  CONSTRAINT PK_TAIKHOAN PRIMARY KEY(MSSV)
);
-- Tạo các khóa ngoại ---------------------------------------------------------

-- SINHVIEN
ALTER TABLE SINHVIEN ADD CONSTRAINT FK01_SINHVIEN FOREIGN KEY(MaKhoa) REFERENCES KHOA(MaKhoa);
ALTER TABLE SINHVIEN ADD CONSTRAINT FK02_SINHVIEN FOREIGN KEY(MaNganh) REFERENCES Nganh(MaNganh);

-- NGANH
ALTER TABLE NGANH ADD CONSTRAINT FK_NGANH FOREIGN KEY(MaKhoa) REFERENCES KHOA(MaKhoa);

-- HOCPHAN
ALTER TABLE HOCPHAN ADD CONSTRAINT FK_HOCPHAN FOREIGN KEY(MaKhoa) REFERENCES KHOA(MaKhoa);

-- KETQUA
ALTER TABLE KETQUA ADD CONSTRAINT FK01_KETQUA FOREIGN KEY(MaHP) REFERENCES HOCPHAN(MaHP);
ALTER TABLE KETQUA ADD CONSTRAINT FK02_KETQUA FOREIGN KEY(MSSV) REFERENCES SINHVIEN(MSSV);

-- THÊM DỮ LIỆU --------------------------------------------------------

-- KHOA
INSERT INTO KHOA (MaKhoa,TenKhoa) VALUES ('HTTT','He thong thong tin');
INSERT INTO KHOA (MaKhoa,TenKhoa) VALUES ('KTMT','Ky thuat may tinh');
INSERT INTO KHOA (MaKhoa,TenKhoa) VALUES ('KTTT','Khoa hoc va ky thuat thong tin');
INSERT INTO KHOA (MaKhoa,TenKhoa) VALUES ('KHMT','Khoa hoc may tinh');
INSERT INTO KHOA (MaKhoa,TenKhoa) VALUES ('CNPM','Cong nghe phan mem');
INSERT INTO KHOA (MaKhoa,TenKhoa) VALUES ('MMT&TT','Mang may tinh va truyen thong');

-- NGANH
INSERT INTO NGANH (MaNganh,MaKhoa,TenNganh) VALUES ('HTTT','HTTT','He thong thong tin');
INSERT INTO NGANH (MaNganh,MaKhoa,TenNganh) VALUES ('TMDT','HTTT','Thuong mai dien tu');
INSERT INTO NGANH (MaNganh,MaKhoa,TenNganh) VALUES ('CNTT','KTTT','Cong nghe thong tin');
INSERT INTO NGANH (MaNganh,MaKhoa,TenNganh) VALUES ('KHDL','KTTT','Khoa hoc du lieu');
INSERT INTO NGANH (MaNganh,MaKhoa,TenNganh) VALUES ('KHMT','KHMT','Khoa hoc may tinh');
INSERT INTO NGANH (MaNganh,MaKhoa,TenNganh) VALUES ('KHNT','KHMT','Tri tue nhan tao');
INSERT INTO NGANH (MaNganh,MaKhoa,TenNganh) VALUES ('KTPM','CNPM','Ky thuat phan mem');
INSERT INTO NGANH (MaNganh,MaKhoa,TenNganh) VALUES ('ATTT','MMT&TT','An toan thong tin');
INSERT INTO NGANH (MaNganh,MaKhoa,TenNganh) VALUES ('MMTT','MMT&TT','Mang may tinh va truyen thong du lieu');
INSERT INTO NGANH (MaNganh,MaKhoa,TenNganh) VALUES ('KTMT','KTMT','Ky Thuat May Tinh');


-- HOCPHAN
INSERT INTO HOCPHAN (MaHP,TenHP,MaKhoa,SoTinChi) VALUES ('IT001','Nhap mon lap trinh','KHMT',4);
INSERT INTO HOCPHAN (MaHP,TenHP,MaKhoa,SoTinChi) VALUES ('IT002','Lap trinh huong doi tuong','CNPM',4);
INSERT INTO HOCPHAN (MaHP,TenHP,MaKhoa,SoTinChi) VALUES ('IT003','Cau truc du lieu va giai thuat','KHMT',4);
INSERT INTO HOCPHAN (MaHP,TenHP,MaKhoa,SoTinChi) VALUES ('IT004','Co so du lieu','HTTT',4);
INSERT INTO HOCPHAN (MaHP,TenHP,MaKhoa,SoTinChi) VALUES ('IT005','Nhap mon mang may tinh','MMT&TT',4);
INSERT INTO HOCPHAN (MaHP,TenHP,MaKhoa,SoTinChi) VALUES ('IT006','Kien truc may tinh','KTMT',3);
INSERT INTO HOCPHAN (MaHP,TenHP,MaKhoa,SoTinChi) VALUES ('IT007','He dieu hanh','KTMT',4);
INSERT INTO HOCPHAN (MaHP,TenHP,MaKhoa,SoTinChi) VALUES ('IT008','Lap trinh truc quan','CNPM',4);
INSERT INTO HOCPHAN (MaHP,TenHP,MaKhoa,SoTinChi) VALUES ('IS201','Phan tich thiet ke he thong thong tin','HTTT',4);
INSERT INTO HOCPHAN (MaHP,TenHP,MaKhoa,SoTinChi) VALUES ('IS210','Hệ quan tri co so du lieu','HTTT',4);
INSERT INTO HOCPHAN (MaHP,TenHP,MaKhoa,SoTinChi) VALUES ('IS216','Lap trinh Java','HTTT',4);
INSERT INTO HOCPHAN (MaHP,TenHP,MaKhoa,SoTinChi) VALUES ('IS207','Phat trien ung dung Web','HTTT',4);
INSERT INTO HOCPHAN (MaHP,TenHP,MaKhoa,SoTinChi) VALUES ('CE107','He thong nhung','KTMT',4);
INSERT INTO HOCPHAN (MaHP,TenHP,MaKhoa,SoTinChi) VALUES ('CE115','Thiet ke mang','KTMT',4);
INSERT INTO HOCPHAN (MaHP,TenHP,MaKhoa,SoTinChi) VALUES ('CE113','Dieu khien tu dong','KTMT',4);
INSERT INTO HOCPHAN (MaHP,TenHP,MaKhoa,SoTinChi) VALUES ('CE222','Thiet ke vi mach so','KTMT',4);
INSERT INTO HOCPHAN (MaHP,TenHP,MaKhoa,SoTinChi) VALUES ('CS114','May hoc','KHMT',4);
INSERT INTO HOCPHAN (MaHP,TenHP,MaKhoa,SoTinChi) VALUES ('CS106','Tri tue nhan tao','KHMT',4);
INSERT INTO HOCPHAN (MaHP,TenHP,MaKhoa,SoTinChi) VALUES ('CS105','Do hoa may tinh','KHMT',4);
INSERT INTO HOCPHAN (MaHP,TenHP,MaKhoa,SoTinChi) VALUES ('CS338','Nhan dang','KHMT',4);
INSERT INTO HOCPHAN (MaHP,TenHP,MaKhoa,SoTinChi) VALUES ('CS114','May hoc','KHMT',4);
INSERT INTO HOCPHAN (MaHP,TenHP,MaKhoa,SoTinChi) VALUES ('CS106','Tri tue nhan tao','KHMT',4);
INSERT INTO HOCPHAN (MaHP,TenHP,MaKhoa,SoTinChi) VALUES ('CS105','Do hoa may tinh','KHMT',4);
INSERT INTO HOCPHAN (MaHP,TenHP,MaKhoa,SoTinChi) VALUES ('CS338','Nhan dang','KHMT',4);
INSERT INTO HOCPHAN (MaHP,TenHP,MaKhoa,SoTinChi) VALUES ('DS300','He khuyen nghi','KTTT',4);
INSERT INTO HOCPHAN (MaHP,TenHP,MaKhoa,SoTinChi) VALUES ('DS301','Cac giai thuat khai pha du lieu lon','KTTT',4);
INSERT INTO HOCPHAN (MaHP,TenHP,MaKhoa,SoTinChi) VALUES ('DS200','Phan tich du lieu lon','KTTT',4);
INSERT INTO HOCPHAN (MaHP,TenHP,MaKhoa,SoTinChi) VALUES ('IE222','Phan tich du lieu','KTTT',4);
INSERT INTO HOCPHAN (MaHP,TenHP,MaKhoa,SoTinChi) VALUES ('NT106','Lap trinh mang can bang','MMT&TT',4);
INSERT INTO HOCPHAN (MaHP,TenHP,MaKhoa,SoTinChi) VALUES ('NT109','Lap trinh ung dung mang','MMT&TT',4);
INSERT INTO HOCPHAN (MaHP,TenHP,MaKhoa,SoTinChi) VALUES ('NT119','Mat ma hoc','MMT&TT',4);
INSERT INTO HOCPHAN (MaHP,TenHP,MaKhoa,SoTinChi) VALUES ('NT101','An toan mang may tinh','MMT&TT',4);
INSERT INTO HOCPHAN (MaHP,TenHP,MaKhoa,SoTinChi) VALUES ('SE215','Giao tiep nguoi máy','CNPM',4);
INSERT INTO HOCPHAN (MaHP,TenHP,MaKhoa,SoTinChi) VALUES ('SE220','Thiet ke game','CNPM',4);
INSERT INTO HOCPHAN (MaHP,TenHP,MaKhoa,SoTinChi) VALUES ('SE213','Xu ly phan bo','CNPM',4);
INSERT INTO HOCPHAN (MaHP,TenHP,MaKhoa,SoTinChi) VALUES ('SE351','Xu ly song song','CNPM',4);

-- TAIKHOAN
INSERT INTO TAIKHOAN (MSSV,MatKhau,Loai) VALUES ('admin','admin','GV');
INSERT INTO TAIKHOAN (MSSV,MatKhau,Loai) VALUES ('18520406','1','SV');
INSERT INTO TAIKHOAN (MSSV,MatKhau,Loai) VALUES ('18521103','1','SV');
INSERT INTO TAIKHOAN (MSSV,MatKhau,Loai) VALUES ('18521582','1','SV');
INSERT INTO TAIKHOAN (MSSV,MatKhau,Loai) VALUES ('18521592','1','SV');
INSERT INTO TAIKHOAN (MSSV,MatKhau,Loai) VALUES ('18521601','1','SV');
INSERT INTO TAIKHOAN (MSSV,MatKhau,Loai) VALUES ('18521623','1','SV');
INSERT INTO TAIKHOAN (MSSV,MatKhau,Loai) VALUES ('18521633','1','SV');
INSERT INTO TAIKHOAN (MSSV,MatKhau,Loai) VALUES ('18521639','1','SV');
INSERT INTO TAIKHOAN (MSSV,MatKhau,Loai) VALUES ('18521690','1','SV');
INSERT INTO TAIKHOAN (MSSV,MatKhau,Loai) VALUES ('18521790','1','SV');
INSERT INTO TAIKHOAN (MSSV,MatKhau,Loai) VALUES ('19521416','1','SV');
INSERT INTO TAIKHOAN (MSSV,MatKhau,Loai) VALUES ('19521420','1','SV');
INSERT INTO TAIKHOAN (MSSV,MatKhau,Loai) VALUES ('19521562','1','SV');
INSERT INTO TAIKHOAN (MSSV,MatKhau,Loai) VALUES ('19522080','1','SV');
INSERT INTO TAIKHOAN (MSSV,MatKhau,Loai) VALUES ('19522081','1','SV');
INSERT INTO TAIKHOAN (MSSV,MatKhau,Loai) VALUES ('19522103','1','SV');
INSERT INTO TAIKHOAN (MSSV,MatKhau,Loai) VALUES ('19522110','1','SV');
INSERT INTO TAIKHOAN (MSSV,MatKhau,Loai) VALUES ('19522120','1','SV');
INSERT INTO TAIKHOAN (MSSV,MatKhau,Loai) VALUES ('19522132','1','SV');
INSERT INTO TAIKHOAN (MSSV,MatKhau,Loai) VALUES ('19522140','1','SV');
INSERT INTO TAIKHOAN (MSSV,MatKhau,Loai) VALUES ('20521123','1','SV');
INSERT INTO TAIKHOAN (MSSV,MatKhau,Loai) VALUES ('20521245','1','SV');
INSERT INTO TAIKHOAN (MSSV,MatKhau,Loai) VALUES ('20521455','1','SV');
INSERT INTO TAIKHOAN (MSSV,MatKhau,Loai) VALUES ('20521466','1','SV');
INSERT INTO TAIKHOAN (MSSV,MatKhau,Loai) VALUES ('20521499','1','SV');
INSERT INTO TAIKHOAN (MSSV,MatKhau,Loai) VALUES ('20521511','1','SV');
INSERT INTO TAIKHOAN (MSSV,MatKhau,Loai) VALUES ('20521563','1','SV');
INSERT INTO TAIKHOAN (MSSV,MatKhau,Loai) VALUES ('20522022','1','SV');
INSERT INTO TAIKHOAN (MSSV,MatKhau,Loai) VALUES ('20522062','1','SV');
INSERT INTO TAIKHOAN (MSSV,MatKhau,Loai) VALUES ('20522191','1','SV');
INSERT INTO TAIKHOAN (MSSV,MatKhau,Loai) VALUES ('21521116','1','SV');
INSERT INTO TAIKHOAN (MSSV,MatKhau,Loai) VALUES ('21521221','1','SV');
INSERT INTO TAIKHOAN (MSSV,MatKhau,Loai) VALUES ('21521362','1','SV');
INSERT INTO TAIKHOAN (MSSV,MatKhau,Loai) VALUES ('21521580','1','SV');
INSERT INTO TAIKHOAN (MSSV,MatKhau,Loai) VALUES ('21522118','1','SV');
INSERT INTO TAIKHOAN (MSSV,MatKhau,Loai) VALUES ('21522203','1','SV');
INSERT INTO TAIKHOAN (MSSV,MatKhau,Loai) VALUES ('21522310','1','SV');
INSERT INTO TAIKHOAN (MSSV,MatKhau,Loai) VALUES ('21522419','1','SV');
INSERT INTO TAIKHOAN (MSSV,MatKhau,Loai) VALUES ('21522530','1','SV');
INSERT INTO TAIKHOAN (MSSV,MatKhau,Loai) VALUES ('21522610','1','SV');

-- SINHVIEN
INSERT INTO SINHVIEN (MSSV,HoTen,GioiTinh,NgaySinh,QueQuan,MaKhoa,MaNganh,KhoaHoc) VALUES ('18520406','Le Thi My Au','Nu','2000-07-04','Ha Noi','MMT&TT','ATTT','13');
INSERT INTO SINHVIEN (MSSV,HoTen,GioiTinh,NgaySinh,QueQuan,MaKhoa,MaNganh,KhoaHoc) VALUES ('18521103','Le Trong Nghia','Nam','2000-04-06','Ca Mau','MMT&TT','ATTT','13');
INSERT INTO SINHVIEN (MSSV,HoTen,GioiTinh,NgaySinh,QueQuan,MaKhoa,MaNganh,KhoaHoc) VALUES ('18521582','Vuong Thanh Tuan','Nam','2000-06-08','Vung Tau','HTTT','HTTT','13');
INSERT INTO SINHVIEN (MSSV,HoTen,GioiTinh,NgaySinh,QueQuan,MaKhoa,MaNganh,KhoaHoc) VALUES ('18521592','Dinh Quang Hung','Nam','2000-01-05','Ca Mau','KTTT','KHDL','13');
INSERT INTO SINHVIEN (MSSV,HoTen,GioiTinh,NgaySinh,QueQuan,MaKhoa,MaNganh,KhoaHoc) VALUES ('18521601','Le Ngoc My Duyen','Nu','2000-02-04','Long An','KHMT','KHMT','13');
INSERT INTO SINHVIEN (MSSV,HoTen,GioiTinh,NgaySinh,QueQuan,MaKhoa,MaNganh,KhoaHoc) VALUES ('18521623','Tran Duc Luong','Nam','2000-10-23','Binh Dinh','KHMT','KHNT','13');
INSERT INTO SINHVIEN (MSSV,HoTen,GioiTinh,NgaySinh,QueQuan,MaKhoa,MaNganh,KhoaHoc) VALUES ('18521633','Le Nhat Minh','Nam','2000-08-17','Binh Phuoc','CNPM','KTPM','13');
INSERT INTO SINHVIEN (MSSV,HoTen,GioiTinh,NgaySinh,QueQuan,MaKhoa,MaNganh,KhoaHoc) VALUES ('18521639','Doan Van Nghia','Nam','2000-03-09','Bac Giang','CNPM','KTPM','13');
INSERT INTO SINHVIEN (MSSV,HoTen,GioiTinh,NgaySinh,QueQuan,MaKhoa,MaNganh,KhoaHoc) VALUES ('18521690','Dang Le Ngoc Tram','Nu','2000-01-01','Ben Tre','HTTT','TMDT','13');
INSERT INTO SINHVIEN (MSSV,HoTen,GioiTinh,NgaySinh,QueQuan,MaKhoa,MaNganh,KhoaHoc) VALUES ('18521790','Dang Minh Hoang','Nam','2000-01-06','Binh Duong','KTMT','KTMT','13');
INSERT INTO SINHVIEN (MSSV,HoTen,GioiTinh,NgaySinh,QueQuan,MaKhoa,MaNganh,KhoaHoc) VALUES ('19521416','Nguyen Thi Thu','Nu','2001-03-08','Binh Dinh','MMT&TT','ATTT','14');
INSERT INTO SINHVIEN (MSSV,HoTen,GioiTinh,NgaySinh,QueQuan,MaKhoa,MaNganh,KhoaHoc) VALUES ('19521420','Nguyen Quang Sang','Nam','2001-01-03','Phu Yen','KTTT','CNTT','14');
INSERT INTO SINHVIEN (MSSV,HoTen,GioiTinh,NgaySinh,QueQuan,MaKhoa,MaNganh,KhoaHoc) VALUES ('19521562','Ho Xuan Ninh','Nam','2001-06-08','Vung Tau','HTTT','HTTT','14');
INSERT INTO SINHVIEN (MSSV,HoTen,GioiTinh,NgaySinh,QueQuan,MaKhoa,MaNganh,KhoaHoc) VALUES ('19522080','Duong Chi Hieu','Nam','2001-04-05','Phu Yen','HTTT','HTTT','14');
INSERT INTO SINHVIEN (MSSV,HoTen,GioiTinh,NgaySinh,QueQuan,MaKhoa,MaNganh,KhoaHoc) VALUES ('19522081','Le Thi Kim Chi','Nu','2001-02-04','Long An','KHMT','KHNT','14');
INSERT INTO SINHVIEN (MSSV,HoTen,GioiTinh,NgaySinh,QueQuan,MaKhoa,MaNganh,KhoaHoc) VALUES ('19522103','Phan Hong Gia Han','Nu','2001-11-23','Quang Nam','KHMT','KHNT','14');
INSERT INTO SINHVIEN (MSSV,HoTen,GioiTinh,NgaySinh,QueQuan,MaKhoa,MaNganh,KhoaHoc) VALUES ('19522110','Nguyen Ba Hoang','Nam','2001-07-16','Binh Phuoc','CNPM','KTPM','14');
INSERT INTO SINHVIEN (MSSV,HoTen,GioiTinh,NgaySinh,QueQuan,MaKhoa,MaNganh,KhoaHoc) VALUES ('19522120','Nguyen Thi Hong','Nu','2001-09-09','HCM','MMT&TT','MMTT','14');
INSERT INTO SINHVIEN (MSSV,HoTen,GioiTinh,NgaySinh,QueQuan,MaKhoa,MaNganh,KhoaHoc) VALUES ('19522132','Le Xuan Minh','Nam','2002-11-07','Ben Tre','HTTT','TMDT','14');
INSERT INTO SINHVIEN (MSSV,HoTen,GioiTinh,NgaySinh,QueQuan,MaKhoa,MaNganh,KhoaHoc) VALUES ('19522140','Bui Le Minh Tri','Nam','2002-03-01','Ninh Thuan','KTMT','KTMT','14');
INSERT INTO SINHVIEN (MSSV,HoTen,GioiTinh,NgaySinh,QueQuan,MaKhoa,MaNganh,KhoaHoc) VALUES ('20521123','Nguyen Thanh Nga','Nu','2002-04-01','Da Nang','MMT&TT','ATTT','15');
INSERT INTO SINHVIEN (MSSV,HoTen,GioiTinh,NgaySinh,QueQuan,MaKhoa,MaNganh,KhoaHoc) VALUES ('20521245','Trinh Le Bao An','Nu','2002-08-03','Phu Yen','KTTT','CNTT','15');
INSERT INTO SINHVIEN (MSSV,HoTen,GioiTinh,NgaySinh,QueQuan,MaKhoa,MaNganh,KhoaHoc) VALUES ('20521455','Bui Duc Lam','Nam','2002-09-08','Vung Tau','HTTT','HTTT','15');
INSERT INTO SINHVIEN (MSSV,HoTen,GioiTinh,NgaySinh,QueQuan,MaKhoa,MaNganh,KhoaHoc) VALUES ('20521466','Pham Quoc Duy Hung','Nam','2002-04-05','Phu Yen','KTTT','KHDL','15');
INSERT INTO SINHVIEN (MSSV,HoTen,GioiTinh,NgaySinh,QueQuan,MaKhoa,MaNganh,KhoaHoc) VALUES ('20521499','Tran Tuy Hoa','Nam','2002-02-04','Long An','KHMT','KHMT','15');
INSERT INTO SINHVIEN (MSSV,HoTen,GioiTinh,NgaySinh,QueQuan,MaKhoa,MaNganh,KhoaHoc) VALUES ('20521511','Nguyen Tan Tai','Nam','2002-12-23','Long An','KHMT','KHNT','15');
INSERT INTO SINHVIEN (MSSV,HoTen,GioiTinh,NgaySinh,QueQuan,MaKhoa,MaNganh,KhoaHoc) VALUES ('20521563','Le Thi Kim Trang','Nu','2002-02-16','Quang Nam','CNPM','KTPM','15');
INSERT INTO SINHVIEN (MSSV,HoTen,GioiTinh,NgaySinh,QueQuan,MaKhoa,MaNganh,KhoaHoc) VALUES ('20522022','Le Minh Thong','Nam','2002-10-09','Hue','MMT&TT','MMTT','15');
INSERT INTO SINHVIEN (MSSV,HoTen,GioiTinh,NgaySinh,QueQuan,MaKhoa,MaNganh,KhoaHoc) VALUES ('20522062','Duong Cong Vu','Nam','2002-11-06','Ninh Binh','HTTT','TMDT','15');
INSERT INTO SINHVIEN (MSSV,HoTen,GioiTinh,NgaySinh,QueQuan,MaKhoa,MaNganh,KhoaHoc) VALUES ('20522191','Duong Minh Hoang','Nam','2002-02-02','Lam Dong','KTMT','KTMT','15');
INSERT INTO SINHVIEN (MSSV,HoTen,GioiTinh,NgaySinh,QueQuan,MaKhoa,MaNganh,KhoaHoc) VALUES ('21521116','Nguyen Hoai Phuong','Nu','2003-05-08','Hue','MMT&TT','ATTT','16');
INSERT INTO SINHVIEN (MSSV,HoTen,GioiTinh,NgaySinh,QueQuan,MaKhoa,MaNganh,KhoaHoc) VALUES ('21521221','Le Quang Hien','Nam','2003-07-03','Ninh Binh','KTTT','CNTT','16');
INSERT INTO SINHVIEN (MSSV,HoTen,GioiTinh,NgaySinh,QueQuan,MaKhoa,MaNganh,KhoaHoc) VALUES ('21521362','Le Doan Tra My','Nu','2003-08-08','Can Tho','HTTT','TMDT','16');
INSERT INTO SINHVIEN (MSSV,HoTen,GioiTinh,NgaySinh,QueQuan,MaKhoa,MaNganh,KhoaHoc) VALUES ('21521580','Tran Minh Huy','Nam','2003-09-10','Can Tho','KTTT','KHDL','16');
INSERT INTO SINHVIEN (MSSV,HoTen,GioiTinh,NgaySinh,QueQuan,MaKhoa,MaNganh,KhoaHoc) VALUES ('21522118','Bui Xuan Nhi','Nu','2003-10-12','Ben Tre','KHMT','KHMT','16');
INSERT INTO SINHVIEN (MSSV,HoTen,GioiTinh,NgaySinh,QueQuan,MaKhoa,MaNganh,KhoaHoc) VALUES ('21522203','Ngo Van Manh','Nam','2003-11-23','Bac Giang','KHMT','KHNT','16');
INSERT INTO SINHVIEN (MSSV,HoTen,GioiTinh,NgaySinh,QueQuan,MaKhoa,MaNganh,KhoaHoc) VALUES ('21522310','Nguyen Hoang Vu','Nam','2003-12-16','Ha Noi','CNPM','KTPM','16');
INSERT INTO SINHVIEN (MSSV,HoTen,GioiTinh,NgaySinh,QueQuan,MaKhoa,MaNganh,KhoaHoc) VALUES ('21522419','Dang Quoc Duy','Nam','2003-09-01','HCM','MMT&TT','MMTT','16');
INSERT INTO SINHVIEN (MSSV,HoTen,GioiTinh,NgaySinh,QueQuan,MaKhoa,MaNganh,KhoaHoc) VALUES ('21522530','Le Xuan Quynh','Nam','2003-08-07','Ben Tre','HTTT','TMDT','16');
INSERT INTO SINHVIEN (MSSV,HoTen,GioiTinh,NgaySinh,QueQuan,MaKhoa,MaNganh,KhoaHoc) VALUES ('21522610','Bui Tuan Vu','Nam','2003-05-06','Lam Dong','KTMT','KTMT','13');

SELECT * FROM KHOA;
SELECT * FROM NGANH;
SELECT * FROM TAIKHOAN;
SELECT * FROM SINHVIEN;
SELECT * FROM HOCPHAN;
SELECT * FROM KETQUA;


