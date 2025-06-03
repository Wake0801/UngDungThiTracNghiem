package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import model.BangDiem;
import model.BoDe;
import model.GiaoVien_DangKy;
import model.MonHoc;
import model.SinhVien;
import service.BangDiemService;
import service.BoDeService;
import service.GiaoVien_DangKyService;
import service.MonHocService;
import service.SinhVienService;

public class ThiFrame extends JFrame {
	private static final int FRAME_WIDTH = 800; // Tăng kích thước khung
	private static final int FRAME_HEIGHT = 600;
	private static final int TIMER_DELAY_MS = 1000;
	private static final float MAX_SCORE = 10.0f;

	private final String maSV;
	private JComboBox<String> cbMaLop, cbMaMH, cbNgayThi, cbLan;
	private JLabel lblQuestion, lblTime, lblSoCauThi, lblTrinhDo, lblProgress;
	private JRadioButton rbA, rbB, rbC, rbD;
	private ButtonGroup group;
	private JButton btnStart, btnPrevious, btnNext, btnSubmit;
	private List<BoDe> questions;
	private Map<Integer, String> userAnswers;
	private int currentQuestionIndex = 0;
	private int score = 0;
	private int timeLeft;
	private Timer timer;
	private GiaoVien_DangKyService dangKyService;
	private BoDeService boDeService;
	private SinhVienService sinhVienService;
	private BangDiemService bangDiemService;
	private MonHocService monHocService;

	public ThiFrame(String maSV) {
		this.maSV = maSV;
		dangKyService = new GiaoVien_DangKyService();
		boDeService = new BoDeService();
		sinhVienService = new SinhVienService();
		bangDiemService = new BangDiemService();
		monHocService = new MonHocService();
		userAnswers = new HashMap<>();

		initializeUI();
		loadComboBoxes();
	}

