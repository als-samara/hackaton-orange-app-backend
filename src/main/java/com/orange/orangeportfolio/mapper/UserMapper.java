package com.orange.orangeportfolio.mapper;

import org.springframework.stereotype.Component;

import com.orange.orangeportfolio.dto.UserCreateDTO;
import com.orange.orangeportfolio.dto.UserDTO;
import com.orange.orangeportfolio.dto.UserUpdateDTO;
import com.orange.orangeportfolio.model.User;

@Component
public class UserMapper {
	
	public UserDTO toDTO(User user) {
		var userDTO = new UserDTO(
				user.getId(),
				user.getName(), 
				user.getEmail());
				
		return userDTO;
	}
	
	public User toUser(UserCreateDTO userCreateDTO) {
		var user = new User( 
				null,
				userCreateDTO.name(), 
				userCreateDTO.email(), 
				userCreateDTO.password());
		
		return user;
	}
	
	public User toUser(UserUpdateDTO userUpdateDTO, User user){
		user.setName(userUpdateDTO.name());
		user.setEmail(userUpdateDTO.email());
		
		return user;
	}
}
