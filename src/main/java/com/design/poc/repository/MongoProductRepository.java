package com.design.poc.repository;

import com.design.poc.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository("mongoProductRepo")
public interface MongoProductRepository extends MongoRepository<Product, String> {
}