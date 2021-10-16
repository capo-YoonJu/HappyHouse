package happyhouse.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import happyhouse.model.dao.UserDAO;
import happyhouse.model.dao.UserDAOImpl;
import happyhouse.model.dto.Favorite;
import happyhouse.model.dto.PageInfo;
import happyhouse.model.dto.User;
import happyhouse.model.dao.FavoriteDAO;
import happyhouse.model.dao.FavoriteDAOImpl;
import happyhouse.model.service.FavoriteService;
import happyhouse.model.service.FavoriteServiceImpl;
import happyhouse.model.service.UserService;
import happyhouse.model.service.UserServiceImpl;

public class favoriteController implements Controller {

	private FavoriteService favoriteService = FavoriteServiceImpl.getUserService();
	
	@Override
	public Object process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String subDoUrl = request.getServletPath().substring(9);	// /favorite
		String subUrl = subDoUrl.substring(0, subDoUrl.lastIndexOf(".do"));
		
		// 관심지역 조회
		if(subUrl.equals("/info")) return info(request, response);
		// 관심지역 추가
		else if(subUrl.equals("/register")) return register(request, response);
		// 관심지역 삭제
		else if(subUrl.equals("/delete")) return delete(request, response);
		
		return null;
	}
	
	protected PageInfo info(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int userNo = Integer.parseInt(request.getParameter("userNo"));
		ArrayList<Favorite> favoriteList = favoriteService.searchFavorite(userNo);
		
		if (favoriteList!=null) {
			// 관심지역 존재
			request.setAttribute("favoriteList", favoriteList);
			return new PageInfo("/favorite.jsp", true);
		} else {
			// 관심지역 조회 실패
			request.setAttribute("errorMessage", "설정한 관심 지역 정보가 없습니다.");
			return new PageInfo("/favorite.jsp", true);
		}
	}

	protected PageInfo register(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		int userNo = Integer.parseInt(request.getParameter("userNo"));
		String favoriteCity = request.getParameter("favoriteCity");
		String favoriteGugun = request.getParameter("favoriteGugun");
		String favoriteDong = request.getParameter("favoriteDong");
		
		boolean flag = favoriteService.registerFavorite(userNo, favoriteCity, favoriteGugun, favoriteDong);
		
		if (flag) {
			// 관심지역 설정 성공
			return info(request, response);
		} else {
			// 관심지역 설정 실패
			request.setAttribute("errorMessages", "관심지역 설정에 실패했습니다.");
			return new PageInfo("/favorite.jsp", true);
		}
	}

	protected PageInfo delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int userNo = Integer.parseInt(request.getParameter("userNo"));
		String favoriteGugun = request.getParameter("favoriteGugun");
		String favoriteDong = request.getParameter("favoriteDong");
		
		boolean flag = favoriteService.deleteFavorite(userNo, favoriteGugun, favoriteDong);
		
		if (flag) {
			// 관심지역 삭제 성공
			return info(request, response);
		} else {
			// 관심지역 삭제 실패
			request.setAttribute("errorMessage", "관심지역 삭제에 실패했습니다.");
			return new PageInfo("/favorite.jsp", true);
		}
	}
	
}
