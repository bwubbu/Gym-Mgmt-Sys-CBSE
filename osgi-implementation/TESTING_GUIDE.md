# OSGi Implementation - Testing Guide

## Prerequisites

1. **Java 17+** installed and configured
2. **Maven 3.6+** installed
3. **OSGi Framework** - Choose one:
   - **Apache Felix** (lightweight, simple)
   - **Eclipse Equinox** (more features, Eclipse-based)

> **Note**: OSGi bundles are **portable** across different framework implementations! Your bundles will work with both Felix and Equinox without any code changes.

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

## Step 2: Choose Your OSGi Framework

### Option A: Apache Felix (Recommended for Beginners)

**Pros:**
- Lightweight and simple
- Easy to set up
- Good for learning OSGi

**Download:**
1. Go to: https://felix.apache.org/downloads.cgi
2. Download **Apache Felix Framework Distribution** (latest version, e.g., 7.0.0)
3. Extract it to a directory (e.g., `C:\felix-framework` or `~/felix-framework`)

**Copy Bundles:**
```bash
# Windows PowerShell
Copy-Item "base-library-bundle\target\*.jar" "C:\felix-framework\bundle\"
Copy-Item "member-management-bundle\target\*.jar" "C:\felix-framework\bundle\"
Copy-Item "report-analytics-bundle\target\*.jar" "C:\felix-framework\bundle\"
Copy-Item "test-client-bundle\target\*.jar" "C:\felix-framework\bundle\"

# Linux/Mac
cp base-library-bundle/target/*.jar ~/felix-framework/bundle/
cp member-management-bundle/target/*.jar ~/felix-framework/bundle/
cp report-analytics-bundle/target/*.jar ~/felix-framework/bundle/
cp test-client-bundle/target/*.jar ~/felix-framework/bundle/
```

**Start Felix:**
```bash
cd C:\felix-framework  # or ~/felix-framework
java -jar bin/felix.jar
```

You should see the Felix Gogo shell prompt: `g!`

**⚠️ IMPORTANT: Install Declarative Services Runtime**

Your bundles use OSGi Declarative Services (DS), so you need to install the Apache Felix SCR (Service Component Runtime) bundle:

1. **Download Apache Felix SCR:**
   - Go to: https://felix.apache.org/downloads.cgi
   - Download **Apache Felix Service Component Runtime (SCR)** (latest version, e.g., 2.2.2)
   - Extract the JAR file (e.g., `org.apache.felix.scr-2.2.2.jar`)

2. **Copy SCR bundle to Felix:**
   ```powershell
   Copy-Item "org.apache.felix.scr-2.2.2.jar" "C:\felix-framework\bundle\"
   ```

3. **In Felix shell, install and start SCR FIRST (before your bundles):**
   ```bash
   g! install file:/C:/felix-framework/bundle/org.apache.felix.scr-2.2.2.jar
   g! start <scr-bundle-id>
   ```

   **Note:** Install and start SCR bundle BEFORE installing your application bundles!

### Option B: Eclipse Equinox

**Pros:**
- More features and tooling
- Better integration with Eclipse IDE
- More enterprise-ready
- Used by Eclipse IDE itself

**Download:**
1. Go to: https://download.eclipse.org/equinox/
2. Download **Equinox SDK** (latest version, e.g., 3.21.0)
3. Extract it to a directory (e.g., `C:\equinox` or `~/equinox`)

**Copy Bundles:**
```bash
# Windows PowerShell
Copy-Item "base-library-bundle\target\*.jar" "C:\equinox\plugins\"
Copy-Item "member-management-bundle\target\*.jar" "C:\equinox\plugins\"
Copy-Item "report-analytics-bundle\target\*.jar" "C:\equinox\plugins\"
Copy-Item "test-client-bundle\target\*.jar" "C:\equinox\plugins\"

# Linux/Mac
cp base-library-bundle/target/*.jar ~/equinox/plugins/
cp member-management-bundle/target/*.jar ~/equinox/plugins/
cp report-analytics-bundle/target/*.jar ~/equinox/plugins/
cp test-client-bundle/target/*.jar ~/equinox/plugins/
```

**Start Equinox:**
```bash
cd C:\equinox  # or ~/equinox
java -jar plugins/org.eclipse.osgi_*.jar -console
```

You should see the Equinox console prompt: `osgi>`

**Equinox Console Commands:**
```bash
osgi> ss                    # List all bundles (similar to Felix's `lb`)
osgi> start <bundle-id>     # Start a bundle
osgi> stop <bundle-id>      # Stop a bundle
osgi> install file:/path/to/bundle.jar  # Install a bundle
osgi> diag <bundle-id>      # Diagnose bundle issues
osgi> services              # List services
osgi> help                  # Show all commands
```

## Step 3: Framework Comparison

