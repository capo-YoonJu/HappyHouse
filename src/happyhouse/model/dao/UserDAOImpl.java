package happyhouse.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import happyhouse.model.dto.*;
import happyhouse.util.DBUtil;

//DAO : DataBase Access Object
public class UserDAOImpl implements UserDAO {
	
	private static UserDAO userDao = new UserDAOImpl();

	private DBUtil dbUtil;
	
	private UserDAOImpl() {
		dbUtil = DBUtil.getInstance();
	}
	
	public static UserDAO getUserDao() {
		return userDao;
	}

	@Override
	public boolean insertUser(User user) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO user(id, password, name, addres, tel) ";
		sql += "VALUES(?, ?, ?, ?, ?)";
		
		try {
			conn = dbUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, user.getUserPassword());
			pstmt.setString(3, user.getUserName());
			pstmt.setString(4, user.getUserAddress());
			pstmt.setString(5, user.getUserTel());
			return pstmt.executeUpdate() > 0;
		} finally {
			dbUtil.close(pstmt, conn);
		}
	}

	@Override
	public int selectUser(String userId, String password) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT no ";
		sql += "FROM user WHERE id = ? AND password = ?";
		
		try {
			conn = dbUtil.getConnection();
			System.out.println(conn.getClass().getName());
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setString(2, password);
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} finally {
			dbUtil.close(rs, pstmt, conn);
		}
		return -1;
	}

	@Override
	public String selectUserId(String userId) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT id ";
		sql += "FROM user WHERE id = ? ";
		
		try {
			conn = dbUtil.getConnection();
			System.out.println(conn.getClass().getName());
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			
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
	public User selectUserInfo(String userId) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT id, password, name, addres, tel ";
		sql += "FROM user WHERE id = ?";
		
		try {
			conn = dbUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return new User(rs.getString("id"), rs.getString("password"), rs.getString("name"), rs.getNString("addres"), rs.getNString("tel"));
			}
		} finally {
			dbUtil.close(rs, pstmt, conn);
		}
		return null;
	}

	@Override
	public boolean updateUser(String userId, User user) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "UPDATE user SET id = ?, password = ?, name = ?, addres = ?, tel = ? ";
		sql += "WHERE id = ?";
		
		try {
			conn = dbUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, user.getUserPassword());
			pstmt.setString(3, user.getUserName());
			pstmt.setString(4, user.getUserAddress());
			pstmt.setString(5, user.getUserTel());
			pstmt.setString(6, userId);
			System.out.println(pstmt);
			return pstmt.executeUpdate()>0;
		} finally {
			dbUtil.close(pstmt, conn);
		}
	}

	@Override
	public boolean deleteUser(String userId) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM user WHERE id = ?";
		
		try {
			conn = dbUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			
			return pstmt.executeUpdate()>0;
		} finally {
			dbUtil.close(pstmt, conn);
		}
	}

}
