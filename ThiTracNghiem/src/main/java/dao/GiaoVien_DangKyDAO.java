package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.GiaoVien_DangKy;
import util.DatabaseConnection;

public class GiaoVien_DangKyDAO {
	public void addGiaoVien_DangKy(GiaoVien_DangKy dk) throws SQLException {
		String sql = "INSERT INTO GiaoVien_DangKy (MAGV, MALOP, MAMH, TRINHDO, NGAYTHI, LAN, SOCAUTHI, THOIGIAN) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, dk.getMaGV());
			stmt.setString(2, dk.getMaLop());
			stmt.setString(3, dk.getMaMH());
			stmt.setString(4, dk.getTrinhDo());
			stmt.setTimestamp(5, dk.getNgayThi());
			stmt.setInt(6, dk.getLan());
			stmt.setInt(7, dk.getSoCauThi());
			stmt.setInt(8, dk.getThoiGian());
			stmt.executeUpdate();
		}
	}

	public void updateGiaoVien_DangKy(GiaoVien_DangKy dk) throws SQLException {
		String sql = "UPDATE GiaoVien_DangKy SET MAGV = ?, TRINHDO = ?, NGAYTHI = ?, SOCAUTHI = ?, THOIGIAN = ? WHERE MALOP = ? AND MAMH = ? AND LAN = ?";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, dk.getMaGV());
			stmt.setString(2, dk.getTrinhDo());
			stmt.setTimestamp(3, dk.getNgayThi());
			stmt.setInt(4, dk.getSoCauThi());
			stmt.setInt(5, dk.getThoiGian());
			stmt.setString(6, dk.getMaLop());
			stmt.setString(7, dk.getMaMH());
			stmt.setInt(8, dk.getLan());
			stmt.executeUpdate();
		}
	}

	public void deleteGiaoVien_DangKy(String maLop, String maMH, int lan) throws SQLException {
		String sql = "DELETE FROM GiaoVien_DangKy WHERE MALOP = ? AND MAMH = ? AND LAN = ?";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, maLop);
			stmt.setString(2, maMH);
			stmt.setInt(3, lan);
			stmt.executeUpdate();
		}
	}

	public GiaoVien_DangKy getGiaoVien_DangKyById(String maLop, String maMH, int lan) throws SQLException {
		String sql = "SELECT * FROM GiaoVien_DangKy WHERE MALOP = ? AND MAMH = ? AND LAN = ?";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, maLop);
			stmt.setString(2, maMH);
			stmt.setInt(3, lan);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return new GiaoVien_DangKy(rs.getString("MAGV"), rs.getString("MALOP"), rs.getString("MAMH"),
						rs.getString("TRINHDO"), rs.getTimestamp("NGAYTHI"), rs.getInt("LAN"), rs.getInt("SOCAUTHI"),
						rs.getInt("THOIGIAN"));
			}
		}
		return null;
	}

	public List<GiaoVien_DangKy> getAllGiaoVien_DangKy() throws SQLException {
		List<GiaoVien_DangKy> dkList = new ArrayList<>();
		String sql = "SELECT * FROM GiaoVien_DangKy";
		try (Connection conn = DatabaseConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				GiaoVien_DangKy dk = new GiaoVien_DangKy(rs.getString("MAGV"), rs.getString("MALOP"),
						rs.getString("MAMH"), rs.getString("TRINHDO"), rs.getTimestamp("NGAYTHI"), rs.getInt("LAN"),
						rs.getInt("SOCAUTHI"), rs.getInt("THOIGIAN"));
				dkList.add(dk);
			}
		}
		return dkList;
	}
}