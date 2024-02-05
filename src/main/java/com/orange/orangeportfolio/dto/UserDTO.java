package com.orange.orangeportfolio.dto;

import lombok.experimental.FieldNameConstants;

@FieldNameConstants
public record UserDTO(
		String name,
		String email) {}
