package model;

public class GiaoVien {
	private String maGV;
	private String ho;
	private String ten;
	private String soDTLL;
	private String diaChi;

	public GiaoVien(String maGV, String ho, String ten, String soDTLL, String diaChi) {
		this.maGV = maGV;
		this.ho = ho;
		this.ten = ten;
		this.soDTLL = soDTLL;
		this.diaChi = diaChi;
	}

	public String getMaGV() {
		return maGV;
	}

	public void setMaGV(String maGV) {
		this.maGV = maGV;
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

	public String getSoDTLL() {
		return soDTLL;
	}

	public void setSoDTLL(String soDTLL) {
		this.soDTLL = soDTLL;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}
}