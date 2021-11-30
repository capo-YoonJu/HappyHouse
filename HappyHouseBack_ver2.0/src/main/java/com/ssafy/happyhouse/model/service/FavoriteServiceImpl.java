package com.ssafy.happyhouse.model.service;

import java.sql.SQLException;
import java.util.ArrayList;

import com.ssafy.happyhouse.model.dao.FavoriteDAO;
import com.ssafy.happyhouse.model.dto.Favorite;

public class FavoriteServiceImpl implements FavoriteService {
	
	// 싱글턴
	private FavoriteDAO favoriteDao;
	
	@Override
	public ArrayList<Favorite> searchFavorite(int userNo) throws SQLException {
		return favoriteDao.selectFavoriteList(userNo);
	}

	@Override
	public boolean registerFavorite(int userNo, String favoriteCity, String favoriteGugun, String favoriteDong)
			throws SQLException {
		// 관심지역 존재 여부 확인
		if (favoriteDao.selectFavorite(userNo, favoriteCity, favoriteGugun, favoriteDong) != null) return false;
		
		String dongcode = favoriteDao.selectDongcode(favoriteCity, favoriteGugun, favoriteDong);
	
		// 관심지역을 DB에 저장
		return favoriteDao.insertFavorite(userNo, dongcode, favoriteGugun, favoriteDong);
	}

	@Override
	public boolean deleteFavorite(int userNo, String favoriteGugun, String favoriteDong) throws SQLException {
		return favoriteDao.deleteFavorite(userNo, favoriteGugun, favoriteDong);
	}
	
	@Override
	public boolean resetFavorites(int userNo) throws SQLException {
		int favoriteCount = favoriteDao.selectFavoriteCount(userNo);
		// 관심지역 개수 조회 실패
		if (favoriteCount==-1) return false;
		// 관심지역 개수 0개는 바로 true 반환
		else if (favoriteCount==0) return true;
		// 관심지역 개수 1개 이상이면 모두 삭제
		else return favoriteDao.deleteAllFavorites(userNo);
	}

}
