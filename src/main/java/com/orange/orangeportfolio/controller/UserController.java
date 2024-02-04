package com.orange.orangeportfolio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.orange.orangeportfolio.dto.UserLoginDTO;
import com.orange.orangeportfolio.dto.UserProjectDTO;
import com.orange.orangeportfolio.dto.UserTokenDTO;
import com.orange.orangeportfolio.dto.UserUpdateDTO;
import com.orange.orangeportfolio.dto.UserUpdatePasswordDTO;
import com.orange.orangeportfolio.model.User;
import com.orange.orangeportfolio.repository.UserRepository;
import com.orange.orangeportfolio.service.UserService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping
    public UserProjectDTO getByEmailWithProjects(@CurrentSecurityContext(expression="authentication.name")String email) throws HttpClientErrorException {
        var userWithProjects = userService.getByEmailWithProjects(email);
        return userWithProjects;
    }
	
	@GetMapping("/user/{email}")
	public UserDTO getByEmail(String email) {
		return userService.getByEmail(email);
    }
	
	@GetMapping("/{id}")
	public UserDTO getById(@PathVariable Long id) throws HttpClientErrorException {
		var user = userService.getById(id);
		return user;
	}
	
	@GetMapping("/{id}/projects")
	public UserProjectDTO getByIdWithProjects(@PathVariable Long id) throws HttpClientErrorException {
		var user = userService.getByIdWithProjects(id);
		return user;
	}
	
	@GetMapping("/all")
	public List<User> getAllUsers() throws HttpClientErrorException {
		return userService.getAll();
	}
	
	@PostMapping("/register")
	public UserDTO post(@RequestBody UserCreateDTO user) throws HttpClientErrorException {
		var createdUser = userService.create(user);
		return createdUser;
	}
	
	@PostMapping("/authenticate")
	public UserTokenDTO authenticateUser(@RequestBody UserLoginDTO userLogin) throws Exception{
		
		var token = userService.authenticate(userLogin);
		
		return token;
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
	
	@PutMapping("/{id}/password")
	public void updatePassword(@PathVariable Long id, @RequestBody UserUpdatePasswordDTO userUpdatePassword) {
		userService.updatePassword(id, userUpdatePassword);
	}
}
