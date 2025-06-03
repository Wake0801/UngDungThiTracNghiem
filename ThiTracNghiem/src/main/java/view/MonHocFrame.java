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

import model.MonHoc;
import service.MonHocService;

public class MonHocFrame extends JFrame {
	private JTextField txtMaMH, txtTenMH;
	private JTable table;
	private DefaultTableModel tableModel;
	private MonHocService monHocService;

	public MonHocFrame() {
		monHocService = new MonHocService();
		setTitle("Quản lý môn học");
		setSize(600, 400);
		setLocationRelativeTo(null);

		JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
		inputPanel.add(new JLabel("Mã môn học:"));
		txtMaMH = new JTextField();
		inputPanel.add(txtMaMH);
		inputPanel.add(new JLabel("Tên môn học:"));
		txtTenMH = new JTextField();
		inputPanel.add(txtTenMH);

		JPanel buttonPanel = new JPanel();
		JButton btnAdd = new JButton("Thêm");
		JButton btnUpdate = new JButton("Sửa");
		JButton btnDelete = new JButton("Xóa");
		JButton btnSearch = new JButton("Tìm");
		buttonPanel.add(btnAdd);
		buttonPanel.add(btnUpdate);
		buttonPanel.add(btnDelete);
		buttonPanel.add(btnSearch);

		tableModel = new DefaultTableModel(new String[] { "Mã môn học", "Tên môn học" }, 0);
		table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);

		// Xu ly su kien cac nut
		btnAdd.addActionListener(e -> {
			try {
				MonHoc monHoc = new MonHoc(txtMaMH.getText(), txtTenMH.getText());
				monHocService.addMonHoc(monHoc);
				loadMonHocData();
				JOptionPane.showMessageDialog(null, "Thêm môn học thành công!");
				txtMaMH.setText("");
				txtTenMH.setText("");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
		});

		btnUpdate.addActionListener(e -> {
			try {
				MonHoc monHoc = new MonHoc(txtMaMH.getText(), txtTenMH.getText());
				monHocService.updateMonHoc(monHoc);
				loadMonHocData();
				JOptionPane.showMessageDialog(null, "Sửa môn học thành công!");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
		});

		btnDelete.addActionListener(e -> {
			try {
				monHocService.deleteMonHoc(txtMaMH.getText());
				loadMonHocData();
				JOptionPane.showMessageDialog(null, "Xóa môn học thành công!");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
		});

		btnSearch.addActionListener(e -> {
			try {
				MonHoc monHoc = monHocService.getMonHocById(txtMaMH.getText());
				if (monHoc != null) {
					tableModel.setRowCount(0);
					tableModel.addRow(new Object[] { monHoc.getMaMH(), monHoc.getTenMH() });
				} else {
					JOptionPane.showMessageDialog(null, "Không tìm thấy môn học!");
				}
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(null, "Lỗi khi tìm kiếm!");
			}
		});

		table.getSelectionModel().addListSelectionListener(e -> {
			int selectedRow = table.getSelectedRow();
			if (selectedRow >= 0) {
				txtMaMH.setText(tableModel.getValueAt(selectedRow, 0).toString());
				txtTenMH.setText(tableModel.getValueAt(selectedRow, 1).toString());
			}
		});

		add(inputPanel, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		loadMonHocData();
	}

	private void loadMonHocData() {
		try {
			tableModel.setRowCount(0);
			List<MonHoc> monHocList = monHocService.getAllMonHoc();
			for (MonHoc monHoc : monHocList) {
				tableModel.addRow(new Object[] { monHoc.getMaMH(), monHoc.getTenMH() });
			}
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu!");
		}
	}
}