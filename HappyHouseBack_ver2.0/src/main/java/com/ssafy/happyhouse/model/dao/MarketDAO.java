package com.ssafy.happyhouse.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.model.dto.Market;

@Mapper
public interface MarketDAO {
	List<Market> searchAll(String guguncode);
}
