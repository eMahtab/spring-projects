package net.mahtabalam.controller;

import net.mahtabalam.client.ProductClient;
import net.mahtabalam.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/catalog")
public class CatalogController {

    private final ProductClient productClient;

    public CatalogController(ProductClient productClient) {
        this.productClient = productClient;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productClient.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable String id) {
        return productClient.getProductById(id);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product newProduct) {
        return productClient.createProduct(newProduct);
    }

    @PutMapping("/{id}")
    public void updateProduct(@PathVariable String id, @RequestBody Product product) {
        product.setId(id);
        productClient.updateProduct(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable String id) {
        productClient.deleteProduct(id);
    }

    @GetMapping("/exchange")
    public ResponseEntity<List<Product>> exchangeGetAllProducts() {
        return productClient.exchangeGetAllProducts();
    }

    @GetMapping("/exchange/{id}")
    public ResponseEntity<Product> exchangeGetProduct(@PathVariable String id) {
        return productClient.exchangeGetProduct(id);
    }

    @PostMapping("/exchange")
    public ResponseEntity<Product> exchangeCreateProduct(@RequestBody Product product) {
        return productClient.exchangeCreateProduct(product);
    }
}
