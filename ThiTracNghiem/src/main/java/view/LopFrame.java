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

import model.Lop;
import service.LopService;

public class LopFrame extends JFrame {
	private JTextField txtMaLop, txtTenLop;
	private JTable table;
	private DefaultTableModel tableModel;
	private LopService lopService;

	public LopFrame() {
		lopService = new LopService();
		setTitle("Quản lý lớp học");
		setSize(600, 400);
		setLocationRelativeTo(null);

		JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
		inputPanel.add(new JLabel("Mã lớp:"));
		txtMaLop = new JTextField();
		inputPanel.add(txtMaLop);
		inputPanel.add(new JLabel("Tên lớp:"));
		txtTenLop = new JTextField();
		inputPanel.add(txtTenLop);

		JPanel buttonPanel = new JPanel();
		JButton btnAdd = new JButton("Thêm");
		JButton btnUpdate = new JButton("Sửa");
		JButton btnDelete = new JButton("Xóa");
		JButton btnSearch = new JButton("Tìm");
		buttonPanel.add(btnAdd);
		buttonPanel.add(btnUpdate);
		buttonPanel.add(btnDelete);
		buttonPanel.add(btnSearch);

		tableModel = new DefaultTableModel(new String[] { "Mã lớp", "Tên lớp" }, 0);
		table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);

		btnAdd.addActionListener(e -> {
			try {
				Lop lop = new Lop(txtMaLop.getText(), txtTenLop.getText());
				lopService.addLop(lop);
				loadLopData();
				JOptionPane.showMessageDialog(null, "Thêm lớp thành công!");
				txtMaLop.setText("");
				txtTenLop.setText("");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
		});

		btnUpdate.addActionListener(e -> {
			try {
				Lop lop = new Lop(txtMaLop.getText(), txtTenLop.getText());
				lopService.updateLop(lop);
				loadLopData();
				JOptionPane.showMessageDialog(null, "Sửa lớp thành công!");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
		});

		btnDelete.addActionListener(e -> {
			try {
				lopService.deleteLop(txtMaLop.getText());
				loadLopData();
				JOptionPane.showMessageDialog(null, "Xóa lớp thành công!");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
		});

		btnSearch.addActionListener(e -> {
			try {
				Lop lop = lopService.getLopById(txtMaLop.getText());
				if (lop != null) {
					tableModel.setRowCount(0);
					tableModel.addRow(new Object[] { lop.getMaLop(), lop.getTenLop() });
				} else {
					JOptionPane.showMessageDialog(null, "Không tìm thấy lớp!");
				}
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(null, "Lỗi khi tìm kiếm!");
			}
		});

		table.getSelectionModel().addListSelectionListener(e -> {
			int selectedRow = table.getSelectedRow();
			if (selectedRow >= 0) {
				txtMaLop.setText(tableModel.getValueAt(selectedRow, 0).toString());
				txtTenLop.setText(tableModel.getValueAt(selectedRow, 1).toString());
			}
		});

		add(inputPanel, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		loadLopData();
	}

	private void loadLopData() {
		try {
			tableModel.setRowCount(0);
			List<Lop> lopList = lopService.getAllLop();
			for (Lop lop : lopList) {
				tableModel.addRow(new Object[] { lop.getMaLop(), lop.getTenLop() });
			}
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu!");
		}
	}

}