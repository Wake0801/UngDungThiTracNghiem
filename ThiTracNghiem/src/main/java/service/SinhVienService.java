package service;

import java.sql.SQLException;
import java.util.List;

import dao.SinhVienDAO;
import model.SinhVien;

public class SinhVienService {
	private final SinhVienDAO sinhVienDAO;

	public SinhVienService() {
		this.sinhVienDAO = new SinhVienDAO();
	}

	public void addSinhVien(SinhVien sinhVien) throws SQLException {
		if (sinhVien.getMaSV() == null || sinhVien.getMaSV().trim().isEmpty()) {
			throw new IllegalArgumentException("Mã sinh viên không được để trống");
		}
		if (sinhVienDAO.getSinhVienById(sinhVien.getMaSV()) != null) {
			throw new IllegalArgumentException("Mã sinh viên đã tồn tại");
		}
		if (sinhVien.getMaLop() == null || sinhVien.getMaLop().trim().isEmpty()) {
			throw new IllegalArgumentException("Mã lớp không được để trống");
		}
		sinhVienDAO.addSinhVien(sinhVien);
	}

	public void updateSinhVien(SinhVien sinhVien) throws SQLException {
		if (sinhVienDAO.getSinhVienById(sinhVien.getMaSV()) == null) {
			throw new IllegalArgumentException("Mã sinh viên không tồn tại");
		}
		sinhVienDAO.updateSinhVien(sinhVien);
	}

	public void deleteSinhVien(String maSV) throws SQLException {
		if (sinhVienDAO.getSinhVienById(maSV) == null) {
			throw new IllegalArgumentException("Mã sinh viên không tồn tại");
		}
		sinhVienDAO.deleteSinhVien(maSV);
	}

	public SinhVien getSinhVienById(String maSV) throws SQLException {
		return sinhVienDAO.getSinhVienById(maSV);
	}

	public List<SinhVien> getAllSinhVien() throws SQLException {
		return sinhVienDAO.getAllSinhVien();
	}

	public static List<SinhVien> getSinhVienByMaLop(String maLop) throws SQLException {
		return SinhVienDAO.getSinhVienByMaLop(maLop);
	}
}