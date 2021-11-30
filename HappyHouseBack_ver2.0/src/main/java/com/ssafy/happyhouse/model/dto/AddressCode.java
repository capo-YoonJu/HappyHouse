package com.ssafy.happyhouse.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressCode {

	private String sidoCode;
	private String sidoName;
	private String gugunCode;
	private String gugunName;
	private String dongcode;
	private String dong;
	private String hdongCode;

}
