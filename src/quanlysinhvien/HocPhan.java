package quanlysinhvien;

public class HocPhan {

    private String maHP;
    private String tenHP;
    private String maKhoa;
    private int soTC;

    public HocPhan() {
    }

    public HocPhan(String maHP, String tenHP, String maKhoa, int soTC) {
        this.maHP = maHP;
        this.tenHP = tenHP;
        this.maKhoa = maKhoa;
        this.soTC = soTC;
    }

    public String getMaHP() {
        return maHP;
    }

    public void setMaHP(String maHP) {
        this.maHP = maHP;
    }

    public String getTenHP() {
        return tenHP;
    }

    public void setTenHP(String tenHP) {
        this.tenHP = tenHP;
    }

    public String getMaKhoa() {
        return maKhoa;
    }

    public void setMaKhoa(String maKhoa) {
        this.maKhoa = maKhoa;
    }

    public int getSoTC() {
        return soTC;
    }

    public void setSoTC(int soTC) {
        this.soTC = soTC;
    }
}
