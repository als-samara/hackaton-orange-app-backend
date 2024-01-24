package com.orange.orangeportfolio.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {

	private Long id;
	private String nome;
	private String sobrenome;
	private String email;
	private String senha;
}