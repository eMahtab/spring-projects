package net.mahtabalam.repository.impl;

import net.mahtabalam.model.Product;
import net.mahtabalam.repository.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryProductRepository implements ProductRepository {

    private final Map<String,Product> datastore = new ConcurrentHashMap<>();

    @Override
    public boolean productExists(String id) {
        return datastore.containsKey(id);
    }

    public Product addProduct(Product product) {
        var productId = UUID.randomUUID().toString();
        product.setId(productId);
        datastore.putIfAbsent(productId, product);
        return datastore.get(productId);
    }

    @Override
    public Optional<Product> getProductById(String id) {
        return Optional.ofNullable(datastore.get(id));
    }

    @Override
    public List<Product> getAllProducts() {
        return new ArrayList<>(datastore.values());
    }

    @Override
    public Product replaceOrAddProduct(String id, Product newProduct) {
        newProduct.setId(id);
        datastore.put(id, newProduct);
        return datastore.get(id);
    }

    @Override
    public Optional<Product> patchProduct(String id, Map<String, Object> params) {
        if (datastore.containsKey(id)) {
            Product product = datastore.get(id);

            params.forEach((key, value) -> {
                if (value instanceof String && List.of("name", "category", "description").contains(key)) {
                    switch (key) {
                        case "name" -> product.setName((String) value);
                        case "category" -> product.setCategory((String) value);
                        case "description" -> product.setDescription((String) value);
                    }
                } else if ("price".equals(key) && value instanceof Number) {
                    product.setPrice(((Number) value).doubleValue());
                } else if ("attributes".equals(key) && value instanceof Map) {
                    Map<String, String> updatedAttributes = (Map<String, String>) value;
                    product.setAttributes(updatedAttributes);
                }
            });

            return Optional.of(product);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Product> deleteProduct(String id) {
        if(datastore.containsKey(id)){
            Product deletedProduct = datastore.get(id);
            datastore.remove(id);
            return Optional.of(deletedProduct);
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<Product>> findProductsByCategory(String category) {
        if(!category.isEmpty()) {
            List<Product> categoryProducts = datastore.values().stream()
                    .filter(product -> product.getCategory().equalsIgnoreCase(category))
                    .toList();
            return Optional.of(categoryProducts);
        }
        return Optional.of(new ArrayList<>(datastore.values()));
    }
}
