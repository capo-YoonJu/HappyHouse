package happyhouse.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import happyhouse.model.dto.DataInfo;
import happyhouse.model.dto.PageInfo;
import happyhouse.model.dto.User;
import happyhouse.model.service.FavoriteService;
import happyhouse.model.service.FavoriteServiceImpl;
import happyhouse.model.service.UserService;
import happyhouse.model.service.UserServiceImpl;

public class UserController implements Controller {

	private UserService userService = UserServiceImpl.getUserService();
	
	private FavoriteService favoriteService = FavoriteServiceImpl.getFavoriteService();
	
	@Override
	public Object process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String subDoUrl = request.getServletPath().substring(5);
		String subUrl = subDoUrl.substring(0, subDoUrl.lastIndexOf(".do"));
		
		// 아이디 중복 체크
		if (subUrl.equals("/idCheck")) return idCheck(request, response);
		// 로그인
		else if(subUrl.equals("/login")) return login(request, response);
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
	
	public DataInfo idCheck(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String checkId = request.getParameter("ckid");
		int cnt = userService.idCheck(checkId);		// 0 or 1
		
		// 아이디 체크 성공
		request.setAttribute("idcnt", cnt);
		return new DataInfo("application/json;charset=utf-8", cnt);
	}

	public PageInfo login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = request.getParameter("userId");
		String userPassword = request.getParameter("userPassword");
		
		// 유효성 검사 실패
		String errorMessage = null;
		if (userId==null || userId.trim().length()==0) {
			errorMessage = "아이디가 올바르지 않습니다.";
		}
		if (userPassword==null || userPassword.trim().length()==0) {
			errorMessage = "비밀번호가 올바르지 않습니다.";
		}
		if (errorMessage!=null) {
			request.setAttribute("errorMessage", errorMessage);
			return new PageInfo("/index.jsp", true);
		}
		
		// 유효성 검사 성공
		int userNo = userService.login(userId, userPassword);
		if (userNo!=-1) {
			// 로그인 성공
			HttpSession session = request.getSession();
			session.setAttribute("userSession", new User(userNo, userId));
			return new PageInfo("/index.jsp", false);
		} else {
			// 로그인 실패
			errorMessage = "아이디 또는 비밀번호가 올바르지 않습니다.";
			request.setAttribute("errorMessage", errorMessage);
			return new PageInfo("/index.jsp", true);
		}
	}

	public PageInfo logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getSession().invalidate();
		return new PageInfo("/index.jsp", false);
	}
	
	public PageInfo register(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
			request.setAttribute("errorMessage", "입력한 아이디를 가진 회원이 이미 존재하여 회원가입에 실패했습니다.");
			return new PageInfo("/index.jsp", true);
		}
	}
	
	public PageInfo info(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Object user = session.getAttribute("userSession");
		
		int userNo = -1;
		String userId = "";
		if (user instanceof User) {
			userNo = ((User) user).getUserno();
			userId = ((User) user).getUserId();
			
			User userInfo = userService.searchUserInfo(userId);
			userInfo.setUserFavList(favoriteService.searchFavorite(userNo));
			
			if (user!=null) {
				// 회원 존재
				request.setAttribute("user", userInfo);
				return new PageInfo("/user-info.jsp", true);
			}
		}
		request.setAttribute("errorMessage", "로그인 이후 사용하실 수 있습니다.");
		return new PageInfo("/index.jsp", true);
	}

	public PageInfo update(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String curUserId = request.getParameter("curUserId");
		String userId = request.getParameter("userId");
		String userPassword = request.getParameter("userPassword");
		String userName = request.getParameter("curUserName");
		String userAddress = request.getParameter("userAddress");
		String userTel = request.getParameter("userTel");
		
		HttpSession session = request.getSession();
		Object user = session.getAttribute("userSession");
		int userNo = -1;
		if (user instanceof User) {
			userNo = ((User) user).getUserno();
		}
		
		boolean flag = userService.updateUser(userNo, new User(userId, userPassword, userName, userAddress, userTel));
		
		if (flag) {
			// 회원 정보 수정 성공
			session.setAttribute("userSession", new User(userNo, userId));
			request.setAttribute("successMessage", "회원 정보가 수정되었습니다.");
			return info(request, response);
		} 
		// 회원 정보 수정 실패
		request.setAttribute("errorMessage", "회원 정보 수정에 실패했습니다.");
		return new PageInfo("/index.jsp", true);
	}

	public PageInfo delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Object user = session.getAttribute("userSession");
		
		int userNo = -1;
		String userId = "";
		
		// 세션에 유효한 회원 정보 저장된 상태
		if (user instanceof User) {
			userNo = ((User) user).getUserno();
			userId = ((User) user).getUserId();
			
			boolean favoriteFlag = favoriteService.resetFavorites(userNo);
			if (favoriteFlag) {
				
				// 회원의 관심지역 정보 삭제 성공
				boolean userFlag = userService.deleteUser(userId);
				if (userFlag) {
					
					// 회원 삭제 성공
					request.getSession().invalidate();
					request.setAttribute("successMessage", "회원 정보가 정상적으로 삭제되었습니다. 다음에 또 만나요!");
					return new PageInfo("/index.jsp", true);
				}
			}
		}
		// 회원 정보 삭제 실패
		request.setAttribute("errorMessage", "회원 정보 삭제에 실패했습니다.");
		return new PageInfo("/index.jsp", true);
	}
	
}
