package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	// SQL Server connection string
	private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=THI_TRAC_NGHIEM;encrypt=false";
	private static final String USER = "sa";
	private static final String PASSWORD = "123";

	public static Connection getConnection() throws SQLException {
		try {
			// Đăng ký driver SQL Server
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			return DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (ClassNotFoundException e) {
			throw new SQLException("SQL Server JDBC Driver không tìm thấy", e);
		}
	}

	// Test connection
	public static void main(String[] args) {
		try {
			Connection conn = getConnection();
			if (conn != null) {
				System.out.println("Kết nối CSDL thành công!");
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("Kết nối CSDL thất bại!");
			e.printStackTrace();
		}
	}
}