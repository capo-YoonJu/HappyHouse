package happyhouse.model.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import happyhouse.model.dto.Favorite;

public interface FavoriteDAO {
	
	ArrayList<Favorite> selectFavoriteList(int userNo) throws SQLException;
	
	String selectFavorite(int userNo, String favoriteCity, String favoriteGugun, String favoriteDong) throws SQLException;
	
	String selectDongcode(String favoriteCity, String favoriteGugun, String favoriteDong) throws SQLException;
	
	boolean insertFavorite(int userNo, String dongcode, String favoriteGugun, String favoriteDong) throws SQLException;
	
	boolean deleteFavorite(int userNo, String favoriteGugun, String favoriteDong) throws SQLException;
	
}
