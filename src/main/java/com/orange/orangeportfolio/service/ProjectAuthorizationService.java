package com.orange.orangeportfolio.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.orange.orangeportfolio.model.Project;
import com.orange.orangeportfolio.model.User;
import com.orange.orangeportfolio.repository.ProjectRepository;
import com.orange.orangeportfolio.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ProjectAuthorizationService {
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private ProjectRepository projectRepository;
	
	public boolean canDeleteProject(Authentication authentication, Long projectId) {
        
		String userEmail = (String) request.getAttribute("userEmail");
	    Optional<User> user = userRepository.findByEmail(userEmail);
	    Optional<Project> project = projectRepository.findById(projectId);
		
        if(user.get().getProjects().contains(project.get()))
        	return true;
        else
        	return false;
		
    }
	
	public boolean canUpdateProject(Authentication authentication, Long projectId) {
        
		String userEmail = (String) request.getAttribute("userEmail");
	    Optional<User> user = userRepository.findByEmail(userEmail);
	    Optional<Project> project = projectRepository.findById(projectId);
		
        if(user.get().getProjects().contains(project.get()))
        	return true;
        else
        	return false;
		
    }
}
