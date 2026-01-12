# Componentization Guide for Team

## Overview

This document explains how to componentize the Gym Management System into Spring Boot microservices. **The Base Library must be shared** by all components.

## Architecture Summary

```
┌─────────────────────────────────────────────────────────┐
│                    Base Library                         │
│  (Entities + Service Interfaces)                      │
│  - Person, Member, Trainer, Admin, Machine, Payment     │
│  - IMemberService, ITrainerService, etc.              │
└─────────────────────────────────────────────────────────┘
           ▲              ▲              ▲              ▲
           │              │              │              │
    ┌──────┴──────┐ ┌─────┴──────┐ ┌─────┴──────┐ ┌─────┴──────┐
    │   Member    │ │  Trainer   │ │  Booking  │ │  Finance  │
    │  Component  │ │ Component  │ │ Component │ │ Component │
    │   (8081)    │ │   (8082)   │ │   (8083)  │ │   (8084)  │
    └─────────────┘ └────────────┘ └───────────┘ └───────────┘
           │              │              │              │
           └──────────────┴──────────────┴──────────────┘
                           │
                  ┌────────┴────────┐
                  │ Report Analytics│
                  │   Component     │
                  │     (8085)      │
                  └─────────────────┘
```

## What Each Person Should Do

### Person 1: Base Library (Shared Foundation)
**Create**: `base-library/` folder
- Contains all entities (Member, Trainer, Machine, etc.)
- Contains all service interfaces (IMemberService, etc.)
- Build and install: `mvn clean install`
- **This must be done FIRST** before other components

### Person 2: Member Management Component
**Create**: `member-management-component/` folder
- Port: 8081
- Implements: `IMemberService` (from Base Library)
- Uses: `Member`, `MemberPlan`, `BodyStats` entities
- Endpoints: `/api/members/*`

### Person 3: Trainer Management Component
**Create**: `trainer-management-component/` folder
- Port: 8082
- Implements: `ITrainerService` (from Base Library)
- Uses: `Trainer` entity
- Endpoints: `/api/trainers/*`

### Person 4: Equipment Booking Component
**Create**: `equipment-booking-component/` folder
- Port: 8083
- Implements: `IBookingService` (from Base Library)
- Uses: `Member`, `Machine` entities
- Endpoints: `/api/bookings/*`, `/api/machines/*`

### Person 5: Financial Management Component
**Create**: `financial-management-component/` folder
- Port: 8084
- Implements: `IFinanceService` (from Base Library)
- Uses: `Payment` entity
- Endpoints: `/api/payments/*`

### Person 6 (You): Report Analytics Component ✅
**Already Created**: `report-analytics-component/` folder
- Port: 8085
- Implements: `IReportService` (from Base Library)
- Uses: All entities (for reporting)
- Endpoints: `/api/reports/*`, `/api/analytics/*`

## Critical: Base Library Must Be Shared

### Why?
- All components use the same entities (Member, Trainer, etc.)
- All components implement service interfaces from Base Library
- Ensures consistency across components
- Follows the component architecture specification

### How to Share?

**Option 1: Maven Local Repository (Recommended for Development)**
1. One person creates Base Library
2. Builds it: `cd base-library && mvn clean install`
3. Other components add it as dependency in `pom.xml`
4. Maven automatically finds it in local repository

**Option 2: Git Submodule (For Version Control)**
1. Base Library in separate Git repository
2. Other components add it as Git submodule
3. Each component references it

**Option 3: Multi-Module Maven Project**
1. Create parent POM
2. Base Library and all components as modules
3. Components automatically have access

## Step-by-Step for Other Team Members

### Step 1: Get Base Library
```bash
# Option A: Clone/copy base-library folder
# Option B: Wait for team member to build and share
```

### Step 2: Install Base Library
```bash
cd base-library
mvn clean install
```

### Step 3: Create Your Component
```bash
# Copy report-analytics-component as template
# Rename to your-component-name
# Modify pom.xml, application.yml, etc.
```

### Step 4: Add Base Library Dependency
In your `pom.xml`:
```xml
<dependency>
    <groupId>com.gymmanagement</groupId>
    <artifactId>base-library</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Step 5: Use Base Library Classes
```java
import com.gymmanagement.base.entity.Member;
import com.gymmanagement.base.service.IMemberService;

@Service
public class MemberService implements IMemberService {
    // Your implementation
}
```

## File Structure Comparison

### Report Analytics Component (Your Component) ✅
```
report-analytics-component/
├── pom.xml (has IReportService interface - should move to Base Library)
├── src/.../base/IReportService.java (should be in Base Library)
└── ...
```

### Other Components Should Follow Same Pattern
```
member-management-component/
├── pom.xml (depends on base-library)
├── src/.../service/MemberService.java (implements IMemberService from Base Library)
└── ...
```

## What Needs to Be Updated

### In Report Analytics Component:
1. **Move `IReportService` to Base Library** (or keep it here if Base Library person creates it)
2. **Update imports** to use Base Library entities
3. **Add Base Library dependency** to pom.xml

### In Base Library:
1. **Create all entity classes** from original codebase
2. **Create all service interfaces**
3. **Build and install** so other components can use it

## Communication Between Components

Components communicate via REST APIs, not direct dependencies:

```java
// In Report Analytics Component
@Value("${services.member.url}")
private String memberServiceUrl;

public List<Member> getAllMembers() {
    return restTemplate.getForObject(
        memberServiceUrl + "/api/members",
        List.class
    );
}
```

## Port Assignments

| Component | Port | Owner |
|-----------|------|-------|
| Member Management | 8081 | Person 2 |
| Trainer Management | 8082 | Person 3 |
| Equipment Booking | 8083 | Person 4 |
| Financial Management | 8084 | Person 5 |
| Report Analytics | 8085 | You (Rudzaidan) |

## Checklist for Team

- [ ] **Base Library created and installed** (Person 1)
- [ ] **All entities moved to Base Library**
- [ ] **All service interfaces created in Base Library**
- [ ] **Each component has unique port**
- [ ] **Each component depends on Base Library**
- [ ] **Service URLs configured in application.yml**
- [ ] **Components can communicate via REST**

## Important Notes

1. **Base Library is shared** - Don't duplicate entities
2. **Each component is separate** - Different ports, different projects
3. **Components communicate via REST** - Not direct Java dependencies
4. **Service interfaces in Base Library** - All components implement them
5. **Entities in Base Library** - All components use them

## Next Steps

1. **Team meeting** - Agree on Base Library structure
2. **One person creates Base Library** - With all entities and interfaces
3. **Each person creates their component** - Following the pattern
4. **Test integration** - Once all components are ready

---

**Key Point**: The Base Library is the **ONLY shared code**. Everything else is separate per component.

