package com.ssafy.happyhouse.model.dto;

public class FavoriteDto {
	
	String userId;
	String dongcode;
	String sido;
	String gugun;
	String dong;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDongcode() {
		return dongcode;
	}
	
	public void setDongcode(String dongcode) {
		this.dongcode = dongcode;
	}
	
	public String getSido() {
		return sido;
	}
	
	public void setSido(String sido) {
		this.sido = sido;
	}
	
	public String getGugun() {
		return gugun;
	}
	
	public void setGugun(String gugun) {
		this.gugun = gugun;
	}
	
	public String getDong() {
		return dong;
	}
	
	public void setDong(String dong) {
		this.dong = dong;
	}

	@Override
	public String toString() {
		return "FavoriteDto [userId=" + userId + ", dongcode=" + dongcode + ", sido=" + sido + ", gugun=" + gugun
				+ ", dong=" + dong + "]";
	}
	
}
