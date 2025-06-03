package service;

import java.sql.SQLException;
import java.util.List;

import dao.UserDAO;
import model.User;

public class UserService {
	private final UserDAO userDAO;

	public UserService() {
		this.userDAO = new UserDAO();
	}

	public void addUser(User user) throws SQLException {
		if (user.getLogin() == null || user.getLogin().trim().isEmpty()) {
			throw new IllegalArgumentException("Tên đăng nhập không được để trống");
		}
		if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
			throw new IllegalArgumentException("Mật khẩu không được để trống");
		}
		if (!List.of("PGV", "GiangVien", "SinhVien").contains(user.getRole())) {
			throw new IllegalArgumentException("Nhóm phải là PGV, GiaoVien hoặc SinhVien");
		}
		if (userDAO.getUserById(user.getLogin()) != null) {
			throw new IllegalArgumentException("Tên đăng nhập đã tồn tại");
		}
		userDAO.addUser(user);
	}

	public void updateUser(User user) throws SQLException {
		if (userDAO.getUserById(user.getLogin()) == null) {
			throw new IllegalArgumentException("Tài khoản không tồn tại");
		}
		userDAO.updateUser(user);
	}

	public void deleteUser(String username) throws SQLException {
		if (userDAO.getUserById(username) == null) {
			throw new IllegalArgumentException("Tài khoản không tồn tại");
		}
		userDAO.deleteUser(username);
	}

	public User getUserById(String username) throws SQLException {
		return userDAO.getUserById(username);
	}

	public List<User> getAllUser() throws SQLException {
		return userDAO.getAllTaiKhoan();
	}

	public boolean checkLogin(String username, String password) throws SQLException {
		return userDAO.checkLogin(username, password);
	}

}
