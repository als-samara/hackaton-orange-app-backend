package com.orange.orangeportfolio.dto;

import java.util.List;

import com.orange.orangeportfolio.model.User;

import lombok.experimental.FieldNameConstants;

@FieldNameConstants
public record ProjectDTO(
		Long id,
		String title,
		String description,
		String photo,
		String link,
		List<String> tags,
		Long userId) {}