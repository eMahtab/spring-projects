package net.mahtabalam.service;

import net.mahtabalam.model.Product;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductService {

    public List<Product> getAllProducts();
    public Optional<Product> getProductById(String id);
    public Product saveProduct(Product product);
    public void deleteProduct(String id);
    public Product updatePartialProduct(String id, Map<String, Object> updates);
    List<Product> getProductsByCategory(String category);
}


