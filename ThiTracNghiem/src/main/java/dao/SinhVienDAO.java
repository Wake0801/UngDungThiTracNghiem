package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.SinhVien;
import util.DatabaseConnection;

public class SinhVienDAO {
	public void addSinhVien(SinhVien sinhVien) throws SQLException {
		String sql = "INSERT INTO SinhVien (MASV, HO, TEN, NGAYSINH, DIACHI, MALOP) VALUES (?, ?, ?, ?, ?, ?)";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, sinhVien.getMaSV());
			stmt.setString(2, sinhVien.getHo());
			stmt.setString(3, sinhVien.getTen());
			stmt.setString(4, sinhVien.getNgaySinh());
			stmt.setString(5, sinhVien.getDiaChi());
			stmt.setString(6, sinhVien.getMaLop());
			stmt.executeUpdate();
		}
	}

	public void updateSinhVien(SinhVien sinhVien) throws SQLException {
		String sql = "UPDATE SinhVien SET HO = ?, TEN = ?, NGAYSINH = ?, DIACHI = ?, MALOP = ? WHERE MASV = ?";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, sinhVien.getHo());
			stmt.setString(2, sinhVien.getTen());
			stmt.setString(3, sinhVien.getNgaySinh());
			stmt.setString(4, sinhVien.getDiaChi());
			stmt.setString(5, sinhVien.getMaLop());
			stmt.setString(6, sinhVien.getMaSV());
			stmt.executeUpdate();
		}
	}

	public void deleteSinhVien(String maSV) throws SQLException {
		String sql = "DELETE FROM SinhVien WHERE MASV = ?";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, maSV);
			stmt.executeUpdate();
		}
	}

	public SinhVien getSinhVienById(String maSV) throws SQLException {
		String sql = "SELECT * FROM SinhVien WHERE MASV = ?";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, maSV);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return new SinhVien(rs.getString("MASV"), rs.getString("HO"), rs.getString("TEN"),
						rs.getString("NGAYSINH"), rs.getString("DIACHI"), rs.getString("MALOP"));
			}
		}
		return null;
	}

	public List<SinhVien> getAllSinhVien() throws SQLException {
		List<SinhVien> sinhVienList = new ArrayList<>();
		String sql = "SELECT * FROM SinhVien";
		try (Connection conn = DatabaseConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				SinhVien sinhVien = new SinhVien(rs.getString("MASV"), rs.getString("HO"), rs.getString("TEN"),
						rs.getString("NGAYSINH"), rs.getString("DIACHI"), rs.getString("MALOP"));
				sinhVienList.add(sinhVien);
			}
		}
		return sinhVienList;
	}

	public static List<SinhVien> getSinhVienByMaLop(String maLop) throws SQLException {
		List<SinhVien> list = new ArrayList<>();
		String sql = "SELECT * FROM SinhVien WHERE MaLop = ?";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, maLop);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				SinhVien sv = new SinhVien(rs.getString("MaSV"), rs.getString("Ho"), rs.getString("Ten"),
						rs.getString("NgaySinh"), rs.getString("DiaChi"), rs.getString("MaLop"));
				list.add(sv);
			}
		}
		return list;
	}
}