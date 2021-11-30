package com.ssafy.happyhouse.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SDot {
	
	private String serialNo;
	private String gugunName;
	private String dongName;
	private String gugunCode;
	private String dongCode;
	
}
