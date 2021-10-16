package happyhouse.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import happyhouse.model.dto.*;
import happyhouse.util.DBUtil;

//DAO : DataBase Access Object
public class MarketDAOImpl implements MarketDAO {

	private static MarketDAO marketDao;
	
	private DBUtil dbUtil = DBUtil.getInstance();
	
	private MarketDAOImpl() {}
	
	public static MarketDAO getMarketDao() {
		if (marketDao == null) marketDao = new MarketDAOImpl();
		return marketDao;
	}
	
	@Override
	public List<Market> searchAll(String guguncode) {
		List<Market> list = new ArrayList<>();
		PreparedStatement pstmt;
		
		try (Connection conn = dbUtil.getConnection();) {
			String sql = "select * \n";
			sql += "from commercial \n";
			sql += "where guguncode = ?";
			pstmt = conn.prepareStatement(sql); 
			pstmt.setString(1, guguncode); 
			ResultSet rs = pstmt.executeQuery();
			try {
				while (rs.next()) {
					Market marketDto = new Market(rs.getString("name"), rs.getString("branch"), rs.getString("category"), rs.getString("guguncode"), rs.getString("address"), rs.getString("lng"), rs.getString("lat"));
					list.add(marketDto);
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
