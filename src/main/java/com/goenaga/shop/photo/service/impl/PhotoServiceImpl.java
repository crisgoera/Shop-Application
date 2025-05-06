package com.goenaga.shop.photo.service.impl;

import com.goenaga.shop.photo.model.Photo;
import com.goenaga.shop.photo.service.PhotoService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@Service
public class PhotoServiceImpl implements PhotoService {
    public List<Photo> processPhotos(String[] titles, MultipartFile[] files) throws IOException {
        if (titles == null) { return null; }

        List<Photo> productPhotos = null;
        for (int i=0; i < files.length; i++) {
            productPhotos.add(
                    Photo.builder()
                            .title(titles[i])
//                            .image(null)  //TODO: SWITCH IMAGE UPLOADING TO A JPA DB TYPE
                            .build()
            );
        }
        return productPhotos;
    }

}
