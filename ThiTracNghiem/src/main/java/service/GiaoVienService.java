package service;

import java.sql.SQLException;
import java.util.List;

import dao.GiaoVienDAO;
import model.GiaoVien;

public class GiaoVienService {
	private final GiaoVienDAO giaoVienDAO;

	public GiaoVienService() {
		this.giaoVienDAO = new GiaoVienDAO();
	}

	public void addGiaoVien(GiaoVien giaoVien) throws SQLException {
		if (giaoVien.getMaGV() == null || giaoVien.getMaGV().trim().isEmpty()) {
			throw new IllegalArgumentException("Mã giáo viên không được để trống");
		}
		if (giaoVienDAO.getGiaoVienById(giaoVien.getMaGV()) != null) {
			throw new IllegalArgumentException("Mã giáo viên đã tồn tại");
		}
		giaoVienDAO.addGiaoVien(giaoVien);
	}

	public void updateGiaoVien(GiaoVien giaoVien) throws SQLException {
		if (giaoVienDAO.getGiaoVienById(giaoVien.getMaGV()) == null) {
			throw new IllegalArgumentException("Mã giáo viên không tồn tại");
		}
		giaoVienDAO.updateGiaoVien(giaoVien);
	}

	public void deleteGiaoVien(String maGV) throws SQLException {
		if (giaoVienDAO.getGiaoVienById(maGV) == null) {
			throw new IllegalArgumentException("Mã giáo viên không tồn tại");
		}
		giaoVienDAO.deleteGiaoVien(maGV);
	}

	public GiaoVien getGiaoVienById(String maGV) throws SQLException {
		return giaoVienDAO.getGiaoVienById(maGV);
	}

	public List<GiaoVien> getAllGiaoVien() throws SQLException {
		return giaoVienDAO.getAllGiaoVien();
	}
}