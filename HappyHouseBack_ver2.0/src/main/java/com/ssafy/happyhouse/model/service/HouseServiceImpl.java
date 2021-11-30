package com.ssafy.happyhouse.model.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.dao.HouseDAO;
import com.ssafy.happyhouse.model.dto.House;

@Service
public class HouseServiceImpl implements HouseService {
	
	private HouseDAO houseDAO;

	@Autowired
	public void setHouseDAO(HouseDAO houseDAO) {
		this.houseDAO = houseDAO;
	}

	@Override
	public List<House> search(HashMap<String, Object> condition) throws SQLException{
		return houseDAO.searchAll(condition);
	}
	
	@Override
	public void rankDong(String dong) throws SQLException{
		houseDAO.rankDong(dong);
	}
	
}
