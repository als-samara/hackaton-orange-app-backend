package com.orange.orangeportfolio.mapper;

import org.springframework.stereotype.Component;

import com.orange.orangeportfolio.dto.ImageDTO;
import com.orange.orangeportfolio.model.Image;
import com.orange.orangeportfolio.utils.ImageUtils;

@Component
public class ImageMapper {
	
	public ImageDTO toDTO(Image image) throws Exception {
		
		var decompressedData = ImageUtils.decompressImage(image.getImageData());
		
		var imageDTO = new ImageDTO(
				decompressedData,
				image.getName(),
				image.getType(),
				image.getUser().getId());
		
		return imageDTO;
	}
}
