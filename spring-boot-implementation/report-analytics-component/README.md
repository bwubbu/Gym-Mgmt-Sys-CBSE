# Report Analytics Component

## Overview

This is the **Report Analytics Component** for the Gym Management System, implemented as a Spring Boot microservice. This component is responsible for generating system reports, analyzing member analytics, analyzing equipment usage, and exporting reports in various formats.

## Component Architecture

According to the component architecture diagram:
- **Component Name**: Report Analytics Component (gym report)
- **Internal Elements**: `Report`, `ReportManager` (implemented as `ReportService`)
- **Provided Interface**: `IReportService` (should be in Base Library)
- **Dependencies**: Base Library (for entities: Member, Trainer, Machine, Payment, Report)

## Features

### 1. Generate System Reports
- Full Report
- Trainer Report
- Member Report
- Machine Report
- Booking Report
- Outstanding Balance Report
- Zero Outstanding Balance Report

### 2. Analyze Member Analytics (NEW FEATURE)
- Member Demographics (age groups, gender distribution)
- Growth Trends (monthly/yearly registrations, growth rate)
- Body Statistics (average BMI, weight, height, BMI categories)
- Fitness Goal Distribution

### 3. Analyze Equipment Usage Analytics (NEW FEATURE)
- Overall utilization statistics
- Machine popularity and booking counts
- Capacity distribution
- Utilization rates per machine

### 4. Export Reports (NEW FEATURE)
- Export to TXT format
- Export to JSON format
- Export to CSV format

## Technology Stack

- **Spring Boot 3.2.0**
- **Java 17**
- **Maven**
- **Spring Web** (REST APIs)
- **Spring Data JPA** (for potential database integration)
- **H2 Database** (for development/testing)
- **Jackson** (JSON processing)
- **Apache Commons CSV** (CSV export)
- **Lombok** (reducing boilerplate)

## Project Structure

```
report-analytics-component/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/gymmanagement/reportanalytics/
│   │   │       ├── base/
│   │   │       │   └── IReportService.java          # Service interface
│   │   │       ├── controller/
│   │   │       │   ├── ReportController.java        # System reports endpoints
│   │   │       │   ├── MemberAnalyticsController.java # Member analytics endpoints
│   │   │       │   └── EquipmentAnalyticsController.java # Equipment analytics endpoints
│   │   │       ├── dto/
│   │   │       │   ├── MemberDemographicsDTO.java
│   │   │       │   ├── GrowthTrendsDTO.java
│   │   │       │   ├── BodyStatisticsDTO.java
│   │   │       │   ├── FitnessGoalDistributionDTO.java
│   │   │       │   ├── EquipmentUsageAnalyticsDTO.java
│   │   │       │   ├── MemberDTO.java
│   │   │       │   ├── TrainerDTO.java
│   │   │       │   ├── MachineDTO.java
│   │   │       │   ├── BookingDTO.java
│   │   │       │   └── PaymentDTO.java
│   │   │       ├── entity/
│   │   │       │   └── Report.java                  # Report entity
│   │   │       ├── service/
│   │   │       │   ├── ReportService.java           # Main service implementation
│   │   │       │   └── DataService.java             # Data fetching from other components
│   │   │       ├── config/
│   │   │       │   └── RestTemplateConfig.java       # REST client configuration
│   │   │       └── ReportAnalyticsApplication.java  # Main application class
│   │   └── resources/
│   │       ├── application.yml
│   │       └── application.properties
│   └── test/
└── pom.xml
```

## API Endpoints

### System Reports

- `GET /api/reports/full` - Generate full system report
- `GET /api/reports/trainer` - Generate trainer report
- `GET /api/reports/member` - Generate member report
- `GET /api/reports/machine` - Generate machine report
- `GET /api/reports/booking` - Generate booking report
- `GET /api/reports/outstanding-balance` - Generate outstanding balance report
- `GET /api/reports/zero-outstanding-balance` - Generate zero outstanding balance report
- `GET /api/reports/export/full/txt` - Export full report as TXT

### Member Analytics

- `GET /api/analytics/member/demographics` - Get member demographics analytics
- `GET /api/analytics/member/growth-trends` - Get growth trends analytics
- `GET /api/analytics/member/body-statistics` - Get body statistics analytics
- `GET /api/analytics/member/fitness-goals` - Get fitness goal distribution
- `GET /api/analytics/member/demographics/export/json` - Export demographics as JSON
- `GET /api/analytics/member/demographics/export/txt` - Export demographics as TXT

### Equipment Analytics

- `GET /api/analytics/equipment/usage` - Get equipment usage analytics
- `GET /api/analytics/equipment/usage/export/json` - Export usage analytics as JSON
- `GET /api/analytics/equipment/usage/export/txt` - Export usage analytics as TXT
- `GET /api/analytics/equipment/usage/export/csv` - Export usage analytics as CSV

## Running the Application

### Prerequisites

- Java 17 or higher
- Maven 3.6+

### Build and Run

```bash
# Navigate to the component directory
cd report-analytics-component

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The application will start on port **8085** by default.

### Access the Application

- API Base URL: `http://localhost:8085`
- H2 Console: `http://localhost:8085/h2-console`

## Integration with Other Components

This component needs to communicate with other components to fetch data:

- **Member Management Component** (port 8081)
- **Trainer Management Component** (port 8082)
- **Equipment Booking Component** (port 8083)
- **Financial Management Component** (port 8084)

The `DataService` class contains placeholder methods that need to be implemented with actual REST API calls once other components are available.

### Example Integration

Update the `DataService` methods to call actual REST endpoints:

```java
public List<MemberDTO> getAllMembers() {
    return restTemplate.getForObject(
        memberServiceUrl + "/api/members", 
        new ParameterizedTypeReference<List<MemberDTO>>() {}
    );
}
```

## Configuration

Service URLs for other components can be configured in `application.yml` or `application.properties`:

```yaml
services:
  member:
    url: http://localhost:8081
  trainer:
    url: http://localhost:8082
  booking:
    url: http://localhost:8083
  finance:
    url: http://localhost:8084
```

## Testing

### Using cURL

```bash
# Get member demographics
curl http://localhost:8085/api/analytics/member/demographics

# Get equipment usage analytics
curl http://localhost:8085/api/analytics/equipment/usage

# Export full report
curl -O http://localhost:8085/api/reports/export/full/txt
```

### Using Postman

Import the API endpoints into Postman and test each endpoint.

## Development Notes

1. **DataService**: Currently returns empty lists. Replace with actual REST API calls to other components.
2. **Database**: H2 in-memory database is used for development. Replace with production database (PostgreSQL, MySQL, etc.) for production.
3. **Error Handling**: Add proper error handling and exception management.
4. **Security**: Add authentication/authorization (Spring Security) for production.
5. **Validation**: Add input validation for API endpoints.
6. **Logging**: Configure proper logging for production.

## Dependencies on Other Components

This component depends on:
- **Base Library**: For entity definitions (Member, Trainer, Machine, Payment)
- **Member Management Component**: For member data
- **Trainer Management Component**: For trainer data
- **Equipment Booking Component**: For machine and booking data
- **Financial Management Component**: For payment data

## Author

**Rudzaidan** - Report Analytics Component

## License

This project is part of the Gym Management System.

