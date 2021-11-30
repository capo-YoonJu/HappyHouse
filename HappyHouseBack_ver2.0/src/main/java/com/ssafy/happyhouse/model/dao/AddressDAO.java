package com.ssafy.happyhouse.model.dao;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.happyhouse.model.dto.AddressCode;
import com.ssafy.happyhouse.model.dto.SDot;

public interface AddressDAO {

	List<AddressCode> getSido() throws SQLException;
	List<AddressCode> getGugunInSido(String sido) throws SQLException;
	List<AddressCode> getGugun() throws SQLException;
	List<AddressCode> getDongInGugun(String gugun) throws SQLException;
	AddressCode getHDong(String dongCode) throws SQLException;
	List<SDot> getSerialNoInGugun(String gugun) throws SQLException;
	
}
