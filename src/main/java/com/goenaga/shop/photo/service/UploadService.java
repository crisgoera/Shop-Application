package com.goenaga.shop.photo.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface UploadService {
    Map<String, String> uploadFile(MultipartFile file) throws IOException;
}
