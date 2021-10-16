package happyhouse.model.service;

import java.sql.SQLException;
import java.util.ArrayList;

import happyhouse.model.dao.*;
import happyhouse.model.dto.*;

public class FavoriteServiceImpl implements FavoriteService {
	
	// 싱글턴
	private static FavoriteService favoriteService = new FavoriteServiceImpl();
	private FavoriteDAO favoriteDao;
	
	private FavoriteServiceImpl() {
		favoriteDao = FavoriteDAOImpl.getFavoriteDao();
	}
	
	public static FavoriteService getUserService() {
		return favoriteService;
	}

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

}