| Feature | Apache Felix | Eclipse Equinox |
|---------|-------------|-----------------|
| **Shell** | Gogo (`g!`) | Equinox Console (`osgi>`) |
| **Commands** | `lb`, `start`, `stop` | `ss`, `start`, `stop` |
| **Bundle Directory** | `bundle/` | `plugins/` |
| **Startup** | `java -jar bin/felix.jar` | `java -jar plugins/org.eclipse.osgi_*.jar -console` |
| **Complexity** | Simpler | More features |
| **Bundle Compatibility** | ✅ Same bundles work on both | ✅ Same bundles work on both |

## Step 4: Install and Start Bundles

### Using Apache Felix (Gogo Shell)

**⚠️ CRITICAL: Install Declarative Services Runtime FIRST!**

Your bundles use OSGi Declarative Services, so you MUST install the SCR bundle before your application bundles:

### 4.0 Install Apache Felix SCR (Service Component Runtime)

**First, download and copy SCR bundle:**
1. Download **Apache Felix SCR** from: https://felix.apache.org/downloads.cgi
2. Extract the JAR (e.g., `org.apache.felix.scr-2.2.2.jar`)
3. Copy it to `C:\felix-framework\bundle\`

**Then in Felix shell, install and start SCR:**
```bash
g! install file:/C:/felix-framework/bundle/org.apache.felix.scr-2.2.2.jar
# Note the bundle ID (e.g., "Bundle ID: 1")
g! start 1
```

**Verify SCR is active:**
```bash
g! lb
# Look for org.apache.felix.scr - should be "Active"
```

**Now proceed with your application bundles:**

### 4.1 List Available Bundles
```bash
g! lb
```
This shows all bundles in the `bundle/` directory.

### 4.2 Install Base Library Bundle
```bash
g! install file:/C:/Users/kyrod/OneDrive/Desktop/Gym-Mgmt/Gym-Mgmt-Sys-CBSE/osgi-implementation/base-library-bundle/target/base-library-bundle-1.0.0.jar
```
Note the bundle ID that's returned (e.g., `1`).

### 4.3 Start Base Library Bundle
```bash
g! start 1
```
Replace `1` with the actual bundle ID from step 4.2.

### 4.4 Install and Start Member Management Bundle
```bash
g! install file:/C:/Users/kyrod/OneDrive/Desktop/Gym-Mgmt/Gym-Mgmt-Sys-CBSE/osgi-implementation/member-management-bundle/target/member-management-bundle-1.0.0.jar
g! start <bundle-id>
```

### 4.5 Install and Start Report Analytics Bundle
```bash
g! install file:/C:/Users/kyrod/OneDrive/Desktop/Gym-Mgmt/Gym-Mgmt-Sys-CBSE/osgi-implementation/report-analytics-bundle/target/report-analytics-bundle-1.0.0.jar
g! start <bundle-id>
```

### 4.6 Install and Start Test Client Bundle
```bash
g! install file:/C:/Users/kyrod/OneDrive/Desktop/Gym-Mgmt/Gym-Mgmt-Sys-CBSE/osgi-implementation/test-client-bundle/target/test-client-bundle-1.0.0.jar
g! start <bundle-id>
```

### Using Eclipse Equinox (Console)

In the Equinox console (`osgi>`), run these commands:

```bash
# List bundles
osgi> ss

# Install and start base library
osgi> install file:/C:/Users/kyrod/OneDrive/Desktop/Gym-Mgmt/Gym-Mgmt-Sys-CBSE/osgi-implementation/base-library-bundle/target/base-library-bundle-1.0.0.jar
osgi> start <bundle-id>

# Install and start member management
osgi> install file:/C:/Users/kyrod/OneDrive/Desktop/Gym-Mgmt/Gym-Mgmt-Sys-CBSE/osgi-implementation/member-management-bundle/target/member-management-bundle-1.0.0.jar
osgi> start <bundle-id>

# Install and start report analytics
osgi> install file:/C:/Users/kyrod/OneDrive/Desktop/Gym-Mgmt/Gym-Mgmt-Sys-CBSE/osgi-implementation/report-analytics-bundle/target/report-analytics-bundle-1.0.0.jar
osgi> start <bundle-id>

# Install and start test client
osgi> install file:/C:/Users/kyrod/OneDrive/Desktop/Gym-Mgmt/Gym-Mgmt-Sys-CBSE/osgi-implementation/test-client-bundle/target/test-client-bundle-1.0.0.jar
osgi> start <bundle-id>
```

## Step 5: Verify Services Are Registered

### Using Apache Felix

```bash
# Check all services
g! services

# Check specific service
g! services | grep ReportService
g! services | grep MemberService

# Check bundle status
g! lb
# All bundles should show status "Active"

# Check bundle dependencies
g! diag <bundle-id>
```

### Using Eclipse Equinox

```bash
# Check all services
osgi> services

