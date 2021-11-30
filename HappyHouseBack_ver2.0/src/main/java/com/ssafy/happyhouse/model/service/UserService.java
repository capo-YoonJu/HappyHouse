package com.ssafy.happyhouse.model.service;

import com.ssafy.happyhouse.model.dto.User;

public interface UserService {
	
	boolean idCheck(String id);

	boolean registerUser(User user);
	
	User getUser(String id);
	
	User login(User user);

	boolean updateUser(User user);
	
	boolean deleteUser(String id);
	
}