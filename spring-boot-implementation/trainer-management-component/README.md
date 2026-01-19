# Trainer Management Component

## Overview

This is the **Trainer Management Component** for the Gym Management System, implemented as a Spring Boot microservice. It handles the lifecycle of gym trainers, including registration, profile management, member assignment, and performance tracking.

## Component Architecture

- **Component Name**: Trainer Management Component (gym trainer)
- **Port**: 8082
- **Internal Elements**: `Trainer` (Entity), `TrainerService` (Logic)
- **Provided Interface**: `ITrainerService`

## Features

### 1. Manage Trainer Staff (UC-5)
- Add new trainers with specialization, hourly rate, and experience level.
- Modify professional and personal details.
- Remove trainers from the system.

### 2. View Trainer Profile (UC-6)
- Search for specific trainers by Registration ID.
- View all trainers registered in the system.

### 3. Assign Trainer to Members (UC-7)
- Link members to specific trainers for personalized training.

### 4. Track Trainer Performance (UC-8)
- Record and calculate average ratings.
- Track attendance days.

## Technology Stack

- **Spring Boot 3.2.0**
- **Java 17**
- **Maven**
- **Spring Web** (REST APIs)
- **Spring Data JPA** (Persistence)
- **H2 Database** (In-memory)
- **Lombok** (Boilerplate reduction)

## Project Structure

```
trainer-management-component/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/gymmanagement/trainer/
│   │   │       ├── base/
│   │   │       │   └── ITrainerService.java      # Service interface
│   │   │       ├── controller/
│   │   │       │   └── TrainerController.java    # REST endpoints
│   │   │       ├── dto/
│   │   │       │   └── PerformanceUpdateDTO.java # Data transfer objects
│   │   │       ├── entity/
│   │   │       │   └── Trainer.java              # Trainer entity
│   │   │       ├── repository/
│   │   │       │   └── TrainerRepository.java    # Data access
│   │   │       ├── service/
│   │   │       │   └── TrainerService.java       # Business logic
│   │   │       ├── config/
│   │   │       │   └── RestTemplateConfig.java   # REST configuration
│   │   │       └── TrainerManagementApplication.java
│   │   └── resources/
│   │       └── application.yml
└── pom.xml
```

## API Endpoints

### Trainer Management
- `POST /api/trainers` - Add a new trainer
- `GET /api/trainers/{id}` - Get trainer details
- `GET /api/trainers` - Get all trainers
- `PUT /api/trainers/{id}` - Update trainer details
- `DELETE /api/trainers/{id}` - Remove a trainer
- `POST /api/trainers/{id}/assign/{memberId}` - Assign a member to a trainer
- `POST /api/trainers/{id}/performance` - Update trainer rating and attendance

## Running the Application

### Prerequisites
- Java 17 or higher
- Maven 3.6+

### Build and Run
```bash
# Navigate to the component directory
cd trainer-management-component

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The application will start on port **8082** by default.
- H2 Console: `http://localhost:8082/h2-console`
