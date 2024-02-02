package com.orange.orangeportfolio.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.orange.orangeportfolio.dto.ProjectDTO;
import com.orange.orangeportfolio.dto.ProjectUpdateDTO;
import com.orange.orangeportfolio.model.Project;
import com.orange.orangeportfolio.repository.ProjectRepository;
import com.orange.orangeportfolio.service.ProjectService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/projects")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
    private ProjectRepository projectRepository;
	
	@PostMapping("/create")
    public ResponseEntity<?> createProject(@RequestBody ProjectCreateDTO project, HttpServletRequest request) {
        String userEmail = (String) request.getAttribute("userEmail");
        return projectService.createProject(project, userEmail);
    }
	
	@GetMapping("/project/{id}")
	public Optional<Project> getById(@PathVariable Long id) throws HttpClientErrorException{
		var project = projectRepository.findById(id);
		return project;
	}
	
	@GetMapping("/all")
	public List<Project> getAll() throws HttpClientErrorException{
		return projectRepository.findAll();
	}
	
	@GetMapping("/user/{id}")
	public List<ProjectDTO> getByUserId(@PathVariable Long id) throws HttpClientErrorException{
		var project = projectService.getAllUserById(id);
		return project;
	}
	
	@PreAuthorize("@projectAuthorizationService.canUpdateProject(authentication, #id)")
	@PutMapping("/update/{id}")
	public ProjectDTO update(@PathVariable Long id, @RequestBody ProjectUpdateDTO project) throws HttpClientErrorException{
		var updateProject = projectService.update(id, project);
		return updateProject;
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("@projectAuthorizationService.canUpdateProject(authentication, #id)")
	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable Long id) throws HttpClientErrorException{
			projectService.deleteById(id);
	}
	
}
