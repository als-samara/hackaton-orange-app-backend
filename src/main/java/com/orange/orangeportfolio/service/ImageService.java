package com.orange.orangeportfolio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.orange.orangeportfolio.model.Image;
import com.orange.orangeportfolio.repository.ImageRepository;
import com.orange.orangeportfolio.utils.ImageUtils;


@Service
public class ImageService {
	
	@Autowired
	private ImageRepository imageRepository;
	
	public void uploadImage(MultipartFile imageFile) throws Exception{
		var imageToSave = Image.builder()
				.name(imageFile.getOriginalFilename())
				.type(imageFile.getContentType())
				.imageData(ImageUtils.compressImage(imageFile.getBytes()))
				.build();
		
		imageRepository.save(imageToSave);
	}
	
	public byte[] downloadImage(String imageName) throws Exception{
		var dbImage = imageRepository.findByName(imageName);
		
		if(dbImage.isEmpty()) {
			throw new Exception();
		}
		var image = dbImage.get();
		var data = ImageUtils.decompressImage(image.getImageData());
		
		return data;
	}
}
