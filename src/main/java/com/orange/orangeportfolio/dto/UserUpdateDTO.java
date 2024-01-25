package com.orange.orangeportfolio.dto;

import lombok.experimental.FieldNameConstants;

@FieldNameConstants
public record UserUpdateDTO(
		String name,
		String email) {}
