package net.mahtabalam.client;

import net.mahtabalam.model.Product;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
