package com.goenaga.shop.product.service;

import com.goenaga.shop.product.model.Photo;
import lombok.RequiredArgsConstructor;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
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
        for (int i=0; i < titles.length; i++) {
            productPhotos.add(
                    Photo.builder()
                            .title(titles[i])
                            .image(new Binary(BsonBinarySubType.BINARY, files[i].getBytes()))
                            .build()
            );
        }
        return productPhotos;
    }

}
