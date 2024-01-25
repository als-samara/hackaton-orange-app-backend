package com.orange.orangeportfolio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orange.orangeportfolio.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
	public Optional<Project> findAllByTitleContainingIgnoreCase(String title);
}
