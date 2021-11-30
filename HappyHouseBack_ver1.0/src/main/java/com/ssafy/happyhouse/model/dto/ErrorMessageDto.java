package com.ssafy.happyhouse.model.dto;

public class ErrorMessageDto {
	
	String message;

	public ErrorMessageDto() {
		super();
	}

	public ErrorMessageDto(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
