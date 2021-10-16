package happyhouse.model.service;

import java.sql.SQLException;
import java.util.List;

import happyhouse.model.dao.*;
import happyhouse.model.dto.*;

public class UserServiceImpl implements UserService {
	
	// 싱글턴
	private static UserService userService = new UserServiceImpl();
	private UserDAO userDao;
	
	private UserServiceImpl() {
		userDao = UserDAOImpl.getUserDao();
	}
	
	public static UserService getUserService() {
		return userService;
	}

	@Override
	public int login(String userId, String password) throws SQLException {
		return userDao.selectUser(userId, password);
	}

	@Override
	public boolean registerUser(User user) throws SQLException {
		// 회원 존재 여부 확인
		if (userDao.selectUserId(user.getUserId()) != null) return false;
	
		// 회원정보를 DB에 저장
		return userDao.insertUser(user);
	}

	@Override
	public User searchUserInfo(String userId) throws SQLException {
		return userDao.selectUserInfo(userId);
	}

	@Override
	public boolean updateUser(String userId, User user) throws SQLException {
		return userDao.updateUser(userId, user);

	}

	@Override
	public boolean deleteUser(String userId) throws SQLException {
		return userDao.deleteUser(userId);

	}

}
