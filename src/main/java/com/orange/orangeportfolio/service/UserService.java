package com.orange.orangeportfolio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.orange.orangeportfolio.dto.UserDTO;
import com.orange.orangeportfolio.mapper.UserMapper;
import com.orange.orangeportfolio.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserMapper userMapper;
	
	public UserDTO create(UserDTO user) throws Exception {
		if(!StringUtils.hasText(user.name())) {
			throw new Exception();
		}
		
		if(!StringUtils.hasText(user.email())) {
			throw new Exception();
		}
		
		if(!StringUtils.hasText(user.password())) {
			throw new Exception();
		}
		
		var userEntity =  userMapper.toUser(user);
		userEntity = userRepository.save(userEntity);
		
		var createdUser = userMapper.toDTO(userEntity);
		
		return createdUser;
	}
	
	public UserDTO getById(Long id) throws Exception {
		var user = userRepository.findById(id);
		
		if(user.isEmpty()) {
			throw new Exception();
		}
		
		return userMapper.toDTO(user.get());
	}
	
	public void deleteById(Long id) throws Exception {
		var user = userRepository.findById(id);
		
		if(user.isEmpty()) {
			throw new Exception();
		}
		
		userRepository.deleteById(id);
	}
	
	public UserDTO update(Long id, UserDTO user) throws Exception {
		if(!StringUtils.hasText(user.name())) {
			throw new Exception();
		}
		
		if(!StringUtils.hasText(user.email())) {
			throw new Exception();
		}
		
		if(!StringUtils.hasText(user.password())) {
			throw new Exception();
		}
		
		var result = userRepository.findById(id);
		
		if(result.isEmpty()) {
			throw new Exception();
		}
		var userEntity = result.get();
		
		userEntity.setName(user.name());
		userEntity.setEmail(user.email());
		userEntity.setPassword(user.password());
		
		userEntity = userRepository.save(userEntity);
		
		var updatedUser = userMapper.toDTO(userEntity);
		
		return updatedUser;
	}
}
