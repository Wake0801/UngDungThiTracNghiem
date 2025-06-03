package service;

import java.sql.SQLException;
import java.util.List;

import dao.LopDAO;
import model.Lop;

public class LopService {
	private final LopDAO lopDAO;

	public LopService() {
		this.lopDAO = new LopDAO();
	}

	public void addLop(Lop lop) throws SQLException {
		if (lop.getMaLop() == null || lop.getMaLop().trim().isEmpty()) {
			throw new IllegalArgumentException("Ma lop khong duoc de trong");
		}
		if (lop.getTenLop() == null || lop.getTenLop().trim().isEmpty()) {
			throw new IllegalArgumentException("Ten lop khong duoc de trong");
		}
		if (lopDAO.getLopById(lop.getMaLop()) != null) {
			throw new IllegalArgumentException("Mã lớp đã tồn tại");
		}
		lopDAO.addLop(lop);
	}

	public void updateLop(Lop lop) throws SQLException {
		if (lopDAO.getLopById(lop.getMaLop()) == null) {
			throw new IllegalArgumentException("Mã lớp không tồn tại");
		}
		lopDAO.updateLop(lop);
	}

	public void deleteLop(String maLop) throws SQLException {
		if (lopDAO.getLopById(maLop) == null) {
			throw new IllegalArgumentException("Mã lớp không tồn tại");
		}
		lopDAO.deleteLop(maLop);
	}

	public Lop getLopById(String maLop) throws SQLException {
		return lopDAO.getLopById(maLop);
	}

	public List<Lop> getAllLop() throws SQLException {
		return lopDAO.getAllLop();
	}
}