package com.goenaga.shop.photo.service;

import com.goenaga.shop.photo.model.Photo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface PhotoService {
    Map<String, String> uploadFile(MultipartFile file) throws IOException;
    Photo createPhotoEntity(Map<String, String> uploadResponse, int productId);
    void savePhoto(Photo photo);
}
