package com.goenaga.shop.product.mapper.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goenaga.shop.product.model.Product;
import org.springframework.stereotype.Service;
import java.util.Map;


@Service
public class MapperService {
    public Product updateProductDetails (Product product, Product updateDetails) {
        ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

        Map<String, Object> updateMap = mapper.convertValue(updateDetails, new TypeReference<Map<String, Object>>() {});
        Map<String, Object> productMap = mapper.convertValue(product, new TypeReference<Map<String, Object>>() {});

        updateMap.forEach((key, value) -> productMap.put(key, value));

        return mapper.convertValue(productMap, Product.class);
    }
}
