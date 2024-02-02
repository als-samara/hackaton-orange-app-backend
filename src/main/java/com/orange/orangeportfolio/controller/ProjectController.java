package com.orange.orangeportfolio.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.orange.orangeportfolio.dto.ProjectDTO;
import com.orange.orangeportfolio.dto.ProjectUpdateDTO;
import com.orange.orangeportfolio.model.Project;
import com.orange.orangeportfolio.model.User;
import com.orange.orangeportfolio.repository.ProjectRepository;
import com.orange.orangeportfolio.repository.UserRepository;
import com.orange.orangeportfolio.service.ProjectService;
import com.orange.orangeportfolio.service.exception.ProjectInvalidPropertyException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
    private HttpServletRequest request;
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private ProjectRepository projectRepository;
	
	@PostMapping
	public ResponseEntity<?> post(@RequestBody Project project) throws HttpClientErrorException{
		
		try {
	    String userEmail = (String) request.getAttribute("userEmail");
	    Optional<User> user = userRepository.findByEmail(userEmail);
		
        ProjectInvalidPropertyException.ThrowIfIsNullOrEmpty("title", project.getTitle());
        ProjectInvalidPropertyException.ThrowIfIsNullOrEmpty("description", project.getDescription());
        ProjectInvalidPropertyException.ThrowIfIsNullOrEmptyList("tags", project.getTags());

	    project.setUser(user.get());
		Project createdProject = projectRepository.save(project);
		
		return ResponseEntity.status(HttpStatus.CREATED).body("Projeto " + createdProject.getTitle() + " criado com sucesso!");
	    
		} catch (ProjectInvalidPropertyException e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	    }
		
	}
	
	@GetMapping("/{id}")
	public Optional<Project> getById(@PathVariable Long id) throws HttpClientErrorException{
		var project = projectRepository.findById(id);
		return project;
	}
	
	@GetMapping("/user/{id}")
	public List<ProjectDTO> getByUserId(@PathVariable Long id) throws HttpClientErrorException{
		var project = projectService.getAllUserById(id);
		return project;
	}
	
	@PutMapping("/{id}")
	public ProjectDTO update(@PathVariable Long id, @RequestBody ProjectUpdateDTO project) throws HttpClientErrorException{
		var updateProject = projectService.update(id, project);
		return updateProject;
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) throws HttpClientErrorException{
		projectService.deleteById(id);
	}
}
