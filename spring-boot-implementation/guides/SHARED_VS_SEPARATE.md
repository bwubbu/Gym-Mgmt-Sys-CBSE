# Shared vs Separate Files - Quick Reference

## ✅ SHARED (Base Library - One Person Creates This)

The **Base Library** contains files that ALL components need:

### Entities (All Components Use These)
```
base-library/src/main/java/com/gymmanagement/base/entity/
├── Person.java
├── Member.java
├── Trainer.java
├── Admin.java
├── Machine.java
├── Payment.java
├── MemberPlan.java (if exists)
└── BodyStats.java (if exists)
```

### Service Interfaces (Contracts)
```
base-library/src/main/java/com/gymmanagement/base/service/
├── IMemberService.java
├── ITrainerService.java
├── IBookingService.java
├── IFinanceService.java
└── IReportService.java
```

**Who creates this?** One person (usually the team lead or first person to start)

**How others use it?** Add as Maven dependency in their `pom.xml`

---

## ❌ SEPARATE (Each Person Creates Their Own)

Each component is **completely separate** - no code sharing between components:

### Member Management Component (Person 2)
```
member-management-component/
├── pom.xml (depends on base-library)
├── src/.../MemberManagementApplication.java
├── src/.../service/MemberService.java (implements IMemberService)
├── src/.../controller/MemberController.java
└── src/.../resources/application.yml (port 8081)
```

### Trainer Management Component (Person 3)
```
trainer-management-component/
├── pom.xml (depends on base-library)
├── src/.../TrainerManagementApplication.java
├── src/.../service/TrainerService.java (implements ITrainerService)
├── src/.../controller/TrainerController.java
└── src/.../resources/application.yml (port 8082)
```

### Equipment Booking Component (Person 4)
```
equipment-booking-component/
├── pom.xml (depends on base-library)
├── src/.../BookingManagementApplication.java
├── src/.../service/BookingService.java (implements IBookingService)
├── src/.../controller/BookingController.java
└── src/.../resources/application.yml (port 8083)
```

### Financial Management Component (Person 5)
```
financial-management-component/
├── pom.xml (depends on base-library)
├── src/.../FinancialManagementApplication.java
├── src/.../service/FinanceService.java (implements IFinanceService)
├── src/.../controller/FinanceController.java
└── src/.../resources/application.yml (port 8084)
```

### Report Analytics Component (You - Person 6) ✅
```
report-analytics-component/
├── pom.xml (depends on base-library)
├── src/.../ReportAnalyticsApplication.java
├── src/.../service/ReportService.java (implements IReportService)
├── src/.../controller/ReportController.java
└── src/.../resources/application.yml (port 8085)
```

---

## Visual Summary

```
┌─────────────────────────────────────┐
│      BASE LIBRARY (SHARED)         │
│  - Entities (Member, Trainer...)  │
│  - Interfaces (IMemberService...) │
│  Created by: One person            │
│  Used by: ALL components           │
└─────────────────────────────────────┘
              ▲
              │ (Maven dependency)
    ┌─────────┼─────────┐
    │         │         │
┌───┴───┐ ┌──┴───┐ ┌───┴───┐
│Member │ │Trainer│ │Booking│
│Comp   │ │Comp   │ │Comp   │
│(8081) │ │(8082) │ │(8083) │
└───────┘ └───────┘ └───────┘
    │         │         │
    └─────────┼─────────┘
              │ (REST API calls)
         ┌────┴────┐
         │ Report  │
         │ Comp    │
         │ (8085)  │
         └─────────┘
```

## Key Points

1. **Base Library = Shared** ✅
   - One person creates it
   - Everyone uses it via Maven dependency
   - Contains entities and interfaces only

2. **Components = Separate** ❌
   - Each person creates their own
   - Different folders, different ports
   - No code sharing between components
   - Communicate via REST APIs

3. **Communication = REST APIs** 🌐
   - Components don't import each other's Java classes
   - They call each other's REST endpoints
   - Example: Report component calls `http://localhost:8081/api/members`

## What Each Person Does

### Person 1: Base Library
- [ ] Create `base-library/` folder
- [ ] Copy entities from original codebase
- [ ] Create service interfaces
- [ ] Build: `mvn clean install`
- [ ] Share with team

### Person 2-6: Components
- [ ] Create your component folder (e.g., `member-management-component/`)
- [ ] Copy structure from `report-analytics-component/` as template
- [ ] Add Base Library dependency to `pom.xml`
- [ ] Implement your service interface
- [ ] Create REST controllers
- [ ] Configure unique port
- [ ] Test independently

## Example: How Member Component Uses Base Library

```java
// In member-management-component/pom.xml
<dependency>
    <groupId>com.gymmanagement</groupId>
    <artifactId>base-library</artifactId>
    <version>1.0.0</version>
</dependency>
```

```java
// In MemberService.java
import com.gymmanagement.base.entity.Member;  // From Base Library
import com.gymmanagement.base.service.IMemberService;  // From Base Library

@Service
public class MemberService implements IMemberService {
    // Implementation uses Member entity from Base Library
}
```

## Don't Do This ❌

- ❌ Copy entities into your component (use Base Library instead)
- ❌ Copy service interfaces into your component (use Base Library instead)
- ❌ Import other components' Java classes directly (use REST APIs instead)
- ❌ Share implementation code between components (each component is separate)

## Do This ✅

- ✅ Add Base Library as Maven dependency
- ✅ Import entities from `com.gymmanagement.base.entity`
- ✅ Implement service interfaces from `com.gymmanagement.base.service`
- ✅ Call other components via REST APIs
- ✅ Keep your component code separate

---

**Remember**: Base Library = Shared Foundation. Components = Separate Services.

