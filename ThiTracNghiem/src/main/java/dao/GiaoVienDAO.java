package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.GiaoVien;
import util.DatabaseConnection;

public class GiaoVienDAO {
	public void addGiaoVien(GiaoVien giaoVien) throws SQLException {
		String sql = "INSERT INTO GiaoVien (MAGV, HO, TEN, SODTLL, DIACHI) VALUES (?, ?, ?, ?, ?)";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, giaoVien.getMaGV());
			stmt.setString(2, giaoVien.getHo());
			stmt.setString(3, giaoVien.getTen());
			stmt.setString(4, giaoVien.getSoDTLL());
			stmt.setString(5, giaoVien.getDiaChi());
			stmt.executeUpdate();
		}
	}

	public void updateGiaoVien(GiaoVien giaoVien) throws SQLException {
		String sql = "UPDATE GiaoVien SET HO = ?, TEN = ?, SODTLL = ?, DIACHI = ? WHERE MAGV = ?";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, giaoVien.getHo());
			stmt.setString(2, giaoVien.getTen());
			stmt.setString(3, giaoVien.getSoDTLL());
			stmt.setString(4, giaoVien.getDiaChi());
			stmt.setString(5, giaoVien.getMaGV());
			stmt.executeUpdate();
		}
	}

	public void deleteGiaoVien(String maGV) throws SQLException {
		String sql = "DELETE FROM GiaoVien WHERE MAGV = ?";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, maGV);
			stmt.executeUpdate();
		}
	}

	public GiaoVien getGiaoVienById(String maGV) throws SQLException {
		String sql = "SELECT * FROM GiaoVien WHERE MAGV = ?";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, maGV);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return new GiaoVien(rs.getString("MAGV"), rs.getString("HO"), rs.getString("TEN"),
						rs.getString("SODTLL"), rs.getString("DIACHI"));
			}
		}
		return null;
	}

	public List<GiaoVien> getAllGiaoVien() throws SQLException {
		List<GiaoVien> giaoVienList = new ArrayList<>();
		String sql = "SELECT * FROM GiaoVien";
		try (Connection conn = DatabaseConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				GiaoVien giaoVien = new GiaoVien(rs.getString("MAGV"), rs.getString("HO"), rs.getString("TEN"),
						rs.getString("SODTLL"), rs.getString("DIACHI"));
				giaoVienList.add(giaoVien);
			}
		}
		return giaoVienList;
	}
}