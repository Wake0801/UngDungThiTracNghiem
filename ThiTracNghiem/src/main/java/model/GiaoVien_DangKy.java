package model;

import java.sql.Timestamp;

public class GiaoVien_DangKy {
	private String maGV;
	private String maLop;
	private String maMH;
	private String trinhDo;
	private Timestamp ngayThi;
	private int lan;
	private int soCauThi;
	private int thoiGian;

	public GiaoVien_DangKy(String maGV, String maLop, String maMH, String trinhDo, Timestamp ngayThi, int lan,
			int soCauThi, int thoiGian) {
		this.maGV = maGV;
		this.maLop = maLop;
		this.maMH = maMH;
		this.trinhDo = trinhDo;
		this.ngayThi = ngayThi;
		this.lan = lan;
		this.soCauThi = soCauThi;
		this.thoiGian = thoiGian;
	}

	public String getMaGV() {
		return maGV;
	}

	public void setMaGV(String maGV) {
		this.maGV = maGV;
	}

	public String getMaLop() {
		return maLop;
	}

	public void setMaLop(String maLop) {
		this.maLop = maLop;
	}

	public String getMaMH() {
		return maMH;
	}

	public void setMaMH(String maMH) {
		this.maMH = maMH;
	}

	public String getTrinhDo() {
		return trinhDo;
	}

	public void setTrinhDo(String trinhDo) {
		this.trinhDo = trinhDo;
	}

	public Timestamp getNgayThi() {
		return ngayThi;
	}

	public void setNgayThi(Timestamp ngayThi) {
		this.ngayThi = ngayThi;
	}

	public int getLan() {
		return lan;
	}

	public void setLan(int lan) {
		this.lan = lan;
	}

	public int getSoCauThi() {
		return soCauThi;
	}

	public void setSoCauThi(int soCauThi) {
		this.soCauThi = soCauThi;
	}

	public int getThoiGian() {
		return thoiGian;
	}

	public void setThoiGian(int thoiGian) {
		this.thoiGian = thoiGian;
	}
}