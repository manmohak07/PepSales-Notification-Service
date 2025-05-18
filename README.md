# Notification Service

This is a Spring Boot application that handles user notifications through different channels (Email, SMS, In-App) using Kafka.

## Overview

This application demonstrates a notification system that:
- Manages users and their notification preferences
- Sends notifications through different channels
- Uses Kafka to queue and process notifications
- Stores data in an H2 in-memory database

## Setup Instructions

### Prerequisites
- Java 17+
- Maven
- Docker

### 1. Start Kafka with Docker

Run these commands to start Zookeeper and Kafka:

```bash
# Start Zookeeper
docker run -d --name zookeeper -p 2181:2181 -e ZOOKEEPER_CLIENT_PORT=2181 -e ZOOKEEPER_TICK_TIME=2000 confluentinc/cp-zookeeper:7.5.0

# Start Kafka
docker run -d --name kafka -p 9092:9092 -e KAKFA_BROKER_ID=1 -e KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 --link zookeeper confluentinc/cp-kafka:7.5.0
```

### 2. Build and Run the Application

```bash
mvn clean install
mvn spring-boot:run
```

### 3. Start Kafka Consumers

First, access the Kafka container by running the following command in three separate terminals:
```bash
docker exec -it kafka bash
```

Then, in these separate terminals, run the following commands to listen to each topic:

**Terminal 1: Email Notifications**
```bash
kafka-console-consumer --bootstrap-server localhost:9092 --topic email-notifications --from-beginning
```

**Terminal 2: SMS Notifications**
```bash
kafka-console-consumer --bootstrap-server localhost:9092 --topic sms-notifications --from-beginning
```

**Terminal 3: In-App Notifications**
```bash
kafka-console-consumer --bootstrap-server localhost:9092 --topic inapp-notifications --from-beginning
```

## Using the Application

### Accessing the H2 Database

1. Navigate to http://localhost:8080/h2-console
2. Use these settings:
   - JDBC URL: `jdbc:h2:mem:notificationdb`
   - Username: `sa`
   - Password: (leave empty)
3. Click "Connect"

You can now browse tables and data in the database. Key tables to check are:
- `APP_USER` - Contains user data and notification preferences
- `NOTIFICATION` - Contains all notifications and their delivery status

### Testing with Postman

#### User Management

- **Create User**: `POST http://localhost:8080/api/create`
  ```json
  {
    "firstName": "User",
    "lastName": "One",
    "email": "user1@example.com",
    "phoneNumber": "1234567890",
    "allowsEmail": true,
    "allowsSMS": true,
    "allowsInApp": true
  }
  ```

  Try creating another user with different preferences:
  ```json
  {
    "firstName": "User",
    "lastName": "Two",
    "email": "user2@example.com",
    "phoneNumber": "9876543210",
    "allowsEmail": true,
    "allowsSMS": false,
    "allowsInApp": true
  }
  ```

- **Get All Users**: `GET http://localhost:8080/api/get-all`

#### Notification Management

- **Send Notification**: `POST http://localhost:8080/api/notifications`
  ```json
  {
    "userId": 1,
    "content": "This is an email notification",
    "type": "EMAIL"
  }
  ```

  Let's send an SMS notification to both users:
  ```json
  {
    "userId": 1,
    "content": "This is an SMS notification for User One",
    "type": "SMS"
  }
  ```

  ```json
  {
    "userId": 2,
    "content": "This SMS won't be delivered to User Two",
    "type": "SMS"
  }
  ```

- **Get User Notifications**: `GET http://localhost:8080/api/users/1/notifications`
  
  This endpoint returns notifications for a specific user, based on their preference only i.e. if you send a SMS type notification to User Two, you would not see the notification when you perform the GET request.

## Understanding User Preferences

The system respects user notification preferences:

1. If a user has `allowsEmail = true`, email notifications will be processed and sent to the Kafka email topic
2. If a user has `allowsEmail = false`, email notifications will be rejected and not sent to the topic

For example, when sending an SMS notification to User Two who has `allowsSMS = false`:
- No message will appear in the SMS Kafka topic
- The notification won't be saved in the database

You can verify this by:
- Creating users with different preferences
- Sending various notification types to each user
- Checking the Kafka consumer terminals to see which messages appear
- Querying the H2 database to see which notifications were saved

## Application Properties

The application uses these key properties:

```properties
# Database Config
spring.datasource.url=jdbc:h2:mem:notificationdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true

# JPA Config
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Kafka Producer Config
spring.kafka.producer.bootstrap-servers=localhost:9092
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=org.apache.kafka.common.serialization.StringSerializer

# Kafka Consumer Config
spring.kafka.consumer.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=notification-group
spring.kafka.consumer.auto-offset-reset=earliest
spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.consumer.value-deserializer=org.apache.kafka.common.serialization.StringDeserializer

```

## How It Works

1. **User Creation**: Create users with notification preferences (email, SMS, in-app)
   ```
   User One allows all notification types
   User Two allows only email and in-app notifications
   ```

2. **Sending Notifications**: When a notification is sent through the API:
- The system checks if the user allows that notification type
- If allowed, the notification is saved to the database and sent to the appropriate Kafka topic
- If not allowed, the GET request would not display anything
   ```
   Sending SMS to User Two → "User has opted out of SMS notifications"
   Sending Email to User Two → Successfully queued in email-notifications topic
   ```

3. **Processing**: Kafka consumers listen to their topics and process messages
   

4. **Verification**: You can see the entire flow by:
   - Checking the API response when sending a notification
   - Watching the Kafka consumer logs to see which messages are processed
   - Querying the H2 database to see saved notifications


## Conclusion

This notification system demonstrates an approach to building scalable communication systems by using Spring Boot and Kafka. The system puts user preferences at the center, ensuring notifications are only sent through channels users have opted into.
 
