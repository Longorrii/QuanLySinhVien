package quanlysinhvien;

public class KetQua {

    private String maHP;
    private String mssv;
    private String tenHP;
    private int soTC;
    private int hocKy;
    private int namHoc;
    private int diem;

    public KetQua() {
    }

    public KetQua(String maHP, String mssv, String tenHP, int soTC, int hocKy, int namHoc, int diem) {
        this.maHP = maHP;
        this.mssv = mssv;
        this.tenHP = tenHP;
        this.soTC = soTC;
        this.hocKy = hocKy;
        this.namHoc = namHoc;
        this.diem = diem;
    }

    public String getMaHP() {
        return maHP;
    }

    public void setMaHP(String maHP) {
        this.maHP = maHP;
    }

    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

    public String getTenHP() {
        return tenHP;
    }

    public void setTenHP(String tenHP) {
        this.tenHP = tenHP;
    }

    public int getSoTC() {
        return soTC;
    }

    public void setSoTC(int soTC) {
        this.soTC = soTC;
    }

    public int getHocKy() {
        return hocKy;
    }

    public void setHocKy(int hocKy) {
        this.hocKy = hocKy;
    }

    public int getNamHoc() {
        return namHoc;
    }

    public void setNamHoc(int namHoc) {
        this.namHoc = namHoc;
    }

    public int getDiem() {
        return diem;
    }

    public void setDiem(int diem) {
        this.diem = diem;
    }
}
