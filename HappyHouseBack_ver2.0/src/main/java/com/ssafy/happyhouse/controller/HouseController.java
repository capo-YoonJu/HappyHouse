package com.ssafy.happyhouse.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.dto.House;
import com.ssafy.happyhouse.model.service.HouseService;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequestMapping("/house")
@RestController
public class HouseController {
	
	private HouseService houseService;
	
	@Autowired
	public void setDirectorService(HouseService houseService) {
		this.houseService = houseService;
	}

	@GetMapping("/search")
	public ResponseEntity<List<House>> searchHouse(@RequestParam(required = false) String dong, @RequestParam(required = false) String apt) throws SQLException {
		HashMap<String, Object> condition = new HashMap<String, Object>();
		if(dong != null && !dong.trim().equals("")) condition.put("dong", dong);
		if(apt != null && !apt.trim().equals("")) condition.put("apt", apt);
		List<House> list = houseService.search(condition);
		System.out.println(list.size());
		return ResponseEntity.ok(list);
	}
	
	@PostMapping("/search")
	public ResponseEntity<Object> makeRank(@RequestBody(required = false) Map<String,String> params) throws SQLException{
		String dong = params.get("dong");
		if(dong != null) houseService.rankDong(dong);
		return ResponseEntity.created(null).build();
	}
}