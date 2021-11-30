package com.ssafy.happyhouse.model.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostComment {

	private int no;
	private int post_no;
	private String writer;
	private String title;
	private String content;
	private String regDate;
	@Override
	public String toString() {
		return "PostComment [no=" + no + ", post_no=" + post_no + ", writer=" + writer + ", title=" + title
				+ ", content=" + content + ", regDate=" + regDate + "]";
	}
	
	
	
}
