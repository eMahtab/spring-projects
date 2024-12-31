# Spring RestTemplate

Synchronous client to perform HTTP requests, exposing a simple, template method API over underlying HTTP client libraries such as the JDK HttpURLConnection, Apache HttpComponents, and others. RestTemplate offers templates for common scenarios by HTTP method, **in addition to the generalized exchange and execute methods** that support less frequent cases.

RestTemplate is typically used as a shared component. However, its configuration does not support concurrent modification, and as such its configuration is typically prepared on startup. If necessary, you can create multiple, differently configured RestTemplate instances on startup. Such instances may use the same underlying ClientHttpRequestFactory if they need to share HTTP client resources.

**NOTE: As of Spring 6.1, RestClient offers a more modern API for synchronous HTTP access. For asynchronous and streaming scenarios, consider the reactive WebClient.**

RestTemplate and RestClient share the same infrastructure (i.e. request factories, request interceptors and initializers, message converters, etc.), so any improvements made therein are shared as well. However, RestClient is the focus for new higher-level features.

## Project :

!["Project"](images/project.png?raw=true)

## Client :
```java
@Service
public class ProductClient {

    private final RestTemplate restTemplate;
    private final String BASE_URL = "http://localhost:8080/api/v1/products";

    public ProductClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Product> getAllProducts() {
        Product[] products = restTemplate.getForObject(BASE_URL, Product[].class);
        return products != null ? Arrays.asList(products) : new ArrayList<>();
    }

    public Product getProductById(String id) {
        return restTemplate.getForObject(BASE_URL + "/" + id, Product.class);
    }

    public ResponseEntity<Product> createProduct(Product newProduct) {
        return restTemplate.postForEntity(BASE_URL, newProduct, Product.class);
    }

    public void updateProduct(Product product) {
        restTemplate.put(BASE_URL + "/" + product.getId(), product);
    }

    public void deleteProduct(String id) {
        restTemplate.delete(BASE_URL + "/" + id);
    }

    public ResponseEntity<Product> exchangeGetProduct(String id) {
        String url = BASE_URL + "/" + id;
        HttpEntity<?> entity = new HttpEntity<>(new HttpHeaders());
        return restTemplate.exchange(url, HttpMethod.GET, entity, Product.class);
    }

    public ResponseEntity<Product> exchangeCreateProduct(Product product) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Product> entity = new HttpEntity<>(product, headers);
        return restTemplate.exchange(BASE_URL, HttpMethod.POST, entity, Product.class);
    }

    public ResponseEntity<List<Product>> exchangeGetAllProducts() {
        HttpEntity<?> entity = new HttpEntity<>(new HttpHeaders());
        ResponseEntity<Product[]> response = restTemplate.exchange(BASE_URL, HttpMethod.GET, entity, Product[].class);
        List<Product> productList = Arrays.asList(response.getBody());
        return new ResponseEntity<>(productList, response.getStatusCode());
    }
}
```



# References :

https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/client/RestTemplate.html
