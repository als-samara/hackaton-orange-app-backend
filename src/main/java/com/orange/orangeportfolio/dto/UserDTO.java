package com.orange.orangeportfolio.dto;

import lombok.experimental.FieldNameConstants;

@FieldNameConstants
public record UserDTO(
		Long id,
		String name,
		String email,
		String photo) {}
