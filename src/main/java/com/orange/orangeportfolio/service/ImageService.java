package com.orange.orangeportfolio.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.orange.orangeportfolio.dto.ImageDTO;
import com.orange.orangeportfolio.mapper.ImageMapper;
import com.orange.orangeportfolio.model.Image;
import com.orange.orangeportfolio.model.User;
import com.orange.orangeportfolio.repository.ImageRepository;
import com.orange.orangeportfolio.repository.UserRepository;
import com.orange.orangeportfolio.service.exception.ImageAlreadyUploadedException;
import com.orange.orangeportfolio.service.exception.ImageInvalidPropertyException;
import com.orange.orangeportfolio.service.exception.ImageNotFoundException;
import com.orange.orangeportfolio.service.exception.UserNotFoundException;
import com.orange.orangeportfolio.service.exception.UserUnauthorizedException;
import com.orange.orangeportfolio.utils.ImageUtils;

import jakarta.servlet.http.HttpServletRequest;


@Service
public class ImageService {
	
	@Autowired
	private ImageRepository imageRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ImageMapper imageMapper;
	
	@Autowired
	HttpServletRequest request;
	
	
	public void uploadImage(MultipartFile imageFile, String userEmail) throws Exception{
		
		var user = userRepository.findByEmail(userEmail);
		
		UserNotFoundException.ThrowIfIsEmpty(user);
		
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
				.user(user.get())
				.build();
		
		imageRepository.save(imageToSave);
	}
	
	public byte[] downloadImage(String imageName) throws Exception{
		var dbImage = imageRepository.findByName(imageName);
		
		ImageNotFoundException.throwsIfNull(imageName, dbImage);
		
		var image = dbImage.get();
		
		var result = userRepository.findById(image.getUser().getId());
		
		UserNotFoundException.ThrowIfIsEmpty(result);
		
		ValidateUser(result.get());
		
		var data = ImageUtils.decompressImage(image.getImageData());
		
		return data;
	}
	
	public List<ImageDTO> getByImages(String userEmail) throws Exception{
		var result = userRepository.findByEmail(userEmail);
		
		UserNotFoundException.ThrowIfIsEmpty(result);
		
		var user = result.get();
		var userImages = imageRepository.findAllByUserId(user.getId());
		
		var imagesDTO = new ArrayList<ImageDTO>();
		
		for(var image : userImages) {
		
			var dto = imageMapper.toDTO(image);
			imagesDTO.add(dto);
		}		
		return imagesDTO;
	}
	
	private void ValidateUser(User user) {
		var userEmail = request
				.getAttribute("userEmail")
				.toString();
		
		var isSameUser = user.getEmail().equals(userEmail);
		
		if(!isSameUser) {
			throw new UserUnauthorizedException();
		}
	}

}
