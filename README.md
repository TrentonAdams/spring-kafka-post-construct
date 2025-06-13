# Spring Kafka Demo - @PostConstruct Issue

This project demonstrates the issue with Spring Kafka's automatic topic creation when using `@PostConstruct` as described in [Spring Kafka Issue #3954](https://github.com/spring-projects/spring-kafka/issues/3954).

## Prerequisites

- Java 17 or higher
- Maven
- Docker and Docker Compose (for running Kafka)

## Running the Demo

### Using Docker Compose (Recommended)

1. Start Kafka, Zookeeper, and Kafka UI using Docker Compose:
   ```bash
   docker-compose up -d
   ```
2. Build the project:
   ```bash
   mvn clean package
   ```
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```
4. To stop all services:
   ```bash
   docker-compose down
   ```

### Accessing Kafka UI

Kafka UI is available at http://localhost:8080. You can use it to:
- Monitor topics
- View messages
- Create topics manually
- Monitor consumer groups
- View broker configurations

### Using Local Kafka

If you prefer to run Kafka locally without Docker:

1. Make sure you have Kafka running locally on port 9092
2. Build the project:
   ```bash
   mvn clean package
   ```
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

## Expected Behavior

The application will fail to start with an error indicating that the topic doesn't exist. This is because the `@PostConstruct` method in `DemoService` runs before the `SmartInitializingSingleton` that creates the Kafka topics.

## Workaround

To fix this issue, you can use one of these approaches:

1. Use `@EventListener(ContextRefreshedEvent.class)` instead of `@PostConstruct`
2. Create the topic manually before starting the application (using Kafka UI)
3. Configure Kafka to auto-create topics (set `KAFKA_AUTO_CREATE_TOPICS_ENABLE: "true"` in docker-compose.yml)

## Issue Details

The issue occurs because:
1. The `@PostConstruct` method runs during bean initialization
2. The `SmartInitializingSingleton` that creates Kafka topics runs after all beans are initialized
3. This means the topic doesn't exist when the `@PostConstruct` method tries to use it 