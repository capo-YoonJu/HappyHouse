package happyhouse.model.service;

import java.sql.SQLException;

import happyhouse.model.dto.*;

public interface UserService {
	
	int idCheck(String checkId) throws SQLException;
	
	int login(String userId, String password) throws SQLException;

	boolean registerUser(User user) throws SQLException;
	
	User searchUserInfo(String userId) throws SQLException;

	boolean updateUser(int userNo, User user) throws SQLException;
	
	boolean deleteUser(String userId) throws SQLException;
	
}