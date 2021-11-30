package com.ssafy.happyhouse.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.dto.User;
import com.ssafy.happyhouse.model.service.UserService;
import com.ssafy.happyhouse.model.service.JwtServiceImpl;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
	
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private JwtServiceImpl jwtService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/{id}")
	public ResponseEntity<Boolean> idCheck(@PathVariable("id") String id) {
		return ResponseEntity.ok(userService.idCheck(id));
	}
	
	@PostMapping("")
	public ResponseEntity<Object> registerUser(@RequestBody User user){
		if (!userService.registerUser(user)) ResponseEntity.internalServerError().build();
		return ResponseEntity.created(null).build();
	}
	
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody User user){
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			User loginUser = userService.login(user);
			if (loginUser != null) {
				String token = jwtService.create("userid", loginUser.getUserId(), "access-token");
				resultMap.put("access-token", token);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} else {
				resultMap.put("message", FAIL);
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@GetMapping("/auth")
	public ResponseEntity<User> getUser() {
		User user = userService.getUser(jwtService.getUserId());
		if (user == null) return ResponseEntity.notFound().build();
		user.setUserPassword(null);
		return ResponseEntity.ok(user);
	}
	
	@PutMapping("/auth")
	public ResponseEntity<Object> updateUser(@RequestBody User user) {
		user.setUserId(jwtService.getUserId());
		if (!userService.updateUser(user)) return ResponseEntity.notFound().build();
		return ResponseEntity.created(null).build();
	}
	
	@DeleteMapping("/auth")
	public ResponseEntity<Object> deleteUser() {
		if (!userService.deleteUser(jwtService.getUserId())) return ResponseEntity.notFound().build();
		return ResponseEntity.created(null).build();
	}
	
//	@GetMapping("/auth/logout")
//	public ResponseEntity<Object> logout(HttpSession session){
//		session.invalidate();
//		return ResponseEntity.ok(null);
//	}
	
}
