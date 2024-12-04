package com.design.poc.model.elasticsearch;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Map;

@Document(indexName = "product")
@Data
public class ElasticsearchProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Text)
    private String sku;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Object)
    private Map<String, Object> metadata;
}
