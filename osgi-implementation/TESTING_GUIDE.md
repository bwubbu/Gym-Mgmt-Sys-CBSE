# OSGi Implementation - Testing Guide

## Prerequisites

1. **Java 17+** installed and configured
2. **Maven 3.6+** installed
3. **Apache Felix Framework** (we'll download this)

## Step 1: Build All OSGi Bundles

First, build all the bundles:

```bash
cd osgi-implementation
mvn clean install
```

This will create JAR files in each bundle's `target/` directory:
- `base-library-bundle/target/base-library-bundle-1.0.0.jar`
- `member-management-bundle/target/member-management-bundle-1.0.0.jar`
- `report-analytics-bundle/target/report-analytics-bundle-1.0.0.jar`

## Step 2: Download Apache Felix Framework

1. Go to: https://felix.apache.org/downloads.cgi
2. Download **Apache Felix Framework Distribution** (latest version, e.g., 7.0.0)
3. Extract it to a directory (e.g., `C:\felix-framework` or `~/felix-framework`)

## Step 3: Copy Bundles to Felix

Copy all built JAR files to Felix's `bundle/` directory:

```bash
# Windows PowerShell
Copy-Item "base-library-bundle\target\*.jar" "C:\felix-framework\bundle\"
Copy-Item "member-management-bundle\target\*.jar" "C:\felix-framework\bundle\"
Copy-Item "report-analytics-bundle\target\*.jar" "C:\felix-framework\bundle\"

# Linux/Mac
cp base-library-bundle/target/*.jar ~/felix-framework/bundle/
cp member-management-bundle/target/*.jar ~/felix-framework/bundle/
cp report-analytics-bundle/target/*.jar ~/felix-framework/bundle/
```

## Step 4: Start Apache Felix

Navigate to Felix directory and start it:

```bash
cd C:\felix-framework  # or ~/felix-framework
java -jar bin/felix.jar
```

You should see the Felix Gogo shell prompt: `g!`

## Step 5: Install and Start Bundles

In the Felix Gogo shell, run these commands:

### 5.1 List Available Bundles
```bash
g! lb
```
This shows all bundles in the `bundle/` directory.

### 5.2 Install Base Library Bundle
```bash
g! install file:/path/to/base-library-bundle-1.0.0.jar
```
Note the bundle ID that's returned (e.g., `1`).

### 5.3 Start Base Library Bundle
```bash
g! start 1
```
Replace `1` with the actual bundle ID from step 5.2.

### 5.4 Install and Start Member Management Bundle
```bash
g! install file:/path/to/member-management-bundle-1.0.0.jar
g! start <bundle-id>
```

### 5.5 Install and Start Report Analytics Bundle
```bash
g! install file:/path/to/report-analytics-bundle-1.0.0.jar
g! start <bundle-id>
```

## Step 6: Verify Services Are Registered

### 6.1 Check All Services
```bash
g! services
```
You should see services like:
- `IMemberService` (from member-management-bundle)
- `IReportService` (from report-analytics-bundle)

### 6.2 Check Specific Service
```bash
g! services | grep ReportService
g! services | grep MemberService
```

### 6.3 Check Bundle Status
```bash
g! lb
```
All bundles should show status `Active`.

### 6.4 Check Bundle Dependencies
```bash
g! diag <bundle-id>
```
This shows if a bundle has any missing dependencies.

## Step 7: Test the Services

### Option A: Create a Simple Test Client Bundle

Create a test client that uses the OSGi services. Here's a simple example:

**TestClient.java:**
```java
package com.gymmanagement.osgi.test;

import com.gymmanagement.osgi.base.entity.*;
import com.gymmanagement.osgi.base.service.*;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class TestClient implements BundleActivator {
    
    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("=== Testing OSGi Services ===");
        
        // Get IMemberService
        ServiceReference<IMemberService> memberRef = 
            context.getServiceReference(IMemberService.class);
        if (memberRef != null) {
            IMemberService memberService = context.getService(memberRef);
            
            // Create a test member
            Date joinDate = new Date(1, 15, 2024);
            Date dob = new Date(5, 20, 1995);
            Payment payment = new Payment(0.0, "Test User", "1234-5678-9012-3456");
            Member member = new Member(1, "John Doe", "john@example.com", 
                "0300-1234567", "123 Main St", joinDate, dob, 29, "Male", 
                1.75, 70.0, payment, "Weight Loss");
            
            // Test adding member
            String result = memberService.addMember(member);
            System.out.println("Add Member Result: " + result);
            
            // Test getting all members
            System.out.println("Total Members: " + memberService.getAllMembers().size());
        }
        
        // Get IReportService
        ServiceReference<IReportService> reportRef = 
            context.getServiceReference(IReportService.class);
        if (reportRef != null) {
            IReportService reportService = context.getService(reportRef);
            
            // Test generating reports
            System.out.println("\n=== Testing Report Generation ===");
            String fullReport = reportService.generateFullReport();
            System.out.println("Full Report Length: " + fullReport.length() + " characters");
            
            // Test analytics
            System.out.println("\n=== Testing Analytics ===");
            Map<String, Object> demographics = reportService.generateMemberDemographicsReport();
            System.out.println("Demographics: " + demographics);
            
            Map<String, Object> equipment = reportService.generateEquipmentUsageAnalyticsReport();
            System.out.println("Equipment Analytics: " + equipment);
        }
        
        System.out.println("\n=== Testing Complete ===");
    }
    
    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("Test Client stopped");
    }
}
```

### Option B: Use Felix Shell Commands

You can also interact with services using Felix shell commands, but this requires additional setup.

## Step 8: Troubleshooting

### Bundle Won't Start
```bash
g! diag <bundle-id>
```
Check for missing dependencies or import/export package mismatches.

### Service Not Found
- Verify the bundle providing the service is `Active`
- Check that the service interface is properly exported
- Ensure the consuming bundle imports the correct package version

### ClassNotFoundException
- Verify package exports in base-library-bundle
- Check package imports in dependent bundles
- Ensure version ranges match (e.g., `[1.0,2)`)

### Check Bundle Logs
```bash
g! log
```
Shows recent log entries from bundles.

## Quick Test Script

Here's a quick script to automate bundle installation (save as `install-bundles.txt`):

```
install file:/absolute/path/to/base-library-bundle-1.0.0.jar
start <id-from-previous-command>
install file:/absolute/path/to/member-management-bundle-1.0.0.jar
start <id-from-previous-command>
install file:/absolute/path/to/report-analytics-bundle-1.0.0.jar
start <id-from-previous-command>
lb
services
```

Then run:
```bash
g! < install-bundles.txt
```

## Expected Output

When everything works correctly, you should see:
- All bundles in `Active` state
- Services registered in the service registry
- No errors in bundle diagnostics
- Test client can successfully call service methods

## Next Steps

1. Create remaining service bundles (Trainer, Machine, Payment)
2. Add persistence layer (database)
3. Create a REST API bridge bundle (if needed)
4. Add comprehensive integration tests

