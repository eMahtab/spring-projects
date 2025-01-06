package net.mahtabalam.controller;

import net.mahtabalam.exception.FeignClientExceptionResponse;
import net.mahtabalam.model.Product;
import net.mahtabalam.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/catalog/products")
public class CatalogController {

    private final ProductService productService;

    public CatalogController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts(@RequestParam(value = "category", required = false) String category) {
        return productService.getAllProducts(category);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable String id) {
        try {
            Optional<Product> product = productService.getProductById(id);
            return ResponseEntity.ok(product.get());
        } catch (FeignClientExceptionResponse exception) {
            return exception.getResponse();  // Return the Feign error response directly
        }
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable String id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
    }

}