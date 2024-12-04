//package com.design.poc.model.mongo;
//
//import com.design.poc.listener.ProductListener;
//import jakarta.persistence.EntityListeners;
//import lombok.Data;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;
//
//import java.io.Serializable;
//import java.util.Map;
//
//@EntityListeners(ProductListener.class)
//@Document(collection = "product")
//@Data
//public class MongoProduct implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//    @Id
//    private String id;
//
//    private String name;
//
//    private String sku;
//
//    private String description;
//    private Map<String, Object> metadata;
//}
