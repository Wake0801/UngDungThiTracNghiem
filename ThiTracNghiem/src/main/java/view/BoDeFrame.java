package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BorderFactory;
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

import model.BoDe;
import model.MonHoc;
import service.BoDeService;
import service.MonHocService;

public class BoDeFrame extends JFrame {
	private JTextField txtCauHoi, txtNoiDung, txtA, txtB, txtC, txtD, txtMaGV;
	private JComboBox<String> cbMaMH, cbTrinhDo, cbDapAn;
	private JTable table;
	private DefaultTableModel tableModel;
	private BoDeService boDeService;
	private MonHocService monHocService;

	public BoDeFrame() {
		boDeService = new BoDeService();
		monHocService = new MonHocService();
		setTitle("Soạn câu hỏi trắc nghiệm");
		setSize(1000, 800);
		setLocationRelativeTo(null);

		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new BorderLayout(10, 10));

		// Tạo panel con để hiển thị các nhãn và thành phần nhập liệu
		JPanel formPanel = new JPanel(new GridLayout(9, 2, 10, 10));
		formPanel.setBorder(BorderFactory.createTitledBorder("Thông tin câu hỏi"));

		// Thêm các thành phần vào formPanel
		formPanel.add(new JLabel("Mã môn học:"));
		cbMaMH = new JComboBox<>();
		formPanel.add(cbMaMH);

		formPanel.add(new JLabel("Câu hỏi số:"));
		txtCauHoi = new JTextField();
		txtCauHoi.setEditable(false);
		formPanel.add(txtCauHoi);

		formPanel.add(new JLabel("Trình độ:"));
		cbTrinhDo = new JComboBox<>(new String[] { "A", "B", "C" });
		formPanel.add(cbTrinhDo);

		formPanel.add(new JLabel("Nội dung:"));
		txtNoiDung = new JTextField();
		formPanel.add(txtNoiDung);

		formPanel.add(new JLabel("Đáp án A:"));
		txtA = new JTextField();
		formPanel.add(txtA);

		formPanel.add(new JLabel("Đáp án B:"));
		txtB = new JTextField();
		formPanel.add(txtB);

		formPanel.add(new JLabel("Đáp án C:"));
		txtC = new JTextField();
		formPanel.add(txtC);

		formPanel.add(new JLabel("Đáp án D:"));
		txtD = new JTextField();
		formPanel.add(txtD);

		formPanel.add(new JLabel("Đáp án đúng:"));
		cbDapAn = new JComboBox<>(new String[] { "A", "B", "C", "D" });
		formPanel.add(cbDapAn);

		// Panel riêng cho Mã GV
		JPanel gvPanel = new JPanel(new BorderLayout(5, 5));
		gvPanel.add(new JLabel("Mã GV:"), BorderLayout.WEST);
		txtMaGV = new JTextField();
		gvPanel.add(txtMaGV, BorderLayout.CENTER);

		// Gắn các panel vào inputPanel
		inputPanel.add(formPanel, BorderLayout.CENTER);
		inputPanel.add(gvPanel, BorderLayout.SOUTH);

		JPanel buttonPanel = new JPanel();
		JButton btnAdd = new JButton("Thêm");
		JButton btnUpdate = new JButton("Sửa");
		JButton btnDelete = new JButton("Xóa");
		JButton btnSearch = new JButton("Tìm");
		buttonPanel.add(btnAdd);
		buttonPanel.add(btnUpdate);
		buttonPanel.add(btnDelete);
		buttonPanel.add(btnSearch);

