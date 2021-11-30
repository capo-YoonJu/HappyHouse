package com.ssafy.happyhouse.model.service;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.dao.UserDao;
import com.ssafy.happyhouse.model.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {

	private SqlSession sqlSession;
	@Autowired
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public UserDto insertUser(UserDto user) {
		sqlSession.getMapper(UserDao.class).insertUser(user);
		return sqlSession.getMapper(UserDao.class).selectUserInfo(user.getId());
	}

	@Override
	public UserDto selectUser(HashMap<String, Object> idPw) {
		return sqlSession.getMapper(UserDao.class).selectUser(idPw);
	}
	
	@Override
	public UserDto selectUserInfo(String id) {
		return sqlSession.getMapper(UserDao.class).selectUserInfo(id);
	}

	@Override
	public UserDto updateUser(HashMap<String, Object> idUser) {
		UserDto user = (UserDto) idUser.get("user");
		
		sqlSession.getMapper(UserDao.class).updateUser(idUser);
		sqlSession.getMapper(UserDao.class).updateFavoritesId(idUser);
		return sqlSession.getMapper(UserDao.class).selectUserInfo(user.getId());
	}

	@Override
	public boolean deleteUser(String id) {
		sqlSession.getMapper(UserDao.class).deleteFavoriteById(id);
		return sqlSession.getMapper(UserDao.class).deleteUser(id);
	}

	@Override
	public UserDto insertFavorite(HashMap<String, Object> idFavorite) {
		sqlSession.getMapper(UserDao.class).insertFavorite(idFavorite);
		return sqlSession.getMapper(UserDao.class).selectFavorites(idFavorite.get("id").toString());
	}

	@Override
	public UserDto selectFavorites(String id) {
		return sqlSession.getMapper(UserDao.class).selectFavorites(id);
	}

	@Override
	public UserDto deleteFavorite(HashMap<String, Object> idDongcode) {
		sqlSession.getMapper(UserDao.class).deleteFavorite(idDongcode);
		return sqlSession.getMapper(UserDao.class).selectFavorites(idDongcode.get("id").toString());
	}

}
