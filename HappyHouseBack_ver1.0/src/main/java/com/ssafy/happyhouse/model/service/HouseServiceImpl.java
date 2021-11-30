package com.ssafy.happyhouse.model.service;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.dao.HouseDao;
import com.ssafy.happyhouse.model.dto.HouseDto;
import com.ssafy.happyhouse.model.dto.SimpleMessageDto;

@Service
public class HouseServiceImpl implements HouseService {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<HouseDto> getSidoList(){
		return sqlSession.getMapper(HouseDao.class).getSidoList();
	}

	@Override
	public List<HouseDto> getGugunList(String sido){
		return sqlSession.getMapper(HouseDao.class).getGugunList(sido);
	}

	@Override
	public List<HouseDto> getDongList(String gugun){
		return sqlSession.getMapper(HouseDao.class).getDongList(gugun);
	}

	@Override
	public SimpleMessageDto getDongCode(HashMap<String, Object> map){
		return sqlSession.getMapper(HouseDao.class).getDongCode(map);
	}

	@Override
	public SimpleMessageDto getAddress(String dongcode) {
		return sqlSession.getMapper(HouseDao.class).getAddress(dongcode);
	}

}
