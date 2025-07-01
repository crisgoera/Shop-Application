package com.goenaga.shop.photo.config;

import com.cloudinary.Cloudinary;
import jakarta.annotation.Resource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class CloudinaryConfigurationTest {
    @Resource
    private Cloudinary cloudinary;

    @Test
    void cloudinary_CorrectlyLoadsConfigValues() {
        Assertions.assertThat(cloudinary.config.cloudName).isNotNull().isInstanceOf(String.class);
        Assertions.assertThat(cloudinary.config.apiKey).isNotNull().isInstanceOf(String.class);
        Assertions.assertThat(cloudinary.config.apiSecret).isNotNull().isInstanceOf(String.class);
    }
}