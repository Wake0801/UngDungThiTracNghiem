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

import model.Lop;
import model.SinhVien;
import service.LopService;
import service.SinhVienService;

public class SinhVienFrame extends JFrame {
	private JTextField txtMaSV, txtHo, txtTen, txtNgaySinh, txtDiaChi;
	private JComboBox<String> cbMaLop;
	private JTable table;
	private DefaultTableModel tableModel;
	private SinhVienService sinhVienService;
	private LopService lopService;

	public SinhVienFrame() {
		sinhVienService = new SinhVienService();
		lopService = new LopService();
		setTitle("Quản lý sinh viên");
		setSize(800, 500);
		setLocationRelativeTo(null);

		JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));
		inputPanel.add(new JLabel("Mã SV:"));
		txtMaSV = new JTextField();
		inputPanel.add(txtMaSV);
		inputPanel.add(new JLabel("Họ:"));
		txtHo = new JTextField();
		inputPanel.add(txtHo);
		inputPanel.add(new JLabel("Tên:"));
		txtTen = new JTextField();
		inputPanel.add(txtTen);
		inputPanel.add(new JLabel("Ngày sinh:"));
		txtNgaySinh = new JTextField();
		inputPanel.add(txtNgaySinh);
		inputPanel.add(new JLabel("Địa chỉ:"));
		txtDiaChi = new JTextField();
		inputPanel.add(txtDiaChi);
		inputPanel.add(new JLabel("Mã lớp:"));
		cbMaLop = new JComboBox<>();
		inputPanel.add(cbMaLop);

		JPanel buttonPanel = new JPanel();
		JButton btnAdd = new JButton("Thêm");
		JButton btnUpdate = new JButton("Sửa");
		JButton btnDelete = new JButton("Xóa");
		JButton btnSearch = new JButton("Tìm");
		buttonPanel.add(btnAdd);
		buttonPanel.add(btnUpdate);
		buttonPanel.add(btnDelete);
		buttonPanel.add(btnSearch);

		tableModel = new DefaultTableModel(new String[] { "Mã SV", "Họ", "Tên", "Ngày sinh", "Địa chỉ", "Mã lớp" }, 0);
		table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);

		btnAdd.addActionListener(e -> {
			try {
				SinhVien sinhVien = new SinhVien(txtMaSV.getText(), txtHo.getText(), txtTen.getText(),
						txtNgaySinh.getText(), txtDiaChi.getText(), cbMaLop.getSelectedItem().toString());
				sinhVienService.addSinhVien(sinhVien);
				loadSinhVienData();
				JOptionPane.showMessageDialog(null, "Thêm sinh viên thành công!");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
		});

		btnUpdate.addActionListener(e -> {
			try {
				SinhVien sinhVien = new SinhVien(txtMaSV.getText(), txtHo.getText(), txtTen.getText(),
						txtNgaySinh.getText(), txtDiaChi.getText(), cbMaLop.getSelectedItem().toString());
				sinhVienService.updateSinhVien(sinhVien);
				loadSinhVienData();
				JOptionPane.showMessageDialog(null, "Sửa sinh viên thành công!");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
		});

		btnDelete.addActionListener(e -> {
			try {
				sinhVienService.deleteSinhVien(txtMaSV.getText());
				loadSinhVienData();
				JOptionPane.showMessageDialog(null, "Xóa sinh viên thành công!");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
		});

		btnSearch.addActionListener(e -> {
			try {
				SinhVien sinhVien = sinhVienService.getSinhVienById(txtMaSV.getText());
				if (sinhVien != null) {
					tableModel.setRowCount(0);
					tableModel.addRow(new Object[] { sinhVien.getMaSV(), sinhVien.getHo(), sinhVien.getTen(),
							sinhVien.getNgaySinh(), sinhVien.getDiaChi(), sinhVien.getMaLop() });
				} else {
					JOptionPane.showMessageDialog(null, "Không tìm thấy sinh viên!");
				}
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(null, "Lỗi khi tìm kiếm!");
			}
		});

		table.getSelectionModel().addListSelectionListener(e -> {
			int selectedRow = table.getSelectedRow();
			if (selectedRow >= 0) {
				txtMaSV.setText(tableModel.getValueAt(selectedRow, 0).toString());
				txtHo.setText(tableModel.getValueAt(selectedRow, 1).toString());
				txtTen.setText(tableModel.getValueAt(selectedRow, 2).toString());
				txtNgaySinh.setText(tableModel.getValueAt(selectedRow, 3).toString());
				txtDiaChi.setText(tableModel.getValueAt(selectedRow, 4).toString());
				cbMaLop.setSelectedItem(tableModel.getValueAt(selectedRow, 5).toString());
			}
		});

		cbMaLop.addItemListener(e -> {
			try {
				String selectedMaLop = (String) cbMaLop.getSelectedItem();
				if (selectedMaLop != null) {
					loadSinhVienTheoLop(selectedMaLop);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "Lỗi khi lọc sinh viên theo lớp!");
			}
		});

		add(inputPanel, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		loadLopComboBox();
		// loadSinh perpetuallyData();
	}

	private void loadLopComboBox() {
		try {
			List<Lop> lopList = lopService.getAllLop();
			cbMaLop.removeAllItems();
			for (Lop lop : lopList) {
				cbMaLop.addItem(lop.getMaLop());
			}
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Lỗi khi tải danh sách lớp!");
		}
	}

	private void loadSinhVienData() {
		try {
			tableModel.setRowCount(0);
			List<SinhVien> sinhVienList = sinhVienService.getAllSinhVien();
			for (SinhVien sv : sinhVienList) {
				tableModel.addRow(new Object[] { sv.getMaSV(), sv.getHo(), sv.getTen(), sv.getNgaySinh(),
						sv.getDiaChi(), sv.getMaLop() });
			}
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu!");
		}
	}

	private void loadSinhVienTheoLop(String maLop) throws SQLException {
		tableModel.setRowCount(0);
		List<SinhVien> list = sinhVienService.getSinhVienByMaLop(maLop);
		for (SinhVien sv : list) {
			tableModel.addRow(new Object[] { sv.getMaSV(), sv.getHo(), sv.getTen(), sv.getNgaySinh(), sv.getDiaChi(),
					sv.getMaLop() });
		}
	}
}