package com.goenaga.shop.product.service;

import com.goenaga.shop.product.model.NewProductDTO;
import com.goenaga.shop.product.model.Product;
import com.goenaga.shop.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final PhotoService photoService;

    public List<Product> getProducts() { return productRepository.findAll(); }

    public Product createNewProduct(NewProductDTO productRequest) throws IOException {
        Product newProduct = Product.builder()
                .productId(productIdSequencer())
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .productImgs(photoService.addPhotos(productRequest.getImageTitles(), productRequest.getImages()))
                .build();
        return productRepository.save(newProduct);
    }

    public Optional<Product> getProductById(int id) {
        return productRepository.findProductById(id);
    }

    public int productIdSequencer() {
        return getProducts().size() + 1;
    }
}
