package com.ssafy.happyhouse.model.service;

import java.util.List;

import com.ssafy.happyhouse.model.dto.AddressCode;
import com.ssafy.happyhouse.model.dto.SDot;

public interface AddressService {

	List<AddressCode> getSido() throws Exception;
	List<AddressCode> getGugunInSido(String sido) throws Exception;
	List<AddressCode> getGugun() throws Exception;
	List<AddressCode> getDongInGugun(String gugun) throws Exception;
	AddressCode getHDong(String dongCode) throws Exception;
	List<SDot> getSerialNoInGugun(String gugun) throws Exception;
}
