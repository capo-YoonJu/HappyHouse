package com.ssafy.happyhouse.model.service;

import java.util.HashMap;
import java.util.List;

import com.ssafy.happyhouse.model.dto.HouseDto;
import com.ssafy.happyhouse.model.dto.SimpleMessageDto;

public interface HouseService {

	List<HouseDto> getSidoList();
	List<HouseDto> getGugunList(String sido);
	List<HouseDto> getDongList(String gugun);
	SimpleMessageDto getDongCode(HashMap<String, Object> map);
	SimpleMessageDto getAddress(String dongcode);
}
