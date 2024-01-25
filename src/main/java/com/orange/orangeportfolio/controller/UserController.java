package com.orange.orangeportfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.orange.orangeportfolio.dto.UserCreateDTO;
import com.orange.orangeportfolio.dto.UserDTO;
import com.orange.orangeportfolio.dto.UserUpdateDTO;
import com.orange.orangeportfolio.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/{id}")
	public UserDTO getById(@PathVariable Long id) throws HttpClientErrorException {
		var user = userService.getById(id);
		return user;
	}
	
	@PostMapping
	public UserDTO post(@RequestBody UserCreateDTO user) throws HttpClientErrorException {
		var createdUser = userService.create(user);
		return createdUser;
	}
	
	@PutMapping("/{id}")
	public UserDTO put(@PathVariable Long id, @RequestBody UserUpdateDTO user) throws HttpClientErrorException {
		var updateUser = userService.update(id, user);
		return updateUser;
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) throws HttpClientErrorException {
		userService.deleteById(id);
	}
}