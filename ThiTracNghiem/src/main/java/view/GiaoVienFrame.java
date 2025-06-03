package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import model.GiaoVien;
import service.GiaoVienService;

public class GiaoVienFrame extends JFrame {
	private JTextField txtMaGV, txtHo, txtTen, txtSoDTLL, txtDiaChi;
	private JTable table;
	private DefaultTableModel tableModel;
	private GiaoVienService giaoVienService;

	public GiaoVienFrame() {
		giaoVienService = new GiaoVienService();
		setTitle("Quản lý giáo viên");
		setSize(700, 400);
		setLocationRelativeTo(null);

		JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
		inputPanel.add(new JLabel("Mã GV:"));
		txtMaGV = new JTextField();
		inputPanel.add(txtMaGV);
		inputPanel.add(new JLabel("Họ:"));
		txtHo = new JTextField();
		inputPanel.add(txtHo);
		inputPanel.add(new JLabel("Tên:"));
		txtTen = new JTextField();
		inputPanel.add(txtTen);
		inputPanel.add(new JLabel("Số điện thoại:"));
		txtSoDTLL = new JTextField();
		inputPanel.add(txtSoDTLL);
		inputPanel.add(new JLabel("Địa chỉ:"));
		txtDiaChi = new JTextField();
		inputPanel.add(txtDiaChi);

		JPanel buttonPanel = new JPanel();
		JButton btnAdd = new JButton("Thêm");
		JButton btnUpdate = new JButton("Sửa");
		JButton btnDelete = new JButton("Xóa");
		JButton btnSearch = new JButton("Tìm");
		buttonPanel.add(btnAdd);
		buttonPanel.add(btnUpdate);
		buttonPanel.add(btnDelete);
		buttonPanel.add(btnSearch);

		tableModel = new DefaultTableModel(new String[] { "Mã GV", "Họ", "Tên", "Số ĐT", "Địa chỉ" }, 0);
		table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);

		btnAdd.addActionListener(e -> {
			try {
				GiaoVien giaoVien = new GiaoVien(txtMaGV.getText(), txtHo.getText(), txtTen.getText(),
						txtSoDTLL.getText(), txtDiaChi.getText());
				giaoVienService.addGiaoVien(giaoVien);
				loadGiaoVienData();
				JOptionPane.showMessageDialog(null, "Thêm giáo viên thành công!");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
		});

		btnUpdate.addActionListener(e -> {
			try {
				GiaoVien giaoVien = new GiaoVien(txtMaGV.getText(), txtHo.getText(), txtTen.getText(),
						txtSoDTLL.getText(), txtDiaChi.getText());
				giaoVienService.updateGiaoVien(giaoVien);
				loadGiaoVienData();
				JOptionPane.showMessageDialog(null, "Sửa giáo viên thành công!");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
		});

		btnDelete.addActionListener(e -> {
			try {
				giaoVienService.deleteGiaoVien(txtMaGV.getText());
				loadGiaoVienData();
				JOptionPane.showMessageDialog(null, "Xóa giáo viên thành công!");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
		});

		btnSearch.addActionListener(e -> {
			try {
				GiaoVien giaoVien = giaoVienService.getGiaoVienById(txtMaGV.getText());
				if (giaoVien != null) {
					tableModel.setRowCount(0);
					tableModel.addRow(new Object[] { giaoVien.getMaGV(), giaoVien.getHo(), giaoVien.getTen(),
							giaoVien.getSoDTLL(), giaoVien.getDiaChi() });
				} else {
					JOptionPane.showMessageDialog(null, "Không tìm thấy giáo viên!");
				}
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(null, "Lỗi khi tìm kiếm!");
			}
		});

		table.getSelectionModel().addListSelectionListener(e -> {
			int selectedRow = table.getSelectedRow();
			if (selectedRow >= 0) {
				txtMaGV.setText(tableModel.getValueAt(selectedRow, 0).toString());
				txtHo.setText(tableModel.getValueAt(selectedRow, 1).toString());
				txtTen.setText(tableModel.getValueAt(selectedRow, 2).toString());
				txtSoDTLL.setText(tableModel.getValueAt(selectedRow, 3).toString());
				txtDiaChi.setText(tableModel.getValueAt(selectedRow, 4).toString());
			}
		});

		add(inputPanel, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		loadGiaoVienData();
	}

	private void loadGiaoVienData() {
		try {
			tableModel.setRowCount(0);
			List<GiaoVien> giaoVienList = giaoVienService.getAllGiaoVien();
			for (GiaoVien gv : giaoVienList) {
				tableModel
						.addRow(new Object[] { gv.getMaGV(), gv.getHo(), gv.getTen(), gv.getSoDTLL(), gv.getDiaChi() });
			}
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu!");
		}
	}
}