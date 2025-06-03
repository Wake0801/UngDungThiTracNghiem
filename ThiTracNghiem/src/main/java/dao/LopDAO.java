package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Lop;
import util.DatabaseConnection;

public class LopDAO {
	public void addLop(Lop lop) throws SQLException {
		String sql = "INSERT INTO Lop (MALOP, TENLOP) VALUES (?, ?)";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, lop.getMaLop());
			stmt.setString(2, lop.getTenLop());
			stmt.executeUpdate();
		}
	}

	public void updateLop(Lop lop) throws SQLException {
		String sql = "UPDATE Lop SET TENLOP = ? WHERE MALOP = ?";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, lop.getTenLop());
			stmt.setString(2, lop.getMaLop());
			stmt.executeUpdate();
		}
	}

	public void deleteLop(String maLop) throws SQLException {
		String sql = "DELETE FROM Lop WHERE MALOP = ?";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, maLop);
			stmt.executeUpdate();
		}
	}

	public Lop getLopById(String maLop) throws SQLException {
		String sql = "SELECT * FROM Lop WHERE MALOP = ?";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, maLop);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return new Lop(rs.getString("MALOP"), rs.getString("TENLOP"));
			}
		}
		return null;
	}

	public List<Lop> getAllLop() throws SQLException {
		List<Lop> lopList = new ArrayList<>();
		String sql = "SELECT * FROM Lop";
		try (Connection conn = DatabaseConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				Lop lop = new Lop(rs.getString("MALOP"), rs.getString("TENLOP"));
				lopList.add(lop);
			}
		}
		return lopList;
	}
}