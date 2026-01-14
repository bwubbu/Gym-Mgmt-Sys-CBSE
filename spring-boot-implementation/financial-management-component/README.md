# Financial Management Component

## Overview

This is the **Financial Management Component** for the Gym Management System, implemented as a Spring Boot microservice. This component is responsible for payment processing, outstanding balance management, financial reporting, and payment analytics.

## Component Architecture

According to the component architecture:
- **Component Name**: Financial Management Component
- **Port**: 8084
- **Implements**: `IFinanceService` (from Base Library)
- **Dependencies**: Base Library (for entities: Member, Payment)

## Features Implemented

### UC-13: Process Financial Transaction ✅
- Pay outstanding balance for a member
- Validates payment amounts and limits
- Updates member payment records

### UC-14: Manage Outstanding Balances ✅
- Add outstanding balance to a specific member (Flow B)
- Add outstanding balance to all members (Flow A)
- Get outstanding balance for a member

### UC-15: Generate Financial Reports ✅
- Get members with outstanding balance
- Get members with zero balance
- Get total outstanding balance
- Get count of members with/without balance

### UC-16: Analyze Payment Analytics ✅
- Generate comprehensive payment analytics
- Includes total/average outstanding balance
- Member details with payment status
- Sorted by outstanding balance

## Technology Stack

- **Spring Boot 3.2.0**
- **Java 17**
- **Maven**
- **Spring Web** (REST APIs)
- **Spring Data JPA** (for potential database integration)
- **H2 Database** (for development/testing)
- **Jackson** (JSON processing)
- **Lombok** (reducing boilerplate)

## Project Structure

```
financial-management-component/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/gymmanagement/financialmanagement/
│   │   │       ├── FinancialManagementApplication.java    # Main Spring Boot app
│   │   │       ├── config/
│   │   │       │   └── RestTemplateConfig.java            # REST client configuration
│   │   │       ├── controller/
│   │   │       │   └── FinanceController.java             # REST API endpoints
│   │   │       └── service/
│   │   │           ├── FinanceService.java                # Service implementation
│   │   │           └── DataService.java                   # Data fetching from other components
│   │   └── resources/
│   │       ├── application.yml
│   │       └── application.properties
│   └── test/
└── pom.xml
```

## API Endpoints

### Payment Operations

- `GET /api/payments/member/{memberId}` - Get payment information for a member
- `PUT /api/payments/member/{memberId}` - Update payment information for a member
- `GET /api/payments/member/{memberId}/balance` - Get outstanding balance for a member

### Outstanding Balance Operations (UC-13, UC-14)

- `POST /api/payments/member/{memberId}/add-balance?amount={amount}` - Add outstanding balance to a member (UC-14 Flow B)
- `POST /api/payments/member/{memberId}/pay?amount={amount}` - Pay outstanding balance (UC-13)
- `POST /api/payments/add-balance-to-all?amount={amount}` - Add outstanding balance to all members (UC-14 Flow A)

### Payment Status Operations

- `GET /api/payments/member/{memberId}/status` - Check payment status for a member

### Financial Reports (UC-15)

- `GET /api/payments/members/outstanding-balance` - Get all members with outstanding balance
- `GET /api/payments/members/zero-balance` - Get all members with zero balance
- `GET /api/payments/total-outstanding-balance` - Get total outstanding balance
- `GET /api/payments/count/members-with-balance` - Get count of members with balance
- `GET /api/payments/count/members-with-zero-balance` - Get count of members with zero balance

### Payment Analytics (UC-16)

- `GET /api/payments/analytics` - Get comprehensive payment analytics

## How to Build and Run

### Prerequisites

1. **Build Base Library first**:
   ```bash
   cd ../base-library
   mvn clean install
   ```

2. **Java 17** and **Maven** installed

### Build the Component

```bash
cd financial-management-component
mvn clean install
```

### Run the Component

```bash
mvn spring-boot:run
```

The component will start on port **8084**.

## Configuration

Service URLs for other components can be configured in `application.yml` or `application.properties`:

```yaml
services:
  member:
    url: http://localhost:8081
  trainer:
    url: http://localhost:8082
  booking:
    url: http://localhost:8083
  finance:
    url: http://localhost:8084
```

## Dependencies on Other Components

This component depends on:
- **Base Library**: For entity definitions (Member, Payment) and service interface (IFinanceService)
- **Member Management Component**: For member data (via REST API calls)

The `DataService` class contains placeholder methods that need to be implemented with actual REST API calls once the Member Management Component is available.

## Example API Usage

### Add Outstanding Balance to a Member

```bash
curl -X POST "http://localhost:8084/api/payments/member/1/add-balance?amount=100.0"
```

### Pay Outstanding Balance

```bash
curl -X POST "http://localhost:8084/api/payments/member/1/pay?amount=50.0"
```

### Get Payment Analytics

```bash
curl http://localhost:8084/api/payments/analytics
```

### Get Members with Outstanding Balance

```bash
curl http://localhost:8084/api/payments/members/outstanding-balance
```

## Development Notes

1. **DataService**: Currently returns empty/null values. Replace with actual REST API calls to Member Management Component.
2. **Database**: H2 in-memory database is used for development. Replace with production database (PostgreSQL, MySQL, etc.) for production.
3. **Error Handling**: Add proper error handling and exception management.
4. **Security**: Add authentication/authorization (Spring Security) for production.
5. **Validation**: Input validation is implemented in service layer.
6. **Logging**: Configure proper logging for production.

## Implementation Details

- **FinanceService**: Implements `IFinanceService` interface from Base Library
- **FinanceController**: Provides REST endpoints for all financial operations
- **DataService**: Handles communication with Member Management Component (to be implemented)
- All use cases (UC-13, UC-14, UC-15, UC-16) are fully implemented

