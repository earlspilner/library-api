# Library API Microservices

[![Java Build Status](https://github.com/earlspilner/library-api/actions/workflows/maven-build.yml/badge.svg)](https://github.com/earlspilner/library-api/actions/workflows/maven-build.yml)

> **Note**
> 
> After adding a new user, you must create a bearer token (/login) and set it in the header of every request to other microservices

This project implements a microservices architecture for managing a library system. The system consists of several services, each responsible for its specific functionality.

### Stack
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)
![MapStruct](https://img.shields.io/badge/MapStruct-006400?style=for-the-badge&logo=mapstruct&logoColor=white)

## Launch Order

| No. | Service Name            | Environment Variables                       | Database               |
|-----|-------------------------|---------------------------------------------|------------------------|
| 1   | Discovery Server        |                                             |                        |
| 2   | Authentication Server    | jwt.secret.key, jwt.expiration.access, jwt.expiration.refresh | library_auth_db        |
| 3   | User Service            | jwt.secret.key                              | library_users_db       |
| 4   | Books Service           | jwt.secret.key                              | library_books_db       |
| 5   | Loan Service            | jwt.secret.key                              | library_loans_db       |
| 6   | Library Service         | jwt.secret.key                              | library_db             |
| 7   | API Gateway             | jwt.secret.key                              |                        |

> **Note**
>
> Make sure to pass the environment variables in the "Modify Run Configuration" menu under the "Environment Variables" section.

## Services

### 1. **User Service**
The user management service is responsible for storing and managing user accounts.

- **Main features:**
  - Managing user registration and details.

- **Endpoints:**  
  `POST /api/users`
  ```
  {
    "name": "Alexander Dudkin",
    "username": "thisdudkin",
    "email": "alexraddan@gmail.com",
    "password": "password"
  }
  ```

  `GET /api/users/{username}`

  `PUT /api/users/{username}`
  ```
  {
    "name": "Ivan Prokopenya",
    "username": "ongotaj",
    "email": "ivan123@gmail.com",
    "password": "password"
  }
  ```

  `DELETE /api/users/{id}`

### 2. **Authentication Server**
The authentication service is responsible for issuing JWT tokens for users in the system.

- **Main features:**
  - User authentication.
  - Issuing access and refresh tokens.
  - Token renewal.

- **Endpoints:**
    
  `POST /api/auth/login`
  ```
  {
    "username": "thisdudkin",
    "password": "password"
  }
  ```
  
  `POST /api/auth/refresh?refreshToken=`  

### 3. **Books Service**
The book management service handles operations related to books, including creation, updating, deletion, and retrieving information about books.

- **Main features:**
  - Managing book data.
  - Searching for books.

- **Endpoints:**  
  `POST /api/books`
  ```
  {
    "isbn": "9785171624927",
    "title": "Евгений Онегин",
    "genre": "Роман",
    "description": "Пронзительная любовная история, драматические повороты сюжета, тонкий психологизм персонажей, детальное описание быта и нравов той эпохи (не случайно Белинский назвал роман \"энциклопедией русской жизни\") – в этом произведении, как в зеркале, отразилась вся русская жизнь. \"Евгений Онегин\" никогда не утратит своей актуальности, и даже спустя два века мы поражаемся точности и верности \"ума холодных наблюдений и сердца горестных замет\" великого русского поэта.",
    "author": "Александр Пушкин"
  }
  ```

  `GET /api/books/{bookId}`
  
  `GET /api/books/isbn/{isbn}`

  `PUT /api/books/{bookId}`
  ```
  {
    "isbn": "978-5-00132-211-5",
    "title": "Сказка о рыбаке и рыбке",
    "genre": "Сказка",
    "description": "Уже в дошкольном возрасте стоит прочитать малышам сказки великого поэта, познакомить их с чудесным миром, который он создал в своих произведениях и без которого мы уже не мыслим свою жизнь, своё духовное развитие.",
    "author": "Александр Пушкин"
  }
  ```
  
  `DELETE /api/books/{bookId}`

### 4. **Discovery Server**
The discovery server (Eureka) allows other services to register and discover each other dynamically within the microservices environment.

- **Main features:**
  - Service registration and discovery.

### 5. **API Gateway**
The API Gateway acts as the entry point to the system, routing requests to the appropriate services.

- **Main features:**
  - Request routing.
  - Centralized authentication and authorization.

- **Endpoints:**  
  `any request on port :8080`

### 6. **Loan Service**
The loan service manages book loan information, including tracking due dates and book returns.

- **Main features:**
  - Tracking loaned books.
  - Managing return deadlines.

- **Endpoints:**  
  `POST /api/loans`
  ```
  {
    "bookId": 1
  }
  ```

  `GET /api/loans/{loanId}`

  `PUT /api/loans/{bookId}`

### 7. **Library Service**
The library management service stores information about available books in the library, including which book is free and which is on loan.

- **Main features:**
  - Store information about books in library

- **Endpoints: (for internal requests!!!)**  
  `POST /api/library`

  `GET /api/library/{bookId}`

  `PUT /api/library/{bookId}`

  `DELETE /api/library/{bookId}`

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
