package net.mahtabalam.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import net.mahtabalam.model.Product;
import net.mahtabalam.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
class ProductController {
    @Autowired
    private ProductService service;


    @GetMapping
    public List<Product> getAllProducts(@RequestParam(required = false) String category) {
        if (category != null) {
            return service.getProductsByCategory(category);
        }
        return service.getAllProducts();
    }

    @Operation(summary = "Get product by id", description = "Fetch product by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Product.class),
                            examples = @ExampleObject(value = "{\"id\":\"6798424b802e4e48c21a277a\",\"name\":\"GARMIN Forerunner 55\",\"category\":\"smartwatch\"}"))),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable String id) {
        Optional<Product> product = service.getProductById(id);
        return product.isPresent() ? ResponseEntity.ok(product.get())
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Product not found"));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> checkProductExists(@PathVariable String id) {
        boolean exists = service.getProductById(id).isPresent();
        return exists ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return service.saveProduct(product);
    }

    @PatchMapping("/{id}")
    public Product patchProduct(@PathVariable String id, @RequestBody Map<String, Object> updates) {
        return service.updatePartialProduct(id, updates);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable String id, @RequestBody Product product) {
        product.setId(id);
        return service.saveProduct(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable String id) {
        service.deleteProduct(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> optionsForProduct(@PathVariable String id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccessControlAllowMethods(Arrays.asList(
                HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE,
                HttpMethod.HEAD, HttpMethod.OPTIONS , HttpMethod.PATCH));
        headers.setAccessControlAllowOrigin("*");
        return ResponseEntity.ok().headers(headers).build();
    }
}