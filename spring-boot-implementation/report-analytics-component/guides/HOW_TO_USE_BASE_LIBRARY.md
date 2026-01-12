# How to Update Report Analytics Component to Use Base Library

## Current Status

Currently, the Report Analytics Component has:
- `IReportService` interface in `src/.../base/IReportService.java`
- DTOs for entities (MemberDTO, TrainerDTO, etc.)

## What Needs to Change

Once the Base Library is created, you should:

### 1. Remove IReportService from This Component

The `IReportService` interface should be in the Base Library, not here.

**Current location:**
```
report-analytics-component/src/.../base/IReportService.java
```

**Should be in:**
```
base-library/src/.../service/IReportService.java
```

### 2. Add Base Library Dependency

Update `pom.xml` to include Base Library:

```xml
<dependencies>
    <!-- Base Library - SHARED -->
    <dependency>
        <groupId>com.gymmanagement</groupId>
        <artifactId>base-library</artifactId>
        <version>1.0.0</version>
    </dependency>
    
    <!-- Other dependencies... -->
</dependencies>
```

### 3. Update Imports

Change imports to use Base Library entities:

**Before:**
```java
import com.gymmanagement.reportanalytics.dto.MemberDTO;
```

**After:**
```java
import com.gymmanagement.base.entity.Member;
```

### 4. Update Service Implementation

**Before:**
```java
import com.gymmanagement.reportanalytics.base.IReportService;

@Service
public class ReportService implements IReportService {
    // Uses MemberDTO, TrainerDTO, etc.
}
```

**After:**
```java
import com.gymmanagement.base.service.IReportService;
import com.gymmanagement.base.entity.Member;
import com.gymmanagement.base.entity.Trainer;
// etc.

@Service
public class ReportService implements IReportService {
    // Uses Member, Trainer entities directly from Base Library
}
```

### 5. Update DataService

The `DataService` should fetch entities from Base Library:

```java
import com.gymmanagement.base.entity.Member;
import com.gymmanagement.base.entity.Trainer;
import com.gymmanagement.base.entity.Machine;

@Service
public class DataService {
    public List<Member> getAllMembers() {
        // Call Member Management Component REST API
        // Return List<Member> (from Base Library)
    }
}
```

## Step-by-Step Update Process

### Step 1: Wait for Base Library
Wait for the team member creating Base Library to:
1. Create `base-library/` folder
2. Add all entities
3. Add `IReportService` interface
4. Build: `mvn clean install`

### Step 2: Add Base Library Dependency
```bash
# In report-analytics-component/pom.xml, add:
<dependency>
    <groupId>com.gymmanagement</groupId>
    <artifactId>base-library</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Step 3: Update Imports
Replace all DTO imports with Base Library entity imports.

### Step 4: Remove Duplicate Code
- Delete `src/.../base/IReportService.java` (it's now in Base Library)
- Delete DTOs that match Base Library entities (MemberDTO, TrainerDTO, etc.)
- Keep only component-specific DTOs (like MemberDemographicsDTO, etc.)

### Step 5: Test
```bash
mvn clean install
mvn spring-boot:run
```

## What Stays in This Component

Keep these (they're component-specific):
- `MemberDemographicsDTO` - Analytics-specific
- `GrowthTrendsDTO` - Analytics-specific
- `BodyStatisticsDTO` - Analytics-specific
- `FitnessGoalDistributionDTO` - Analytics-specific
- `EquipmentUsageAnalyticsDTO` - Analytics-specific
- `Report` entity - Report-specific
- `ReportService` implementation
- Controllers

## What Moves to Base Library

Move these (they're shared):
- `IReportService` interface
- Entity classes (if you created any duplicates)

## Summary

**Before Base Library:**
- Component has its own `IReportService`
- Component uses DTOs for entities

**After Base Library:**
- Component depends on Base Library
- Component uses entities directly from Base Library
- Component implements `IReportService` from Base Library
- Component keeps only analytics-specific DTOs

---

**Note**: You can keep the current structure for now. Once Base Library is ready, follow these steps to update.

