package model;

public class SinhVien {
	private String maSV;
	private String ho;
	private String ten;
	private String ngaySinh; // java.sql.Date
	private String diaChi;
	private String maLop;

	public SinhVien() {
		// TODO Auto-generated constructor stub
	}

	// Constructor
	public SinhVien(String maSV, String ho, String ten, String ngaySinh, String diaChi, String maLop) {
		this.maSV = maSV;
		this.ho = ho;
		this.ten = ten;
		this.ngaySinh = ngaySinh;
		this.diaChi = diaChi;
		this.maLop = maLop;
	}

	public String getMaSV() {
		return maSV;
	}

	public void setMaSV(String maSV) {
		this.maSV = maSV;
	}

	public String getHo() {
		return ho;
	}

	public void setHo(String ho) {
		this.ho = ho;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(String ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public String getMaLop() {
		return maLop;
	}

	public void setMaLop(String maLop) {
		this.maLop = maLop;
	}

}
