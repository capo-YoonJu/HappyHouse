package com.ssafy.happyhouse.model.dto;

import java.util.List;

public class User {

	private String userId;
	private String userPassword;
	private String userName;
	private String userAddress;
	private String userTel;
	private List<Favorite> userFavList;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	public String getUserTel() {
		return userTel;
	}
	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}
	public List<Favorite> getUserFavList() {
		return userFavList;
	}
	public void setUserFavList(List<Favorite> userFavList) {
		this.userFavList = userFavList;
	}

}
