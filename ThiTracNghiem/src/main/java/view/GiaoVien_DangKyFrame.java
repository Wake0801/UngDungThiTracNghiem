package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.sql.Timestamp;
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

import model.GiaoVien;
import model.GiaoVien_DangKy;
import model.Lop;
import model.MonHoc;
import service.GiaoVienService;
import service.GiaoVien_DangKyService;
import service.LopService;
import service.MonHocService;

public class GiaoVien_DangKyFrame extends JFrame {
	private JComboBox<String> cbMaGV, cbMaLop, cbMaMH, cbTrinhDo;
	private JTextField txtNgayThi, txtLan, txtSoCauThi, txtThoiGian;
	private JTable table;
	private DefaultTableModel tableModel;
	private GiaoVien_DangKyService dangKyService;
	private GiaoVienService giaoVienService;
	private LopService lopService;
	private MonHocService monHocService;

	public GiaoVien_DangKyFrame() {
		dangKyService = new GiaoVien_DangKyService();
		giaoVienService = new GiaoVienService();
		lopService = new LopService();
		monHocService = new MonHocService();
		setTitle("Chuẩn bị thi");
		setSize(800, 500);
		setLocationRelativeTo(null);

		JPanel inputPanel = new JPanel(new GridLayout(8, 2, 10, 10));
		inputPanel.add(new JLabel("Mã giáo viên:"));
		cbMaGV = new JComboBox<>();
		inputPanel.add(cbMaGV);
		inputPanel.add(new JLabel("Mã lớp:"));
		cbMaLop = new JComboBox<>();
		inputPanel.add(cbMaLop);
		inputPanel.add(new JLabel("Mã môn học:"));
		cbMaMH = new JComboBox<>();
		inputPanel.add(cbMaMH);
		inputPanel.add(new JLabel("Trình độ:"));
		cbTrinhDo = new JComboBox<>(new String[] { "A", "B", "C" });
		inputPanel.add(cbTrinhDo);
		inputPanel.add(new JLabel("Ngày thi:"));
		txtNgayThi = new JTextField();
		inputPanel.add(txtNgayThi);
		inputPanel.add(new JLabel("Lần thi:"));
		txtLan = new JTextField();
		inputPanel.add(txtLan);
		inputPanel.add(new JLabel("Số câu thi:"));
		txtSoCauThi = new JTextField();
		inputPanel.add(txtSoCauThi);
		inputPanel.add(new JLabel("Thời gian (phút):"));
		txtThoiGian = new JTextField();
		inputPanel.add(txtThoiGian);

		JPanel buttonPanel = new JPanel();
		JButton btnAdd = new JButton("Thêm");
		JButton btnUpdate = new JButton("Sửa");
		JButton btnDelete = new JButton("Xóa");
		buttonPanel.add(btnAdd);
		buttonPanel.add(btnUpdate);
		buttonPanel.add(btnDelete);

		tableModel = new DefaultTableModel(
				new String[] { "Mã GV", "Mã lớp", "Mã MH", "Trình độ", "Ngày thi", "Lần", "Số câu", "Thời gian" }, 0);
		table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);

		btnAdd.addActionListener(e -> {
			try {
				GiaoVien_DangKy dk = new GiaoVien_DangKy(cbMaGV.getSelectedItem().toString(),
						cbMaLop.getSelectedItem().toString(), cbMaMH.getSelectedItem().toString(),
						cbTrinhDo.getSelectedItem().toString(), Timestamp.valueOf(txtNgayThi.getText()),
						Integer.parseInt(txtLan.getText()), Integer.parseInt(txtSoCauThi.getText()),
						Integer.parseInt(txtThoiGian.getText()));
				dangKyService.addGiaoVien_DangKy(dk);
				loadDangKyData();
				JOptionPane.showMessageDialog(null, "Thêm đăng ký thi thành công!");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
		});

		btnUpdate.addActionListener(e -> {
			try {
				GiaoVien_DangKy dk = new GiaoVien_DangKy(cbMaGV.getSelectedItem().toString(),
						cbMaLop.getSelectedItem().toString(), cbMaMH.getSelectedItem().toString(),
						cbTrinhDo.getSelectedItem().toString(), Timestamp.valueOf(txtNgayThi.getText()),
						Integer.parseInt(txtLan.getText()), Integer.parseInt(txtSoCauThi.getText()),
						Integer.parseInt(txtThoiGian.getText()));
				dangKyService.updateGiaoVien_DangKy(dk);
				loadDangKyData();
				JOptionPane.showMessageDialog(null, "Sửa đăng ký thi thành công!");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
		});

		btnDelete.addActionListener(e -> {
			try {
				dangKyService.deleteGiaoVien_DangKy(cbMaLop.getSelectedItem().toString(),
						cbMaMH.getSelectedItem().toString(), Integer.parseInt(txtLan.getText()));
				loadDangKyData();
				JOptionPane.showMessageDialog(null, "Xóa đăng ký thi thành công!");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
		});

		table.getSelectionModel().addListSelectionListener(e -> {
			int selectedRow = table.getSelectedRow();
			if (selectedRow >= 0) {
				cbMaGV.setSelectedItem(tableModel.getValueAt(selectedRow, 0).toString());
				cbMaLop.setSelectedItem(tableModel.getValueAt(selectedRow, 1).toString());
				cbMaMH.setSelectedItem(tableModel.getValueAt(selectedRow, 2).toString());
				cbTrinhDo.setSelectedItem(tableModel.getValueAt(selectedRow, 3).toString());
				txtNgayThi.setText(tableModel.getValueAt(selectedRow, 4).toString());
				txtLan.setText(tableModel.getValueAt(selectedRow, 5).toString());
				txtSoCauThi.setText(tableModel.getValueAt(selectedRow, 6).toString());
				txtThoiGian.setText(tableModel.getValueAt(selectedRow, 7).toString());
			}
		});

		add(inputPanel, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		loadComboBoxes();
		loadDangKyData();
	}

	private void loadComboBoxes() {
		try {
			List<GiaoVien> giaoVienList = giaoVienService.getAllGiaoVien();
			cbMaGV.removeAllItems();
			for (GiaoVien gv : giaoVienList) {
				cbMaGV.addItem(gv.getMaGV());
			}

			List<Lop> lopList = lopService.getAllLop();
			cbMaLop.removeAllItems();
			for (Lop lop : lopList) {
				cbMaLop.addItem(lop.getMaLop());
			}

			List<MonHoc> monHocList = monHocService.getAllMonHoc();
			cbMaMH.removeAllItems();
			for (MonHoc mh : monHocList) {
				cbMaMH.addItem(mh.getMaMH());
			}
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Lỗi khi tải danh sách!");
		}
	}

	private void loadDangKyData() {
		try {
			tableModel.setRowCount(0);
			List<GiaoVien_DangKy> dkList = dangKyService.getAllGiaoVien_DangKy();
			for (GiaoVien_DangKy dk : dkList) {
				tableModel.addRow(new Object[] { dk.getMaGV(), dk.getMaLop(), dk.getMaMH(), dk.getTrinhDo(),
						dk.getNgayThi(), dk.getLan(), dk.getSoCauThi(), dk.getThoiGian() });
			}
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu!");
		}
	}
}