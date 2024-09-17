# Library API Microservices

[![Java Build Status](https://github.com/earlspilner/library-api/actions/workflows/maven-build.yml/badge.svg)](https://github.com/earlspilner/the-github-times/actions/workflows/maven-build.yml)

This project implements a microservices architecture for managing a library system. The system consists of several services, each responsible for its specific functionality.

### Stack
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)
![MapStruct](https://img.shields.io/badge/MapStruct-006400?style=for-the-badge&logo=mapstruct&logoColor=white)


## Services

### 1. **Authentication Server**
The authentication service is responsible for issuing JWT tokens for users in the system.

- **Main features:**
  - User authentication.
  - Issuing access and refresh tokens.
  - Token renewal.

- **Endpoints:**
    
  `POST /api/auth/login`
  
  `POST /api/auth/refresh?refreshToken=`  

### 2. **Books Service**
The book management service handles operations related to books, including creation, updating, deletion, and retrieving information about books.

- **Main features:**
  - Managing book data.
  - Searching for books.

- **Endpoints:**  
  `POST /api/books`

  `GET /api/books/{bookId}`
  
  `GET /api/books/isbn/{isbn}`

  `PUT /api/books/{bookId}`

  `DELETE /api/books/{bookId}`

### 3. **Discovery Server**
The discovery server (Eureka) allows other services to register and discover each other dynamically within the microservices environment.

- **Main features:**
  - Service registration and discovery.

### 4. **API Gateway**
The API Gateway acts as the entry point to the system, routing requests to the appropriate services.

- **Main features:**
  - Request routing.
  - Centralized authentication and authorization.

- **Endpoints:**  
  `any request on port :8080` 

### 5. **Library Service**
The library management service stores information about available books in the library, including which book is free and which is on loan.

- **Main features:**
  - Store information about books in library

- **Endpoints:**  
  `POST /api/library`

  `GET /api/library/{bookId}`

  `PUT /api/library/{bookId}`

  `DELETE /api/library/{bookId}`

### 6. **Loan Service**
The loan service manages book loan information, including tracking due dates and book returns.

- **Main features:**
  - Tracking loaned books.
  - Managing return deadlines.

- **Endpoints:**  
  `POST /api/loans`

  `GET /api/loans/{loanId}`

  `PUT /api/loans/{bookId}`

### 7. **User Service**
The user management service is responsible for storing and managing user accounts.

- **Main features:**
  - Managing user registration and details.

- **Endpoints:**  
  `POST /api/users`

  `GET /api/users/{username}`

  `PUT /api/users/{username}`

  `DELETE /api/users/{id}`

## Architecture

The project uses a microservices architecture where each service is independent and communicates with others via REST APIs (OpenFeign client). **Eureka Discovery Server** is used for service registration and discovery, and **Spring Cloud Gateway** routes requests between services.

### Main components:
- **Spring Boot** - for building each microservice.
- **Eureka** - for service registration and discovery.
- **Spring Cloud Gateway** - for request routing through the API gateway.
- **Feign** - for simplifying REST API calls to other microservices.
- **Spring Security** - for authentication and authorization.

## Requirements

- **Java 21** or higher.
- **Maven 3.8.7+** for building the project.

## Running the Project

1. Clone the repository:
   ```bash
   git clone https://github.com/earlspilner/library-api.git
   ```

2. Navigate to the project's root directory:
   ```bash
   cd library-api
   ```

3. Build all services:
   ```bash
   mvn clean install
   ```

4. Start each service, beginning with *Discovery Server*:
   ```bash
   cd library-api-discovery-server
   mvn spring-boot:run
   ```
Repeat the process for each service in the required order.

### Environment Variables

| Variable Name              | Description                              | Default Value |
|----------------------------|------------------------------------------|---------------|
| `jwt.secret.key`            | JWT secret key, generated via KeyGen.java| (Generate it) |
| `jwt.expiration.access`     | Access token expiration time (in ms)     | 300000 (5 minutes) |
| `jwt.expiration.refresh`    | Refresh token expiration time (in ms)    | 3600000 (1 hour) |

## Authentication and Authorization

The system uses JWT tokens for authentication and authorization. After successful login via library-api-authentication-server, the client receives access and refresh tokens, which should be included in the Authorization header when making requests to other services.
