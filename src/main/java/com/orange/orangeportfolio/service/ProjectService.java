package com.orange.orangeportfolio.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.orange.orangeportfolio.dto.ProjectCreateDTO;
import com.orange.orangeportfolio.dto.ProjectDTO;
import com.orange.orangeportfolio.dto.ProjectUpdateDTO;
import com.orange.orangeportfolio.mapper.ProjectMapper;
import com.orange.orangeportfolio.model.Project;
import com.orange.orangeportfolio.model.User;
import com.orange.orangeportfolio.repository.ProjectRepository;
import com.orange.orangeportfolio.repository.UserRepository;
import com.orange.orangeportfolio.security.JwtService;
import com.orange.orangeportfolio.service.exception.ProjectInvalidPropertyException;
import com.orange.orangeportfolio.service.exception.ProjectNotFoundException;
import com.orange.orangeportfolio.service.exception.ProjectPropertyTooLongException;
import com.orange.orangeportfolio.service.exception.UserInvalidPropertyException;
import com.orange.orangeportfolio.service.exception.UserNotFoundException;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private ProjectMapper projectMapper;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired JwtService jwtService;
	
	public ResponseEntity<?> createProject(Project project, String userEmail) {
        try {
            Optional<User> user = userRepository.findByEmail(userEmail);

            validateProjectProperties(project);

            project.setUser(user.orElseThrow(() -> new UserNotFoundException()));

            Project createdProject = projectRepository.save(project);

            return ResponseEntity.status(HttpStatus.CREATED).body("Projeto " + createdProject.getTitle() + " criado com sucesso!");

        } catch (ProjectInvalidPropertyException | ProjectPropertyTooLongException | UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    private void validateProjectProperties(Project project) {
        ProjectInvalidPropertyException.ThrowIfIsNullOrEmpty("title", project.getTitle());
        ProjectInvalidPropertyException.ThrowIfIsNullOrEmpty("description", project.getDescription());
        ProjectInvalidPropertyException.ThrowIfIsNullOrEmptyList("tags", project.getTags());
        ProjectPropertyTooLongException.ThrowIfDataIsTooLong("title", project.getTitle(), 80);
        ProjectPropertyTooLongException.ThrowIfDataIsTooLong("description", project.getDescription(), 650);
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
	
	public List<ProjectDTO> getAllUserById(Long userId){
		var projects = projectRepository.findAllByUserId(userId);
		
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