# Demo Project - Spring Boot Full Stack Application

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.10-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.java.net/)
[![MySQL](https://img.shields.io/badge/MySQL-9.4.0-blue.svg)](https://www.mysql.com/)
[![Redis](https://img.shields.io/badge/Redis-8.2.1-red.svg)](https://redis.io/)
[![Kafka](https://img.shields.io/badge/Apache%20Kafka-3.3.2-black.svg)](https://kafka.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

A comprehensive Spring Boot demo application showcasing modern Java development practices with multiple technology integrations including MySQL, Redis, Apache Kafka, and comprehensive API documentation.

## 🚀 Features

- **RESTful API** with comprehensive OpenAPI 3 documentation
- **User Management** with CRUD operations
- **Database Integration** with MySQL and MyBatis Plus ORM
- **Caching Layer** with Redis for improved performance
- **Message Queue** with Apache Kafka for asynchronous processing
- **Security** with Spring Security integration
- **Database Migration** with Liquibase
- **Containerized Development** with Docker Compose
- **Code Generation** with MyBatis Plus Generator
- **Validation** with Bean Validation
- **Exception Handling** with global exception handler

## 🛠️ Technology Stack

### Backend

- **Framework**: Spring Boot 3.4.10
- **Java Version**: 21
- **Build Tool**: Maven
- **ORM**: MyBatis Plus 3.5.14
- **Database Migration**: Liquibase
- **Security**: Spring Security
- **Validation**: Bean Validation
- **Documentation**: SpringDoc OpenAPI 3

### Infrastructure

- **Database**: MySQL 9.4.0
- **Cache**: Redis 8.2.1
- **Message Queue**: Apache Kafka 3.3.2
- **Containerization**: Docker & Docker Compose
- **Kafka UI**: Kafka UI for monitoring

### Development Tools

- **Code Generation**: MyBatis Plus Generator
- **Lombok**: Reducing boilerplate code
- **DevTools**: Spring Boot DevTools for hot reload

## 📋 Prerequisites

Before running this project, make sure you have the following installed:

- **Java 21** or higher
- **Maven 3.6+**
- **Docker** and **Docker Compose**
- **Git**

## 🚀 Quick Start

### 1. Clone the Repository

```bash
git clone <repository-url>
cd demo
```

### 2. Environment Setup

Create a `.env` file in the root directory with the following variables:

```env
# Database Configuration
MYSQL_PORT=3306
MYSQL_USERNAME=demo
MYSQL_PASSWORD=demo
MYSQL_DATABASE=demo

# Redis Configuration
REDIS_PORT=6379
REDIS_PASSWORD=your_redis_password

# Kafka Configuration
KAFKA_PORT=9094
KAFKA_USERNAME=your_kafka_username
KAFKA_PASSWORD=your_kafka_password
KAFKA_UI_PORT=8080

# Data Directory
DATA_DIR=./components
```

### 3. Start Infrastructure Services

```bash
# Start all services (MySQL, Redis, Kafka, Kafka UI)
docker-compose up -d
```

### 4. Run the Application

```bash
# Using Maven
./mvnw spring-boot:run

# Or build and run JAR
./mvnw clean package
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

### 5. Access the Application

- **Application**: http://localhost:8081
- **API Documentation**: http://localhost:8081/swagger-ui.html
- **Kafka UI**: http://localhost:8080

## 📚 API Documentation

The application provides comprehensive API documentation using OpenAPI 3 (Swagger). Once the application is running, you can access:

- **Swagger UI**: http://localhost:8081/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8081/api-docs

### Available Endpoints

#### User Management

- `POST /api/v1/users` - Create a new user
- More endpoints will be available as the application grows

## 🏗️ Project Structure

```
demo/
├── src/
│   ├── main/
│   │   ├── java/com/example/demo/
│   │   │   ├── config/          # Configuration classes
│   │   │   ├── controller/      # REST controllers
│   │   │   ├── dto/            # Data Transfer Objects
│   │   │   ├── enums/          # Enumerations
│   │   │   ├── exceptions/     # Custom exceptions
│   │   │   ├── mapper/         # MyBatis mappers
│   │   │   ├── model/          # Entity models
│   │   │   ├── service/        # Business logic
│   │   │   └── tools/          # Utility tools
│   │   └── resources/
│   │       ├── db/             # Database migration scripts
│   │       └── application.properties
│   └── test/                   # Test classes
├── components/                 # Docker data volumes
├── scripts/                    # Database and Kafka scripts
├── docker-compose.yml          # Docker services configuration
└── pom.xml                     # Maven configuration
```

## 🔧 Configuration

### Database Configuration

The application uses MySQL as the primary database with MyBatis Plus for ORM operations. Database migrations are handled by Liquibase.

### Redis Configuration

Redis is configured for caching and session management to improve application performance.

### Kafka Configuration

Apache Kafka is set up for message queuing and event-driven architecture patterns.

### Security Configuration

Spring Security is integrated for authentication and authorization (currently configured for development).

## 🧪 Testing

```bash
# Run all tests
./mvnw test

# Run tests with coverage
./mvnw test jacoco:report
```

## 🐳 Docker Services

The project includes the following Docker services:

- **MySQL**: Database server
- **Redis**: Cache server
- **Kafka**: Message broker
- **Kafka UI**: Web interface for Kafka management

## 📝 Development Guidelines

### Code Standards

- Use English for all code, comments, and documentation
- Follow Spring Boot best practices
- Use Lombok for reducing boilerplate code
- Implement proper exception handling
- Use validation annotations for request DTOs
- Follow RESTful API design principles

### Database Migration

Database schema changes should be managed through Liquibase changelog files in `src/main/resources/db/changelog/changesets/`.

### API Documentation

All REST endpoints should be properly documented using OpenAPI annotations for automatic documentation generation.

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🆘 Support

If you encounter any issues or have questions, please:

1. Check the [Issues](https://github.com/your-repo/demo/issues) page
2. Create a new issue with detailed information
3. Contact the development team

## 🔮 Roadmap

- [ ] Authentication and Authorization implementation
- [ ] Additional CRUD operations for User entity
- [ ] Kafka message producers and consumers
- [ ] Redis caching implementation
- [ ] Unit and integration tests
- [ ] CI/CD pipeline setup
- [ ] Monitoring and logging improvements

---

**Happy Coding! 🎉**
