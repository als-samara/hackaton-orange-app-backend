package com.orange.orangeportfolio.mapper;

import org.springframework.stereotype.Component;

import com.orange.orangeportfolio.dto.UserDTO;
import com.orange.orangeportfolio.model.User;

@Component
public class UserMapper {
	
	public UserDTO toDTO(User user) {
		var userDTO = new UserDTO(
				user.getId(),
				user.getName(), 
				user.getEmail(),
				user.getPassword());
		
		return userDTO;
	}
	
	public User toUser(UserDTO userDTO) {
		var user = new User(
				userDTO.id(), 
				userDTO.name(), 
				userDTO.email(), 
				userDTO.password());
		
		return user;
	}
}
