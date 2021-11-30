package com.ssafy.happyhouse.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class House {
	@NonNull
	private String sido;
	@NonNull
	private String gugun;
	@NonNull
	private String dong;
	@NonNull
	private String aptName;
	@NonNull
	private String dealAmount;
	@NonNull
	private String dealYear;
	@NonNull
	private String dealMonth;
	@NonNull
	private String dealDay;
	@NonNull
	private String buildYear;
	@NonNull
	private String jibun;
	@NonNull
	private String area;
	@NonNull
	private String type;
	@NonNull
	private String lat;
	@NonNull
	private String lng;
}
