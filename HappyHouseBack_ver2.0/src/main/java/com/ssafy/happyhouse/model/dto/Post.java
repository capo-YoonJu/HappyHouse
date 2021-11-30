package com.ssafy.happyhouse.model.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
	
	private int no;
	private String type;
	private String writer;
	private String title;
	private String content;
	private List<String> tags;
	private String regDate;
	
}
