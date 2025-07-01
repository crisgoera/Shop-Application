package com.goenaga.shop.photo.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.goenaga.shop.photo.PhotoRepository;
import com.goenaga.shop.photo.model.PhotoFile;
import com.goenaga.shop.photo.service.UploadService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;


@Service
@RequiredArgsConstructor
@PropertySource("classpath:cloudinary.properties")
public class UploadServiceImpl implements UploadService {
    private final Cloudinary cloudinaryService;
    private final PhotoRepository photoRepository;

    @Value("cloudinary.folder_name")
    private String folderName;
    @Value("cloudinary.path_name")
    private String pathName;

    @Override
    public Map<String, String> uploadFile(PhotoFile photoFile) throws IOException {
        try {
            return cloudinaryService.uploader().upload(
                    photoFile.getFile(), ObjectUtils.asMap(folderName, pathName)
            );
        } catch (IOException e) {
            throw new IOException("Failed to upload the file");
        }
    }
}
