package happyhouse.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import happyhouse.model.dto.Favorite;
import happyhouse.model.dto.PageInfo;
import happyhouse.model.dto.User;
import happyhouse.model.service.FavoriteService;
import happyhouse.model.service.FavoriteServiceImpl;
import happyhouse.model.service.UserService;
import happyhouse.model.service.UserServiceImpl;

public class UserController implements Controller {

	private UserService userService = UserServiceImpl.getUserService();
	
	private FavoriteService favoriteService = FavoriteServiceImpl.getUserService();
	
	@Override
	public Object process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String subDoUrl = request.getServletPath().substring(5);
		String subUrl = subDoUrl.substring(0, subDoUrl.lastIndexOf(".do"));
		
		// 로그인
		if(subUrl.equals("/login")) return login(request, response);
		// 로그아웃
		else if(subUrl.equals("/logout")) return logout(request, response);
		// 회원가입
		else if(subUrl.equals("/register")) return register(request, response);
		// 회원 정보 조회
		else if(subUrl.equals("/info")) return info(request, response);
		// 회원 정보 수정
		else if(subUrl.equals("/update")) return update(request, response);
		// 회원 탈퇴
		else if(subUrl.equals("/delete")) return delete(request, response);
		
		return null;
	}

	protected PageInfo login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = request.getParameter("userId");
		String userPassword = request.getParameter("userPassword");
		
		// 유효성 검사 실패
		ArrayList<String> errorMessages = new ArrayList<String>();
		if (userId==null || userId.trim().length()==0) {
			errorMessages.add("아이디가 올바르지 않습니다.");
		}
		if (userPassword==null || userPassword.trim().length()==0) {
			errorMessages.add("비밀번호가 올바르지 않습니다.");
		}
		if (errorMessages.size()>0) {
			request.setAttribute("loginErrorMsgs", errorMessages);
			return new PageInfo("/index.jsp", true);
		}
		
		// 유효성 검사 성공
		int userNo = userService.login(userId, userPassword);
		
		if (userNo!=-1) {
			HttpSession session = request.getSession();
			session.setAttribute("user", new User(userNo, userId));
			return new PageInfo("/index.jsp", false);
		} else {
			// 로그인 실패 : 로그인 페이지로 이동
			errorMessages.add("아이디 또는 비밀번호가 올바르지 않습니다.");
			request.setAttribute("loginErrorMsgs", errorMessages);
			return new PageInfo("/index.jsp", true);
		}
	}

	protected PageInfo logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getSession().invalidate();
		return new PageInfo("/index.jsp", false);
	}
	
	protected PageInfo register(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String userId = request.getParameter("userId");
		String userPassword = request.getParameter("userPassword");
		String userName = request.getParameter("userName");
		String userAddress = request.getParameter("userAddress");
		String userTel = request.getParameter("userTel");
		
		boolean flag = userService.registerUser(new User(userId, userPassword, userName, userAddress, userTel));
		
		if (flag) {
			// 회원가입 성공
			return login(request, response);
		} else {
			// 회원가입 실패
			ArrayList<String> errorMessages = new ArrayList<String>();
			errorMessages.add("입력한 아이디를 가진 회원이 이미 존재하여 회원가입에 실패했습니다.");
			request.setAttribute("errorMessages", errorMessages);
			return new PageInfo("/index.jsp", true);
		}
	}
	
	protected PageInfo info(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Object user = session.getAttribute("user");
		
		int userNo = -1;
		String userId = "";
		if (user instanceof User) {
			userNo = ((User) user).getUserno();
			userId = ((User) user).getUserId();
			
			User userInfo = userService.searchUserInfo(userId);
			ArrayList<Favorite> favoriteList = favoriteService.searchFavorite(userNo);
			
			if (user!=null) {
				// 회원 존재
				request.setAttribute("user", userInfo);
				request.setAttribute("favoriteList", favoriteList);
				return new PageInfo("/user-info.jsp", true);
			}
		}
		
		// 회원 정보 조회 실패
		ArrayList<String> errorMessages = new ArrayList<String>();
		errorMessages.add("회원 정보 조회에 실패했습니다.");
		request.setAttribute("errorMessages", errorMessages);
		return new PageInfo("/index.jsp", true);
		
	}

	protected PageInfo update(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String curUserId = request.getParameter("curUserId");
		String userId = request.getParameter("userId");
		String userPassword = request.getParameter("userPassword");
		String userName = request.getParameter("curUserName");
		String userAddress = request.getParameter("userAddress");
		String userTel = request.getParameter("userTel");
		
		boolean flag = userService.updateUser(curUserId, new User(userId, userPassword, userName, userAddress, userTel));
		
		if (flag) {
			// 회원 정보 수정 성공
			return info(request, response);
		} else {
			// 회원 정보 수정 실패
			ArrayList<String> errorMessages = new ArrayList<String>();
			errorMessages.add("회원 정보 수정에 실패했습니다.");
			request.setAttribute("errorMessages", errorMessages);
			return new PageInfo("/index.jsp", true);
		}
	}

	protected PageInfo delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = request.getParameter("userId");
		
		boolean flag = userService.deleteUser(userId);
		
		if (flag) {
			// 회원 정보 삭제 성공
			request.getSession().invalidate();
			return new PageInfo("/index.jsp", false);
		} else {
			// 회원 정보 삭제 실패
			ArrayList<String> errorMessages = new ArrayList<String>();
			errorMessages.add("회원 정보 삭제에 실패했습니다.");
			request.setAttribute("errorMessage", errorMessages);
			return new PageInfo("/index.jsp", true);
		}
	}
	
}
