# Base Library - Shared Components

## Overview

The **Base Library** is a shared Maven library that contains:
1. **Core Entities** - All domain entities used across components
2. **Service Interfaces** - All service contracts that components implement

According to the component architecture, all components depend on the Base Library (dependency depth = 1).

## Structure

```
base-library/
├── pom.xml
├── README.md
├── BUILD_AND_INSTALL.md
├── COMPONENT_GUIDE.md
└── src/
    └── main/
        └── java/
            └── com/gymmanagement/base/
                ├── entity/          # Core entities
                │   ├── Person.java
                │   ├── Member.java
                │   ├── Trainer.java
                │   ├── Admin.java
                │   ├── Machine.java
                │   ├── Payment.java
                │   ├── Date.java
                │   └── GymMachine.java (interface)
                └── service/         # Service interfaces
                    ├── IMemberService.java
                    ├── ITrainerService.java
                    ├── IBookingService.java
                    ├── IFinanceService.java
                    └── IReportService.java
```

## Entities

### Person (Abstract Base Class)
- Base class for Member, Trainer, and Admin
- Contains common fields: regId, name, gmail, phoneNum, address, joinDate, dateOfBirth, age, gender
- Contains validation methods

### Member
- Extends Person
- Additional fields: height, weight, bmi, memberPayment, fitnessGoal
- Payment relationship

### Trainer
- Extends Person
- Additional fields: specialization, hourlyRate, weeklyWorkingHours, experienceLevel
- Salary calculation methods

### Admin
- Represents gym administrator
- Fields: username, password
- Login validation methods

### Machine
- Represents gym equipment
- Implements GymMachine interface
- Fields: regId, name, brand, model, maxWeightCapacity, machineWeight, type
- Booking array (max 8 bookings per machine)

### Payment
- Represents payment information
- Fields: outStandingBalance, creditCardAccountHolder, creditCardNum
- Payment status and validation methods

### Date
- Custom date class
- Fields: day, month, year
- Date validation methods

## Service Interfaces

### IMemberService
Contract for Member Management operations:
- CRUD operations (add, get, update, delete)
- Search operations
- Validation

### ITrainerService
Contract for Trainer Management operations:
- CRUD operations
- Search by name, specialization
- Validation

### IBookingService
Contract for Equipment Booking operations:
- Booking operations (book, cancel)
- Machine CRUD operations
- Search operations

### IFinanceService
Contract for Financial Management operations:
- Payment operations
- Outstanding balance management
- Financial reports

### IReportService
Contract for Report Analytics operations:
- System report generation
- Member analytics
- Equipment analytics
- Export functionality

## How to Use

### 1. Build and Install

```bash
cd base-library
mvn clean install
```

This installs the library to your local Maven repository.

### 2. Add as Dependency

In your component's `pom.xml`:

```xml
<dependency>
    <groupId>com.gymmanagement</groupId>
    <artifactId>base-library</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 3. Import and Use

```java
import com.gymmanagement.base.entity.Member;
import com.gymmanagement.base.service.IMemberService;

@Service
public class MemberService implements IMemberService {
    // Your implementation
}
```

## Version

Current version: **1.0.0**

When making changes:
1. Update version in `pom.xml`
2. Run `mvn clean install`
3. Notify team to update their dependency version

## Dependencies

- **Java 17+** required
- **Lombok** (optional, for reducing boilerplate)

## Team Coordination

- **One person** (group leader) manages Base Library
- **All team members** use the same version
- **Breaking changes** require version increment and team notification
- **Entity changes** should be discussed with the team first

## Component Dependencies

All components depend on Base Library:
- Member Management Component → Base Library
- Trainer Management Component → Base Library
- Equipment Booking Component → Base Library
- Financial Management Component → Base Library
- Report Analytics Component → Base Library

## Notes

- Base Library does **NOT** depend on any component
- Base Library contains **NO** implementations, only entities and interfaces
- Components implement the service interfaces
- Components communicate with each other via REST APIs, not direct Java dependencies

## Author

**Group Leader** - Base Library Maintainer
