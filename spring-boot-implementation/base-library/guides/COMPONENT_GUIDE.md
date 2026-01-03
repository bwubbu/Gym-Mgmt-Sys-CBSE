# Guide for Componentizing Other Modules

## Overview

This guide explains how other team members should componentize their assigned modules. Each person should create their own Spring Boot component similar to the Report Analytics Component, but they should **share the Base Library**.

## Step-by-Step Guide

### Step 1: Create Your Component Structure

Each team member should create a new folder for their component:

```
your-component-name/
├── pom.xml
├── src/
│   └── main/
│       ├── java/
│       │   └── com/gymmanagement/yourcomponent/
│       │       ├── YourComponentApplication.java
│       │       ├── controller/
│       │       ├── service/
│       │       ├── repository/ (if using database)
│       │       └── config/
│       └── resources/
│           └── application.yml
└── README.md
```

### Step 2: Add Base Library Dependency

In your component's `pom.xml`, add the Base Library as a dependency:

```xml
<dependencies>
    <!-- Base Library - SHARED -->
    <dependency>
        <groupId>com.gymmanagement</groupId>
        <artifactId>base-library</artifactId>
        <version>1.0.0</version>
    </dependency>
    
    <!-- Other Spring Boot dependencies -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <!-- ... other dependencies ... -->
</dependencies>
```

### Step 3: Use Entities from Base Library

Import and use entities from the Base Library:

```java
import com.gymmanagement.base.entity.Member;
import com.gymmanagement.base.entity.Trainer;
import com.gymmanagement.base.entity.Machine;
// etc.
```

### Step 4: Implement Service Interface

If your component implements a service interface, implement it:

```java
import com.gymmanagement.base.service.IMemberService;
import com.gymmanagement.base.entity.Member;

@Service
public class MemberService implements IMemberService {
    // Your implementation
}
```

### Step 5: Create REST Controllers

Create REST controllers for your component's functionality:

```java
@RestController
@RequestMapping("/api/members")  // Adjust based on your component
public class MemberController {
    // Your endpoints
}
```

## Component Assignments

Based on the architecture:

### 1. Member Management Component
- **Port**: 8081
- **Implements**: `IMemberService`
- **Uses**: `Member`, `MemberPlan`, `BodyStats` entities
- **Endpoints**: `/api/members/*`

### 2. Trainer Management Component
- **Port**: 8082
- **Implements**: `ITrainerService`
- **Uses**: `Trainer` entity
- **Endpoints**: `/api/trainers/*`

### 3. Equipment Booking Component
- **Port**: 8083
- **Implements**: `IBookingService`
- **Uses**: `Member`, `Machine` entities
- **Endpoints**: `/api/bookings/*`

### 4. Financial Management Component
- **Port**: 8084
- **Implements**: `IFinanceService`
- **Uses**: `Payment` entity
- **Endpoints**: `/api/payments/*`

### 5. Report Analytics Component
- **Port**: 8085
- **Implements**: `IReportService`
- **Uses**: All entities (for reporting)
- **Endpoints**: `/api/reports/*`, `/api/analytics/*`

## Important: What to Share vs. What's Separate

### ✅ SHARED (Base Library)
- All entity classes (Member, Trainer, Machine, Payment, etc.)
- All service interfaces (IMemberService, ITrainerService, etc.)
- Common DTOs (if any)

### ❌ SEPARATE (Each Component)
- Service implementations (MemberService, TrainerService, etc.)
- REST controllers
- Component-specific configuration
- Component-specific DTOs
- Database repositories (if using database)

## Development Workflow

### 1. First, Build Base Library
```bash
cd base-library
mvn clean install
```

This installs the Base Library to your local Maven repository.

### 2. Then, Build Your Component
```bash
cd your-component-name
mvn clean install
```

Your component will now have access to Base Library classes.

### 3. Run Your Component
```bash
mvn spring-boot:run
```

## Example: Member Management Component Structure

```
member-management-component/
├── pom.xml
└── src/
    └── main/
        ├── java/
        │   └── com/gymmanagement/member/
        │       ├── MemberManagementApplication.java
        │       ├── controller/
        │       │   └── MemberController.java
        │       ├── service/
        │       │   └── MemberService.java  # Implements IMemberService
        │       └── repository/
        │           └── MemberRepository.java
        └── resources/
            └── application.yml  # Port 8081
```

## Configuration

Each component should have its own `application.yml`:

```yaml
server:
  port: 8081  # Different port for each component

spring:
  application:
    name: member-management-component
```

## Communication Between Components

Components communicate via REST APIs:

```java
@Service
public class ReportService {
    @Value("${services.member.url}")
    private String memberServiceUrl;
    
    private final RestTemplate restTemplate;
    
    public List<Member> getAllMembers() {
        return restTemplate.getForObject(
            memberServiceUrl + "/api/members",
            new ParameterizedTypeReference<List<Member>>() {}
        );
    }
}
```

## Checklist for Each Component

- [ ] Create component folder structure
- [ ] Add Base Library dependency to pom.xml
- [ ] Implement relevant service interface from Base Library
- [ ] Create REST controllers
- [ ] Configure unique port in application.yml
- [ ] Test component independently
- [ ] Document API endpoints
- [ ] Update service URLs in other components that depend on yours

## Common Issues

### Issue: "Cannot find symbol: Member"
**Solution**: Make sure Base Library is installed: `cd base-library && mvn clean install`

### Issue: "Port already in use"
**Solution**: Each component must use a different port (8081, 8082, 8083, 8084, 8085)

### Issue: "ClassNotFoundException"
**Solution**: Ensure Base Library is in your component's classpath (check pom.xml dependency)

## Team Coordination

1. **Agree on Base Library structure first** - Before starting components
2. **One person manages Base Library** - To avoid conflicts
3. **Share service URLs** - So components can communicate
4. **Test integration** - Once all components are ready

---

**Remember**: The Base Library is the foundation. All components depend on it, but components don't depend on each other directly (they communicate via REST APIs).