# Check bundle status
osgi> ss
# All bundles should show status "ACTIVE"

# Check bundle dependencies
osgi> diag <bundle-id>
```

**Expected Services:**
- `IMemberService` (from member-management-bundle)
- `IReportService` (from report-analytics-bundle)

## Step 6: Test the Services

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

### Automatic Testing with Test Client Bundle

When you start the `test-client-bundle`, it will automatically:
- Test `IMemberService` (add, get, search members)
- Test `IReportService` (generate reports, analytics, exports)
- Print test results with ✅ or ❌ indicators

You should see output like:
```
=== Testing OSGi Services ===
✅ IMemberService found and working
✅ IReportService found and working
=== Testing Complete ===
```

## Step 7: Troubleshooting

### Bundle Already Installed Error

**Error:** `Bundle symbolic name and version are not unique: com.gymmanagement.osgi.base:1.0.0`

This means the bundle is already installed. You have two options:

**Option 1: Check and Start Existing Bundles (Recommended)**
```bash
# List all bundles to see what's installed
g! lb

# Find your bundle ID (look for com.gymmanagement.osgi.base)
# If it's not Active, just start it:
g! start <bundle-id>
```

**Option 2: Uninstall and Reinstall**
```bash
# List bundles to find the ID
g! lb

# Uninstall the existing bundle
g! uninstall <bundle-id>

# Then install again
g! install file:/path/to/your-bundle.jar
g! start <new-bundle-id>
```

**Quick Reset (Uninstall All Your Bundles):**
```bash
# List bundles
g! lb

# Uninstall all your application bundles (keep SCR and system bundles)
# Replace <id1>, <id2>, etc. with your actual bundle IDs
g! uninstall <id1> <id2> <id3> <id4>

# Then reinstall in order
```

### Bundle Won't Start

**Felix:**
```bash
g! diag <bundle-id>
```

**Equinox:**
```bash
osgi> diag <bundle-id>
```

Check for missing dependencies or import/export package mismatches.

### Service Not Found
- Verify the bundle providing the service is `Active` (Felix) or `ACTIVE` (Equinox)
- Check that the service interface is properly exported
- Ensure the consuming bundle imports the correct package version

### ClassNotFoundException
- Verify package exports in base-library-bundle
- Check package imports in dependent bundles
- Ensure version ranges match (e.g., `[1.0,2)`)

### Check Bundle Logs

**Felix:**
```bash
g! log
```

**Equinox:**
```bash
osgi> log
```

Shows recent log entries from bundles.

## Quick Test Script

### For Apache Felix

Save as `install-bundles-felix.txt`:
```
install file:/C:/Users/kyrod/OneDrive/Desktop/Gym-Mgmt/Gym-Mgmt-Sys-CBSE/osgi-implementation/base-library-bundle/target/base-library-bundle-1.0.0.jar
start 1
install file:/C:/Users/kyrod/OneDrive/Desktop/Gym-Mgmt/Gym-Mgmt-Sys-CBSE/osgi-implementation/member-management-bundle/target/member-management-bundle-1.0.0.jar
start 2
install file:/C:/Users/kyrod/OneDrive/Desktop/Gym-Mgmt/Gym-Mgmt-Sys-CBSE/osgi-implementation/report-analytics-bundle/target/report-analytics-bundle-1.0.0.jar
start 3
install file:/C:/Users/kyrod/OneDrive/Desktop/Gym-Mgmt/Gym-Mgmt-Sys-CBSE/osgi-implementation/test-client-bundle/target/test-client-bundle-1.0.0.jar
start 4
lb
services
```

Then run:
```bash
g! < install-bundles-felix.txt
```

### For Eclipse Equinox

Save as `install-bundles-equinox.txt`:
```
install file:/C:/Users/kyrod/OneDrive/Desktop/Gym-Mgmt/Gym-Mgmt-Sys-CBSE/osgi-implementation/base-library-bundle/target/base-library-bundle-1.0.0.jar
start 1
install file:/C:/Users/kyrod/OneDrive/Desktop/Gym-Mgmt/Gym-Mgmt-Sys-CBSE/osgi-implementation/member-management-bundle/target/member-management-bundle-1.0.0.jar
start 2
install file:/C:/Users/kyrod/OneDrive/Desktop/Gym-Mgmt/Gym-Mgmt-Sys-CBSE/osgi-implementation/report-analytics-bundle/target/report-analytics-bundle-1.0.0.jar
start 3
install file:/C:/Users/kyrod/OneDrive/Desktop/Gym-Mgmt/Gym-Mgmt-Sys-CBSE/osgi-implementation/test-client-bundle/target/test-client-bundle-1.0.0.jar
start 4
ss
services
```

Then run:
```bash
osgi> < install-bundles-equinox.txt
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

