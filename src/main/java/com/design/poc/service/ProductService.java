package com.design.poc.service;

import com.design.poc.model.Product;
import com.design.poc.model.elasticsearch.ElasticsearchProduct;
import com.design.poc.repository.ElasticsearchProductRepository;
import com.design.poc.repository.MongoProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    @Qualifier("mongoProductRepo")
    private MongoProductRepository mongoProductRepository;

    @Autowired
    @Qualifier("elasticsearchProductRepo")
    private ElasticsearchProductRepository elasticsearchRepository;

    public Product saveProduct(Product product) {
        // Save to MongoDB
        Product savedProduct = mongoProductRepository.save(product);
        // Index in Elasticsearch
        // Map MongoProduct to ElasticsearchProduct
        ElasticsearchProduct elasticProduct = mapToElasticsearchProduct(savedProduct);

        elasticsearchRepository.save(elasticProduct);
        return savedProduct;
    }

    private ElasticsearchProduct mapToElasticsearchProduct(Product mongoProduct) {
        ElasticsearchProduct elasticProduct = new ElasticsearchProduct();
        elasticProduct.setId(mongoProduct.getId());
        elasticProduct.setName(mongoProduct.getName());
        elasticProduct.setSku(mongoProduct.getSku());
        elasticProduct.setDescription(mongoProduct.getDescription());
        elasticProduct.setMetadata(mongoProduct.getMetadata());
        return elasticProduct;
    }

    @Cacheable(value = "products")
    public List<Product> getAllProducts() {
        logger.info("Fetching all products...");
        List<Product> products = mongoProductRepository.findAll();
        logger.debug("All products fetched successfully.");
        return products;
    }

    @Cacheable(value = "product", key = "#id")
    public Product getProductById(String id) {
        return mongoProductRepository.findById(id).orElse(null);
    }

    @CacheEvict(value = "product", key = "#id")
    public void deleteProduct(String id) {
        mongoProductRepository.deleteById(id);
        // Remove from Elasticsearch
        elasticsearchRepository.deleteById(id);
    }
}
