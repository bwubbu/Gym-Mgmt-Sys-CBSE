# Running OSGi Bundles in Eclipse IDE - Step by Step

Eclipse IDE is built on Equinox, so you can run and debug OSGi bundles directly from the IDE. This is much easier than manual setup!

## Prerequisites

1. **Eclipse IDE for Java Developers** (or Eclipse IDE for Enterprise Java and Web Developers)
   - Download from: https://www.eclipse.org/downloads/
   - Version: 2023-12 or newer recommended
   - Make sure it includes "Equinox" (it's included by default)

## Step 1: Import Your Maven Projects into Eclipse

### Option A: Import Existing Maven Projects

1. Open Eclipse IDE
2. Go to: **File → Import...**
3. Select: **Maven → Existing Maven Projects**
4. Click **Next**
5. Browse to: `C:\Users\kyrod\OneDrive\Desktop\Gym-Mgmt\Gym-Mgmt-Sys-CBSE\osgi-implementation`
6. Eclipse should detect all Maven modules:
   - `base-library-bundle`
   - `member-management-bundle`
   - `report-analytics-bundle`
   - `test-client-bundle`
7. Make sure all are checked
8. Click **Finish**

### Option B: Import Each Project Separately

If Option A doesn't work, import each bundle separately:

1. **File → Import... → Maven → Existing Maven Projects**
2. Browse to each bundle folder:
   - `osgi-implementation\base-library-bundle`
   - `osgi-implementation\member-management-bundle`
   - `osgi-implementation\report-analytics-bundle`
   - `osgi-implementation\test-client-bundle`
3. Click **Finish** for each

## Step 2: Build Projects in Eclipse

1. Right-click on `osgi-implementation` (parent project) in Package Explorer
2. Select: **Run As → Maven install**
   - Or: Right-click → **Maven → Update Project** (to refresh)
3. Wait for build to complete
4. Check that JAR files are created in each bundle's `target/` folder

## Step 3: Create OSGi Run Configuration

### 3.1 Create New OSGi Framework Run Configuration

1. Go to: **Run → Run Configurations...**
2. Right-click **OSGi Framework** → **New Configuration**
3. Name it: `Gym Management OSGi`

### 3.2 Configure Bundles

In the **Bundles** tab:

1. **Deselect All** (to start fresh)
2. **Add Required Bundles** (this adds Equinox system bundles)
3. Manually select your bundles:
   - ✅ `com.gymmanagement.osgi.base` (base-library-bundle)
   - ✅ `com.gymmanagement.osgi.member` (member-management-bundle)
   - ✅ `com.gymmanagement.osgi.trainer` (trainer-management-bundle) ⚠️ **IMPORTANT**
   - ✅ `com.gymmanagement.osgi.machine` (machine-management-bundle)
   - ✅ `com.gymmanagement.osgi.payment` (payment-management-bundle)
   - ✅ `com.gymmanagement.osgi.report` (report-analytics-bundle)
   - ✅ `com.gymmanagement.osgi.test` (test-client-bundle)

**⚠️ CRITICAL: Set Auto-Start to `true`**

For each bundle, you'll see two columns: "Start Level" and "Auto-Start". The "Auto-Start" column should be set to **`true`** (not "default"):

1. Find each bundle in the list
2. Click on the "Auto-Start" column (shows "default")
3. Change it to **`true`** from the dropdown (options are: `default`, `true`, `false`)
4. This ensures bundles start automatically when the OSGi framework launches

**Why this matters:** If bundles are set to "default", they may not start automatically, causing services like `ITrainerService` to not be registered.

**Important:** Make sure to check:
- ✅ **Add new workspace bundles to this launch configuration automatically**
- ✅ **Validate bundles automatically**

### 3.3 Set Target Platform (Optional)

In the **Target Platform** tab:
- Leave as default (usually "Equinox")

### 3.4 Set Arguments (Optional)

In the **Arguments** tab:
- You can add VM arguments if needed (usually not necessary)

## Step 4: Run OSGi Configuration

1. Click **Run** button
2. Eclipse will start an Equinox OSGi runtime
3. You should see console output showing:
   - Bundles starting
   - Services registering
   - Test client output

## Step 5: Verify Services Are Running

### Check OSGi Console

Eclipse will show an OSGi console. You can interact with it:

```bash
# List all bundles
ss

# Check services
services

# Start/stop bundles
start <bundle-id>
stop <bundle-id>
```

### Check Console Output

Look for output from your test client:
```
========================================================
OSGi Test Client - Starting Tests
========================================================
--- Testing IMemberService ---
✅ IMemberService found and working
--- Testing IReportService ---
✅ IReportService found and working
========================================================
```

## Step 6: Debug OSGi Bundles (Optional)

To debug:

1. Set breakpoints in your Java code
2. Go to: **Run → Debug Configurations...**
3. Select your OSGi configuration
4. Click **Debug**
5. Execution will pause at breakpoints

## Troubleshooting

### Bundles Not Showing Up

**Problem:** Your bundles don't appear in the Bundles list

**Solution:**
1. Make sure projects are built: **Project → Clean... → Build**
2. Refresh projects: Right-click → **Refresh**
3. Check that `MANIFEST.MF` files are generated in `META-INF/` folders
4. Verify Maven Bundle Plugin is configured correctly

### Services Not Registering

**Problem:** Services not found

**Solution:**
1. Check bundle status: `ss` command in OSGi console
2. Make sure all bundles are `ACTIVE` (not just `INSTALLED`)
3. Verify Declarative Services XML files are in `OSGI-INF/` folders
4. Check that base-library-bundle is started before dependent bundles

### ClassNotFoundException

**Problem:** Classes not found

**Solution:**
1. Verify package exports in `MANIFEST.MF`:
   - Base library should export: `com.gymmanagement.osgi.base.entity`, `com.gymmanagement.osgi.base.service`
2. Verify package imports in dependent bundles
3. Check version ranges match

### Maven Build Errors

**Problem:** Maven build fails in Eclipse

**Solution:**
1. Right-click project → **Maven → Update Project**
2. Check Java version: **Project → Properties → Java Build Path**
3. Make sure Java 17+ is configured
4. Try: **Project → Clean... → Build**

## Advantages of Using Eclipse IDE

✅ **No manual setup** - Equinox is built-in  
✅ **Visual debugging** - Set breakpoints, step through code  
✅ **Automatic dependency resolution** - Eclipse handles bundle dependencies  
✅ **Integrated console** - OSGi console built into Eclipse  
✅ **Easy bundle management** - Visual interface for managing bundles  
✅ **Hot reload** - Can update bundles without restarting (with some configuration)  

## Quick Reference: Eclipse OSGi Commands

In Eclipse's OSGi console:
```bash
ss              # List all bundles
start <id>      # Start a bundle
stop <id>       # Stop a bundle
diag <id>       # Diagnose bundle issues
services        # List all services
help            # Show all commands
```

## Alternative: Use Eclipse PDE (Plug-in Development Environment)

If you want even more OSGi features:

1. Install **Eclipse IDE for RCP and RAP Developers** (includes PDE)
2. Or install PDE from: **Help → Eclipse Marketplace → Search "PDE"**
3. This gives you:
   - OSGi bundle editor
   - Manifest editor
   - Service component editor
   - More advanced debugging tools

## Next Steps

Once everything runs in Eclipse:
1. Add breakpoints and debug your services
2. Create remaining bundles (Trainer, Machine, Payment)
3. Add unit tests
4. Export bundles for deployment

---

**That's it!** Eclipse IDE makes OSGi development much easier. No need to manually download Equinox or manage JAR files!
