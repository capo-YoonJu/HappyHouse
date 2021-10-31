package happyhouse.model.service;

import java.sql.SQLException;
import java.util.ArrayList;

import happyhouse.model.dto.*;

public interface FavoriteService {
	
	ArrayList<Favorite> searchFavorite(int userNo) throws SQLException;

	boolean registerFavorite(int userNo, String favoriteCity, String favoriteGugun, String favoriteDong) throws SQLException;
	
	boolean deleteFavorite(int userNo, String favoriteGugun, String favoriteDong) throws SQLException;
	
	boolean resetFavorites(int userNo) throws SQLException;
	
}