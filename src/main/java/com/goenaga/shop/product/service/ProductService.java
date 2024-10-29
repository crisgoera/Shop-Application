package com.goenaga.shop.product.service;

import com.goenaga.shop.product.mapper.service.MapperService;
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
    private final MapperService mapperService;

    public List<Product> getProducts() { return productRepository.findAll(); }

    public Product createNewProduct(NewProductDTO productRequest) throws IOException {
        Product newProduct = Product.builder()
                .productId(productIdSequencer())
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .photos(photoService.processPhotos(productRequest.getImageTitles(), productRequest.getImages()))
                .build();
        return productRepository.save(newProduct);
    }

    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }

    public Product updateProduct(String id, Product updateDetails) {
        Optional<Product> product = productRepository.findById(id);

        if (product.isPresent()) {
            Product updatedProduct = mapperService.updateProductDetails(product.get(), updateDetails);
            return productRepository.save(updatedProduct);
        }

        return null;
    }

    public String productIdSequencer() {
        return String.valueOf(getProducts().size() + 1);
    }
}