		tableModel = new DefaultTableModel(
				new String[] { "Mã MH", "Câu hỏi", "Trình độ", "Nội dung", "A", "B", "C", "D", "Đáp án", "Mã GV" }, 0);
		table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);

		btnAdd.addActionListener(e -> {
			try {
				String maMH = cbMaMH.getSelectedItem().toString();
				int cauHoi = boDeService.getNextCauHoi(maMH); // Tự sinh số câu hỏi

				BoDe boDe = new BoDe(maMH, cauHoi, cbTrinhDo.getSelectedItem().toString(), txtNoiDung.getText(),
						txtA.getText(), txtB.getText(), txtC.getText(), txtD.getText(),
						cbDapAn.getSelectedItem().toString(), txtMaGV.getText());

				boDeService.addBoDe(boDe);
				loadBoDeData();
				JOptionPane.showMessageDialog(null, "Thêm câu hỏi thành công!");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
		});

		btnUpdate.addActionListener(e -> {
			try {
				BoDe boDe = new BoDe(cbMaMH.getSelectedItem().toString(), Integer.parseInt(txtCauHoi.getText()),
						cbTrinhDo.getSelectedItem().toString(), txtNoiDung.getText(), txtA.getText(), txtB.getText(),
						txtC.getText(), txtD.getText(), cbDapAn.getSelectedItem().toString(), txtMaGV.getText());
				boDeService.updateBoDe(boDe);
				loadBoDeData();
				JOptionPane.showMessageDialog(null, "Sửa câu hỏi thành công!");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
		});

		btnDelete.addActionListener(e -> {
			try {
				boDeService.deleteBoDe(cbMaMH.getSelectedItem().toString(), Integer.parseInt(txtCauHoi.getText()));
				loadBoDeData();
				JOptionPane.showMessageDialog(null, "Xóa câu hỏi thành công!");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
		});

		btnSearch.addActionListener(e -> {
			try {
				BoDe boDe = boDeService.getBoDeById(cbMaMH.getSelectedItem().toString(),
						Integer.parseInt(txtCauHoi.getText()));
				if (boDe != null) {
					tableModel.setRowCount(0);
					tableModel.addRow(new Object[] { boDe.getMaMH(), boDe.getCauHoi(), boDe.getTrinhDo(),
							boDe.getNoiDung(), boDe.getA(), boDe.getB(), boDe.getC(), boDe.getD(), boDe.getDapAn(),
							boDe.getMaGV() });
				} else {
					JOptionPane.showMessageDialog(null, "Không tìm thấy câu hỏi!");
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Lỗi khi tìm kiếm!");
			}
		});

		table.getSelectionModel().addListSelectionListener(e -> {
			int selectedRow = table.getSelectedRow();
			if (selectedRow >= 0) {
				cbMaMH.setSelectedItem(tableModel.getValueAt(selectedRow, 0).toString());
				txtCauHoi.setText(tableModel.getValueAt(selectedRow, 1).toString());
				cbTrinhDo.setSelectedItem(tableModel.getValueAt(selectedRow, 2).toString());
				txtNoiDung.setText(tableModel.getValueAt(selectedRow, 3).toString());
				txtA.setText(tableModel.getValueAt(selectedRow, 4).toString());
				txtB.setText(tableModel.getValueAt(selectedRow, 5).toString());
				txtC.setText(tableModel.getValueAt(selectedRow, 6).toString());
				txtD.setText(tableModel.getValueAt(selectedRow, 7).toString());
				cbDapAn.setSelectedItem(tableModel.getValueAt(selectedRow, 8).toString());
				txtMaGV.setText(tableModel.getValueAt(selectedRow, 9).toString());
			}
		});

		add(inputPanel, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		loadMonHocComboBox();
		loadBoDeData();
	}

	private void loadMonHocComboBox() {
		try {
			List<MonHoc> monHocList = monHocService.getAllMonHoc();
			cbMaMH.removeAllItems();
			for (MonHoc mh : monHocList) {
				cbMaMH.addItem(mh.getMaMH());
			}
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Lỗi khi tải danh sách môn học!");
		}
	}

	private void loadBoDeData() {
		try {
			tableModel.setRowCount(0);
			List<BoDe> boDeList = boDeService.getAllBoDe();
			for (BoDe bd : boDeList) {
				tableModel.addRow(new Object[] { bd.getMaMH(), bd.getCauHoi(), bd.getTrinhDo(), bd.getNoiDung(),
						bd.getA(), bd.getB(), bd.getC(), bd.getD(), bd.getDapAn(), bd.getMaGV() });
			}
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu!");
		}
	}
}