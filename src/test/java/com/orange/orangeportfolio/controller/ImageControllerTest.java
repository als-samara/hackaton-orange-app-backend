package com.orange.orangeportfolio.controller;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import com.orange.orangeportfolio.dto.UserCreateDTO;
import com.orange.orangeportfolio.model.Image;
import com.orange.orangeportfolio.repository.ImageRepository;
import com.orange.orangeportfolio.repository.UserRepository;
import com.orange.orangeportfolio.service.ImageService;
import com.orange.orangeportfolio.service.UserService;
import com.orange.orangeportfolio.service.exception.ImageAlreadyUploadedException;
import com.orange.orangeportfolio.service.exception.ImageNotFoundException;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ImageControllerTest {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private ImageService imageService;
    
    @Autowired    
    private ImageController imageController;
    
    @BeforeEach
	void start() {
		userRepository.deleteAll();
		userService.create(new UserCreateDTO("Root", "root@root.com", "rootroot", ""));
	}
    
    @Test
    @DisplayName("Upload Image")
    void testUploadImageSuccess() throws Exception {

        MockMultipartFile imageFile = new MockMultipartFile("image", "test-image.jpg", "image/jpeg", "conteúdo da imagem".getBytes());
        
        when(imageRepository.findByName(any())).thenReturn(Optional.empty());

        imageService.uploadImage(imageFile);

        verify(imageRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Prevent duplicated Image")
    void testUploadImageImageAlreadyUploadedException() throws Exception {
        MockMultipartFile imageFile = new MockMultipartFile("image", "test-image.jpg", "image/jpeg", "conteúdo da imagem".getBytes());
        
        when(imageRepository.findByName(any())).thenReturn(Optional.of(mock(Image.class)));
        
        assertThrows(ImageAlreadyUploadedException.class, () -> imageService.uploadImage(imageFile));
        
        verify(imageRepository, never()).save(any());
    }
	
    @Test
    void testDownloadImageSuccess() throws Exception {
        // Mock do ImageService para retornar dados de imagem
        byte[] expectedData = "mockedImageData".getBytes();
        when(imageService.downloadImage(any())).thenReturn(expectedData);

        // Executar o método do controlador
        ResponseEntity<?> responseEntity = imageController.downloadImage("test-image.jpg");

        // Verificar se o resultado é uma resposta HTTP OK
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "O status HTTP deve ser OK");

        // Verificar se os dados da imagem não são nulos
        assertNotNull(responseEntity.getBody(), "Os dados da imagem não devem ser nulos");

        // Verificar se os dados da imagem são os esperados
        byte[] actualData = (byte[]) responseEntity.getBody();
        assertArrayEquals(expectedData, actualData, "Os dados da imagem não são os esperados");
    }
    

    @Test
    void testDownloadImageImageNotFoundException() {
        // Mock do ImageRepository para retornar vazio (imagem não encontrada)
        when(imageRepository.findByName(any())).thenReturn(Optional.empty());

        // Executar o método de serviço e esperar que a exceção seja lançada
        assertThrows(ImageNotFoundException.class, () -> imageService.downloadImage("non-existent-image.jpg"));

        // Verificar se o método do repositório findByName foi chamado
        verify(imageRepository, times(1)).findByName("non-existent-image.jpg");
    }

}
