package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class DatabaseService {
	private String username;
	private String password;
	private String databaseName;

	public DatabaseService() throws IOException {
		Properties props = new Properties();
		props.load(DatabaseService.class.getResourceAsStream("/config.properties"));
		this.username = props.getProperty("db.username");
		this.password = props.getProperty("db.password");
		this.databaseName = props.getProperty("db.database");
	}

	public void backupDatabase(String backupPath) throws IOException, InterruptedException {
		// SQL Server backup command
		String command = String.format(
			"sqlcmd -S localhost -U %s -P %s -Q \"BACKUP DATABASE [%s] TO DISK='%s' WITH INIT\"", 
			username, password, databaseName, backupPath
		);
		executeCommand(command);
	}

	public void restoreDatabase(String backupPath) throws IOException, InterruptedException {
		// SQL Server restore command - đảm bảo database offline trước khi restore
		String command = String.format(
			"sqlcmd -S localhost -U %s -P %s -Q \"USE [master]; ALTER DATABASE [%s] SET OFFLINE WITH ROLLBACK IMMEDIATE; RESTORE DATABASE [%s] FROM DISK='%s' WITH REPLACE\"", 
			username, password, databaseName, databaseName, backupPath
		);
		executeCommand(command);
	}

	private void executeCommand(String command) throws IOException, InterruptedException {
		Process process = Runtime.getRuntime().exec(new String[] { "cmd.exe", "/c", command });
		StringBuilder output = new StringBuilder();
		StringBuilder error = new StringBuilder();

		// Capture standard output
		try (BufferedReader stdOutput = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
			String line;
			while ((line = stdOutput.readLine()) != null) {
				output.append(line).append("\n");
			}
		}

		// Capture error output bắt lỗi
		try (BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
			String line;
			while ((line = stdError.readLine()) != null) {
				error.append(line).append("\n");
			}
		}

		int exitCode = process.waitFor();
		if (exitCode != 0) {
			throw new IOException("Lệnh thất bại: " + error.toString());
		}
	}
}