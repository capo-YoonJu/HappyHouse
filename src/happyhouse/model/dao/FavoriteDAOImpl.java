package happyhouse.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import happyhouse.model.dto.Favorite;
import happyhouse.util.DBUtil;

public class FavoriteDAOImpl implements FavoriteDAO {

	private static FavoriteDAO favoriteDao = new FavoriteDAOImpl();

	private DBUtil dbUtil;
	
	private FavoriteDAOImpl() {
		dbUtil = DBUtil.getInstance();
	}
	
	public static FavoriteDAO getFavoriteDao() {
		return favoriteDao;
	}

	@Override
	public ArrayList<Favorite> selectFavoriteList(int userNo) throws SQLException {
		ArrayList<Favorite> search = new ArrayList<Favorite>();
		Favorite favorite = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT no, dongcode, gu, dong ";
		sql += "FROM favorite WHERE user_no = ? ";
		
		try {
			conn = dbUtil.getConnection();
			System.out.println(conn.getClass().getName());
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userNo);
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				favorite = new Favorite(rs.getInt("no"), rs.getString("dongcode"), rs.getString("gu"), rs.getString("dong"));
				search.add(favorite);
			}
			return search;
		} finally {
			dbUtil.close(rs, pstmt, conn);
		}
	}

	@Override
	public String selectFavorite(int userNo, String favoriteCity, String favoriteGugun, String favoriteDong)
			throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT dongcode ";
		sql += "FROM favorite WHERE user_no = ? AND gu = ? AND dong = ? ";
		
		try {
			conn = dbUtil.getConnection();
			System.out.println(conn.getClass().getName());
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userNo);
			pstmt.setString(2, favoriteGugun);
			pstmt.setString(3, favoriteDong);
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}
		} finally {
			dbUtil.close(rs, pstmt, conn);
		}
		return null;
	}
	
	@Override
	public String selectDongcode(String favoriteCity, String favoriteGugun, String favoriteDong) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT dongcode ";
		sql += "FROM dongcode WHERE city = ? AND gugun = ? AND dong = ? ";
		
		try {
			conn = dbUtil.getConnection();
			System.out.println(conn.getClass().getName());
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, favoriteCity);
			pstmt.setString(2, favoriteGugun);
			pstmt.setString(3, favoriteDong);
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}
		} finally {
			dbUtil.close(rs, pstmt, conn);
		}
		return null;
	}

	@Override
	public boolean insertFavorite(int userNo, String dongcode, String favoriteGugun, String favoriteDong) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO favorite(user_no, dongcode, gu, dong) ";
		sql += "VALUES(?, ?, ?, ?)";
		
		try {
			conn = dbUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userNo);
			pstmt.setString(2, dongcode);
			pstmt.setString(3, favoriteGugun);
			pstmt.setString(4, favoriteDong);
			return pstmt.executeUpdate() > 0;
		} finally {
			dbUtil.close(pstmt, conn);
		}
	}

	@Override
	public boolean deleteFavorite(int userNo, String favoriteGugun, String favoriteDong) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM favorite WHERE user_no = ? AND gu = ? AND dong = ? ";
		
		try {
			conn = dbUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userNo);
			pstmt.setString(2, favoriteGugun);
			pstmt.setString(3, favoriteDong);
			
			return pstmt.executeUpdate()>0;
		} finally {
			dbUtil.close(pstmt, conn);
		}
	}

}
