package net.mahtabalam.controller;

import jakarta.validation.Valid;
import net.mahtabalam.exception.MissingRequiredFieldException;
import net.mahtabalam.exception.ProductDeleteException;
import net.mahtabalam.exception.ProductNotFoundException;
import net.mahtabalam.exception.ProductPatchException;
import net.mahtabalam.model.Product;
import net.mahtabalam.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@Valid @RequestBody Product product, BindingResult bindingResult) {
        try{
            if (bindingResult.hasErrors()) {
                for (FieldError error : bindingResult.getFieldErrors()) {
                    if (error.getCode().equals("NotNull")) {
                        throw new MissingRequiredFieldException(error.getDefaultMessage());
                    }
                }
            }
            Product savedProduct = productService.addProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
        }catch(MissingRequiredFieldException exp){
            return handleMissingRequiredFieldException(exp);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable String id) {
        try {
            Optional<Product> product = productService.getProductById(id);
            return product.map(ResponseEntity::ok)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));
        } catch (ProductNotFoundException e) {
            return handleProductNotFoundException(e);
        }
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> replaceOrAddProduct(@PathVariable String id, @RequestBody Product product) {
        return ResponseEntity.ok(productService.replaceOrAddProduct(id, product));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchProduct(@PathVariable String id, @RequestBody Map<String, Object> updates) {
        try {
            if(!productService.productExists(id))
              throw new ProductNotFoundException("Product not found with ID: " + id);

            Optional<Product> updatedProduct = productService.patchProduct(id, updates);
            return updatedProduct.map(ResponseEntity::ok)
                    .orElseThrow(() -> new ProductPatchException("Error occurred while updating product :" + id));
        } catch(ProductNotFoundException notFoundException) {
            return handleProductNotFoundException(notFoundException);
        } catch(ProductPatchException patchException) {
            return handleBackendGeneralException(patchException);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        try {
             if(!productService.productExists(id))
                throw new ProductNotFoundException("Product not found with ID: " + id);

            Optional<Product> deletedProduct = productService.deleteProduct(id);
            return deletedProduct.map(ResponseEntity::ok)
                    .orElseThrow(() -> new ProductDeleteException("Error occurred while deleting product :" + id));
        } catch (ProductNotFoundException notFoundException) {
            return handleProductNotFoundException(notFoundException);
        } catch (ProductDeleteException deleteException) {
            return handleBackendGeneralException(deleteException);
        }
    }

    private ResponseEntity<String> handleProductNotFoundException(ProductNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    private ResponseEntity<String> handleMissingRequiredFieldException(MissingRequiredFieldException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    private ResponseEntity<String> handleBackendGeneralException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

}
