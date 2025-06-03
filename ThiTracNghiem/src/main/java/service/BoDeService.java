package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import dao.BoDeDAO;
import model.BoDe;
import util.DatabaseConnection;

public class BoDeService {
	private final BoDeDAO boDeDAO;

	public BoDeService() {
		this.boDeDAO = new BoDeDAO();
	}

//	public void addBoDe(BoDe boDe) throws SQLException {
//		if (boDe.getMaMH() == null || boDe.getMaMH().trim().isEmpty()) {
//			throw new IllegalArgumentException("Mã môn học không được để trống");
//		}
//		if (!List.of("A", "B", "C").contains(boDe.getTrinhDo())) {
//			throw new IllegalArgumentException("Trình độ phải là A, B hoặc C");
//		}
//		if (!List.of("A", "B", "C", "D").contains(boDe.getDapAn())) {
//			throw new IllegalArgumentException("Đáp án phải là A, B, C hoặc D");
//		}
//		boDeDAO.addBoDe(boDe);
//	}
	public void addBoDe(BoDe boDe) throws SQLException {
		String sql = "INSERT INTO BoDe (MAMH, TRINHDO, NOIDUNG, A, B, C, D, DAP_AN, MAGV) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, boDe.getMaMH());
			stmt.setString(2, boDe.getTrinhDo());
			stmt.setString(3, boDe.getNoiDung());
			stmt.setString(4, boDe.getA());
			stmt.setString(5, boDe.getB());
			stmt.setString(6, boDe.getC());
			stmt.setString(7, boDe.getD());
			stmt.setString(8, boDe.getDapAn());
			stmt.setString(9, boDe.getMaGV());
			stmt.executeUpdate();
		}
	}

	public void updateBoDe(BoDe boDe) throws SQLException {
		if (boDeDAO.getBoDeById(boDe.getMaMH(), boDe.getCauHoi()) == null) {
			throw new IllegalArgumentException("Câu hỏi không tồn tại");
		}
		boDeDAO.updateBoDe(boDe);
	}

	public void deleteBoDe(String maMH, int cauHoi) throws SQLException {
		if (boDeDAO.getBoDeById(maMH, cauHoi) == null) {
			throw new IllegalArgumentException("Câu hỏi không tồn tại");
		}
		boDeDAO.deleteBoDe(maMH, cauHoi);
	}

	public BoDe getBoDeById(String maMH, int cauHoi) throws SQLException {
		return boDeDAO.getBoDeById(maMH, cauHoi);
	}

	public List<BoDe> getAllBoDe() throws SQLException {
		return boDeDAO.getAllBoDe();
	}

	public List<BoDe> getRandomQuestions(String maMH, String trinhDo, int soCauThi) throws SQLException {
		List<BoDe> questions = boDeDAO.getRandomQuestions(maMH, trinhDo, soCauThi);
		if (questions.size() < soCauThi) {
			throw new IllegalArgumentException("Không đủ câu hỏi cho môn học và trình độ này");
		}
		return questions;
	}

	public int getNextCauHoi(String maMH) throws SQLException {
		String sql = "SELECT MAX(CAUHOI) FROM BoDe WHERE MAMH = ?";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, maMH);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) + 1;
			}
		}
		return 1; // Nếu chưa có câu nào
	}

}