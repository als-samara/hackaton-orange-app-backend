package com.orange.orangeportfolio.model;

import lombok.Data;

@Data
public class UserLogin {
	
	private Long id;
	private String name;
	private String email;
	private String password;
	private String token;
}