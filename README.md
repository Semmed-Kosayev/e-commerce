# E-Commerce Microservices Platform

> **Status**: 🚧 In Development

A scalable e-commerce platform built using microservices architecture, implementing Domain-Driven Design (DDD) and Hexagonal Architecture patterns with event-driven communication via Apache Kafka.

## 🏗️ Architecture

This project follows **Hexagonal Architecture** (Ports & Adapters) combined with **Domain-Driven Design (DDD)** principles to ensure clean separation of concerns and maintainable code.

### Microservices

- **Order Service** - Manages order creation, retrieval, and lifecycle
- **Inventory Service** - Handles stock management and reservations
- **Notification Service** - Sends email notifications for order updates
- **Kafka Shared Classes** - Common event classes and DTOs shared across services

### Communication Flow

```
1. Order Created → Order Created Event
2. Inventory Service → Stock Check & Reservation
3. Inventory Service → Confirm/Reject Event
4. Order Service → Finalize Order
5. Notification Service → Send Email Confirmation
```

## 🚀 Tech Stack

- **Framework**: Spring Boot 3.x
- **Database**: PostgreSQL
- **Message Broker**: Apache Kafka
- **Build Tool**: Gradle
- **Architecture**: Hexagonal + DDD
- **Language**: Java
- **Validation**: Spring Boot Validation
- **ORM**: Spring Data JPA

## 📋 Features

### Order Service
- ✅ Create new orders
- ✅ Fetch order by ID
- ✅ Fetch orders by customer email
- ✅ Order lifecycle management (Pending → Confirmed/Rejected)

### Inventory Service
- ✅ Stock availability checking
- ✅ Stock reservation on order creation
- ✅ Event-driven inventory updates

### Notification Service
- ✅ Email notifications for order confirmations
- ✅ Email notifications for order rejections
- ⚠️ *Note: Emails currently go to spam folder*

## 🛠️ Prerequisites

- Java 21 or higher
- Docker & Docker Compose
- PostgreSQL
- Apache Kafka
- Gradle 8.x

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/Shammad-Kosayev/e-commerce.git
cd e-commerce
```

### 2. Start Infrastructure Services

```bash
# Start PostgreSQL and Kafka using Docker Compose
docker-compose up -d postgres kafka zookeeper
```

### 3. Build the Project

```bash
# Build all services
./gradlew build
```

### 4. Run Services

Start each service in separate terminals:

```bash
# Terminal 1 - Order Service
./gradlew :order-service:bootRun

# Terminal 2 - Inventory Service
./gradlew :inventory-service:bootRun

# Terminal 3 - Notification Service
./gradlew :notification-service:bootRun
```

## 📊 API Endpoints

### Order Service

| Method | Endpoint              | Description |
|--------|-----------------------|-------------|
| POST | `/api/orders`         | Create a new order |
| GET | `/api/orders/{id}`    | Get order by ID |
| GET | `/api/orders?{email}` | Get orders by customer email |

### Example Request

```bash
# Create Order
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerEmail": "customer@example.com",
    "items": [
      {
        "productId": "PRODUCT_001",
        "quantity": 2,
        "price": 29.99
      }
    ]
  }'
```

## 🏗️ Project Structure

```
e-commerce/
├── order-service/              # Order management service
│   ├── src/main/java/
│   │   └── domain/            # Business logic & entities
│   │   └── application/       # Use cases & services
│   │   └── infrastructure/    # External integrations
│   └── build.gradle
├── inventory-service/          # Stock management service
├── notification-service/       # Email notification service
├── kafka-shared-classes/       # Shared DTOs and events
└── docker-compose.yml         # Infrastructure setup
```

## 🔄 Event Flow

### Order Creation Flow

1. **Order Created Event**
   ```json
   {
     "orderId": "ORDER_123",
     "items": ["..."],
     "customerEmail": "user@example.com"
   }
   ```

2. **Inventory Check & Reserve**
    - Inventory service checks stock availability
    - Reserves items if available

3. **Confirmation/Rejection Event**
    - InventoryReservedEvent or InventoryUnavailableEvent
   ```json
   {
     "orderId": "ORDER_123"
   }
   ```

4. **Order Finalization**
    - Order service updates order status

5. **Notification Sent**
    - Email confirmation/rejection sent to customer

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 License

This project is currently under development and not yet licensed.

## 📞 Contact

**Shammad Kosayev** - [GitHub Profile](https://github.com/Shammad-Kosayev)

Project Link: [https://github.com/Shammad-Kosayev/e-commerce](https://github.com/Shammad-Kosayev/e-commerce)

---

*This project is actively being developed. Features and documentation may change frequently.*