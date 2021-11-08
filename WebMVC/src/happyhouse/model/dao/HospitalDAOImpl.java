package happyhouse.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import happyhouse.model.dto.*;
import happyhouse.util.DBUtil;

//DAO : DataBase Access Object
public class HospitalDAOImpl implements HospitalDAO {

	private static HospitalDAO hospitalDao;
	
	private DBUtil dbUtil = DBUtil.getInstance();
	
	private HospitalDAOImpl() {}
	
	public static HospitalDAO getHospitalDao() {
		if (hospitalDao == null) hospitalDao = new HospitalDAOImpl();
		return hospitalDao;
	}
	
	@Override
	public List<Hospital> searchAll(String guguncode) {
		List<Hospital> list = new ArrayList<>();
		PreparedStatement pstmt;
		try (Connection conn = dbUtil.getConnection();) {
			String sql = "select * \n";
			sql += "from hospital \n";
			sql += "where guguncode = ?";
			pstmt = conn.prepareStatement(sql); 
			pstmt.setString(1, guguncode); 
			ResultSet rs = pstmt.executeQuery();
			try {
				while (rs.next()) {
					Hospital hospitalDto = new Hospital(rs.getString("sido"), rs.getString("gungu"), rs.getString("name"), rs.getString("address"), rs.getString("type"), rs.getString("tel"), rs.getString("guguncode"));
					list.add(hospitalDto);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
