package com.orange.orangeportfolio.dto;

import lombok.experimental.FieldNameConstants;

@FieldNameConstants
public record UserCreateDTO(
		String name,
		String email,
		String password,
		String photo) {}
