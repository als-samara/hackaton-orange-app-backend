package com.orange.orangeportfolio.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.orange.orangeportfolio.dto.ProjectCreateDTO;
import com.orange.orangeportfolio.dto.ProjectDTO;
import com.orange.orangeportfolio.dto.ProjectUpdateDTO;
import com.orange.orangeportfolio.mapper.ProjectMapper;
import com.orange.orangeportfolio.repository.ProjectRepository;
import com.orange.orangeportfolio.service.exception.ProjectNotFoundException;
import com.orange.orangeportfolio.service.exception.UserInvalidPropertyException;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private ProjectMapper projectMapper;
	
	public ProjectDTO create(ProjectCreateDTO project) throws HttpClientErrorException{
		
		UserInvalidPropertyException.ThrowIfIsNullOrEmpty(ProjectCreateDTO.Fields.title, project.title());
		UserInvalidPropertyException.ThrowIfIsNullOrEmpty(ProjectCreateDTO.Fields.description, project.description());
		
		var projectEntity = projectMapper.toProject(project);
		projectEntity = projectRepository.save(projectEntity);
		
		var createdProject = projectMapper.toDTO(projectEntity);
		
		return createdProject;
	}
	
	public ProjectDTO update(Long id, ProjectUpdateDTO project) throws HttpClientErrorException{
		
		UserInvalidPropertyException.ThrowIfIsNullOrEmpty(ProjectCreateDTO.Fields.title, project.title());
		UserInvalidPropertyException.ThrowIfIsNullOrEmpty(ProjectCreateDTO.Fields.description, project.description());
		
		var result = projectRepository.findById(id);
		
		ProjectNotFoundException.ThrowIfIsEmpty(result);
		
		var projectEntity = result.get();
		
		projectEntity = projectMapper.toProject(project, projectEntity);
		projectEntity = projectRepository.save(projectEntity);
		
		var updatedProject = projectMapper.toDTO(projectEntity);
		
		return updatedProject;
	}
	
	public ProjectDTO getById(Long id) throws HttpClientErrorException{
		var result = projectRepository.findById(id);
		
		ProjectNotFoundException.ThrowIfIsEmpty(result);
		
		var projectEntity = result.get();
		
		var project = projectMapper.toDTO(projectEntity);
		
		return project;
	}
	
	public List<ProjectDTO> getAll(){
		var projects = projectRepository.findAll();
		
		
		
		var projectsDTO = projects.stream()
				.map(project -> projectMapper.toDTO(project))
				.collect(Collectors.toList());
		
		return projectsDTO;
	}
	
	public void deleteById(Long id) throws HttpClientErrorException{
		var result = projectRepository.findById(id);
		
		ProjectNotFoundException.ThrowIfIsEmpty(result);
		
		projectRepository.deleteById(id);
	}
	
}