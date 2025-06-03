package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.BangDiem;
import util.DatabaseConnection;

public class BangDiemDAO {
	public void addBangDiem(BangDiem bangDiem) throws SQLException {
		String sql = "INSERT INTO BangDiem (MASV, MAMH, LAN, NGAYTHI, DIEM) VALUES (?, ?, ?, ?, ?)";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, bangDiem.getMaSV());
			stmt.setString(2, bangDiem.getMaMH());
			stmt.setInt(3, bangDiem.getLan());
			stmt.setString(4, bangDiem.getNgayThi());
			stmt.setFloat(5, bangDiem.getDiem());
			stmt.executeUpdate();
		}
	}

	public void updateBangDiem(BangDiem bangDiem) throws SQLException {
		String sql = "UPDATE BangDiem SET NGAYTHI = ?, DIEM = ? WHERE MASV = ? AND MAMH = ? AND LAN = ?";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, bangDiem.getNgayThi());
			stmt.setFloat(2, bangDiem.getDiem());
			stmt.setString(3, bangDiem.getMaSV());
			stmt.setString(4, bangDiem.getMaMH());
			stmt.setInt(5, bangDiem.getLan());
			stmt.executeUpdate();
		}
	}

	public void deleteBangDiem(String maSV, String maMH, int lan) throws SQLException {
		String sql = "DELETE FROM BangDiem WHERE MASV = ? AND MAMH = ? AND LAN = ?";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, maSV);
			stmt.setString(2, maMH);
			stmt.setInt(3, lan);
			stmt.executeUpdate();
		}
	}

	public BangDiem getBangDiemById(String maSV, String maMH, int lan) throws SQLException {
		String sql = "SELECT * FROM BangDiem WHERE MASV = ? AND MAMH = ? AND LAN = ?";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, maSV);
			stmt.setString(2, maMH);
			stmt.setInt(3, lan);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return new BangDiem(rs.getString("MASV"), rs.getString("MAMH"), rs.getInt("LAN"),
						rs.getString("NGAYTHI"), rs.getFloat("DIEM"));
			}
		}
		return null;
	}

	public List<BangDiem> getAllBangDiem() throws SQLException {
		List<BangDiem> bangDiemList = new ArrayList<>();
		String sql = "SELECT * FROM BangDiem";
		try (Connection conn = DatabaseConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				BangDiem bangDiem = new BangDiem(rs.getString("MASV"), rs.getString("MAMH"), rs.getInt("LAN"),
						rs.getString("NGAYTHI"), rs.getFloat("DIEM"));
				bangDiemList.add(bangDiem);
			}
		}
		return bangDiemList;
	}
}