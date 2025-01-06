package net.mahtabalam.service;

import net.mahtabalam.client.ProductClient;
import net.mahtabalam.model.Product;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductClient productClient;

    public ProductService(ProductClient productClient){
        this.productClient = productClient;
    }

    public List<Product> getAllProducts(String category) {
        return productClient.getAllProducts(category);
    }

    public Optional<Product> getProductById(String id) {
        Product product = productClient.getProductById(id);
        return Optional.ofNullable(product);
    }

    public Product createProduct(Product product) {
        return productClient.createProduct(product);
    }

    public Product patchProduct(String id, Map<String, Object> updates) {
        return productClient.patchProduct(id, updates);
    }

    public Product updateProduct(String id, Product product) {
        return productClient.updateProduct(id, product);
    }

    public void deleteProduct(String id) {
        productClient.deleteProduct(id);
    }
}