package com.ssafy.happyhouse.model.dao;

import java.util.HashMap;
import java.util.List;

import com.ssafy.happyhouse.model.dto.HouseDto;
import com.ssafy.happyhouse.model.dto.SimpleMessageDto;

public interface HouseDao {
	
	// 시/도 리스트 받기
	List<HouseDto> getSidoList();
	
	// 시/도에 맞는 구/군 리스트 받기 
	List<HouseDto> getGugunList(String sido);
	
	// 구/군에 맞는 동 리스트 받기
	List<HouseDto> getDongList(String gugun);
	
	// 시/도, 구/군, 동에 맞는 동코드 리턴 
	SimpleMessageDto getDongCode(HashMap<String, Object> map);
	
	// 동코드에 맞는 주소(시+구+동) 리턴
	SimpleMessageDto getAddress(String dongcode);
}
