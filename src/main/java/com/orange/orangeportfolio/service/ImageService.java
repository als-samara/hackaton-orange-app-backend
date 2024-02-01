package com.orange.orangeportfolio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.orange.orangeportfolio.model.Image;
import com.orange.orangeportfolio.repository.ImageRepository;
import com.orange.orangeportfolio.service.exception.ImageAlreadyUploadedException;
import com.orange.orangeportfolio.service.exception.ImageInvalidPropertyException;
import com.orange.orangeportfolio.service.exception.ImageNotFoundException;
import com.orange.orangeportfolio.utils.ImageUtils;


@Service
public class ImageService {
	
	@Autowired
	private ImageRepository imageRepository;
	
	public void uploadImage(MultipartFile imageFile) throws Exception{
		
		var imageName = imageFile.getOriginalFilename();
		var imageContentType = imageFile.getContentType();
		var imageData = ImageUtils.compressImage(imageFile.getBytes());
		
		ImageInvalidPropertyException.ThrowIfIsNullOrEmpty("imageName", imageName);
		ImageInvalidPropertyException.ThrowIfIsNullOrEmpty("imageContentType", imageContentType);
		
		if(imageData.length <= 0) {
			throw new ImageInvalidPropertyException("data");
		}
		
		var dbImage = imageRepository.findByName(imageName);
		
		if(dbImage.isPresent()) {
			throw new ImageAlreadyUploadedException();
		}
		
		var imageToSave = Image.builder()
				.name(imageName)
				.type(imageContentType)
				.imageData(imageData)
				.build();
		
		imageRepository.save(imageToSave);
	}
	
	public byte[] downloadImage(String imageName) throws Exception{
		var dbImage = imageRepository.findByName(imageName);
		
		ImageNotFoundException.throwsIfNull(imageName, dbImage);
		
		var image = dbImage.get();
		var data = ImageUtils.decompressImage(image.getImageData());
		
		return data;
	}
}
