package com.orange.orangeportfolio.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

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
	
	@ManyToMany(mappedBy = "projects") // falta criar get e set
	private Set<Tag> tags = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	
	public String getLink() {
		return link;
	}

	
	public void setLink(String link) {
		this.link = link;
	}

	
	public Set<Tag> getTags() {
		return tags;
	}

	
	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}
	
	
	
}
