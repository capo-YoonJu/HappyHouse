package com.ssafy.happyhouse.model.service;

import java.util.HashMap;

import com.ssafy.happyhouse.model.dto.UserDto;

public interface UserService {

	UserDto insertUser(UserDto user);
	
	UserDto selectUser(HashMap<String, Object> idPw);
	
	UserDto selectUserInfo(String id);
	
	UserDto updateUser(HashMap<String, Object> idUser);
	
	boolean deleteUser(String id);

	UserDto insertFavorite(HashMap<String, Object> idFavorite);
	
	UserDto selectFavorites(String id);
	
	UserDto deleteFavorite(HashMap<String, Object> idDongcode);
	
}
