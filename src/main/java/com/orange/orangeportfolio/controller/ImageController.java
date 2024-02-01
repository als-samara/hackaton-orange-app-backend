package com.orange.orangeportfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

import com.orange.orangeportfolio.service.ImageService;

@RestController
@RequestMapping("/image")
public class ImageController {
	
	@Autowired
	private ImageService imageService;
	
	@PostMapping
	public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) throws Exception{
		imageService.uploadImage(file);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
	}
	
	@GetMapping("/{fileName}")
	public ResponseEntity<?> downloadImage(@PathVariable String fileName) throws Exception{
		var imageData = imageService.downloadImage(fileName);
		return ResponseEntity.status(HttpStatus.OK)
				.contentType(MediaType.valueOf(IMAGE_PNG_VALUE))
				.body(imageData);
	}
}
