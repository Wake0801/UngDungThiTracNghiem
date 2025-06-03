package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import util.DatabaseConnection;

public class BackupService {

	public void backupDatabase(String backupPath) throws SQLException {
		String sql = "BACKUP DATABASE [THI_TRAC_NGHIEM] TO DISK = ? WITH INIT";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, backupPath);
			stmt.execute();
		}
	}

	public void restoreDatabase(String backupPath) throws SQLException {
		// Trước khi restore, phải đảm bảo không có kết nối đang sử dụng DB
		String sql = "USE [master]; ALTER DATABASE [THI_TRAC_NGHIEM] SET SINGLE_USER WITH ROLLBACK IMMEDIATE; " +
		             "RESTORE DATABASE [THI_TRAC_NGHIEM] FROM DISK = ? WITH REPLACE; " +
		             "ALTER DATABASE [THI_TRAC_NGHIEM] SET MULTI_USER";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, backupPath);
			stmt.execute();
		}
	}

}
