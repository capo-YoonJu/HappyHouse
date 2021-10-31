package happyhouse.model.dao;

import java.sql.SQLException;

import happyhouse.model.dto.*;

public interface UserDAO {
	
	boolean insertUser(User user) throws SQLException;
	
	int selectUser(String userId, String password) throws SQLException;
	
	String selectUserId(String userId) throws SQLException;
	
	User selectUserInfo(String userId) throws SQLException;
	
	boolean updateUser(int userNo, User user) throws SQLException;
	
	boolean deleteUser(String userId) throws SQLException;
}
