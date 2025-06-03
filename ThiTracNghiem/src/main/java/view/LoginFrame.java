package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import service.UserService;

public class LoginFrame extends JFrame {
	private JTextField txtLogin;
	private JPasswordField txtPassword;
	private JButton btnLogin;
	private JLabel lblLogin;
	private JComboBox<String> comboRole;
	private UserService userService;
	private JCheckBox chkShowPassword;

	public LoginFrame() {
		userService = new UserService();
		setTitle("Đăng nhập hệ thống");
		setSize(450, 420);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);

		// Font cổ điển
		Font fontLabel = new Font("Serif", Font.BOLD, 16);
		Font fontInput = new Font("Serif", Font.PLAIN, 14);

		// Panel chính
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBackground(new Color(245, 245, 220)); // màu be nhẹ cổ điển
		mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
		add(mainPanel);

		// Panel trên cùng (logo + tiêu đề)
		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.setOpaque(false);

		// Logo
		ImageIcon logoIcon = new ImageIcon(getClass().getResource("/images/ptit-logo.png"));
		Image logoImage = logoIcon.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
		JLabel logoLabel = new JLabel(new ImageIcon(logoImage));
		logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		topPanel.add(logoLabel, BorderLayout.NORTH);

		// Tiêu đề
		JLabel titleLabel = new JLabel("Hệ thống thi trắc nghiệm PTIT", SwingConstants.CENTER);
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 22));
		titleLabel.setForeground(new Color(139, 69, 19));
		titleLabel.setBorder(new EmptyBorder(15, 0, 10, 0));
		topPanel.add(titleLabel, BorderLayout.SOUTH);

		mainPanel.add(topPanel, BorderLayout.NORTH);

		// Form đăng nhập
		JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 15));
		formPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(new Color(139, 69, 19), 2, true), "Thông tin đăng nhập",
				TitledBorder.CENTER, TitledBorder.TOP, new Font("Serif", Font.BOLD, 16), new Color(139, 69, 19)));
		formPanel.setBackground(new Color(255, 248, 220));

		// Vai trò
		JLabel lblRole = new JLabel("Vai trò:");
		lblRole.setFont(fontLabel);
		comboRole = new JComboBox<>(new String[] { "Giáo viên", "Sinh viên" });
		comboRole.setFont(fontInput);
		comboRole.setBorder(BorderFactory.createLineBorder(new Color(210, 180, 140), 1));

		formPanel.add(lblRole);
		formPanel.add(comboRole);

		// Login
		JLabel lblLogin = new JLabel("Tài khoản:");
		lblLogin.setFont(fontLabel);
		txtLogin = new JTextField();
		styleTextField(txtLogin, fontInput);

		formPanel.add(lblLogin);
		formPanel.add(txtLogin);

		// Mật khẩu
		JLabel lblPassword = new JLabel("Mật khẩu:");
		lblPassword.setFont(fontLabel);
		txtPassword = new JPasswordField();
		styleTextField(txtPassword, fontInput);

		formPanel.add(lblPassword);
		formPanel.add(txtPassword);

		// Nút đăng nhập
		btnLogin = new JButton("Đăng nhập");
		styleButton(btnLogin);
		formPanel.add(new JLabel());
		formPanel.add(btnLogin);

		mainPanel.add(formPanel, BorderLayout.CENTER);

		// Sự kiện
		comboRole.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (comboRole.getSelectedItem().equals("Sinh viên")) {
					lblLogin.setText("Mã SV:");
				} else {
					lblLogin.setText("Giáo viên:");
				}
			}
		});

		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String login = txtLogin.getText().trim();
				String password = new String(txtPassword.getPassword());

				try {
					if (userService.checkLogin(login, password)) {
						JOptionPane.showMessageDialog(null, "Đăng nhập thành công!");
						new MainFrame(login).setVisible(true);
						dispose();
					} else {
						JOptionPane.showMessageDialog(null, "Sai thông tin đăng nhập!");
					}
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu!");
					ex.printStackTrace();
				}
			}
		});

		add(mainPanel);
	}

	private void styleTextField(JTextField field, Font font) {
		field.setFont(font);
		field.setBorder(BorderFactory.createCompoundBorder(new LineBorder(new Color(210, 180, 140), 1),
				new EmptyBorder(5, 8, 5, 8)));
		field.setBackground(Color.WHITE);
	}

	private void styleButton(JButton button) {
		button.setFont(new Font("Serif", Font.BOLD, 15));
		button.setBackground(new Color(222, 184, 135));
		button.setForeground(Color.WHITE);
		button.setFocusPainted(false);
		button.setBorder(new LineBorder(new Color(139, 69, 19), 2, true));
		button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new LoginFrame().setVisible(true);
		});
	}

}
