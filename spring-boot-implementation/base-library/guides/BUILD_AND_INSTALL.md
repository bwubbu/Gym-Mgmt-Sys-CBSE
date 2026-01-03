# How to Build and Install Base Library

## Quick Start

As the group leader, you need to build and install the Base Library so all team members can use it.

### Step 1: Navigate to Base Library Directory

```bash
cd base-library
```

### Step 2: Build and Install

```bash
mvn clean install
```

This will:
- Compile all Java files
- Package them into a JAR file
- Install the JAR to your local Maven repository (`~/.m2/repository/com/gymmanagement/base-library/1.0.0/`)

### Step 3: Verify Installation

Check that the JAR was created:
```bash
ls target/base-library-1.0.0.jar
```

## What This Does

When you run `mvn clean install`, Maven will:
1. **Clean** - Remove old build artifacts
2. **Compile** - Compile all Java source files
3. **Package** - Create a JAR file
4. **Install** - Copy the JAR to your local Maven repository

Once installed, other team members can add it as a dependency in their `pom.xml`:

```xml
<dependency>
    <groupId>com.gymmanagement</groupId>
    <artifactId>base-library</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Sharing with Team

### Each Team Member Builds Locally
1. Share the `base-library/` folder with your team (via Git, shared drive, etc.)
2. Each team member runs `mvn clean install` on their machine
3. Each team member's component can now use Base Library

## Updating Base Library

If you need to update Base Library:

1. Make your changes
2. Update version in `pom.xml` (e.g., `1.0.0` → `1.0.1`)
3. Run `mvn clean install` again
4. Notify team members to update their dependency version

## Troubleshooting

### "mvn: command not found"
- Install Maven: https://maven.apache.org/download.cgi
- Add Maven to your PATH

### "Java version error"
- Ensure Java 17+ is installed
- Set JAVA_HOME environment variable

### "Cannot find symbol" errors
- Make sure you've run `mvn clean install` successfully
- Check that all Java files compile without errors

## Next Steps

After building Base Library:
1. ✅ Share `base-library/` folder with team
2. ✅ Tell team members to run `mvn clean install` in base-library
3. ✅ Team members can now add Base Library dependency to their components
4. ✅ Start building components!

---

**Note**: The Base Library is version 1.0.0. When you make changes, increment the version number.

