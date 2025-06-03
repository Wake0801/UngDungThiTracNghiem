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
import javax.swing.table.DefaultTableModel;

import model.BangDiem;
import model.Lop;
import model.MonHoc;
import model.SinhVien;
import service.BangDiemService;
import service.LopService;
import service.MonHocService;
import service.SinhVienService;

public class BangDiemFrame extends JFrame {
	private JComboBox<String> cbMaLop, cbMaMH, cbLan;
	private JTable table;
	private DefaultTableModel tableModel;
	private BangDiemService bangDiemService;
	private LopService lopService;
	private MonHocService monHocService;
	private SinhVienService sinhVienService;

	public BangDiemFrame() {
		bangDiemService = new BangDiemService();
		lopService = new LopService();
		monHocService = new MonHocService();
		sinhVienService = new SinhVienService();

		setTitle("Bảng điểm môn học");
		setSize(600, 400);
		setLocationRelativeTo(null);

		JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
		inputPanel.add(new JLabel("Mã lớp:"));
		cbMaLop = new JComboBox<>();
		inputPanel.add(cbMaLop);
		inputPanel.add(new JLabel("Mã môn học:"));
		cbMaMH = new JComboBox<>();
		inputPanel.add(cbMaMH);
		inputPanel.add(new JLabel("Lần thi:"));
		cbLan = new JComboBox<>(new String[] { "1", "2" });
		inputPanel.add(cbLan);

		JButton btnSearch = new JButton("Xem bảng điểm");
		inputPanel.add(btnSearch);

		tableModel = new DefaultTableModel(new String[] { "STT", "Mã SV", "Họ", "Tên", "Điểm", "Điểm chữ" }, 0);
		table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);

		btnSearch.addActionListener(e -> loadBangDiemData());

		add(inputPanel, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);

		loadComboBoxes();
	}

	private void loadComboBoxes() {
		try {
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

	private void loadBangDiemData() {
		try {
			tableModel.setRowCount(0);
			List<BangDiem> bangDiemList = bangDiemService.getAllBangDiem();
			int stt = 1;
			for (BangDiem bd : bangDiemList) {
				if (bd.getMaMH().equals(cbMaMH.getSelectedItem().toString())
						&& bd.getLan() == Integer.parseInt(cbLan.getSelectedItem().toString())) {
					SinhVien sv = sinhVienService.getSinhVienById(bd.getMaSV());
					if (sv != null && sv.getMaLop().equals(cbMaLop.getSelectedItem().toString())) {
						String diemChu = convertToDiemChu(bd.getDiem());
						tableModel.addRow(
								new Object[] { stt++, bd.getMaSV(), sv.getHo(), sv.getTen(), bd.getDiem(), diemChu });
					}
				}
			}
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Lỗi khi tải bảng điểm!");
		}
	}

	private String convertToDiemChu(float diem) {
		int nguyen = (int) diem;
		int le = Math.round((diem - nguyen) * 10);

		String[] chuSo = { "Không", "Một", "Hai", "Ba", "Bốn", "Năm", "Sáu", "Bảy", "Tám", "Chín" };

		String chuNguyen = (nguyen >= 0 && nguyen <= 10) ? chuSo[nguyen] : Float.toString(diem);
		String chuLe = (le > 0 && le <= 9) ? " phẩy " + chuSo[le] : "";

		return chuNguyen + chuLe;
	}

	public boolean hasTakenExam(String maSV, String maMH, int lan) throws SQLException {
		// Truy vấn cơ sở dữ liệu để kiểm tra xem có bản ghi BangDiem nào khớp với maSV,
		// maMH, lan
		return false; // Thay bằng logic thực tế
	}
}