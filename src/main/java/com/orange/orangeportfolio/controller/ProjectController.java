package com.orange.orangeportfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orange.orangeportfolio.dto.ProjectCreateDTO;
import com.orange.orangeportfolio.dto.ProjectDTO;
import com.orange.orangeportfolio.dto.ProjectUpdateDTO;
import com.orange.orangeportfolio.service.ProjectService;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;
	
	@PostMapping
	public ProjectDTO post(@RequestBody ProjectCreateDTO project) {
		var createProject = projectService.create(project);
		return createProject;
	}
	
	@GetMapping("/{id}")
	public ProjectDTO getById(@PathVariable Long id) {
		var project = projectService.getById(id);
		return project;
	}
	
	@PutMapping("/{id}")
	public ProjectDTO update(@PathVariable Long id, ProjectUpdateDTO project) {
		var updateProject = projectService.update(id, project);
		return updateProject;
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) throws Exception{
		projectService.deleteById(id);
	}
}
