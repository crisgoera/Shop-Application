package com.goenaga.shop.photo.service.impl;

import com.goenaga.shop.photo.PhotoRepository;
import com.goenaga.shop.photo.model.Photo;
import com.goenaga.shop.photo.model.PhotoFile;
import com.goenaga.shop.photo.service.PhotoService;
import com.goenaga.shop.photo.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {
    private final UploadService uploadService;
    private final PhotoRepository photoRepository;

    @Override
    public void savePhoto(Photo photo) {
        photoRepository.save(photo);
    }

    @Override
    public Map<String, String> uploadFile(PhotoFile file) throws IOException {
        return uploadService.uploadFile(file);
    }

    @Override
    public Photo createPhotoEntity(Map<String, String> uploadResponse, int productId) {
        String photoName = uploadResponse.get("original_filename");
        String photoUrl = uploadResponse.get("url");
        String photoId = uploadResponse.get("asset_id");

        return Photo.builder()
                .photoId(photoId)
                .productId(productId)
                .photoName(photoName)
                .photoUrl(photoUrl)
                .build();
    }
}
