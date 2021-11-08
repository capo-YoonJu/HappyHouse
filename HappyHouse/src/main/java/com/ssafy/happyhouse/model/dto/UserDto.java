package com.ssafy.happyhouse.model.dto;

import java.util.List;

public class UserDto {
	String id;
	String pw;
	String name;
	String email;
	String address;
	List<FavoriteDto> favorites;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getPw() {
		return pw;
	}
	
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}

	public List<FavoriteDto> getFavorites() {
		return favorites;
	}

	public void setFavorites(List<FavoriteDto> favorites) {
		this.favorites = favorites;
	}

	@Override
	public String toString() {
		return "UserDto [id=" + id + ", pw=" + pw + ", name=" + name + ", email=" + email + ", address=" + address
				+ ", favorites=" + favorites + "]";
	}
	
}
