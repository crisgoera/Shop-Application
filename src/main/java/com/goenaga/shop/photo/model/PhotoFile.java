package com.goenaga.shop.photo.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
public class PhotoFile {
    private String photoName;
    private MultipartFile file;
}
