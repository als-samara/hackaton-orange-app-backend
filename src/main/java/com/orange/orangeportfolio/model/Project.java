package com.orange.orangeportfolio.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
	
	@Column(length=5000)
	@Size(message="Photo path cannot be longer than 5000 characters")
	private String link;
	
	@ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "tb_tags", joinColumns = @JoinColumn(name = "project_id"))
	@Column(name = "tags", nullable = false)
	private List<String> tags;
	
	@ManyToOne
    //@JoinColumn(name="user_id")
    @JsonIgnoreProperties("projects")
	private User user;
	
	
	
}
