package com.ssafy.happyhouse.model.dao;

import com.ssafy.happyhouse.model.dto.User;

public interface UserDAO {
	
	boolean insertUser(User user);
	
	User selectUserById(String userId);
	
	User selectUser(User user);
	
	boolean updateUser(User user);
	
	boolean deleteUser(String userId);
}
