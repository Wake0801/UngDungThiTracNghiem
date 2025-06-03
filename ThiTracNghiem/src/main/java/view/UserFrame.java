package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import model.User;
import service.UserService;

public class UserFrame extends JFrame {
	private JTextField txtUsername, txtPassword;
	private JComboBox<String> cbNhom;
	private JTable table;
	private DefaultTableModel tableModel;
	private UserService userService;

	public UserFrame() {
		userService = new UserService();
		setTitle("Quản lý tài khoản");
		setSize(600, 400);
		setLocationRelativeTo(null);

		JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
		inputPanel.add(new JLabel("Tên đăng nhập:"));
		txtUsername = new JTextField();
		inputPanel.add(txtUsername);
		inputPanel.add(new JLabel("Mật khẩu:"));
		txtPassword = new JTextField();
		inputPanel.add(txtPassword);
		inputPanel.add(new JLabel("Nhóm:"));
		cbNhom = new JComboBox<>(new String[] { "PGV", "GiangVien", "SinhVien" });
		inputPanel.add(cbNhom);

		JPanel buttonPanel = new JPanel();
		JButton btnAdd = new JButton("Thêm");
		JButton btnUpdate = new JButton("Sửa");
		JButton btnDelete = new JButton("Xóa");
		buttonPanel.add(btnAdd);
		buttonPanel.add(btnUpdate);
		buttonPanel.add(btnDelete);

		tableModel = new DefaultTableModel(new String[] { "Tên đăng nhập", "Mật khẩu", "Nhóm" }, 0);
		table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);

		btnAdd.addActionListener(e -> {
			try {
				User user = new User(txtUsername.getText(), txtPassword.getText(), cbNhom.getSelectedItem().toString());
				userService.addUser(user);
				loadTaiKhoanData();
				JOptionPane.showMessageDialog(null, "Thêm tài khoản thành công!");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
		});

		btnUpdate.addActionListener(e -> {
			try {
				User user = new User(txtUsername.getText(), txtPassword.getText(), cbNhom.getSelectedItem().toString());
				userService.updateUser(user);
				loadTaiKhoanData();
				JOptionPane.showMessageDialog(null, "Sửa tài khoản thành công!");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
		});

		btnDelete.addActionListener(e -> {
			try {
				userService.deleteUser(txtUsername.getText());
				loadTaiKhoanData();
				JOptionPane.showMessageDialog(null, "Xóa tài khoản thành công!");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
		});

		table.getSelectionModel().addListSelectionListener(e -> {
			int selectedRow = table.getSelectedRow();
			if (selectedRow >= 0) {
				txtUsername.setText(tableModel.getValueAt(selectedRow, 0).toString());
				txtPassword.setText(tableModel.getValueAt(selectedRow, 1).toString());
				cbNhom.setSelectedItem(tableModel.getValueAt(selectedRow, 2).toString());
			}
		});

		add(inputPanel, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		loadTaiKhoanData();
	}

	private void loadTaiKhoanData() {
		try {
			tableModel.setRowCount(0);
			List<User> userList = userService.getAllUser();
			for (User user : userList) {
				if (!user.getRole().equals("SinhVien")) {
					tableModel.addRow(new Object[] { user.getLogin(), user.getPassword(), user.getRole() });
				}
			}
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu!");
		}
	}
}