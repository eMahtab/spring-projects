package net.mahtabalam.client;

import net.mahtabalam.config.FeignClientConfig;
import net.mahtabalam.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "product-client", url = "${products.api.url}", configuration = FeignClientConfig.class)
public interface ProductClient {

    @GetMapping
    List<Product> getAllProducts(@RequestParam(value = "category", required = false) String category);

    @GetMapping("/{id}")
    Product getProductById(@PathVariable("id") String id);

    @PostMapping
    Product createProduct(@RequestBody Product product);

    @PatchMapping("/{id}")
    Product patchProduct(@PathVariable("id") String id, @RequestBody Map<String, Object> updates);

    @PutMapping("/{id}")
    Product updateProduct(@PathVariable("id") String id, @RequestBody Product product);

    @DeleteMapping("/{id}")
    void deleteProduct(@PathVariable("id") String id);

}