	private void initializeUI() {
		setTitle("Thi Trắc Nghiệm");
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setBackground(new Color(245, 245, 245));

		// Input panel with GridBagLayout
		JPanel inputPanel = new JPanel(new GridBagLayout());
		inputPanel.setBorder(new TitledBorder("Thông Tin Kỳ Thi"));
		inputPanel.setBackground(Color.WHITE);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 10, 5, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		cbMaLop = new JComboBox<>();
		cbMaMH = new JComboBox<>();
		cbNgayThi = new JComboBox<>();
		cbLan = new JComboBox<>(new String[] { "1", "2" });

		gbc.gridx = 0;
		gbc.gridy = 0;
		inputPanel.add(new JLabel("Mã lớp:"), gbc);
		gbc.gridx = 1;
		inputPanel.add(cbMaLop, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		inputPanel.add(new JLabel("Mã môn học:"), gbc);
		gbc.gridx = 1;
		inputPanel.add(cbMaMH, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		inputPanel.add(new JLabel("Ngày thi:"), gbc);
		gbc.gridx = 1;
		inputPanel.add(cbNgayThi, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		inputPanel.add(new JLabel("Lần thi:"), gbc);
		gbc.gridx = 1;
		inputPanel.add(cbLan, gbc);

		// Info panel
		JPanel infoPanel = new JPanel(new GridLayout(3, 2, 10, 10));
		infoPanel.setBorder(new TitledBorder("Chi Tiết"));
		infoPanel.setBackground(Color.WHITE);
		lblSoCauThi = new JLabel("Chưa chọn");
		lblTrinhDo = new JLabel("Chưa chọn");
		lblProgress = new JLabel("Câu 0/0");
		infoPanel.add(new JLabel("Số câu thi:"));
		infoPanel.add(lblSoCauThi);
		infoPanel.add(new JLabel("Trình độ:"));
		infoPanel.add(lblTrinhDo);
		infoPanel.add(new JLabel("Tiến độ:"));
		infoPanel.add(lblProgress);

		// Question panel
		JPanel questionPanel = new JPanel(new BorderLayout());
		questionPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		questionPanel.setBackground(Color.WHITE);

		lblTime = new JLabel("Thời gian: 00:00", SwingConstants.CENTER);
		lblTime.setFont(new Font("Arial", Font.BOLD, 16));
		lblQuestion = new JLabel("Chưa có câu hỏi", SwingConstants.LEFT);
		lblQuestion.setFont(new Font("Arial", Font.PLAIN, 14));

		rbA = new JRadioButton();
		rbB = new JRadioButton();
		rbC = new JRadioButton();
		rbD = new JRadioButton();
		group = new ButtonGroup();
		group.add(rbA);
		group.add(rbB);
		group.add(rbC);
		group.add(rbD);

		JPanel optionsPanel = new JPanel(new GridLayout(4, 1, 5, 5));
		optionsPanel.add(rbA);
		optionsPanel.add(rbB);
		optionsPanel.add(rbC);
		optionsPanel.add(rbD);

		questionPanel.add(lblTime, BorderLayout.NORTH);
		questionPanel.add(lblQuestion, BorderLayout.CENTER);
		questionPanel.add(optionsPanel, BorderLayout.SOUTH);

		// Button panel
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		btnStart = new JButton("Bắt đầu thi");
		btnPrevious = new JButton("Câu trước");
		btnNext = new JButton("Câu tiếp");
		btnSubmit = new JButton("Nộp bài");
		btnPrevious.setEnabled(false);
		btnNext.setEnabled(false);
		btnSubmit.setEnabled(false);

		buttonPanel.add(btnStart);
		buttonPanel.add(btnPrevious);
		buttonPanel.add(btnNext);
		buttonPanel.add(btnSubmit);

		// Main layout
		setLayout(new BorderLayout(10, 10));
		add(inputPanel, BorderLayout.NORTH);
		add(infoPanel, BorderLayout.WEST);
		add(questionPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		// Event listeners
		btnStart.addActionListener(e -> startExam());
		btnPrevious.addActionListener(e -> previousQuestion());
		btnNext.addActionListener(e -> nextQuestion());
		btnSubmit.addActionListener(e -> confirmSubmit());
		cbMaLop.addActionListener(e -> loadDangKyInfo());
		cbMaMH.addActionListener(e -> loadDangKyInfo());
		cbNgayThi.addActionListener(e -> loadDangKyInfo());
		cbLan.addActionListener(e -> loadDangKyInfo());

		// Clean up timer on close
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent e) {
				stopTimer();
			}
		});
	}

	private void loadComboBoxes() {
		try {
			// Load class
			SinhVien sv = sinhVienService.getSinhVienById(maSV);
			if (sv != null && sv.getMaLop() != null && !sv.getMaLop().isEmpty()) {
				cbMaLop.addItem(sv.getMaLop());
				cbMaLop.setSelectedItem(sv.getMaLop());
			} else {
				JOptionPane.showMessageDialog(this, "Không tìm thấy mã lớp cho sinh viên " + maSV + "!");
				cbMaLop.addItem("Không có dữ liệu");
			}

			// Load subjects
			cbMaMH.removeAllItems();
			List<MonHoc> monHocs = monHocService.getAllMonHoc();
			if (monHocs.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Không có môn học nào trong cơ sở dữ liệu!");
				cbMaMH.addItem("Không có dữ liệu");
			} else {
				for (MonHoc mh : monHocs) {
					cbMaMH.addItem(mh.getMaMH());
				}
			}

			// Load exam dates (filtered in loadDangKyInfo)
			loadDangKyInfo();

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	private void loadDangKyInfo() {
		try {
			if (cbMaLop.getSelectedItem() == null || cbMaMH.getSelectedItem() == null
					|| cbLan.getSelectedItem() == null) {
				cbNgayThi.removeAllItems();
				cbNgayThi.addItem("Chọn mã lớp và môn học trước");
				return;
			}

			String maLop = cbMaLop.getSelectedItem().toString();
			String maMH = cbMaMH.getSelectedItem().toString();
			int lan = Integer.parseInt(cbLan.getSelectedItem().toString());

			// Filter exam dates
			cbNgayThi.removeAllItems();
			List<GiaoVien_DangKy> dangKys = dangKyService.getAllGiaoVien_DangKy();
			boolean hasDates = false;
			for (GiaoVien_DangKy dk : dangKys) {
				if (dk.getMaLop().equals(maLop) && dk.getMaMH().equals(maMH) && dk.getLan() == lan) {
					cbNgayThi.addItem(dk.getNgayThi().toString());
					hasDates = true;
				}
			}
			if (!hasDates) {
				cbNgayThi.addItem("Không có ngày thi phù hợp");
			}

			// Load exam details
			GiaoVien_DangKy dk = dangKyService.getGiaoVien_DangKyById(maLop, maMH, lan);
			if (dk != null) {
				lblSoCauThi.setText(String.valueOf(dk.getSoCauThi()));
				lblTrinhDo.setText(dk.getTrinhDo());
				timeLeft = dk.getThoiGian() * 60;
			} else {
				lblSoCauThi.setText("Chưa chọn");
				lblTrinhDo.setText("Chưa chọn");
			}

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, "Lỗi khi tải thông tin kỳ thi: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	private void startExam() {
		try {
			// Validate inputs
			if (!validateExamInputs()) {
				return;
			}

			int soCau = Integer.parseInt(lblSoCauThi.getText());
			String maMH = cbMaMH.getSelectedItem().toString();
			String trinhDo = lblTrinhDo.getText();
			int lan = Integer.parseInt(cbLan.getSelectedItem().toString());

			// Check if student has already taken this exam
			if (bangDiemService.hasTakenExam(maSV, maMH, lan)) {
				JOptionPane.showMessageDialog(this, "Bạn đã thi lần " + lan + " cho môn " + maMH + "!");
				return;
			}

			// Load questions
			questions = boDeService.getRandomQuestions(maMH, trinhDo, soCau);
			if (questions == null || questions.isEmpty()) {
				JOptionPane.showMessageDialog(this,
						"Không tìm thấy câu hỏi phù hợp cho môn " + maMH + " và trình độ " + trinhDo);
				return;
			}

			Collections.shuffle(questions);
			currentQuestionIndex = 0;
			score = 0;
			userAnswers.clear();
			loadQuestion();
			updateProgress();

			// Enable/disable buttons
			btnStart.setEnabled(false);
			btnPrevious.setEnabled(false);
			btnNext.setEnabled(questions.size() > 1);
			btnSubmit.setEnabled(true);

			// Start timer
			timer = new Timer();
			timer.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					timeLeft--;
					lblTime.setText("Thời gian: " + (timeLeft / 60) + ":" + String.format("%02d", (timeLeft % 60)));
					if (timeLeft <= 0) {
						endExam();
					}
				}
			}, TIMER_DELAY_MS, TIMER_DELAY_MS);

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, "Lỗi khi bắt đầu thi: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	private boolean validateExamInputs() {
		if (cbMaLop.getSelectedItem() == null || cbMaMH.getSelectedItem() == null || cbNgayThi.getSelectedItem() == null
				|| cbLan.getSelectedItem() == null || cbMaLop.getSelectedItem().toString().startsWith("Không")
				|| cbMaMH.getSelectedItem().toString().startsWith("Không")
				|| cbNgayThi.getSelectedItem().toString().startsWith("Không")) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn đầy đủ và đúng thông tin kỳ thi!");
			return false;
		}
		try {
			Integer.parseInt(lblSoCauThi.getText());
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Số câu thi không hợp lệ!");
			return false;
		}
		return true;
	}

	private void loadQuestion() {
		if (currentQuestionIndex >= questions.size()) {
			return;
		}
		BoDe q = questions.get(currentQuestionIndex);
		lblQuestion.setText("<html>Câu " + (currentQuestionIndex + 1) + ": " + q.getNoiDung() + "</html>");
		rbA.setText("A. " + q.getA());
		rbB.setText("B. " + q.getB());
		rbC.setText("C. " + q.getC());
		rbD.setText("D. " + q.getD());

		// Load previous answer if exists
		group.clearSelection();
		String previousAnswer = userAnswers.getOrDefault(currentQuestionIndex, null);
		if (previousAnswer != null) {
			switch (previousAnswer) {
			case "A":
				rbA.setSelected(true);
				break;
			case "B":
				rbB.setSelected(true);
				break;
			case "C":
				rbC.setSelected(true);
				break;
			case "D":
				rbD.setSelected(true);
				break;
			}
		}

		// Update button states
		btnPrevious.setEnabled(currentQuestionIndex > 0);
		btnNext.setEnabled(currentQuestionIndex < questions.size() - 1);
	}

	private void previousQuestion() {
		saveAnswer();
		if (currentQuestionIndex > 0) {
			currentQuestionIndex--;
			loadQuestion();
			updateProgress();
		}
	}

	private void nextQuestion() {
		saveAnswer();
		if (currentQuestionIndex < questions.size() - 1) {
			currentQuestionIndex++;
			loadQuestion();
			updateProgress();
		} else {
			endExam();
		}
	}

	private void saveAnswer() {
		String selectedAnswer = null;
		if (rbA.isSelected())
			selectedAnswer = "A";
		else if (rbB.isSelected())
			selectedAnswer = "B";
		else if (rbC.isSelected())
			selectedAnswer = "C";
		else if (rbD.isSelected())
			selectedAnswer = "D";

		if (selectedAnswer != null) {
			userAnswers.put(currentQuestionIndex, selectedAnswer);
		}
	}

	private void updateProgress() {
		lblProgress.setText("Câu " + (currentQuestionIndex + 1) + "/" + questions.size());
	}

	private void confirmSubmit() {
		int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn nộp bài?", "Xác nhận nộp bài",
				JOptionPane.YES_NO_OPTION);
		if (confirm == JOptionPane.YES_OPTION) {
			endExam();
		}
	}

