package com.ssafy.happyhouse.model.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.dao.UserDAO;
import com.ssafy.happyhouse.model.dto.User;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDAO userDao;

	@Override
	public boolean idCheck(String id) {
		if (userDao.selectUserById(id) == null) return false;
		return true;
	}

	@Override
	public boolean registerUser(User user) {
		return userDao.insertUser(user);
	}

	@Override
	public User getUser(String id) {
		return userDao.selectUserById(id);
	}

	@Override
	public User login(User user) {
		return userDao.selectUser(user);
	}

	@Override
	public boolean updateUser(User user) {
		return userDao.updateUser(user);
	}

	@Override
	public boolean deleteUser(String id) {
		return userDao.deleteUser(id);
	}

}
