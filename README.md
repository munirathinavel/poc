# poc
```
System Design POC
```


Install & Configure Kafka:

o install Kafka in Docker and create the new-product-topic as mentioned, follow these steps:

1. Install Docker (If Not Already Installed)
   Ensure Docker is installed and running on your system. Download it from Docker's official website.

2. Run Kafka Using Docker
   Create a docker-compose.yml file for Kafka and Zookeeper. Place the following configuration in it:

yaml
```
version: '3.8'
services:
zookeeper:
image: confluentinc/cp-zookeeper:7.4.0
container_name: zookeeper
ports:
- "2181:2181"
environment:
ZOOKEEPER_CLIENT_PORT: 2181
ZOOKEEPER_TICK_TIME: 2000

kafka:
image: confluentinc/cp-kafka:7.4.0
container_name: kafka
ports:
- "9092:9092"
environment:
KAFKA_BROKER_ID: 1
KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092
KAFKA_LISTENERS: PLAINTEXT://0.0.0.0:9092
KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
depends_on:
- zookeeper
```
3. Start Kafka and Zookeeper
   Run the following command in the directory where you saved the docker-compose.yml file:

bash

docker-compose up -d
This will start Zookeeper on port 2181 and Kafka on port 9092.

4. Verify Kafka Installation
   Check if Kafka and Zookeeper containers are running:

bash

```
docker ps
```
You should see the zookeeper and kafka containers running.

5. Create a New Kafka Topic
   To create a new topic named new-product-topic, use the following command:

Enter the Kafka container:

bash

```
docker exec -it kafka bash
```
Use the kafka-topics script to create the topic:

bash

```
kafka-topics --create \
--topic new-product-topic \
--bootstrap-server localhost:9092 \
--partitions 1 \
--replication-factor 1
```
Verify that the topic was created:

bash

```
kafka-topics --list --bootstrap-server localhost:9092
```
You should see new-product-topic in the list of topics.

6. Consume Messages from the Topic
   To test if messages are being sent to the topic, use the Kafka console consumer:

Start a consumer:

bash

```
kafka-console-consumer --topic new-product-topic --bootstrap-server localhost:9092
```
Keep this terminal running to see incoming messages.

7. (Optional) Publish Test Messages
   You can also send test messages to the topic using the Kafka console producer:

bash

```
kafka-console-producer --topic new-product-topic --bootstrap-server localhost:9092
```
Type a message and press Enter to send it.

8. Clean Up
   To stop the containers:

bash

docker-compose down


To add functionality to send a message to Kafka when creating a new product in your Spring Boot application, you can follow these steps:

1. Add Kafka Dependencies
   Include Kafka dependencies in your pom.xml:

xml

```
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter</artifactId>
</dependency>
<dependency>
<groupId>org.springframework.kafka</groupId>
<artifactId>spring-kafka</artifactId>
</dependency>
```
2. Configure Kafka Properties
   In your application.properties, add the necessary Kafka configurations:

properties

```
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.consumer.value-deserializer=org.apache.kafka.common.serialization.StringDeserializer
```
Replace localhost:9092 with the address of your Kafka broker.

3. Create a Kafka Producer
   Define a Kafka producer component to send messages:

java

```
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${product.topic.name}")
    private String topicName;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message) {
        kafkaTemplate.send(topicName, message);
    }
}
```
Add the topic name to application.properties:

properties

```
product.topic.name=new-product-topic
```
4. Update the Product Creation Logic
   Modify the product creation logic to send a Kafka message after a product is created:

java

```
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product savedProduct = productService.createProduct(product);

        // Serialize product data as JSON or another format
        String message = String.format("New product created: %s", savedProduct.toString());
        kafkaProducerService.sendMessage(message);

        return ResponseEntity.ok(savedProduct);
    }
}
```
5. Verify Kafka Setup
   Start Kafka: Make sure Kafka and Zookeeper are running. Use tools like Kafka CLI or Kafka UI to verify topic messages.
   bash
   
```
   kafka-topics.sh --create --topic new-product-topic --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
   kafka-console-consumer.sh --topic new-product-topic --bootstrap-server localhost:9092
   ```
   Run Application: Start your Spring Boot application and create a product via your REST API.
6. Monitor the Kafka Topic
   When you create a new product, a message like "New product created: {product details}" should appear in the Kafka topic new-product-topic. Use a Kafka consumer to verify this.

This setup ensures that every new product created in the system triggers a Kafka message, making it suitable for event-driven architectures and microservices communication.

