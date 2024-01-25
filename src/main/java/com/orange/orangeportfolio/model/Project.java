package com.orange.orangeportfolio.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "tb_projects")
public class Project {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message="Title cannot be blank")
	private String title;
	
	@Column(length=600)
	@Size(message="Description cannot be longer than 600 characters")
	private String description;
	
	@Column(length=5000)
	@Size(message="Photo path cannot be longer than 5000 characters")
	private String photo;
	
	private String link;
	
	private List<String> tags;
	
	
	
}
