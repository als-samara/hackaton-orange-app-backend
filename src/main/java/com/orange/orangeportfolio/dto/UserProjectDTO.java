package com.orange.orangeportfolio.dto;

import java.util.List;

import lombok.experimental.FieldNameConstants;

@FieldNameConstants
public record UserProjectDTO(
		Long id,
		String name,
		String email,
		String photo,
		List<ProjectDTO> projects) {}
