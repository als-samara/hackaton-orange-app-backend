package com.orange.orangeportfolio.mapper;

import org.springframework.stereotype.Component;

import com.orange.orangeportfolio.dto.ProjectCreateDTO;
import com.orange.orangeportfolio.dto.ProjectDTO;
import com.orange.orangeportfolio.dto.ProjectUpdateDTO;
import com.orange.orangeportfolio.model.Project;
import com.orange.orangeportfolio.model.User;

@Component
public class ProjectMapper {

	public ProjectDTO toDTO(Project project) {
		var projectDTO = new ProjectDTO(
				project.getId(),
				project.getTitle(),
				project.getDescription(),
				project.getPhoto(),
				project.getLink(),
				project.getTags(),
				project.getUser().getId());
		
		return projectDTO;
	}
	
	public Project toProject(ProjectCreateDTO projectCreateDTO) {
		var user = User
				.builder()
				.id(projectCreateDTO.userId())
				.build();
		
		var project = Project.builder()
				.title(projectCreateDTO.title())
				.description(projectCreateDTO.description())
				.photo(projectCreateDTO.photo())
				.link(projectCreateDTO.link())
				.tags(projectCreateDTO.tags())
				.user(user)
				.build();
		
		return project;
	}
	
	public Project toProject(ProjectUpdateDTO projectUpdateDTO, Project project) {
		project.setTitle(projectUpdateDTO.title());
		project.setDescription(projectUpdateDTO.description());
		project.setPhoto(projectUpdateDTO.photo());
		project.setLink(projectUpdateDTO.link());
		project.setTags(projectUpdateDTO.tags());
		
		return project;
	}
}