package com.goenaga.shop.product.service;

import com.goenaga.shop.product.model.Photo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PhotoService {
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
