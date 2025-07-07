package com.goenaga.shop.photo.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.goenaga.shop.photo.service.UploadService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Map;
import java.util.Objects;


@Service
@RequiredArgsConstructor
@PropertySource("classpath:cloudinary.properties")
public class UploadServiceImpl implements UploadService {
    private final Cloudinary cloudinaryService;

    @Value("cloudinary.folder_name")
    private String folderName;
    @Value("cloudinary.path_name")
    private String pathName;

    @Override
    public Map<String, String> uploadFile(MultipartFile multipartFile) throws IOException {
        if (!Objects.equals(multipartFile.getContentType(), "image/jpeg")) {
            throw new IOException("Wrong file type");
        }

//        Multipart needs to be processed into File object to be uploaded
        File tempFile = new File("src/main/resources/tmp/" + multipartFile.getOriginalFilename() + ".tmp");

        try (OutputStream os = new FileOutputStream(tempFile)) {
            os.write(multipartFile.getBytes());
        }

        try {
            return cloudinaryService.uploader().upload(
                    tempFile, ObjectUtils.asMap(folderName, pathName)
            );
        } catch (IOException e) {
            throw new IOException("Failed to upload the file");
        } finally {
            tempFile.delete();
        }
    }
}
