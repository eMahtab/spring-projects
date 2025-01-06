# Feign Client example with Exception Handling

This demo project uses FeignClient to make HTTP calls. We first run [Rest API MongoDB project](https://github.com/eMahtab/spring-projects/tree/main/spring-boot/rest-api-mongodb) on default port 8080. We then run this project (feign-client-example) on port 8090, which makes API calls against the API exposed by Rest API MongoDB project.

## Project :

!["Project"](images/project.png?raw=true)

## Controller :
```java
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
```

## ProductService

```java
@Service
public class ProductService {

    private final ProductClient productClient;

    public ProductService(ProductClient productClient){
        this.productClient = productClient;
    }

    public List<Product> getAllProducts(String category) {
        return productClient.getAllProducts(category);
    }

    public Optional<Product> getProductById(String id) {
        Product product = productClient.getProductById(id);
        return Optional.ofNullable(product);
    }

    public Product createProduct(Product product) {
        return productClient.createProduct(product);
    }

    public Product patchProduct(String id, Map<String, Object> updates) {
        return productClient.patchProduct(id, updates);
    }

    public Product updateProduct(String id, Product product) {
        return productClient.updateProduct(id, product);
    }

    public void deleteProduct(String id) {
        productClient.deleteProduct(id);
    }
}
```

## FeignClient

```java
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
```

## Application

```java
@SpringBootApplication
@EnableFeignClients
public class FeignClientExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeignClientExampleApplication.class, args);
	}

}
```

## Feign client custom error Decoder

```java
public class CustomErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == HttpStatus.NOT_FOUND.value()) {
            try {
                Map<String, Object> map = objectMapper.readValue(response.body().asInputStream(), Map.class);
                String errorMessage = map.get("error").toString();
                return new FeignClientExceptionResponse(ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", errorMessage)));
            } catch (IOException e) {
                return new FeignClientExceptionResponse(ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Resource not found")));
            }
        }
        return new Default().decode(methodKey, response);
    }
}
```
