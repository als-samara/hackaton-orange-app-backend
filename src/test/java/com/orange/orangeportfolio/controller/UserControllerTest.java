package com.orange.orangeportfolio.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.orange.orangeportfolio.dto.ProjectCreateDTO;
import com.orange.orangeportfolio.dto.ProjectDTO;
import com.orange.orangeportfolio.dto.UserCreateDTO;
import com.orange.orangeportfolio.dto.UserDTO;
import com.orange.orangeportfolio.dto.UserProjectDTO;
import com.orange.orangeportfolio.dto.UserTokenDTO;
import com.orange.orangeportfolio.dto.UserUpdateDTO;
import com.orange.orangeportfolio.dto.UserUpdatePasswordDTO;
import com.orange.orangeportfolio.model.User;
import com.orange.orangeportfolio.model.UserLogin;
import com.orange.orangeportfolio.repository.UserRepository;
import com.orange.orangeportfolio.service.ProjectService;
import com.orange.orangeportfolio.service.UserService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTest {
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ProjectService projectService;
	
	@Autowired
	UserRepository userRepository;
	
	@BeforeEach
	void start() {
		userRepository.deleteAll();
		userService.create(new UserCreateDTO("Root", "root@root.com", "rootroot", ""));
	}
	
	@Test
	@DisplayName("Register User")
	public void itShouldRegisterAUser() {
		HttpEntity<UserCreateDTO> requestBody = new HttpEntity<UserCreateDTO>(
				new UserCreateDTO("User Name", "email@email.com", "senha123", ""));
		
		HttpEntity<UserCreateDTO> responseBody = testRestTemplate.exchange("/api/users/register", HttpMethod.POST, requestBody, UserCreateDTO.class);
		
		assertEquals(HttpStatus.OK, ((ResponseEntity<UserCreateDTO>) responseBody).getStatusCode());
	}
	
	@Test
	@DisplayName("Find a User searching by its ID")
	public void itShouldFindUserById() {
		UserCreateDTO userCreateDTO = new UserCreateDTO("Test User", "user@test.com", "testuser", "");
		UserDTO userDTO = userService.create(userCreateDTO);
		
		ResponseEntity<UserDTO> response = testRestTemplate
				.withBasicAuth("root@root.com", "rootroot")
				.exchange("/api/users/{id}", HttpMethod.GET, null, UserDTO.class, userDTO.id());
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	@DisplayName("Authenticate User")
	public void itShouldAuthenticateTheUser() {
		UserCreateDTO userCreateDTO = new UserCreateDTO("Test User", "user@test.com", "testuser", "");
		userService.create(userCreateDTO);
		
	    String email = "user@test.com";
	    String password = "testuser";

	    UserLogin userLogin = new UserLogin();
	    userLogin.setEmail(email);
	    userLogin.setPassword(password);

	    HttpEntity<UserLogin> requestBody = new HttpEntity<>(userLogin);
	    
	    ResponseEntity<UserTokenDTO> responseBody = testRestTemplate.exchange(
	            "/api/users/authenticate", HttpMethod.POST, requestBody, UserTokenDTO.class
	    );
		
	    assertEquals(HttpStatus.OK, responseBody.getStatusCode());
	    assertNotNull(responseBody.getBody());
	}
	
	@Test
	@DisplayName("Update an existing User searching by its Id")
	public void itShouldUpdateUserInformation() {
		
		UserCreateDTO userCreateDTO = new UserCreateDTO("Test User", "user@test.com", "testuser", "");
		UserDTO userDTO = userService.create(userCreateDTO);
								
		HttpEntity<UserUpdateDTO> requestBody = new HttpEntity<UserUpdateDTO>(new UserUpdateDTO("Updated Test User", "user@test.com", ""));
		
		ResponseEntity<UserUpdateDTO> responseBody = testRestTemplate
				.withBasicAuth("root@root.com", "rootroot")
				.exchange("/api/users/{id}", HttpMethod.PUT, requestBody, UserUpdateDTO.class, userDTO.id());
		
		assertEquals(HttpStatus.OK, responseBody.getStatusCode());
	}
	
	@Test
	@DisplayName("Update Password")
	public void itShouldUpdatePassword() {
		UserCreateDTO userCreateDTO = new UserCreateDTO("Test User", "user@test.com", "testuser", "");
		UserDTO userDTO = userService.create(userCreateDTO);
		
		HttpEntity<UserUpdatePasswordDTO> requestBody = new HttpEntity<UserUpdatePasswordDTO>(new UserUpdatePasswordDTO("Updated Password"));
		
		ResponseEntity<UserUpdatePasswordDTO> responseBody = testRestTemplate.
				withBasicAuth("root@root.com", "rootroot")
				.exchange("/api/users/{id}/password",
						HttpMethod.PUT, 
						requestBody, 
						UserUpdatePasswordDTO.class, 
						userDTO.id());
		
		assertEquals(HttpStatus.OK, responseBody.getStatusCode());
								
	}
	
	@Test
	@DisplayName("Delete User searching by its Id")
	public void itShouldDeleteTheUser() {
		UserCreateDTO userCreateDTO = new UserCreateDTO("Test User", "user@test.com", "testuser");
		UserDTO userDTO = userService.create(userCreateDTO);
		
		ResponseEntity<UserDTO> response = testRestTemplate
				.withBasicAuth("root@root.com", "rootroot")
				.exchange("/api/users/{id}", HttpMethod.DELETE, null, UserDTO.class, userDTO.id());
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	@DisplayName("Prevent Duplicate Emails")
	public void itShouldPreventHavingDuplicateEmails() {
		userService.create(new UserCreateDTO("Test User", "user@test.com", "testuser"));
		
		HttpEntity<UserCreateDTO> requestBody = new HttpEntity<UserCreateDTO>(
				new UserCreateDTO("Test User", "user@test.com", "testuser"));
		
		ResponseEntity<UserDTO> responseBody = testRestTemplate
				.withBasicAuth("root@root.com", "rootroot")
				.exchange("/api/users/register", HttpMethod.POST, requestBody, UserDTO.class);
		
		assertEquals(HttpStatus.BAD_REQUEST, responseBody.getStatusCode());
	}
	
	@Test
	@DisplayName("List all Users")
	public void itShouldReturnAListOfAllUsers() {
		userService.create(new UserCreateDTO("Test User 1", "user1@test.com", "testuser1"));
		userService.create(new UserCreateDTO("Test User 2", "user2@test.com", "testuser2"));
		
		ResponseEntity<List<User>> response = testRestTemplate
				.withBasicAuth("root@root.com", "rootroot")
				.exchange("/api/users/all", HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {});
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	// TODO: retorna o user pelo id do projeto - @GetMapping("/{id}/projects")
	@Test
	@DisplayName("Return User by ID Project")
	public void itShouldReturnTheUserByIdProject() {
		
		UserDTO userDTO = userService.create(new UserCreateDTO("User Name", "email@email.com", "senha123"));
		projectService.create((new ProjectCreateDTO("Project Title", "description X", "photo", "link", Arrays.asList("tag 1"), userDTO.id())));
		
		ResponseEntity<UserProjectDTO> response = testRestTemplate
				.withBasicAuth("root@root.com", "rootroot")
				.exchange("/api/users/{id}/projects", HttpMethod.GET, null, UserProjectDTO.class, userDTO.id());
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
}
