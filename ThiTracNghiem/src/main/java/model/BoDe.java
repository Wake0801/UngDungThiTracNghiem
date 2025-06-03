package model;

public class BoDe {
	private String maMH;
	private int cauHoi;
	private String trinhDo;
	private String noiDung;
	private String a;
	private String b;
	private String c;
	private String d;
	private String dapAn;
	private String maGV;

	public BoDe(String maMH, int cauHoi, String trinhDo, String noiDung, String a, String b, String c, String d,
			String dapAn, String maGV) {
		this.maMH = maMH;
		this.cauHoi = cauHoi;
		this.trinhDo = trinhDo;
		this.noiDung = noiDung;
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.dapAn = dapAn;
		this.maGV = maGV;
	}

	public String getMaMH() {
		return maMH;
	}

	public void setMaMH(String maMH) {
		this.maMH = maMH;
	}

	public int getCauHoi() {
		return cauHoi;
	}

	public void setCauHoi(int cauHoi) {
		this.cauHoi = cauHoi;
	}

	public String getTrinhDo() {
		return trinhDo;
	}

	public void setTrinhDo(String trinhDo) {
		this.trinhDo = trinhDo;
	}

	public String getNoiDung() {
		return noiDung;
	}

	public void setNoiDung(String noiDung) {
		this.noiDung = noiDung;
	}

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public String getD() {
		return d;
	}

	public void setD(String d) {
		this.d = d;
	}

	public String getDapAn() {
		return dapAn;
	}

	public void setDapAn(String dapAn) {
		this.dapAn = dapAn;
	}

	public String getMaGV() {
		return maGV;
	}

	public void setMaGV(String maGV) {
		this.maGV = maGV;
	}
}