# Notification Service Backend

A simple notification system that can send email, SMS, and in-app notifications via Kafka.

## Requirements

- Java 17+
- Maven
- Docker (for Kafka and Zookeeper)

## Running the Application

1. Make sure Kafka and Zookeeper are running:
   ```
   # You mentioned you have Docker images ready for Kafka and Zookeeper
   ```

2. Build and run the application:
   ```
   ./mvnw spring-boot:run
   ```

3. The application will be available at http://localhost:8080

## API Endpoints

### Send a Notification
```
POST /api/notifications
```

Request body:
```json
{
  "userId": 1,
  "content": "This is a test notification",
  "type": "EMAIL"
}
```

The `type` can be one of: `EMAIL`, `SMS`, or `IN_APP`.

### Get User Notifications
```
GET /api/users/{userId}/notifications
```

## H2 Database Console

The H2 database console is available at:
```
http://localhost:8080/h2-console
```

Use the following credentials:
- JDBC URL: jdbc:h2:mem:notificationdb  
- Username: sa
- Password: [leave empty]

## Sample Data

The application initializes with two sample users:
1. John Doe (ID: 1) - Accepts all notification types
2. Jane Smith (ID: 2) - Accepts email and in-app notifications, but not SMS

## Architecture

The application consists of the following components:

1. User Management:
   - User entity with notification preferences
   - UserService for CRUD operations

2. Notification System:
   - Notification entity
   - NotificationService for sending notifications
   - Kafka topics for each notification type (`notifications.email`, `notifications.sms`, `notifications.in_app`)
   - Notification consumers for processing messages from topics

3. API Layer:
   - REST controllers for sending notifications and retrieving user notifications 
>>>>>>> 11a2db7 (V 1.0)
