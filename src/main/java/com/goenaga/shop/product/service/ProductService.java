package com.goenaga.shop.product.service;

import com.goenaga.shop.product.mapper.service.MapperService;
import com.goenaga.shop.product.model.NewProductDTO;
import com.goenaga.shop.product.model.Product;
import com.goenaga.shop.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.Currency;
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
                .price(roundPrice(productRequest.getPrice()))
                .currency(Currency.getInstance("EUR"))
                .photos(photoService.processPhotos(productRequest.getImageTitles(), productRequest.getImages()))
                .build();
        return productRepository.save(newProduct);
    }

    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }

    public Product updateProduct(Product product, Product updateDetails) {
        updateDetails.setPrice(roundPrice(updateDetails.getPrice()));
        Product updatedProduct = mapperService.updateProductDetails(product, updateDetails);
        return productRepository.save(updatedProduct);
    }

    public void removeProduct(String id) {
        Optional<Product> foundProduct = getProductById(id);
        if (foundProduct.isPresent()) {
            productRepository.delete(foundProduct.get());
        }
    }

    private String productIdSequencer() {
        try {
            int lastIssuedId = Integer.parseInt(productRepository.findTopByOrderByProductIdDesc().getProductId());
            return String.valueOf(lastIssuedId + 1);
        } catch (NullPointerException nullPointer) {
            return "1";
        }
    }

    private double roundPrice(double price) {
        return Math.round(price * 100.00)/100.00;
    }
}
