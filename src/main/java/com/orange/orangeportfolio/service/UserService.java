package com.orange.orangeportfolio.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import com.orange.orangeportfolio.dto.UserCreateDTO;
import com.orange.orangeportfolio.dto.UserDTO;
import com.orange.orangeportfolio.dto.UserUpdateDTO;
import com.orange.orangeportfolio.mapper.UserMapper;
import com.orange.orangeportfolio.model.User;
import com.orange.orangeportfolio.model.UserLogin;
import com.orange.orangeportfolio.repository.UserRepository;
import com.orange.orangeportfolio.security.JwtService;
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

	@Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

	public Optional<User> cadastrarUsuario(User user) {

		if (userRepository.findByEmail(user.getEmail()).isPresent())
			return Optional.empty();

		user.setPassword(encodePassword(user.getPassword()));

		return Optional.of(userRepository.save(user));
	
	}

	public Optional<User> atualizarUsuario(User user) {
		
		if(userRepository.findById(user.getId()).isPresent()) {

			Optional<User> searchUser = userRepository.findByEmail(user.getEmail());

			if ( (searchUser.isPresent()) && ( searchUser.get().getId() != user.getId()))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists!", null);

			user.setPassword(encodePassword(user.getPassword()));

			return Optional.ofNullable(userRepository.save(user));
			
		}

		return Optional.empty();
	
	}	

	public Optional<UserLogin> autenticarUsuario(Optional<UserLogin> userLogin) {
        
        // Gera o Objeto de autenticação
		var credentials = new UsernamePasswordAuthenticationToken(userLogin.get().getEmail(), userLogin.get().getPassword());
		
        // Autentica o Usuario
		Authentication authentication = authenticationManager.authenticate(credentials);
        
        // Se a autenticação foi efetuada com sucesso
		if (authentication.isAuthenticated()) {

            // Busca os dados do usuário
			Optional<User> user = userRepository.findByEmail(userLogin.get().getEmail());

            // Se o usuário foi encontrado
			if (user.isPresent()) {

                // Preenche o Objeto userLogin com os dados encontrados 
			   userLogin.get().setId(user.get().getId());
			   userLogin.get().setName(user.get().getName());
			   userLogin.get().setEmail(user.get().getEmail());
			   userLogin.get().setToken(gerarToken(userLogin.get().getEmail()));
			   userLogin.get().setPassword("");
				
                 // Retorna o Objeto preenchido
			   return userLogin;
			
			}

        } 
            
		return Optional.empty();

    }

	private String encodePassword(String password) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		return encoder.encode(password);

	}

	private String gerarToken(String user) {
		return "Bearer " + jwtService.generateToken(user);
	}

}

