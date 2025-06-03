package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.MonHoc;
import util.DatabaseConnection;

public class MonHocDAO {
	public void addMonHoc(MonHoc monHoc) throws SQLException {
		String sql = "INSERT INTO MonHoc (MAMH, TENMH) VALUES (?, ?)";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, monHoc.getMaMH());
			stmt.setString(2, monHoc.getTenMH());
			stmt.executeUpdate();
		}
	}

	public void updateMonHoc(MonHoc monHoc) throws SQLException {
		String sql = "UPDATE MonHoc SET TENMH = ? WHERE MAMH = ?";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, monHoc.getTenMH());
			stmt.setString(2, monHoc.getMaMH());
			stmt.executeUpdate();
		}
	}

	public void deleteMonHoc(String maMH) throws SQLException {
		String sql = "DELETE FROM MonHoc WHERE MAMH = ?";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, maMH);
			stmt.executeUpdate();
		}
	}

	public MonHoc getMonHocById(String maMH) throws SQLException {
		String sql = "SELECT * FROM MonHoc WHERE MAMH = ?";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, maMH);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return new MonHoc(rs.getString("MAMH"), rs.getString("TENMH"));
			}
		}
		return null;
	}

	public List<MonHoc> getAllMonHoc() throws SQLException {
		List<MonHoc> monHocList = new ArrayList<>();
		String sql = "SELECT * FROM MonHoc";
		try (Connection conn = DatabaseConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				MonHoc monHoc = new MonHoc(rs.getString("MAMH"), rs.getString("TENMH"));
				monHocList.add(monHoc);
			}
		}
		return monHocList;
	}
}