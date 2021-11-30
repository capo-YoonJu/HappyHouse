package com.ssafy.happyhouse.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ssafy.happyhouse.model.dto.ErrorMessageDto;

@RestControllerAdvice
public class RestGlobalExceptionAdvice {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessageDto> handleException(Exception e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessageDto("처리 중 문제가 발생했습니다."));
	}
}
