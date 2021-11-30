package com.ssafy.happyhouse.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.model.dto.Hospital;

@Mapper
public interface HospitalDAO {
	List<Hospital> searchAll(String guguncode);
}
