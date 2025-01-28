# REST API with MongoDB

## Running the code :

To run the code, first start the mongodb (either as a docker container, or install mongodb on your machine). There is a docker-compose.yml file which defines mongodb and mongo-express (one of the many GUI client for MongoDB), just run **`docker compose up`**

After that run `RestApiMongodbApplication` class inside `net.mahtabalam` package

## Project :

!["Spring project"](images/spring-project.png?raw=true)

### ProductController 
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

## POST Endpoint : /api/v1/products

!["Post Endpoint"](images/post-endpoint.png?raw=true)

## Mongo Express : store database, products collection

!["Mongo Express"](images/mongo-express.png?raw=true)


# Let's add openapi documentation for our API

We can use `springdoc-openapi-starter-webmvc-ui` to add swagger API documentation for our API endpoints.

```xml
<dependency>
   <groupId>org.springdoc</groupId>
   <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
   <version>2.8.3</version>
</dependency>
```

!["Swagger UI"](images/swagger-ui.png?raw=true)

# References :

1. https://www.baeldung.com/spring-rest-openapi-documentation
