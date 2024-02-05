package com.orange.orangeportfolio.dto;

import lombok.experimental.FieldNameConstants;

@FieldNameConstants
public record ImageDTO(
		byte[] image_data,
		String name,
		String type,
		Long user_id) {}
