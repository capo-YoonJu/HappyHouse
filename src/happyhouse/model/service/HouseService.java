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
 8. 회원 탈퇴  (컨트롤러에서 처리)
 
 // 조회
 9. 동 조회
 10. 실거래가 조회
 
 // 관리자?
 11. 회원 조회
 */

public interface HouseService {
	List<House> search(String sido, String gugun, String dong, String aptName) throws SQLException;
}