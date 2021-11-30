package com.ssafy.happyhouse.model.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.ssafy.happyhouse.model.dto.House;

public interface HouseService {
	List<House> search(HashMap<String, Object> condition) throws SQLException;
	void rankDong(String dong) throws SQLException;
}