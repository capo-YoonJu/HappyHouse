package com.ssafy.happyhouse.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.dto.FavoriteDto;
import com.ssafy.happyhouse.model.dto.UserDto;
import com.ssafy.happyhouse.model.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	private UserService userService;
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping(params = "idCheck")
	public ResponseEntity<Boolean> idCheck(@RequestParam String id) {
		UserDto user = userService.selectUserInfo(id);
		if (user != null) {
			return ResponseEntity.ok().body(true);
		} else {
			return ResponseEntity.noContent().build();
		}
	}
	
	@PostMapping
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto user) {
		return ResponseEntity.ok().body(userService.insertUser(user));
	}
	
	@GetMapping
	public ResponseEntity<UserDto> searchUserExist(@RequestParam String id, @RequestParam String pw, HttpServletRequest request) {
		HashMap<String, Object> idPw = new HashMap<String, Object>();
		idPw.put("id", id);
		idPw.put("pw", pw);
		
		UserDto user = userService.selectUser(idPw);
		if (user != null) {
			HttpSession session = request.getSession();
			if (session.getAttribute("loginSession")!=null) {
				session.removeAttribute("loginSession");
			}
			session.setAttribute("loginSession", user);
			
			return ResponseEntity.ok().body(user);
		} else {
			return ResponseEntity.noContent().build();
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> searchUserInfo(@PathVariable String id) {
		return ResponseEntity.ok().body(userService.selectUserInfo(id));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UserDto> modifyUser(@RequestBody UserDto user, @PathVariable String id) {
		HashMap<String, Object> idUser = new HashMap<String, Object>();
		idUser.put("id", id);
		idUser.put("user", user);
		
		return ResponseEntity.ok().body(userService.updateUser(idUser));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> removeUser(@PathVariable String id) {
		return ResponseEntity.ok().body(userService.deleteUser(id));
	}
	
	@PostMapping(value = "/{id}")
	public ResponseEntity<UserDto> registerFavorite(@RequestBody FavoriteDto favorite, @PathVariable String id) {
		HashMap<String, Object> idFavorite = new HashMap<String, Object>();
		idFavorite.put("id", id);
		idFavorite.put("favorite", favorite);
		
		return ResponseEntity.ok().body(userService.insertFavorite(idFavorite));
	}
	
	@GetMapping(value = "/{id}", params = "favorite")
	public ResponseEntity<UserDto> searchFavorites(@PathVariable String id) {
		UserDto user = userService.selectFavorites(id);
		if (user != null) {
			return ResponseEntity.ok().body(user);
		} else {
			return ResponseEntity.noContent().build();
		}
	}
	
	@DeleteMapping(value = "/{id}", params = "favorite")
	public ResponseEntity<UserDto> removeFavorite(@RequestParam String dongcode, @PathVariable String id) {
		HashMap<String, Object> idDongcode = new HashMap<String, Object>();
		idDongcode.put("id", id);
		idDongcode.put("dongcode", dongcode);
		
		return ResponseEntity.ok().body(userService.deleteFavorite(idDongcode));
	}
	
}