# OSGi Implementation - Quick Start Guide

## Quick Setup

### 1. Build All Bundles

```bash
cd osgi-implementation
mvn clean install
```

This will build:
- `base-library-bundle/target/base-library-bundle-1.0.0.jar`
- `member-management-bundle/target/member-management-bundle-1.0.0.jar`

### 2. Download Apache Felix

```bash
# Download from https://felix.apache.org/downloads.cgi
# Extract to a directory
```

### 3. Copy Bundles to Felix

```bash
# Copy built JARs to felix-framework/bundle/
cp base-library-bundle/target/*.jar /path/to/felix-framework/bundle/
cp member-management-bundle/target/*.jar /path/to/felix-framework/bundle/
```

### 4. Start Felix Framework

```bash
cd /path/to/felix-framework
java -jar bin/felix.jar
```

### 5. Install and Start Bundles

In the Felix Gogo shell:

```bash
# List available bundles
g! lb

# Install base library bundle
g! install file:/path/to/base-library-bundle-1.0.0.jar
# Note the bundle ID (e.g., 1)

# Start base library bundle
g! start 1

# Install member management bundle
g! install file:/path/to/member-management-bundle-1.0.0.jar
# Note the bundle ID (e.g., 2)

# Start member management bundle
g! start 2

# Check services
g! services | grep MemberService

# List all bundles
g! lb
```

## Testing the Service

Create a simple test client or use the OSGi console to verify services are working.

## Troubleshooting

### Bundle Not Starting
- Check dependencies: `g! diag <bundle-id>`
- Verify imports/exports match
- Check bundle state: `g! lb`

### Service Not Found
- Verify service is registered: `g! services`
- Check component is active: `g! inspect capability service <bundle-id>`

### ClassNotFoundException
- Verify package exports in base-library-bundle
- Check package imports in dependent bundles
- Ensure versions match

