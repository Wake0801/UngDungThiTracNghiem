package model;

import java.time.LocalDate;

public class BangDiem {
	private String maSV;
	private String maMH;
	private int lan;
	private String ngayThi;
	private float diem;

	public BangDiem(String maSV, String maMH, int lan, String ngayThi, float diem) {
		this.maSV = maSV;
		this.maMH = maMH;
		this.lan = lan;
		this.ngayThi = ngayThi;
		this.diem = diem;
	}

	public BangDiem(String maSV2, String string, int int1, LocalDate localDate, float diem2) {
		// TODO Auto-generated constructor stub
	}

	public String getMaSV() {
		return maSV;
	}

	public void setMaSV(String maSV) {
		this.maSV = maSV;
	}

	public String getMaMH() {
		return maMH;
	}

	public void setMaMH(String maMH) {
		this.maMH = maMH;
	}

	public int getLan() {
		return lan;
	}

	public void setLan(int lan) {
		this.lan = lan;
	}

	public String getNgayThi() {
		return ngayThi;
	}

	public void setNgayThi(String ngayThi) {
		this.ngayThi = ngayThi;
	}

	public float getDiem() {
		return diem;
	}

	public void setDiem(float diem) {
		this.diem = diem;
	}
}