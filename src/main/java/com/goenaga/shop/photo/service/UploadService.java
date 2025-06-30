package com.goenaga.shop.photo.service;

import com.goenaga.shop.photo.model.PhotoFile;

import java.io.IOException;
import java.util.Map;

public interface UploadService {
    Map<String, String> uploadFile(PhotoFile photoFile) throws IOException;
}
