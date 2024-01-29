package com.orange.orangeportfolio.mapper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.orange.orangeportfolio.dto.UserCreateDTO;
import com.orange.orangeportfolio.dto.UserDTO;
import com.orange.orangeportfolio.dto.UserUpdateDTO;
import com.orange.orangeportfolio.dto.UserUpdatePasswordDTO;
import com.orange.orangeportfolio.model.User;

@Component
public class UserMapper {
	
	private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	public UserDTO toDTO(User user) {
		var userDTO = new UserDTO(
				user.getId(),
				user.getName(), 
				user.getEmail(),
				user.getPhoto());
				
		return userDTO;
	}
	
	public User toUser(UserCreateDTO userCreateDTO) {
		
		var passwordHash = encoder.encode(userCreateDTO.password());
		
		var user = User.builder()
				.name(userCreateDTO.name())
				.email(userCreateDTO.email())
				.password(passwordHash)
				.photo(userCreateDTO.photo())
				.build();
		
		return user;
	}
	
	public User toUser(UserUpdateDTO userUpdateDTO, User user){
		user.setName(userUpdateDTO.name());
		user.setEmail(userUpdateDTO.email());
		user.setPhoto(userUpdateDTO.photo());
		
		return user;
	}
	
	public User toUser(UserUpdatePasswordDTO userUpdatePasswordDTO, User user) {
		
		var passwordHash = encoder.encode(userUpdatePasswordDTO.password());
		
		user.setPassword(passwordHash);
	
		return user;		
	}
}
