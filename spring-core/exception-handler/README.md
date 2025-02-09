# Spring @ExceptionHandler

In Spring we can add extra (@ExceptionHandler) methods to any controller to specifically handle exceptions thrown by request handling (@RequestMapping) methods in the same controller. Methods annotated with @ExceptionHandler are used to build a custom error response.

## Error Handling

### GET - Product with given id not found

!["Product with given id not found"](images/error-1.png)

### POST - Required field, name missing in the request body

!["Required field missing in request body"](images/error-2.png)

### DELETE - Product with given id not found

!["Product with given id not found"](images/error-3.png)

## ProductController

**In the below ProductController, note that we are not using try-catch blocks within controller methods, rather we have defined error handler methods annotated with @ExceptionHandler, depending on the type of Exception thrown at runtime the corresponding error handler method will be invoked by Spring.**

**👉 Note : This is already a great improvement over https://github.com/eMahtab/spring-projects/tree/main/spring-core/error-handling approach.**

```java
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@Valid @RequestBody Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                if (error.getCode().equals("NotNull")) {
                    throw new MissingRequiredFieldException(error.getDefaultMessage());
                }
            }
        }
        Product savedProduct = productService.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable String id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));
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
        if(!productService.productExists(id))
            throw new ProductNotFoundException("Product not found with ID: " + id);

        Optional<Product> updatedProduct = productService.patchProduct(id, updates);

        return updatedProduct.map(ResponseEntity::ok)
                    .orElseThrow(() -> new ProductPatchException("Error occurred while updating product :" + id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        if(!productService.productExists(id))
            throw new ProductNotFoundException("Product not found with ID: " + id);

        Optional<Product> deletedProduct = productService.deleteProduct(id);

        return deletedProduct.map(ResponseEntity::ok)
                    .orElseThrow(() -> new ProductDeleteException("Error occurred while deleting product :" + id));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    private ResponseEntity<String> handleProductNotFoundException(ProductNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(MissingRequiredFieldException.class)
    private ResponseEntity<String> handleMissingRequiredFieldException(MissingRequiredFieldException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler({ProductPatchException.class, ProductDeleteException.class})
    private ResponseEntity<String> handleBackendGeneralException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

}
```
