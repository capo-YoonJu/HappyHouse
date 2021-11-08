package com.ssafy.happyhouse.model.dao;

import java.util.HashMap;

import org.springframework.transaction.annotation.Transactional;

import com.ssafy.happyhouse.model.dto.UserDto;

@Transactional
public interface UserDao {

	boolean insertUser(UserDto user);
	
	UserDto selectUser(HashMap<String, Object> idPw);
	
	UserDto selectUserInfo(String id);
	
	boolean updateUser(HashMap<String, Object> idUser);
	
	boolean deleteUser(String id);
	
	boolean insertFavorite(HashMap<String, Object> idFavorite);
	
	UserDto selectFavorites(String id);
	
	boolean updateFavoritesId(HashMap<String, Object> originUpdateId);
	
	boolean deleteFavorite(HashMap<String, Object> idDongcode);
	
	boolean deleteFavoriteById(String id);
	
}
