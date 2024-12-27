# REST API with MongoDB

### Running the code :

To run the code, first start the mongodb (either as a docker container, or install mongodb on your machine). There is a docker-compose.yml file which defines mongodb and mongo-express (one of the many GUI client for MongoDB), just run **`docker compose up`**

After that just run `RestApiMongodbApplication` class inside `net.mahtabalam` package

```java
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

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable String id) {
        return service.getProductById(id).orElseThrow(() -> new RuntimeException("Product not found"));
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
```

