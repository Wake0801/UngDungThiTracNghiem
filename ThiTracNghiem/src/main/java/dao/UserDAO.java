package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.User;
import util.DatabaseConnection;

public class UserDAO {
	public void addUser(User user) throws SQLException {
		String sql = "INSERT INTO [User] (LOGIN, PASSWORD, ROLE) VALUES (?, ?, ?)";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, user.getLogin());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getRole());
			stmt.executeUpdate();
		}
	}

	public void updateUser(User user) throws SQLException {
		String sql = "UPDATE [User] SET PASSWORD = ?, ROLE = ? WHERE LOGIN = ?";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, user.getPassword());
			stmt.setString(2, user.getRole());
			stmt.setString(3, user.getLogin());
			stmt.executeUpdate();
		}
	}

	public void deleteUser(String username) throws SQLException {
		String sql = "DELETE FROM [User] WHERE LOGIN = ?";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, username);
			stmt.executeUpdate();
		}
	}

	public User getUserById(String username) throws SQLException {
		String sql = "SELECT * FROM [User] WHERE LOGIN= ?";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return new User(rs.getString("LOGIN"), rs.getString("PASSWORD"), rs.getString("ROLE"));
			}
		}
		return null;
	}

	public List<User> getAllTaiKhoan() throws SQLException {
		List<User> userList = new ArrayList<>();
		String sql = "SELECT * FROM [User]";
		try (Connection conn = DatabaseConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				User user = new User(rs.getString("LOGIN"), rs.getString("PASSWORD"), rs.getString("ROLE"));
				userList.add(user);
			}
		}
		return userList;
	}

	public boolean checkLogin(String username, String password) throws SQLException {
		String sql = "SELECT * FROM [User] WHERE LOGIN = ? AND PASSWORD = ?";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, username);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();
			return rs.next();
		}
	}
}