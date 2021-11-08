package happyhouse.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import happyhouse.model.dto.*;
import happyhouse.util.DBUtil;

public class HouseDAOImpl implements HouseDAO {

	private static HouseDAO houseDao;
	
	private DBUtil dbUtil = DBUtil.getInstance();

	private HouseDAOImpl() {}

	public static HouseDAO getHouseDao() {
		if (houseDao == null)
			houseDao = new HouseDAOImpl();
		return houseDao;
	}

	@Override
	public List<House> searchAll(String sido, String gugun, String dong, String aptName) {
		List<House> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		try (Connection conn = dbUtil.getConnection();) {
			String sql = "select distinct city, gugun, dong, aptName, dealAmount, area, type, lat, lng \n";
			sql += "from searchDeal\n";
			sql += "where city = ? and gugun = ? and dong = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, sido);
			pstmt.setString(2, gugun);	
			pstmt.setString(3, dong);	
			
			try (ResultSet rs = pstmt.executeQuery();) {
				while (rs.next()) {
					House houseDto = new House();
					houseDto.setSido(rs.getString("city"));
					houseDto.setGugun(rs.getString("gugun"));
					houseDto.setDong(rs.getString("dong"));
					houseDto.setAptName(rs.getString("aptName"));
					houseDto.setDealAmount(rs.getString("dealAmount"));
					houseDto.setArea(rs.getString("area"));
					houseDto.setType(rs.getString("type"));
					houseDto.setLat(rs.getString("lat"));
					houseDto.setLng(rs.getString("lng"));
					
					if(aptName.trim().length() != 0 && !houseDto.getAptName().contains(aptName.trim())) continue;
					list.add(houseDto);
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
