package happyhouse.model.service;

import java.sql.SQLException;
import java.util.List;

import happyhouse.model.dto.*;

/*
 // 사용자
 1. 로그인
 2. 로그아웃 (컨트롤러에서 처리)
 3. 회원가입
 4. 회원정보 수정
 5. 관심지역 등록
 6. 관심지역 삭제
 7. 관심지역 조회
 8. 회원 탈퇴
 
 // 조회
 9. 동 조회
 10. 실거래가 조회
 
 // 관리자?
 11. 회원 조회
 */

public interface UserService {
	// 로그인
	int login(String userId, String password) throws SQLException;

	boolean registerUser(User user) throws SQLException;
	
	User searchUserInfo(String userId) throws SQLException;

	boolean updateUser(String userId, User user) throws SQLException;
	
	boolean deleteUser(String userId) throws SQLException;
	
//	boolean register_Favorite(User user) throws SQLException;
//
//	boolean update_Favorite(User user) throws SQLException;
//	
//	List<Favorite> show_FavoriteList(User user) throws SQLException;
}