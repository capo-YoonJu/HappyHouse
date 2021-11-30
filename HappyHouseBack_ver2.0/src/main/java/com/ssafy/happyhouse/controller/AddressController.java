package com.ssafy.happyhouse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.dto.AddressCode;
import com.ssafy.happyhouse.model.dto.SDot;
import com.ssafy.happyhouse.model.service.AddressService;

@RestController
@RequestMapping("/address")
public class AddressController {
	
	@Autowired
	private AddressService addressService;
	
	@GetMapping("/sido")
	public ResponseEntity<List<AddressCode>> sido() throws Exception {
		return new ResponseEntity<List<AddressCode>>(addressService.getSido(), HttpStatus.OK);
	}
	
//	@GetMapping("/gugun")
//	public ResponseEntity<List<SidoGugunCodeDto>> gugun(@RequestParam("sido") String sido) throws Exception {
//		return new ResponseEntity<List<SidoGugunCodeDto>>(addressService.getGugunInSido(sido), HttpStatus.OK);
//	}
	
	@GetMapping("/gugun")
	public ResponseEntity<List<AddressCode>> gugun() throws Exception {
		return new ResponseEntity<List<AddressCode>>(addressService.getGugun(), HttpStatus.OK);
	}
	
	@GetMapping("/dong")
	public ResponseEntity<List<AddressCode>> dong(@RequestParam("gugun") String gugun) throws Exception {
		return new ResponseEntity<List<AddressCode>>(addressService.getDongInGugun(gugun), HttpStatus.OK);
	}
	
	@GetMapping("/hdong")
	public ResponseEntity<AddressCode> hdong(@RequestParam("dongCode") String dongCode) throws Exception {
		return new ResponseEntity<AddressCode>(addressService.getHDong(dongCode), HttpStatus.OK);
	}
	
	@GetMapping("/sdot")
	public ResponseEntity<List<SDot>> serialNo(@RequestParam("gugun") String gugun) throws Exception {
		return new ResponseEntity<List<SDot>>(addressService.getSerialNoInGugun(gugun), HttpStatus.OK);
	}

}
