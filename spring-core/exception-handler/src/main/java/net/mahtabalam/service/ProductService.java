package net.mahtabalam.service;

import net.mahtabalam.model.Product;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductService {

    boolean productExists(String id);
    Product addProduct(Product product);
    Optional<Product> getProductById(String id);
    List<Product> getAllProducts();

    Product replaceOrAddProduct(String id, Product product);
    Optional<Product> patchProduct(String id, Map<String, Object> attributes);

    Optional<Product> deleteProduct(String id);
    Optional<List<Product>> findProductsByCategory(String category);
}
