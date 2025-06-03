package service;

import java.sql.SQLException;
import java.util.List;

import dao.BoDeDAO;
import dao.GiaoVien_DangKyDAO;
import model.BoDe;
import model.GiaoVien_DangKy;

public class GiaoVien_DangKyService {
	private final GiaoVien_DangKyDAO giaoVienDangKyDAO;
	private final BoDeDAO boDeDAO;

	public GiaoVien_DangKyService() {
		this.giaoVienDangKyDAO = new GiaoVien_DangKyDAO();
		this.boDeDAO = new BoDeDAO();
	}

	public void addGiaoVien_DangKy(GiaoVien_DangKy dk) throws SQLException {
		if (dk.getSoCauThi() < 10 || dk.getSoCauThi() > 100) {
			throw new IllegalArgumentException("Số câu thi phải từ 10 đến 100");
		}
		if (dk.getThoiGian() < 5 || dk.getThoiGian() > 60) {
			throw new IllegalArgumentException("Thời gian thi phải từ 5 đến 60 phút");
		}
		if (dk.getLan() < 1 || dk.getLan() > 2) {
			throw new IllegalArgumentException("Lần thi phải là 1 hoặc 2");
		}
		// Kiểm tra số câu hỏi có đủ trong bộ đề không
		List<BoDe> availableQuestions = boDeDAO.getRandomQuestions(dk.getMaMH(), dk.getTrinhDo(), dk.getSoCauThi());
		if (availableQuestions.size() < dk.getSoCauThi()) {
			throw new IllegalArgumentException("Không đủ câu hỏi cho môn học và trình độ này");
		}
		giaoVienDangKyDAO.addGiaoVien_DangKy(dk);
	}

	public void updateGiaoVien_DangKy(GiaoVien_DangKy dk) throws SQLException {
		if (giaoVienDangKyDAO.getGiaoVien_DangKyById(dk.getMaLop(), dk.getMaMH(), dk.getLan()) == null) {
			throw new IllegalArgumentException("Đăng ký không tồn tại");
		}
		giaoVienDangKyDAO.updateGiaoVien_DangKy(dk);
	}

	public void deleteGiaoVien_DangKy(String maLop, String maMH, int lan) throws SQLException {
		if (giaoVienDangKyDAO.getGiaoVien_DangKyById(maLop, maMH, lan) == null) {
			throw new IllegalArgumentException("Đăng ký không tồn tại");
		}
		giaoVienDangKyDAO.deleteGiaoVien_DangKy(maLop, maMH, lan);
	}

	public GiaoVien_DangKy getGiaoVien_DangKyById(String maLop, String maMH, int lan) throws SQLException {
		return giaoVienDangKyDAO.getGiaoVien_DangKyById(maLop, maMH, lan);
	}

	public List<GiaoVien_DangKy> getAllGiaoVien_DangKy() throws SQLException {
		return giaoVienDangKyDAO.getAllGiaoVien_DangKy();
	}
}