package quanlysinhvien;

public class Khoa {

    private String maKhoa;
    private String tenKhoa;
    int soSV;

    public Khoa() {
    }

    public Khoa(String maKhoa, String tenKhoa, int soSV) {
        this.maKhoa = maKhoa;
        this.tenKhoa = tenKhoa;
        this.soSV = soSV;
    }

    public String getMaKhoa() {
        return maKhoa;
    }

    public void setMaKhoa(String maKhoa) {
        this.maKhoa = maKhoa;
    }

    public String getTenKhoa() {
        return tenKhoa;
    }

    public void setTenKhoa(String tenKhoa) {
        this.tenKhoa = tenKhoa;
    }

    public int getSoSV() {
        return soSV;
    }

    public void setSoSV(int soSV) {
        this.soSV = soSV;
    }

}
