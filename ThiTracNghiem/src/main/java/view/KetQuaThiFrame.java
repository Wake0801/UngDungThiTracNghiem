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

import model.Lop;
import model.MonHoc;
import service.BangDiemService;
import service.GiaoVien_DangKyService;
import service.LopService;
import service.MonHocService;

public class KetQuaThiFrame extends JFrame {
	private final String username;
	private JComboBox<String> cbMaLop, cbMaMH, cbTrinhDo;
	private JTable table;
	private DefaultTableModel tableModel;
	private BangDiemService bangDiemService;
	private LopService lopService;
	private MonHocService monHocService;
	private GiaoVien_DangKyService dangKyService;

	public KetQuaThiFrame(String username) {
		this.username = username;
		bangDiemService = new BangDiemService();
		lopService = new LopService();
		monHocService = new MonHocService();
		dangKyService = new GiaoVien_DangKyService();
		setTitle("Xem kết quả");
		setSize(800, 500);
		setLocationRelativeTo(null);

		JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
		inputPanel.add(new JLabel("Mã lớp:"));
		cbMaLop = new JComboBox<>();
		inputPanel.add(cbMaLop);
		inputPanel.add(new JLabel("Mã môn học:"));
		cbMaMH = new JComboBox<>();
		inputPanel.add(cbMaMH);
		inputPanel.add(new JLabel("Trình độ:"));
		cbTrinhDo = new JComboBox<>(new String[] { "A", "B", "C" });
		inputPanel.add(cbTrinhDo);

		JButton btnSearch = new JButton("Xem kết quả");
		inputPanel.add(btnSearch);

		tableModel = new DefaultTableModel(
				new String[] { "STT", "Câu số", "Nội dung", "A", "B", "C", "D", "Trả lời", "Đáp án" }, 0);
		table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);

		btnSearch.addActionListener(e -> loadKetQuaData());

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

	private void loadKetQuaData() {
		try {
			// Giả sử cần lấy kết quả dựa trên mã lớp, môn học, trình độ
			// Trong thực tế, cần một bảng lưu trữ câu trả lời của sinh viên
			// Chức năng này cần thêm bảng lưu câu trả lời!
			JOptionPane.showMessageDialog(null, "chưa hoàn thành chức năng");
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Lỗi khi tải kết quả!");
		}
	}
}