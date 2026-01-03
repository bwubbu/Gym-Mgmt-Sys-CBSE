# Base Library - Quick Start Guide

## ✅ What's Included

The Base Library contains:

### Entities (7 classes)
- ✅ `Person` - Abstract base class
- ✅ `Member` - Gym member entity
- ✅ `Trainer` - Gym trainer entity
- ✅ `Admin` - Administrator entity
- ✅ `Machine` - Gym equipment entity
- ✅ `Payment` - Payment entity
- ✅ `Date` - Custom date utility class

### Interfaces (2)
- ✅ `GymMachine` - Machine booking interface
- ✅ Service interfaces (5) - See below

### Service Interfaces (5)
- ✅ `IMemberService` - Member management contract
- ✅ `ITrainerService` - Trainer management contract
- ✅ `IBookingService` - Booking management contract
- ✅ `IFinanceService` - Financial management contract
- ✅ `IReportService` - Report analytics contract

## 🚀 How to Build and Install

### Step 1: Navigate to Base Library
```bash
cd base-library
```

### Step 2: Build and Install
```bash
mvn clean install
```

This will:
- Compile all Java files
- Create a JAR file: `target/base-library-1.0.0.jar`
- Install it to your local Maven repository

### Step 3: Verify
```bash
ls target/base-library-1.0.0.jar
```

If the file exists, you're good to go! ✅

## 📦 How Team Members Use It

Once you've built and installed Base Library, team members can:

### 1. Add Dependency in Their Component's `pom.xml`:

```xml
<dependency>
    <groupId>com.gymmanagement</groupId>
    <artifactId>base-library</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 2. Import and Use:

```java
import com.gymmanagement.base.entity.Member;
import com.gymmanagement.base.service.IMemberService;

@Service
public class MemberService implements IMemberService {
    // Implementation
}
```

## 🔄 Updating Base Library

If you need to make changes:

1. Make your changes to entities/interfaces
2. Update version in `pom.xml` (e.g., `1.0.0` → `1.0.1`)
3. Run `mvn clean install`
4. Notify team to update their dependency version

## ✅ Status

**Base Library is ready!** 

All entities and service interfaces have been created. You can now:
1. Build it: `mvn clean install`
2. Share it with your team
3. Start building components!

---

**Version**: 1.0.0  
**Maintained by**: Group Leader

