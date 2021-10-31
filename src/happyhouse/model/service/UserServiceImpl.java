package happyhouse.model.service;

import java.sql.SQLException;

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
	public int idCheck(String checkId) throws SQLException {
		if (userDao.selectUserId(checkId) != null) return 1;
		else return 0;
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
	public boolean updateUser(int userNo, User user) throws SQLException {
		return userDao.updateUser(userNo, user);

	}

	@Override
	public boolean deleteUser(String userId) throws SQLException {
		if (userDao.deleteUser(userId)) {
			System.out.println("service true");
			return true;
		} else {
			System.out.println("service false");
			return false;
		}

	}

}
