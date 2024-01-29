package com.orange.orangeportfolio.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
import com.orange.orangeportfolio.dto.ProjectUpdateDTO;
import com.orange.orangeportfolio.dto.UserCreateDTO;
import com.orange.orangeportfolio.dto.UserDTO;
import com.orange.orangeportfolio.repository.ProjectRepository;
import com.orange.orangeportfolio.repository.UserRepository;
import com.orange.orangeportfolio.service.ProjectService;
import com.orange.orangeportfolio.service.UserService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProjectControllerTest {
	
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	ProjectService projectService;
	
	@BeforeEach
	void start() {
		userRepository.deleteAll();
		userService.create(new UserCreateDTO("Root", "root@root.com", "rootroot", ""));
	}
	
	@Test
	@DisplayName("Create Project")
	public void itShouldCreateAProject() {
		HttpEntity<ProjectCreateDTO> requestBody = 
				new HttpEntity<ProjectCreateDTO>(new ProjectCreateDTO("Project Title", "description X", "", "", Arrays.asList("tag 1"), 1L));
		
		ResponseEntity<ProjectDTO> responseBody = testRestTemplate
				.withBasicAuth("root@root.com", "rootroot")
				.exchange("/api/projects", HttpMethod.POST, requestBody, ProjectDTO.class);
		
		assertEquals(HttpStatus.OK, ((ResponseEntity<ProjectDTO>) responseBody).getStatusCode());
	}
	
	@Test
	@DisplayName("Find Project searching by its ID")
	public void itShouldFindProjectById() {
		
		UserCreateDTO user = new UserCreateDTO("User Name", "email@email.com", "senha123", "");
		UserDTO userCreated = userService.create(user);
		
		ProjectCreateDTO projectCreateDTO = (new ProjectCreateDTO("Project Title", "description X", "photo", "link", Arrays.asList("tag 1"), userCreated.id()));
		ProjectDTO projectDTO = projectService.create(projectCreateDTO);
		
		ResponseEntity<ProjectDTO> response = testRestTemplate
				.withBasicAuth("root@root.com", "rootroot")
				.exchange("/api/projects/{id}", HttpMethod.GET, null, ProjectDTO.class, projectDTO.id());
		
		assertEquals(HttpStatus.OK, ((ResponseEntity<ProjectDTO>) response).getStatusCode());
	}
	
	@Test
	@DisplayName("Update Project")
	public void itShouldUpdateProjectInformation() {
		UserCreateDTO user = new UserCreateDTO("User Name", "email@email.com", "senha123", "");
		UserDTO userCreated = userService.create(user);
		ProjectCreateDTO projectCreateDTO = (new ProjectCreateDTO("Project Title", "description X", "", "", Arrays.asList("tag 1"), userCreated.id()));
		ProjectDTO projectDTO = projectService.create(projectCreateDTO);
								
		HttpEntity<ProjectUpdateDTO> requestBody = new HttpEntity<ProjectUpdateDTO>(new ProjectUpdateDTO("Updated Project title","Updated description","","", Arrays.asList("tag 1", "tag 2")));
		
		ResponseEntity<ProjectDTO> responseBody = testRestTemplate
				.withBasicAuth("root@root.com", "rootroot")
				.exchange("/api/projects/{id}", HttpMethod.PUT, requestBody, ProjectDTO.class, projectDTO.id());
		
		assertEquals(HttpStatus.OK, responseBody.getStatusCode());
	}
	
	@Test
	@DisplayName("Delete Project searching by its ID")
	public void itShouldDeleteTheProjectById() {
		UserCreateDTO user = new UserCreateDTO("User Name", "email@email.com", "senha123", "");
		UserDTO userCreated = userService.create(user);
		ProjectCreateDTO projectCreateDTO = (new ProjectCreateDTO("Project Title", "description X", "", "", Arrays.asList("tag 1"), userCreated.id()));
		ProjectDTO projectDTO = projectService.create(projectCreateDTO);
		
		ResponseEntity<ProjectDTO> response = testRestTemplate
				.withBasicAuth("root@root.com", "rootroot")
				.exchange("/api/projects/{id}", HttpMethod.DELETE, null, ProjectDTO.class, projectDTO.id());
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	@DisplayName("Return User Projects List")
	public void itShouldReturnUserProjectsList() {
		
		//user
		UserCreateDTO user = new UserCreateDTO("User Name", "email@email.com", "senha123", "");
		UserDTO userCreated = userService.create(user);
		
		// project 1
		ProjectCreateDTO projectCreateDTO1 = (new ProjectCreateDTO("Project Title 1", "description X", "", "", Arrays.asList("tag 1"), userCreated.id()));
		ProjectDTO projectDTO1 = projectService.create(projectCreateDTO1);
		
		// project 2
		ProjectCreateDTO projectCreateDTO2 = (new ProjectCreateDTO("Project Title 2", "description X", "", "", Arrays.asList("tag 1"), userCreated.id()));
		ProjectDTO projectDTO2 = projectService.create(projectCreateDTO2);
		
		ResponseEntity<List<ProjectDTO>> response = testRestTemplate
				.withBasicAuth("root@root.com", "rootroot")
				.exchange("/api/projects/user/{id}", HttpMethod.GET, null, new ParameterizedTypeReference<List<ProjectDTO>>() {}, userCreated.id());
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
}
