package com.orange.orangeportfolio.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tb_google_users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoogleLoginUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter private Long id;
	
	@JsonProperty
	@NotBlank(message = "Name cannot be blank")
	private String name;
	
	@JsonProperty
	@Schema(example = "email@email.com.br")
	@Email
	private String email;
	
	@JsonIgnoreProperties("googleLoginUser")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "googleLoginUser", cascade = CascadeType.ALL)
	private List<Project> projects;
	
	@JsonIgnoreProperties("googleLoginUser")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "googleLoginUser", cascade = CascadeType.REMOVE)
	private List<Image> images;
	
	@JsonProperty
	private String profilePicture;
}
