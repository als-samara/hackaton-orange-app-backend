package com.orange.orangeportfolio.service;

import java.util.List;

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
import com.orange.orangeportfolio.model.User;
import com.orange.orangeportfolio.repository.ProjectRepository;
import com.orange.orangeportfolio.repository.UserRepository;
import com.orange.orangeportfolio.security.JwtService;
import com.orange.orangeportfolio.service.exception.FailedAuthenticationException;
import com.orange.orangeportfolio.service.exception.UserInvalidEmailFormatException;
import com.orange.orangeportfolio.service.exception.UserInvalidPropertyException;
import com.orange.orangeportfolio.service.exception.UserInvalidPropertySizeException;
import com.orange.orangeportfolio.service.exception.UserNotFoundException;

import com.orange.orangeportfolio.service.exception.UserPasswordInvalidException;
import com.orange.orangeportfolio.service.exception.UserUnauthorizedException;
import com.orange.orangeportfolio.service.exception.UserWithSameEmailAlreadyCreatedException;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	HttpServletRequest request;

	public UserDTO create(UserCreateDTO user) throws HttpClientErrorException {

		UserInvalidPropertyException.ThrowIfIsNullOrEmpty(UserCreateDTO.Fields.name, user.name());
		UserInvalidPropertySizeException.ThrowIfInvalidName(user.name());
		UserInvalidPropertyException.ThrowIfIsNullOrEmpty(UserCreateDTO.Fields.email, user.email());
		UserInvalidEmailFormatException.throwIfInvalidEmail(user.email());
		UserInvalidPropertyException.ThrowIfIsNullOrEmpty(UserCreateDTO.Fields.password, user.password());
		UserPasswordInvalidException.ThrowIfInvalidPassword(user.password());
		UserInvalidPropertySizeException.ThrowIfInvalidPassword(user.password());

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
	
	public UserDTO getByEmail(String email) throws HttpClientErrorException {
		var user = userRepository.findByEmail(email);

		UserNotFoundException.ThrowIfIsEmpty(user);

		return userMapper.toDTO(user.get());
	}
	
	public List<User> getAll() throws HttpClientErrorException {
		return userRepository.findAll();
	}
	
	public UserProjectDTO getByIdWithProjects(Long id) throws HttpClientErrorException {
		var user = userRepository.findById(id);

		UserNotFoundException.ThrowIfIsEmpty(user);

		return userMapper.toUserProjectDTO(user.get());
	
	}
	
	public UserProjectDTO getByEmailWithProjects(String email) throws HttpClientErrorException {
		var user = userRepository.findByEmail(email);

		UserNotFoundException.ThrowIfIsEmpty(user);

		return getByIdWithProjects(user.get().getId());
		
		
	}

	public void deleteById(Long id) throws HttpClientErrorException {
		var result = userRepository.findById(id);

		UserNotFoundException.ThrowIfIsEmpty(result);
		
		ValidateUser(result.get());
		
		userRepository.deleteById(id);
	}

	public UserDTO update(Long id, UserUpdateDTO user) throws HttpClientErrorException {

		UserInvalidPropertyException.ThrowIfIsNullOrEmpty(UserUpdateDTO.Fields.name, user.name());
		UserInvalidPropertyException.ThrowIfIsNullOrEmpty(UserUpdateDTO.Fields.email, user.email());

		ValidateEmailDuplication(user.email(), id);

		var result = userRepository.findById(id);

		UserNotFoundException.ThrowIfIsEmpty(result);
		
		ValidateUser(result.get());

		var userEntity = result.get();
		userEntity = userMapper.toUser(user, userEntity);
		userEntity = userRepository.save(userEntity);

		var updatedUser = userMapper.toDTO(userEntity);

		return updatedUser;
	}
	
	public void updatePassword(Long id, UserUpdatePasswordDTO password) {
		var user = userRepository.findById(id);
		
		UserNotFoundException.ThrowIfIsEmpty(user);
		
		ValidateUser(user.get());
		
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
	
	private void ValidateUser(User user) {
		var userEmail = request
				.getAttribute("userEmail")
				.toString();
		
		var isSameUser = user.getEmail().equals(userEmail);
		
		if(!isSameUser) {
			throw new UserUnauthorizedException();
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
