package com.goenaga.shop.photo.service.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//TODO:
@TestPropertySource("classpath:files")
@SpringBootTest
class UploadServiceImplTest {
    @Value("${istockphoto-1396814518-1024x1024.jpg}")
    private MultipartFile file;

    @InjectMocks
    private UploadServiceImpl uploadService;

    @Test
    void stockImgInit() {
        Assertions.assertThat(file).isNotNull();
    }

    @Test
    void uploadFile() throws IOException {
//        PhotoFile mockedPhotoFile = PhotoFile.builder().photoName("mockedPhoto").file(file).build();
        Map<String, String> uploadResponse = uploadService.uploadFile(file);

        assertThat(uploadResponse).isNotNull();
    }
}