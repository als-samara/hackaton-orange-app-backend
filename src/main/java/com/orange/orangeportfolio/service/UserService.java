package com.orange.orangeportfolio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.orange.orangeportfolio.dto.UserCreateDTO;
import com.orange.orangeportfolio.dto.UserDTO;
import com.orange.orangeportfolio.dto.UserLoginDTO;
import com.orange.orangeportfolio.dto.UserProjectDTO;
import com.orange.orangeportfolio.dto.UserTokenDTO;
import com.orange.orangeportfolio.dto.UserUpdateDTO;
import com.orange.orangeportfolio.dto.UserUpdatePasswordDTO;
import com.orange.orangeportfolio.mapper.UserMapper;
import com.orange.orangeportfolio.repository.UserRepository;
import com.orange.orangeportfolio.security.JwtService;
import com.orange.orangeportfolio.service.exception.FailedAuthenticationException;
import com.orange.orangeportfolio.service.exception.UserInvalidPropertyException;
import com.orange.orangeportfolio.service.exception.UserNotFoundException;
import com.orange.orangeportfolio.service.exception.UserWithSameEmailAlreadyCreatedException;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	public UserDTO create(UserCreateDTO user) throws HttpClientErrorException {

		UserInvalidPropertyException.ThrowIfIsNullOrEmpty(UserCreateDTO.Fields.name, user.name());
		UserInvalidPropertyException.ThrowIfIsNullOrEmpty(UserCreateDTO.Fields.email, user.email());
		UserInvalidPropertyException.ThrowIfIsNullOrEmpty(UserCreateDTO.Fields.password, user.password());

		ValidateEmailDuplication(user.email());

		var userEntity = userMapper.toUser(user);
		userEntity = userRepository.save(userEntity);

		var createdUser = userMapper.toDTO(userEntity);

		return createdUser;
	}

	public UserDTO getById(Long id) throws HttpClientErrorException {
		var user = userRepository.findById(id);

		UserNotFoundException.ThrowIfIsEmpty(user);

		return userMapper.toDTO(user.get());
	}
	
	public UserProjectDTO getByIdWithProjects(Long id) throws HttpClientErrorException {
		var user = userRepository.findById(id);

		UserNotFoundException.ThrowIfIsEmpty(user);

		return userMapper.toUserProjectDTO(user.get());
	}

	public void deleteById(Long id) throws HttpClientErrorException {
		var user = userRepository.findById(id);

		UserNotFoundException.ThrowIfIsEmpty(user);

		userRepository.deleteById(id);
	}

	public UserDTO update(Long id, UserUpdateDTO user) throws HttpClientErrorException {

		UserInvalidPropertyException.ThrowIfIsNullOrEmpty(UserUpdateDTO.Fields.name, user.name());
		UserInvalidPropertyException.ThrowIfIsNullOrEmpty(UserUpdateDTO.Fields.email, user.email());

		ValidateEmailDuplication(user.email(), id);

		var result = userRepository.findById(id);

		UserNotFoundException.ThrowIfIsEmpty(result);

		var userEntity = result.get();
		userEntity = userMapper.toUser(user, userEntity);
		userEntity = userRepository.save(userEntity);

		var updatedUser = userMapper.toDTO(userEntity);

		return updatedUser;
	}
	
	public void updatePassword(Long id, UserUpdatePasswordDTO password) {
		var user = userRepository.findById(id);
		
		UserNotFoundException.ThrowIfIsEmpty(user);
		
		var updatedPasswordUser = userMapper.toUser(password, user.get());
		
		updatedPasswordUser = userRepository.save(updatedPasswordUser);
	}

	public UserTokenDTO authenticate(UserLoginDTO userLogin) throws Exception {

		var credentials = new UsernamePasswordAuthenticationToken(
				userLogin.email(),
				userLogin.password());
		
		Authentication authentication = authenticationManager.authenticate(credentials);
		
		if(!authentication.isAuthenticated()) {
			throw new FailedAuthenticationException();
		}
		
		var token = gerarToken(userLogin.email());
		var userToken = new UserTokenDTO(token);
		
		return userToken;
	}

	private void ValidateEmailDuplication(String email, Long id) {
		var result = userRepository.findByEmail(email);

		if (result.isEmpty()) {
			return;
		}

		var user = result.get();
		var isSameId = user.getId() == id;

		if (!isSameId) {
			throw new UserWithSameEmailAlreadyCreatedException();
		}
	}

	private void ValidateEmailDuplication(String email) {
		var result = userRepository.findByEmail(email);

		if (result.isPresent()) {
			throw new UserWithSameEmailAlreadyCreatedException();
		}
	}

	private String encodePassword(String password) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		return encoder.encode(password);

	}

	private String gerarToken(String user) {
		return "Bearer " + jwtService.generateToken(user);
	}

}
