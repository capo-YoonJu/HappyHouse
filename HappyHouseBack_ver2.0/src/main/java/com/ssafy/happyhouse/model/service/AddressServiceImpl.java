package com.ssafy.happyhouse.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.dao.AddressDAO;
import com.ssafy.happyhouse.model.dto.AddressCode;
import com.ssafy.happyhouse.model.dto.SDot;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	AddressDAO addressDao;
	
	@Override
	public List<AddressCode> getSido() throws Exception {
		return addressDao.getSido();
	}

	@Override
	public List<AddressCode> getGugunInSido(String sido) throws Exception {
		return addressDao.getGugunInSido(sido);
	}
	
	@Override
	public List<AddressCode> getGugun() throws Exception {
		return addressDao.getGugun();
	}
	
	@Override
	public List<AddressCode> getDongInGugun(String gugun) throws Exception {
		return addressDao.getDongInGugun(gugun);
	}
	
	@Override
	public AddressCode getHDong(String dongCode) throws Exception {
		return addressDao.getHDong(dongCode);
	}

	@Override
	public List<SDot> getSerialNoInGugun(String gugun) throws Exception {
		return addressDao.getSerialNoInGugun(gugun);
	}

}
