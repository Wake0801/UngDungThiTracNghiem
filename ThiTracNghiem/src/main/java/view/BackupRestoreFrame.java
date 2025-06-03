package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import service.DatabaseService;

public class BackupRestoreFrame extends JFrame {
	private JList<String> lstDatabases;
	private JTextField txtBackupPath, txtRestoreTime;
	private JCheckBox chkRestoreTime;
	private DatabaseService dbService;

	public BackupRestoreFrame() {
		try {
			dbService = new DatabaseService();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Không thể đọc cấu hình: " + e.getMessage());
			System.exit(1);
		}

		setTitle("Backup & Restore");
		setSize(600, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Left Panel
		JPanel leftPanel = new JPanel(new BorderLayout());
		lstDatabases = new JList<>(new String[] { "THI_TRAC_NGHIEM" });
		leftPanel.add(new JLabel("Cơ sở dữ liệu:"), BorderLayout.NORTH);
		leftPanel.add(new JScrollPane(lstDatabases), BorderLayout.CENTER);

		// Right Panel
		JPanel rightPanel = new JPanel(new GridLayout(5, 2, 10, 10));
		rightPanel.add(new JLabel("Đường dẫn backup:"));
		txtBackupPath = new JTextField();
		txtBackupPath.setEditable(false);
		rightPanel.add(txtBackupPath);

		rightPanel.add(new JLabel(""));
		JButton btnBrowse = new JButton("Chọn đường dẫn");
		rightPanel.add(btnBrowse);

		rightPanel.add(new JLabel(""));
		JButton btnBackup = new JButton("Sao lưu");
		rightPanel.add(btnBackup);

		rightPanel.add(new JLabel(""));
		JButton btnRestore = new JButton("Phục hồi");
		rightPanel.add(btnRestore);

		rightPanel.add(new JLabel("Phục hồi theo thời gian:"));
		chkRestoreTime = new JCheckBox();
		rightPanel.add(chkRestoreTime);

		rightPanel.add(new JLabel("Thời điểm phục hồi:"));
		txtRestoreTime = new JTextField();
		txtRestoreTime.setEnabled(false);
		rightPanel.add(txtRestoreTime);

		// Layout
		add(leftPanel, BorderLayout.WEST);
		add(rightPanel, BorderLayout.CENTER);

		// Event Listeners
		btnBrowse.addActionListener(e -> {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
				txtBackupPath.setText(fileChooser.getSelectedFile().getAbsolutePath());
			}
		});

		btnBackup.addActionListener(e -> {
			String backupPath = txtBackupPath.getText();
			if (backupPath.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn đường dẫn backup!");
				return;
			}
			try {
				dbService.backupDatabase(backupPath);
				JOptionPane.showMessageDialog(this, "Sao lưu thành công!");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Lỗi khi sao lưu: " + ex.getMessage());
			}
		});

		btnRestore.addActionListener(e -> {
			String backupPath = txtBackupPath.getText();
			if (backupPath.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn tệp backup để phục hồi!");
				return;
			}
			if (chkRestoreTime.isSelected()) {
				JOptionPane.showMessageDialog(this, "Phục hồi theo thời gian chưa được hỗ trợ!");
			} else {
				try {
					dbService.restoreDatabase(backupPath);
					JOptionPane.showMessageDialog(this, "Phục hồi thành công!");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(this, "Lỗi khi phục hồi: " + ex.getMessage());
				}
			}
		});

		chkRestoreTime.addActionListener(e -> {
			txtRestoreTime.setEnabled(chkRestoreTime.isSelected());
		});
	}

}