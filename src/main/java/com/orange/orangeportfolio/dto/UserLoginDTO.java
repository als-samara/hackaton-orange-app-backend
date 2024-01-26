package com.orange.orangeportfolio.dto;

import lombok.experimental.FieldNameConstants;

@FieldNameConstants
public record UserLoginDTO(
		String email,
		String password) {}
