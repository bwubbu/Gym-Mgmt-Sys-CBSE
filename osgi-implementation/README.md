# OSGi Implementation - Gym Management System

This directory contains the OSGi-based componentization of the Gym Management System. The system is broken down into modular OSGi bundles that can be dynamically loaded, started, stopped, and updated at runtime.

## Architecture

The OSGi implementation follows a modular architecture where each component is a separate OSGi bundle:

### Bundles

1. **base-library-bundle** - Contains shared entities and service interfaces
   - Entities: `Person`, `Member`, `Trainer`, `Machine`, `Payment`, `Date`, `Admin`
   - Service Interfaces: `IMemberService`, `ITrainerService`, `IMachineService`, `IPaymentService`

2. **member-management-bundle** - Member management service implementation
   - Implements `IMemberService`
   - Provides CRUD operations for members

3. **trainer-management-bundle** - Trainer management service (to be implemented)
4. **machine-management-bundle** - Machine and booking management service
   - Implements `IMachineService`
   - Provides CRUD operations for machines and Booking logic
5. **payment-management-bundle** - Payment and financial management service (to be implemented)
6. **report-analytics-bundle** - Reporting and analytics service
   - Implements `IReportService`
   - Provides system reports and analytics

## Building the Bundles

### Prerequisites
- Java 17 or higher
- Maven 3.6+

### Build All Bundles

```bash
cd osgi-implementation
mvn clean install
```

### Build Individual Bundle

```bash
cd osgi-implementation/base-library-bundle
mvn clean install
```

## Running the OSGi Framework

### Option 1: Using Apache Felix

1. Download Apache Felix Framework from https://felix.apache.org/
2. Extract to a directory (e.g., `felix-framework`)
3. Copy all built JAR files to `felix-framework/bundle/`
4. Run Felix:

```bash
cd felix-framework
java -jar bin/felix.jar
```

5. In the Felix Gogo shell, install and start bundles:

```
g! install file:/path/to/base-library-bundle-1.0.0.jar
g! start <bundle-id>
g! install file:/path/to/member-management-bundle-1.0.0.jar
g! start <bundle-id>
```

### Option 2: Using Maven with Felix Maven Plugin

We can set up a Maven plugin to automatically launch Felix with all bundles. (To be added)

## Bundle Dependencies

```
base-library-bundle (no dependencies)
    ↑
    ├── member-management-bundle
    ├── trainer-management-bundle
    ├── machine-management-bundle
    ├── payment-management-bundle
    └── report-analytics-bundle
```

## OSGi Service Registry

Services are registered using OSGi Declarative Services (DS) annotations:

```java
@Component(
    name = "com.gymmanagement.osgi.member.service",
    service = IMemberService.class,
    immediate = true
)
public class MemberServiceImpl implements IMemberService {
    // Implementation
}
```

## Key OSGi Concepts Used

1. **Bundles**: Each component is packaged as an OSGi bundle (JAR with MANIFEST.MF)
2. **Services**: Components expose functionality through OSGi service registry
3. **Dependencies**: Bundles import/export packages via MANIFEST.MF
4. **Lifecycle**: BundleActivator handles start/stop events
5. **Dynamic**: Bundles can be installed, started, stopped, updated, and uninstalled at runtime

## Testing

To test the OSGi implementation:

1. Start the OSGi framework
2. Install and start the base-library-bundle
3. Install and start service bundles
4. Use OSGi console commands to verify services are registered
5. Test service functionality through a test client or console

## Comparison with Spring Boot Implementation

| Aspect | Spring Boot | OSGi |
|--------|-------------|-----|
| **Component Model** | Spring Beans | OSGi Services |
| **Dependency Injection** | Spring DI | OSGi Service Registry |
| **Lifecycle** | Application Context | Bundle Lifecycle |
| **Hot Deployment** | Limited (DevTools) | Full support |
| **Modularity** | Maven modules | OSGi Bundles |
| **Runtime** | JVM + Spring | OSGi Framework |

## Next Steps

1. Complete remaining service bundles
2. Create a test client bundle
3. Set up automated OSGi runtime launcher
4. Add persistence layer (database)
5. Create REST API bridge (if needed)
6. Add comprehensive tests

## Documentation

- [OSGi Specification](https://www.osgi.org/developer/specifications/)
- [Apache Felix Documentation](https://felix.apache.org/documentation/index.html)
- [OSGi Declarative Services](https://osgi.org/specification/osgi.cmpn/7.0.0/service.component.html)

