package com.design.poc.repository;

import com.design.poc.model.Product;
import com.design.poc.model.elasticsearch.ElasticsearchProduct;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("elasticsearchProductRepo")
public interface ElasticsearchProductRepository extends ElasticsearchRepository<ElasticsearchProduct, String> {
    List<ElasticsearchProduct> findByName(String name);
}
