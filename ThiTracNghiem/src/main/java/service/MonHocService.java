package service;

import java.sql.SQLException;
import java.util.List;

import dao.MonHocDAO;
import model.MonHoc;

public class MonHocService {
	private final MonHocDAO monHocDAO;

	public MonHocService() {
		this.monHocDAO = new MonHocDAO();
	}

	public void addMonHoc(MonHoc monHoc) throws SQLException {
		if (monHoc.getMaMH() == null || monHoc.getMaMH().trim().isEmpty()) {
			throw new IllegalArgumentException("Mã môn học không được để trống");
		}
		if (monHoc.getTenMH() == null || monHoc.getTenMH().trim().isEmpty()) {
			throw new IllegalArgumentException("Tên môn học không được để trống");
		}
		if (monHocDAO.getMonHocById(monHoc.getMaMH()) != null) {
			throw new IllegalArgumentException("Mã môn học đã tồn tại");
		}
		monHocDAO.addMonHoc(monHoc);
	}

	public void updateMonHoc(MonHoc monHoc) throws SQLException {
		if (monHocDAO.getMonHocById(monHoc.getMaMH()) == null) {
			throw new IllegalArgumentException("Mã môn học không tồn tại");
		}
		monHocDAO.updateMonHoc(monHoc);
	}

	public void deleteMonHoc(String maMH) throws SQLException {
		if (monHocDAO.getMonHocById(maMH) == null) {
			throw new IllegalArgumentException("Mã môn học không tồn tại");
		}
		monHocDAO.deleteMonHoc(maMH);
	}

	public MonHoc getMonHocById(String maMH) throws SQLException {
		return monHocDAO.getMonHocById(maMH);
	}

	public List<MonHoc> getAllMonHoc() throws SQLException {
		return monHocDAO.getAllMonHoc();
	}
}