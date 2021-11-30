package com.ssafy.happyhouse.model.dao;

import java.util.HashMap;
import java.util.List;

import com.ssafy.happyhouse.model.dto.House;

public interface HouseDAO {
	List<House> searchAll(HashMap<String, Object> condition);
	void rankDong(String dong);
}