	private void endExam() {
		stopTimer();
		calculateScore();

		float diem = ((float) score / questions.size()) * MAX_SCORE;
		String resultMessage = String.format("Điểm: %.2f\nSố câu đúng: %d/%d", diem, score, questions.size());
		JOptionPane.showMessageDialog(this, resultMessage);

		try {
			BangDiem bangDiem = new BangDiem(maSV, cbMaMH.getSelectedItem().toString(),
					Integer.parseInt(cbLan.getSelectedItem().toString()), cbNgayThi.getSelectedItem().toString(), diem);
			bangDiemService.addBangDiem(bangDiem);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, "Lỗi khi lưu điểm: " + ex.getMessage());
			ex.printStackTrace();
		}

		dispose();
	}

	private void calculateScore() {
		score = 0;
		for (int i = 0; i < questions.size(); i++) {
			String userAnswer = userAnswers.getOrDefault(i, null);
			if (userAnswer != null && userAnswer.equals(questions.get(i).getDapAn())) {
				score++;
			}
		}
	}

	private void stopTimer() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	public static void main(String[] args) {
		// Chạy giao diện trên Event Dispatch Thread
		javax.swing.SwingUtilities.invokeLater(() -> {
			ThiFrame frame = new ThiFrame("SV001");
			frame.setVisible(true);
		});
	}
}