package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatIntelliJLaf;

import model.User;
import service.UserService;

public class MainFrame extends JFrame {
	private final String username;
	private final UserService userService;

	public MainFrame(String username) {
		this.username = username;
		this.userService = new UserService();

		try {
			UIManager.setLookAndFeel(new FlatIntelliJLaf());
		} catch (Exception e) {
			e.printStackTrace();
		}

		setTitle("Hệ thống thi trắc nghiệm");
		setSize(1000, 800);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setIconImage(new ImageIcon("icon/app.png").getImage());

		setLayout(new BorderLayout());

		// Header
		JLabel header = new JLabel("Hệ thống thi trắc nghiệm " + " Xin chào, " + username + "!", JLabel.CENTER);
		header.setFont(new Font("Segoe UI", Font.BOLD, 24));
		header.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
		add(header, BorderLayout.NORTH);

		// Sidebar (Slide bar bên trái)
		JPanel sidebar = new JPanel();
		sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
		sidebar.setBackground(new Color(240, 240, 240));
		sidebar.setPreferredSize(new Dimension(250, getHeight()));

		// Tạo nút chức năng
		JButton btnLop = new JButton("🏫 Quản lý lớp");
		JButton btnMonHoc = new JButton("📘 Quản lý môn học");
		JButton btnSinhVien = new JButton("👩‍🎓 Quản lý sinh viên");
		JButton btnGiaoVien = new JButton("👨‍🏫 Quản lý giáo viên");
		JButton btnCauHoi = new JButton("❓ Soạn câu hỏi");
		JButton btnDangKyThi = new JButton("📝 Chuẩn bị thi");
		JButton btnThi = new JButton("🧠 Thi trắc nghiệm");
		JButton btnKetQua = new JButton("📊 Xem kết quả");
		JButton btnBangDiem = new JButton("📄 Bảng điểm môn học");
		JButton btnTaiKhoan = new JButton("🔐 Quản lý tài khoản");
		JButton btnBackup = new JButton("💾 Backup & Restore");
		JButton btnThoat = new JButton("🚪 Thoát");

		// Add nút theo vai trò
		try {
			User user = userService.getUserById(username);
			String role = user.getRole();

			if (role.equals("PGV")) {
				sidebar.add(btnLop);
				sidebar.add(btnMonHoc);
				sidebar.add(btnSinhVien);
				sidebar.add(btnGiaoVien);
				sidebar.add(btnTaiKhoan);
				sidebar.add(btnCauHoi);
				sidebar.add(btnDangKyThi);
				sidebar.add(btnThi);
				sidebar.add(btnKetQua);
				sidebar.add(btnBangDiem);
				sidebar.add(btnBackup);
				sidebar.add(btnThoat);
			} else if (role.equals("GiaoVien")) {
				sidebar.add(btnCauHoi);
				sidebar.add(btnDangKyThi);
				sidebar.add(btnThi);
				sidebar.add(btnKetQua);
				sidebar.add(btnBangDiem);
				sidebar.add(btnThoat);
			} else if (role.equals("SinhVien")) {
				sidebar.add(btnThi);
				sidebar.add(btnKetQua);
				sidebar.add(btnThoat);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Lỗi tải quyền người dùng");
			e.printStackTrace();
		}

		// Style nút
		for (Component comp : sidebar.getComponents()) {
			if (comp instanceof JButton) {
				JButton btn = (JButton) comp;
				btn.setAlignmentX(Component.CENTER_ALIGNMENT);
				btn.setMaximumSize(new Dimension(220, 40));
				btn.setFocusable(false);
			}
		}

		add(sidebar, BorderLayout.WEST);

		// Nội dung trung tâm (placeholder)
		JLabel content = new JLabel("Hãy chọn chức năng từ menu bên trái.", JLabel.CENTER);
		content.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		add(content, BorderLayout.CENTER);

		// Gán sự kiện
		btnLop.addActionListener(e -> new LopFrame().setVisible(true));
		btnMonHoc.addActionListener(e -> new MonHocFrame().setVisible(true));
		btnSinhVien.addActionListener(e -> new SinhVienFrame().setVisible(true));
		btnGiaoVien.addActionListener(e -> new GiaoVienFrame().setVisible(true));
		btnCauHoi.addActionListener(e -> new BoDeFrame().setVisible(true));
		btnDangKyThi.addActionListener(e -> new GiaoVien_DangKyFrame().setVisible(true));
		btnThi.addActionListener(e -> new ThiFrame(username).setVisible(true));
		btnKetQua.addActionListener(e -> new KetQuaThiFrame(username).setVisible(true));
		btnBangDiem.addActionListener(e -> new BangDiemFrame().setVisible(true));
		btnTaiKhoan.addActionListener(e -> new UserFrame().setVisible(true));
		btnBackup.addActionListener(e -> new BackupRestoreFrame().setVisible(true));
		btnThoat.addActionListener(e -> {
			int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn đăng xuất?", "Xác nhận thoát",
					JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				this.dispose(); // đóng MainFrame hiện tại
				new LoginFrame().setVisible(true); // mở lại LoginFrame
			}
		});
	}
}
