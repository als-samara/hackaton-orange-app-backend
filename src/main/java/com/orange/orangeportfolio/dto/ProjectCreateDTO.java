package com.orange.orangeportfolio.dto;

import java.util.List;

import lombok.experimental.FieldNameConstants;

@FieldNameConstants
public record ProjectCreateDTO(
		String title,
		String description,
		String photo,
		String link,
		List<String> tags,
		Long userId) {}
