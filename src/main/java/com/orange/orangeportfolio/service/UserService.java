package com.orange.orangeportfolio.service;

import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;

import com.orange.orangeportfolio.dto.UserCreateDTO;
import com.orange.orangeportfolio.dto.UserDTO;
import com.orange.orangeportfolio.dto.UserUpdateDTO;
import com.orange.orangeportfolio.mapper.UserMapper;
import com.orange.orangeportfolio.repository.UserRepository;
import com.orange.orangeportfolio.service.exception.UserInvalidPropertyException;
import com.orange.orangeportfolio.service.exception.UserNotFoundException;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserMapper userMapper;
	
	public UserDTO create(UserCreateDTO user) throws HttpClientErrorException {
		
		UserInvalidPropertyException.ThrowIfIsNullOrEmpty(UserCreateDTO.Fields.name, user.name());
		UserInvalidPropertyException.ThrowIfIsNullOrEmpty(UserCreateDTO.Fields.email, user.email());
		UserInvalidPropertyException.ThrowIfIsNullOrEmpty(UserCreateDTO.Fields.password, user.password());
		
		var userEntity =  userMapper.toUser(user);
		userEntity = userRepository.save(userEntity);
		
		var createdUser = userMapper.toDTO(userEntity);
		
		return createdUser;
	}
	
	public UserDTO getById(Long id) throws HttpClientErrorException {
		var user = userRepository.findById(id);	
		
		UserNotFoundException.ThrowIfIsEmpty(user);
		
		return userMapper.toDTO(user.get());
	}
	
	public void deleteById(Long id) throws HttpClientErrorException {
		var user = userRepository.findById(id);
		
		UserNotFoundException.ThrowIfIsEmpty(user);
		
		userRepository.deleteById(id);
	}
	
	public UserDTO update(Long id, UserUpdateDTO user) throws HttpClientErrorException {

		UserInvalidPropertyException.ThrowIfIsNullOrEmpty(UserUpdateDTO.Fields.name, user.name());
		UserInvalidPropertyException.ThrowIfIsNullOrEmpty(UserUpdateDTO.Fields.email, user.email());
		
		var result = userRepository.findById(id);
		
		UserNotFoundException.ThrowIfIsEmpty(result);
		
		var userEntity = result.get();
	
		userEntity = userMapper.toUser(user, userEntity);
		
		userEntity = userRepository.save(userEntity);
		
		var updatedUser = userMapper.toDTO(userEntity);
		
		return updatedUser;
	}
}
