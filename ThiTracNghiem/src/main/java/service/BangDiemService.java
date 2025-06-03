package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import dao.BangDiemDAO;
import model.BangDiem;
import util.DatabaseConnection;

public class BangDiemService {
	private final BangDiemDAO bangDiemDAO;

	public BangDiemService() {
		this.bangDiemDAO = new BangDiemDAO();
	}

	public void addBangDiem(BangDiem bangDiem) throws SQLException {
		if (bangDiem.getDiem() < 0 || bangDiem.getDiem() > 10) {
			throw new IllegalArgumentException("Điểm phải từ 0 đến 10");
		}
		if (bangDiem.getLan() < 1 || bangDiem.getLan() > 2) {
			throw new IllegalArgumentException("Lần thi phải là 1 hoặc 2");
		}
		if (bangDiemDAO.getBangDiemById(bangDiem.getMaSV(), bangDiem.getMaMH(), bangDiem.getLan()) != null) {
			throw new IllegalArgumentException("Điểm cho lần thi này đã tồn tại");
		}
		bangDiemDAO.addBangDiem(bangDiem);
	}

	public void updateBangDiem(BangDiem bangDiem) throws SQLException {
		if (bangDiemDAO.getBangDiemById(bangDiem.getMaSV(), bangDiem.getMaMH(), bangDiem.getLan()) == null) {
			throw new IllegalArgumentException("Điểm không tồn tại");
		}
		bangDiemDAO.updateBangDiem(bangDiem);
	}

	public void deleteBangDiem(String maSV, String maMH, int lan) throws SQLException {
		if (bangDiemDAO.getBangDiemById(maSV, maMH, lan) == null) {
			throw new IllegalArgumentException("Điểm không tồn tại");
		}
		bangDiemDAO.deleteBangDiem(maSV, maMH, lan);
	}

	public BangDiem getBangDiemById(String maSV, String maMH, int lan) throws SQLException {
		return bangDiemDAO.getBangDiemById(maSV, maMH, lan);
	}

	public List<BangDiem> getAllBangDiem() throws SQLException {
		return bangDiemDAO.getAllBangDiem();
	}

	public boolean hasTakenExam(String maSV, String maMH, int lan) throws SQLException {
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn
						.prepareStatement("SELECT COUNT(*) FROM BangDiem WHERE maSV = ? AND maMH = ? AND lan = ?")) {
			stmt.setString(1, maSV);
			stmt.setString(2, maMH);
			stmt.setInt(3, lan);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
			return false;
		}
	}
}