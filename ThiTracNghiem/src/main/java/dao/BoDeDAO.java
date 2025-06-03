package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.BoDe;
import util.DatabaseConnection;

public class BoDeDAO {
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
		String sql = "UPDATE BoDe SET TRINHDO = ?, NOIDUNG = ?, A = ?, B = ?, C = ?, D = ?, DAP_AN = ?, MAGV = ? WHERE MAMH = ? AND CAUHOI = ?";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, boDe.getTrinhDo());
			stmt.setString(2, boDe.getNoiDung());
			stmt.setString(3, boDe.getA());
			stmt.setString(4, boDe.getB());
			stmt.setString(5, boDe.getC());
			stmt.setString(6, boDe.getD());
			stmt.setString(7, boDe.getDapAn());
			stmt.setString(8, boDe.getMaGV());
			stmt.setString(9, boDe.getMaMH());
			stmt.setInt(10, boDe.getCauHoi());
			stmt.executeUpdate();
		}
	}

	public void deleteBoDe(String maMH, int cauHoi) throws SQLException {
		String sql = "DELETE FROM BoDe WHERE MAMH = ? AND CAUHOI = ?";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, maMH);
			stmt.setInt(2, cauHoi);
			stmt.executeUpdate();
		}
	}

	public BoDe getBoDeById(String maMH, int cauHoi) throws SQLException {
		String sql = "SELECT * FROM BoDe WHERE MAMH = ? AND CAUHOI = ?";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, maMH);
			stmt.setInt(2, cauHoi);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return new BoDe(rs.getString("MAMH"), rs.getInt("CAUHOI"), rs.getString("TRINHDO"),
						rs.getString("NOIDUNG"), rs.getString("A"), rs.getString("B"), rs.getString("C"),
						rs.getString("D"), rs.getString("DAP_AN"), rs.getString("MAGV"));
			}
		}
		return null;
	}

	public List<BoDe> getAllBoDe() throws SQLException {
		List<BoDe> boDeList = new ArrayList<>();
		String sql = "SELECT * FROM BoDe";
		try (Connection conn = DatabaseConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				BoDe boDe = new BoDe(rs.getString("MAMH"), rs.getInt("CAUHOI"), rs.getString("TRINHDO"),
						rs.getString("NOIDUNG"), rs.getString("A"), rs.getString("B"), rs.getString("C"),
						rs.getString("D"), rs.getString("DAP_AN"), rs.getString("MAGV"));
				boDeList.add(boDe);
			}
		}
		return boDeList;
	}

	public List<BoDe> getRandomQuestions(String maMH, String trinhDo, int soCauThi) throws SQLException {
		List<BoDe> questions = new ArrayList<>();
		String sql = "SELECT TOP (?) * FROM BoDe WHERE MAMH = ? AND TRINHDO = ? ORDER BY NEWID()";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, soCauThi);
			stmt.setString(2, maMH);
			stmt.setString(3, trinhDo);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				BoDe boDe = new BoDe(rs.getString("MAMH"), rs.getInt("CAUHOI"), rs.getString("TRINHDO"),
						rs.getString("NOIDUNG"), rs.getString("A"), rs.getString("B"), rs.getString("C"),
						rs.getString("D"), rs.getString("DAP_AN"), rs.getString("MAGV"));
				questions.add(boDe);
			}
		}
		return questions;
	}
}