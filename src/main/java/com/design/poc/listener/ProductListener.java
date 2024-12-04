package com.design.poc.listener;

import com.design.poc.model.elasticsearch.ElasticsearchProduct;
import com.design.poc.repository.ElasticsearchProductRepository;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductListener {
    @Autowired
    private ElasticsearchProductRepository elasticsearchRepository;

    @PostPersist
    @PostUpdate
    public void syncToElasticsearch(ElasticsearchProduct product) {
        elasticsearchRepository.save(product);
    }

    @PostRemove
    public void removeFromElasticsearch(ElasticsearchProduct product) {
        elasticsearchRepository.deleteById(product.getId());
    }
}