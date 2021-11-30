package com.ssafy.happyhouse.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.dto.HouseDto;
import com.ssafy.happyhouse.model.dto.SimpleMessageDto;
import com.ssafy.happyhouse.model.service.HouseService;

@RestController
@RequestMapping("/house")
public class HouseController {

	@Autowired
	private HouseService houseService;
	
	@GetMapping("/sido")
	public ResponseEntity<List<HouseDto>> getSido() {
		return new ResponseEntity<List<HouseDto>>(houseService.getSidoList(), HttpStatus.OK);
	}

	@GetMapping("/gugun")
	public ResponseEntity<List<HouseDto>> getGugun(@RequestParam("sido") String sido) {
		return new ResponseEntity<List<HouseDto>>(houseService.getGugunList(sido), HttpStatus.OK);
	}
	
	@GetMapping("/dong")
	public ResponseEntity<List<HouseDto>> getDongList(@RequestParam("gugun") String gugun) {
		return new ResponseEntity<List<HouseDto>>(houseService.getDongList(gugun), HttpStatus.OK);
	}
	
	@GetMapping("/dongcode")
	public ResponseEntity<SimpleMessageDto> getDongCode(@RequestParam("sido") String sido, @RequestParam("gugun") String gugun, @RequestParam("dong") String dong) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("sido", sido);
		map.put("gugun", gugun);
		map.put("dong", dong);
		return new ResponseEntity<SimpleMessageDto>(houseService.getDongCode(map), HttpStatus.OK);
	}
	
	@GetMapping("/address")
	public ResponseEntity<SimpleMessageDto> getAddress(@RequestParam("dongcode") String dongcode) {
		return new ResponseEntity<SimpleMessageDto>(houseService.getAddress(dongcode), HttpStatus.OK);
	}
}
