package net.mahtabalam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import net.mahtabalam.model.Product;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Product> getAllProducts() {
        return mongoTemplate.findAll(Product.class);
    }

    public Optional<Product> getProductById(String id) {
        Product product = mongoTemplate.findById(id, Product.class);
        return Optional.ofNullable(product);
    }

    public Product saveProduct(Product product) {
        return mongoTemplate.save(product);
    }

    public void deleteProduct(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, Product.class);
    }

    public Product updatePartialProduct(String id, Map<String, Object> updates) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update();

        updates.forEach((key, value) -> {
            if (key.equals("attributes") && value instanceof Map) {
                Map<String, String> updatedAttributes = (Map<String, String>) value;
                updatedAttributes.forEach((attrKey, attrValue) ->
                        update.set("attributes." + attrKey, attrValue));
            } else {
                update.set(key, value);
            }
        });

        mongoTemplate.updateFirst(query, update, Product.class);
        return mongoTemplate.findById(id, Product.class);
    }

    public List<Product> getProductsByCategory(String category) {
        Query query = new Query(Criteria.where("category").is(category));
        return mongoTemplate.find(query, Product.class);
    }
}
