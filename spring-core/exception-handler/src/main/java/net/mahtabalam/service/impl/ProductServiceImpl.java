package net.mahtabalam.service.impl;

import net.mahtabalam.model.Product;
import net.mahtabalam.repository.ProductRepository;
import net.mahtabalam.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public boolean productExists(String id) {
        return productRepository.productExists(id);
    }

    @Override
    public Product addProduct(Product product) {
        return productRepository.addProduct(product);
    }

    @Override
    public Optional<Product> getProductById(String id) {
       return productRepository.getProductById(id);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    @Override
    public Product replaceOrAddProduct(String id, Product product) {
        return productRepository.replaceOrAddProduct(id, product);
    }

    @Override
    public Optional<Product> patchProduct(String id, Map<String, Object> attributes) {
        return productRepository.patchProduct(id, attributes);
    }

    @Override
    public Optional<Product> deleteProduct(String id) {
        return productRepository.deleteProduct(id);
    }

    @Override
    public Optional<List<Product>> findProductsByCategory(String category) {
        return productRepository.findProductsByCategory(category);
    }
}
