## 4.2.0 Spring Boot Implementation – Development Details

### Overview

The Spring Boot implementation of the Gym Management System is a **dynamic and efficient microservices-based application** developed using Spring Boot, leveraging its annotations for effective structuring and rapid development. Leveraging Spring Boot and modern tools allowed us to build, manage, and scale the system effectively.

The system is broken down into modular Spring Boot microservices that can be independently deployed and scaled:

- **base-library**: Contains shared entities, service interfaces, and DTOs used across all microservices
- **member-management-component** (Port 8081): Implements `IMemberService` for member CRUD operations
- **trainer-management-component** (Port 8082): Implements `ITrainerService` for trainer management
- **equipment-booking-component** (Port 8083): Implements `IMachineService` for machine and booking management
- **financial-management-component** (Port 8084): Implements `IFinanceService` for payment and financial management
- **report-analytics-component** (Port 8085): Implements `IReportService` for system reports and analytics

### Primary Technologies and Tools

- **Java**: The primary programming language (Java 17)
- **Spring Boot**: Used for rapid application development with minimal configuration, enabling standalone microservice deployment across all components
- **Maven**: Handled dependency management and build automation

#### Primary Technologies and Tools

- **Java**: The primary programming language (Java 17)
- **Spring Boot**: Used for rapid application development with minimal configuration, enabling standalone microservice deployment
- **Maven**: Handled dependency management and build automation

#### Spring Annotations and Components

- **@SpringBootApplication**: Used in the main application class of each microservice to enable auto-configuration, component scanning, and Spring Boot features, serving as the entry point for each microservice. Examples include:
  - `ReportAnalyticsApplication` in report-analytics-component (port 8085)
  - `TrainerManagementApplication` in trainer-management-component (port 8082)
  - `FinancialManagementApplication` in financial-management-component (port 8084)

```8:11:spring-boot-implementation/report-analytics-component/src/main/java/com/gymmanagement/reportanalytics/ReportAnalyticsApplication.java
@SpringBootApplication
@ComponentScan(basePackages = "com.gymmanagement.reportanalytics")
@EntityScan(basePackages = "com.gymmanagement.reportanalytics")
public class ReportAnalyticsApplication {
```

- **@Service**: Used to encapsulate business logic in service classes across all microservices:
  - `ReportService` class in report-analytics-component for report generation and analytics operations
  - `DataService` class in report-analytics-component for data access operations (fetching data from other microservices)
  - Similar service classes exist in other components (TrainerService, PaymentService, etc.)

```17:18:spring-boot-implementation/report-analytics-component/src/main/java/com/gymmanagement/reportanalytics/service/ReportService.java
@Service
public class ReportService implements IReportService {
```

```20:21:spring-boot-implementation/report-analytics-component/src/main/java/com/gymmanagement/reportanalytics/service/DataService.java
@Service
public class DataService {
```

- **@RestController**: Used to define REST API controllers that handle HTTP requests and return JSON or HTML responses across all microservices:
  - `ReportController` class in report-analytics-component for system report endpoints (`/api/reports/*`)
  - `MemberAnalyticsController` class in report-analytics-component for member analytics endpoints (`/api/analytics/member/*`)
  - `EquipmentAnalyticsController` class in report-analytics-component for equipment analytics endpoints (`/api/analytics/equipment/*`)
  - Similar controllers exist in other components (TrainerController, PaymentController, etc.)

```16:19:spring-boot-implementation/report-analytics-component/src/main/java/com/gymmanagement/reportanalytics/controller/ReportController.java
@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
public class ReportController {
```

- **@RequestMapping**: Used to map HTTP requests to controller methods, defining base URL paths for each controller across all microservices (`/api/reports`, `/api/trainers`, `/api/members`, `/api/payments`, etc.)

- **@GetMapping**, **@PostMapping**, **@PutMapping**, **@DeleteMapping**: Used to map HTTP requests to specific handler methods, defining individual endpoints for CRUD operations, report generation, analytics retrieval, and HTML UI rendering

```29:44:spring-boot-implementation/report-analytics-component/src/main/java/com/gymmanagement/reportanalytics/controller/ReportController.java
@GetMapping(value = "/full", produces = {MediaType.TEXT_HTML_VALUE, MediaType.TEXT_PLAIN_VALUE})
public ResponseEntity<String> generateFullReport(@RequestHeader(value = "Accept", required = false) String accept) {
    if (accept == null || accept.contains("text/html") || accept.contains("*/*")) {
        String htmlReport = reportServiceImpl.generateFullReportHtml();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_HTML);
        return new ResponseEntity<>(htmlReport, headers, HttpStatus.OK);
    } else {
        String report = reportService.generateFullReport();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        return new ResponseEntity<>(report, headers, HttpStatus.OK);
    }
}
```

- **@Value**: Used for automatic dependency injection of configuration properties, ensuring service URLs and other configuration values are configurable (e.g., injecting member service URL, trainer service URL from `application.yml`)

```23:33:spring-boot-implementation/report-analytics-component/src/main/java/com/gymmanagement/reportanalytics/service/DataService.java
@Value("${services.member.url:http://localhost:8081}")
private String memberServiceUrl;

@Value("${services.trainer.url:http://localhost:8082}")
private String trainerServiceUrl;

@Value("${services.booking.url:http://localhost:8083}")
private String bookingServiceUrl;

@Value("${services.finance.url:http://localhost:8084}")
private String financeServiceUrl;
```

- **@CrossOrigin**: Used to enable Cross-Origin Resource Sharing (CORS), allowing the REST APIs to be accessed from web browsers and other frontend applications across all microservices

- **@Configuration**: Used in configuration classes (e.g., `RestTemplateConfig`) to define beans and application configuration

#### Key Functionalities

The Spring Boot implementation provides comprehensive gym management capabilities across multiple microservices:

- **Member Management**: Allows users to add, view, edit, delete, and search for members through the member-management-component (port 8081)
- **Trainer Management**: Provides trainer CRUD operations, profile management, member assignment, and performance tracking through the trainer-management-component (port 8082)
- **Equipment and Booking Management**: Enables machine management, booking operations, maintenance tracking, and usage statistics through the equipment-booking-component (port 8083)
- **Payment Management**: Handles payment processing, outstanding balance management, financial reporting, and payment analytics through the financial-management-component (port 8084)
- **System Report Generation**: Generates comprehensive system reports including full reports, trainer reports, member reports, machine reports, booking reports, and financial status reports through the report-analytics-component (port 8085)
- **Member Analytics**: Includes features to analyze member demographics, growth trends, body statistics, and fitness goal distribution
- **Equipment Usage Analytics**: Analyzes equipment utilization rates, machine popularity, capacity distribution, and overall equipment usage statistics
- **Report Export**: Allows users to export reports in multiple formats (TXT, JSON, CSV) for further analysis or archival purposes
- **HTML Dashboard**: Provides user-friendly HTML interfaces for analytics endpoints, enabling browser-based visualization and presentation

#### Additional Tools and Libraries

- **Spring Web**: Used for building RESTful web services and handling HTTP requests/responses
- **Spring Data JPA**: Used for potential database integration and data persistence (configured for future database connectivity)
- **Jackson**: Used for JSON processing and serialization/deserialization of DTOs in REST API responses
- **Apache Commons CSV**: Used for CSV export functionality, enabling structured data export in CSV format
- **Lombok**: Used for reducing boilerplate code (getters, setters, constructors) in DTO classes
- **H2 Database**: Used as an in-memory database for testing and development (can be replaced with actual database in production)
- **JUnit**: Used for writing and running unit tests (included via `spring-boot-starter-test` dependency). Test files are located in `src/test/java/` and can be run using `mvn test` or from the IDE.
- **Mockito**: Used for creating mock objects for unit testing, enabling isolated testing of service components without dependencies on external services (included via `spring-boot-starter-test` dependency). Example test files include `ReportServiceTest.java` and `ReportControllerTest.java`.
- **Maven Compiler Plugin**: Used for compiling Java source code with Java 17 compatibility

#### Summary

The Spring Boot-based Gym Management System leverages microservices architecture, dependency injection, and clean separation of concerns. It highlights the use of Spring Boot annotations (`@SpringBootApplication`, `@Service`, `@RestController`, `@RequestMapping`, `@Value`, `@CrossOrigin`) for rapid development and loose coupling, Spring Web for RESTful API development, Spring Data JPA for data persistence, Jackson for JSON processing, and JUnit with Mockito for comprehensive testing. The microservices architecture enables each component to be independently deployed, scaled, and maintained, providing a flexible and extensible system for gym management operations including member management, trainer management, equipment booking, payment processing, and comprehensive reporting and analytics.

---

## 4.2.1 Spring Boot Implementation – Report Analytics Component (My Module)

### Overview

The Spring Boot implementation of the **Report Analytics Component** is a standalone **microservice** that exposes REST APIs and lightweight HTML dashboards for generating reports and analytics about the gym. It focuses on:

- System reports (full, member, trainer, machine, booking, outstanding/zero balance)
- Member analytics (demographics, growth trends, body statistics, fitness goals)
- Equipment usage analytics
- Export of reports (TXT, JSON, CSV)

All of this is implemented as a Spring Boot application in the `report-analytics-component` module.

### Architecture and Componentization

- **Component**: Report Analytics Component (Spring Boot microservice)
- **Main application class**: starts the Spring context and exposes the APIs

```1:16:spring-boot-implementation/report-analytics-component/src/main/java/com/gymmanagement/reportanalytics/ReportAnalyticsApplication.java
@SpringBootApplication
@ComponentScan(basePackages = "com.gymmanagement.reportanalytics")
@EntityScan(basePackages = "com.gymmanagement.reportanalytics")
public class ReportAnalyticsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReportAnalyticsApplication.class, args);
    }
}
```

- **Core service (ReportService)**: implements the business logic for all reports and analytics, via the `IReportService` interface.
- **Data access service (DataService)**: currently returns **in-memory sample data** for members, trainers, machines and bookings (so my component can run and be demoed even if the other microservices are not ready).
- **Controllers**: expose clean REST endpoints for system reports and analytics, plus simple HTML GUIs for presentation.

### Key Classes and Responsibilities

- **`ReportService`** – main report/analytics implementation

```11:18:spring-boot-implementation/report-analytics-component/src/main/java/com/gymmanagement/reportanalytics/service/ReportService.java
/**
 * ReportService Implementation
 * 
 * This is the ReportManager/ReportService implementation for the Report Analytics Component.
 * It implements IReportService interface and provides all report generation and analytics functionality.
 */
@Service
public class ReportService implements IReportService {
```

- **`DataService`** – provides sample data for demo (this would later call other microservices in a real deployment):

```41:52:spring-boot-implementation/report-analytics-component/src/main/java/com/gymmanagement/reportanalytics/service/DataService.java
// =====================================================================
// SAMPLE DATA FOR DEMO / PRESENTATION
// ---------------------------------------------------------------------
// For now, we return in-memory sample data so that analytics and reports
// have something meaningful to show even if other components are offline.
// =====================================================================

private List<MemberDTO> sampleMembers;
private List<TrainerDTO> sampleTrainers;
private List<MachineDTO> sampleMachines;
private List<BookingDTO> sampleBookings;
```

- **System report controller (`ReportController`)**:

```16:29:spring-boot-implementation/report-analytics-component/src/main/java/com/gymmanagement/reportanalytics/controller/ReportController.java
@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
public class ReportController {
    
    private final IReportService reportService;
    private final ReportService reportServiceImpl;
```

- **Member analytics controller (`MemberAnalyticsController`)** and **Equipment analytics controller (`EquipmentAnalyticsController`)** expose both JSON APIs and HTML UIs:

```16:27:spring-boot-implementation/report-analytics-component/src/main/java/com/gymmanagement/reportanalytics/controller/MemberAnalyticsController.java
@RestController
@RequestMapping("/api/analytics/member")
@CrossOrigin(origins = "*")
public class MemberAnalyticsController {
    
    private final IReportService reportService;
```

```16:27:spring-boot-implementation/report-analytics-component/src/main/java/com/gymmanagement/reportanalytics/controller/EquipmentAnalyticsController.java
@RestController
@RequestMapping("/api/analytics/equipment")
@CrossOrigin(origins = "*")
public class EquipmentAnalyticsController {
    
    private final IReportService reportService;
```

### How the Component is Run (for Demo)

From the project root:

```bash
cd spring-boot-implementation/report-analytics-component
mvn clean install
mvn spring-boot:run
```

The service starts on **port 8085** and exposes:

- Main HTML dashboard: `http://localhost:8085/api/reports/full`
- JSON and HTML endpoints for all analytics (see below).

For the purposes of the report and presentation, this Spring Boot component behaves as an **autonomous analytics microservice** that other parts of the system (or a frontend) can call.

> **Figure suggestion**: Overall architecture of Spring Boot components showing `report-analytics-component` as one microservice, with arrows to other services (member, trainer, equipment booking, finance).  
> **Code reference for figure caption**: `report-analytics-component/src/main/java/com/gymmanagement/reportanalytics/ReportAnalyticsApplication.java` (Lines 1–16) and `service/ReportService.java` (Lines 11–24).

---

## 4.2.6 Spring Boot Report Analytics – System Reports

This subsection corresponds to the **system-wide reports**: full report, trainer/member/machine/booking overviews, and financial status (outstanding vs zero balance).

### Full Report Generation Logic

The full report reuses the same logic as the OSGi version but wrapped in Spring Boot. It concatenates all sub‑reports into a single string:

```28:39:spring-boot-implementation/report-analytics-component/src/main/java/com/gymmanagement/reportanalytics/service/ReportService.java
@Override
public String generateFullReport() {
    LocalDate currentDate = LocalDate.now();
    String lines = "--------------------------------";
    String reportDetails = "\n Date : " + currentDate + "\n";
    reportDetails += generateTrainerReport() + lines 
            + generateMemberReport() + lines
            + generateMachineReport() + lines 
            + generateBookingReport() + lines 
            + generateReportOfOutstandingBalance() + lines
            + generateReportOfZeroOutstandingBalance() + lines;
    return reportDetails;
}
```

On top of that, I added a **beautiful HTML version** of the full report for browser-based demoing:

```45:96:spring-boot-implementation/report-analytics-component/src/main/java/com/gymmanagement/reportanalytics/service/ReportService.java
public String generateFullReportHtml() {
    LocalDate currentDate = LocalDate.now();
    StringBuilder html = new StringBuilder();
    ...
    html.append("        <div class=\"header\">\n");
    html.append("            <h1>🏋️ Gym Management System</h1>\n");
    html.append("            <div class=\"date\">Report Generated: ").append(currentDate).append("</div>\n");
    html.append("            <div class=\"nav-bar\">\n");
    html.append("                <a class=\"nav-btn\" href=\"/api/reports/full\" target=\"_blank\"><span class=\"icon\">📄</span><span>View Full Report (Text)</span></a>\n");
    html.append("                <a class=\"nav-btn\" href=\"/api/reports/export/full/txt\" target=\"_blank\"><span class=\"icon\">⬇️</span><span>Download Full Report (TXT)</span></a>\n");
    html.append("                <a class=\"nav-btn\" href=\"/api/analytics/member/demographics/ui\" target=\"_blank\"><span class=\"icon\">👥</span><span>Member Demographics</span></a>\n");
    html.append("                <a class=\"nav-btn\" href=\"/api/analytics/member/growth-trends/ui\" target=\"_blank\"><span class=\"icon\">📈</span><span>Growth Trends</span></a>\n");
    html.append("                <a class=\"nav-btn\" href=\"/api/analytics/member/body-statistics/ui\" target=\"_blank\"><span class=\"icon\">⚖️</span><span>Body Statistics</span></a>\n");
    html.append("                <a class=\"nav-btn\" href=\"/api/analytics/member/fitness-goals/ui\" target=\"_blank\"><span class=\"icon\">🎯</span><span>Fitness Goals</span></a>\n");
    html.append("                <a class=\"nav-btn\" href=\"/api/analytics/equipment/usage/ui\" target=\"_blank\"><span class=\"icon\">🛠️</span><span>Equipment Usage</span></a>\n");
    html.append("            </div>\n");
    ...
}
```

This HTML method is invoked when the browser calls `GET /api/reports/full` with an `Accept: text/html` header (or when there is no explicit `Accept` header):

```29:44:spring-boot-implementation/report-analytics-component/src/main/java/com/gymmanagement/reportanalytics/controller/ReportController.java
@GetMapping(value = "/full", produces = {MediaType.TEXT_HTML_VALUE, MediaType.TEXT_PLAIN_VALUE})
public ResponseEntity<String> generateFullReport(@RequestHeader(value = "Accept", required = false) String accept) {
    if (accept == null || accept.contains("text/html") || accept.contains("*/*")) {
        String htmlReport = reportServiceImpl.generateFullReportHtml();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_HTML);
        return new ResponseEntity<>(htmlReport, headers, HttpStatus.OK);
    } else {
        String report = reportService.generateFullReport();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        return new ResponseEntity<>(report, headers, HttpStatus.OK);
    }
}
```

> **Figure suggestion**: Screenshot of the HTML **Full Report Dashboard** showing trainers, members, machines, bookings, and the navigation buttons.  
> **Code reference**: `ReportService.java` (Lines 45–96) and `ReportController.java` (Lines 29–44).

### Sub‑Reports (Trainer, Member, Machine, Booking, Financial)

Each sub‑report is implemented as a simple method in `ReportService` and has a corresponding REST endpoint in `ReportController`:

```223:293:spring-boot-implementation/report-analytics-component/src/main/java/com/gymmanagement/reportanalytics/service/ReportService.java
@Override
public String generateTrainerReport() { ... }

@Override
public String generateMemberReport() { ... }

@Override
public String generateMachineReport() { ... }

@Override
public String generateBookingReport() { ... }

@Override
public String generateReportOfOutstandingBalance() { ... }

@Override
public String generateReportOfZeroOutstandingBalance() { ... }
```

```47:81:spring-boot-implementation/report-analytics-component/src/main/java/com/gymmanagement/reportanalytics/controller/ReportController.java
@GetMapping("/trainer")
public ResponseEntity<String> generateTrainerReport() { ... }

@GetMapping("/member")
public ResponseEntity<String> generateMemberReport() { ... }

@GetMapping("/machine")
public ResponseEntity<String> generateMachineReport() { ... }

@GetMapping("/booking")
public ResponseEntity<String> generateBookingReport() { ... }

@GetMapping("/outstanding-balance")
public ResponseEntity<String> generateOutstandingBalanceReport() { ... }

@GetMapping("/zero-outstanding-balance")
public ResponseEntity<String> generateZeroOutstandingBalanceReport() { ... }
```

These endpoints can be called directly (for plain text), or you can stay on the HTML dashboard and rely on the embedded navigation buttons.

> **Figure suggestion**: Table in the report summarizing endpoints vs methods, e.g. `/api/reports/member` → `generateMemberReport()` in `ReportService`.  
> **Code reference**: `ReportService.java` (Lines 223–293) and `ReportController.java` (Lines 47–81).

### Exporting Reports (TXT / JSON / CSV)

The Spring Boot version exposes a TXT export endpoint for the full report:

```83:93:spring-boot-implementation/report-analytics-component/src/main/java/com/gymmanagement/reportanalytics/controller/ReportController.java
@GetMapping(value = "/export/full/txt", produces = MediaType.TEXT_PLAIN_VALUE)
public ResponseEntity<byte[]> exportFullReportTxt() {
    String report = reportService.generateFullReport();
    byte[] content = reportService.exportReportToTxt(report, "FullReport");
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.TEXT_PLAIN);
    headers.setContentDispositionFormData("attachment", "FullReport.txt");
    return new ResponseEntity<>(content, headers, HttpStatus.OK);
}
```

Internally, all export formats are handled by `ReportService`:

```539:552:spring-boot-implementation/report-analytics-component/src/main/java/com/gymmanagement/reportanalytics/service/ReportService.java
@Override
public byte[] exportReportToJson(Object reportData, String reportType) { ... }

@Override
public byte[] exportReportToTxt(String reportContent, String reportType) { ... }

@Override
public byte[] exportReportToCsv(String reportContent, String reportType) { ... }
```

> **Figure suggestion**: Screenshot of the browser file download prompt for `FullReport.txt`.  
> **Code reference**: `ReportController.java` (Lines 83–93).

---

## 4.2.7 Spring Boot Report Analytics – Member & Equipment Analytics

This subsection covers the **analytics** part of my module. All analytics endpoints use the same sample data in `DataService`, which gives consistent numbers across:

- Member demographics
- Growth trends
- Body statistics
- Fitness goal distribution
- Equipment usage analytics

### Member Analytics – Demographics

The logic for demographics is implemented in `ReportService`:

```298:345:spring-boot-implementation/report-analytics-component/src/main/java/com/gymmanagement/reportanalytics/service/ReportService.java
@Override
public MemberDemographicsDTO generateMemberDemographicsReport() {
    LocalDate currentDate = LocalDate.now();
    List<MemberDTO> members = dataService.getAllMembers();
    ...
    // genderDistribution, genderPercentages
    // ageGroupDistribution, ageGroupPercentages
    ...
    return new MemberDemographicsDTO(totalMembers, genderDistribution, ageGroupDistribution,
            genderPercentages, ageGroupPercentages, currentDate.toString());
}
```

The JSON endpoint and HTML GUI are exposed via `MemberAnalyticsController`:

```27:31:spring-boot-implementation/report-analytics-component/src/main/java/com/gymmanagement/reportanalytics/controller/MemberAnalyticsController.java
@GetMapping("/demographics")
public ResponseEntity<MemberDemographicsDTO> getMemberDemographics() {
    MemberDemographicsDTO report = reportService.generateMemberDemographicsReport();
    return ResponseEntity.ok(report);
}
```

```36:81:spring-boot-implementation/report-analytics-component/src/main/java/com/gymmanagement/reportanalytics/controller/MemberAnalyticsController.java
@GetMapping(value = "/demographics/ui", produces = MediaType.TEXT_HTML_VALUE)
public ResponseEntity<String> getMemberDemographicsHtml() {
    MemberDemographicsDTO report = reportService.generateMemberDemographicsReport();
    StringBuilder html = new StringBuilder();
    ...
    html.append("<h2>Gender Distribution</h2>");
    ...
    html.append("<h2 style=\"margin-top:24px;\">Age Group Distribution</h2>");
    ...
}
```

> **Figure suggestion**: Screenshot of the **Member Demographics** HTML page (`/api/analytics/member/demographics/ui`).  
> **Code reference**: `MemberAnalyticsController.java` (Lines 36–81).

### Member Analytics – Growth Trends

Growth trends logic groups registrations per month and per year, and calculates a simple year‑over‑year growth rate:

```361:386:spring-boot-implementation/report-analytics-component/src/main/java/com/gymmanagement/reportanalytics/service/ReportService.java
@Override
public GrowthTrendsDTO generateGrowthTrendsReport() {
    LocalDate currentDate = LocalDate.now();
    List<MemberDTO> members = dataService.getAllMembers();
    ...
    // monthlyRegistrations, yearlyRegistrations
    ...
    if (years.size() >= 2) {
        int currentYear = years.get(years.size() - 1);
        int previousYear = years.get(years.size() - 2);
        int currentCount = yearlyRegistrations.get(currentYear);
        int previousCount = yearlyRegistrations.get(previousYear);
        if (previousCount > 0) {
            growthRate = ((currentCount - previousCount) * 100.0) / previousCount;
            growthRatePeriod = previousYear + " to " + currentYear;
        }
    }
    return new GrowthTrendsDTO(...);
}
```

The HTML GUI for growth trends:

```93:136:spring-boot-implementation/report-analytics-component/src/main/java/com/gymmanagement/reportanalytics/controller/MemberAnalyticsController.java
@GetMapping(value = "/growth-trends/ui", produces = MediaType.TEXT_HTML_VALUE)
public ResponseEntity<String> getGrowthTrendsHtml() {
    GrowthTrendsDTO report = reportService.generateGrowthTrendsReport();
    ...
    // Monthly table (Month/Year vs Registrations)
    // Yearly table (Year vs Registrations)
    // Optional paragraph for growthRate and growthRatePeriod
}
```

> **Figure suggestion**: Screenshot of the **Growth Trends** HTML page with the monthly and yearly tables.  
> **Code reference**: `MemberAnalyticsController.java` (Lines 93–136).

### Member Analytics – Body Statistics & Fitness Goals

Body statistics compute averages and BMI category distributions:

```402:450:spring-boot-implementation/report-analytics-component/src/main/java/com/gymmanagement/reportanalytics/service/ReportService.java
@Override
public BodyStatisticsDTO generateBodyStatisticsReport() {
    ...
    // totalHeight, totalWeight, totalBMI, bmiCategoryDistribution
    ...
    double avgHeight = validRecords > 0 ? totalHeight / validRecords : 0;
    double avgWeight = validRecords > 0 ? totalWeight / validRecords : 0;
    double avgBMI = validRecords > 0 ? totalBMI / validRecords : 0;
    return new BodyStatisticsDTO(avgHeight, avgWeight, avgBMI, 
            bmiCategoryDistribution, bmiCategoryPercentages, validRecords, currentDate.toString());
}
```

HTML body statistics GUI:

```148:188:spring-boot-implementation/report-analytics-component/src/main/java/com/gymmanagement/reportanalytics/controller/MemberAnalyticsController.java
@GetMapping(value = "/body-statistics/ui", produces = MediaType.TEXT_HTML_VALUE)
public ResponseEntity<String> getBodyStatisticsHtml() {
    BodyStatisticsDTO report = reportService.generateBodyStatisticsReport();
    ...
    // Shows Average Height, Average Weight, Average BMI
    // plus a table of BMI categories vs count and percentage
}
```

Fitness goal distribution uses the member goals:

```454:479:spring-boot-implementation/report-analytics-component/src/main/java/com/gymmanagement/reportanalytics/service/ReportService.java
@Override
public FitnessGoalDistributionDTO generateFitnessGoalDistributionReport() {
    ...
    // goalDistribution and goalPercentages based on fitnessGoal field
    ...
    return new FitnessGoalDistributionDTO(totalMembers, membersWithGoals, 
            goalDistribution, goalPercentages, currentDate.toString());
}
```

HTML GUI:

```200:233:spring-boot-implementation/report-analytics-component/src/main/java/com/gymmanagement/reportanalytics/controller/MemberAnalyticsController.java
@GetMapping(value = "/fitness-goals/ui", produces = MediaType.TEXT_HTML_VALUE)
public ResponseEntity<String> getFitnessGoalDistributionHtml() {
    FitnessGoalDistributionDTO report = reportService.generateFitnessGoalDistributionReport();
    ...
    // Table of Goal vs Count vs Percentage
}
```

> **Figure suggestion**: Two separate screenshots – one for **Body Statistics** (averages + BMI categories) and one for **Fitness Goal Distribution** (goals vs percentages).  
> **Code reference**: `MemberAnalyticsController.java` (Lines 148–188 and 200–233).

### Equipment Usage Analytics

Equipment usage analytics is based on how many bookings each machine has, and calculates utilization rates and capacity distribution:

```484:533:spring-boot-implementation/report-analytics-component/src/main/java/com/gymmanagement/reportanalytics/service/ReportService.java
@Override
public EquipmentUsageAnalyticsDTO generateEquipmentUsageAnalyticsReport() {
    ...
    int totalMachines = machines.size();
    int totalCapacity = totalMachines * 8; // Each machine has capacity of 8 bookings
    int totalBookings = 0;
    ...
    // machineBookingCount, machineUtilization, capacityDistribution
    ...
    return new EquipmentUsageAnalyticsDTO(totalMachines, totalCapacity, totalBookings,
            averageUtilization, machinesWithBookings, machinesFullyBooked, machinesUnused,
            machineBookingCount, machineUtilization, capacityDistribution, currentDate.toString());
}
```

The HTML GUI for equipment usage:

```36:88:spring-boot-implementation/report-analytics-component/src/main/java/com/gymmanagement/reportanalytics/controller/EquipmentAnalyticsController.java
@GetMapping(value = "/usage/ui", produces = MediaType.TEXT_HTML_VALUE)
public ResponseEntity<String> getEquipmentUsageAnalyticsHtml() {
    EquipmentUsageAnalyticsDTO report = reportService.generateEquipmentUsageAnalyticsReport();
    ...
    // Overall statistics (total machines, capacity, bookings, avg utilization)
    // Capacity distribution (with bookings / fully booked / unused)
    // Machine-level utilization (per machine)
}
```

> **Figure suggestion**: Screenshot of the **Equipment Usage Analytics** HTML page.  
> **Code reference**: `EquipmentAnalyticsController.java` (Lines 36–88).

---

## 6.5 Evaluation of Spring Boot Report Analytics Component

### What Works Well

- The Spring Boot Report Analytics component can be **run and demonstrated in isolation**, thanks to:
  - In-memory sample data in `DataService`.
  - Self-contained analytics logic in `ReportService`.
  - HTML GUIs served directly from the microservice for presentation.
- All analytics endpoints are exposed as **REST APIs**, which means:
  - They can be used by any frontend (web, mobile).
  - They could be called by other microservices in a larger architecture.
- Export features (TXT / JSON / CSV) are already implemented and re‑used by both system reports and analytics export endpoints.

### Limitations and Future Work

- Currently, `DataService` does **not** call real member/trainer/booking/finance microservices – it only returns mock data:

```53:71:spring-boot-implementation/report-analytics-component/src/main/java/com/gymmanagement/reportanalytics/service/DataService.java
// -------- Payments --------
PaymentDTO p1 = new PaymentDTO(0.0, "Paid", LocalDate.of(2024, 4, 1));
...
// -------- Members (12) --------
sampleMembers = List.of(
    new MemberDTO(1001, "John Doe", "john.doe@gmail.com", "0300-1234567", ... ),
    ...
);
```

- For production, `DataService` should be updated to call:
  - Member Management (e.g. `GET /api/members`)
  - Trainer Management (e.g. `GET /api/trainers`)
  - Equipment Booking (e.g. `GET /api/bookings` + `GET /api/machines`)
  - Financial Management (e.g. `GET /api/payments`)

### Role in the Overall System

This Spring Boot component mirrors the responsibilities of the OSGi Report Analytics bundle but in a **microservice architecture**:

- In **OSGi**, the component discovered services via the OSGi Service Registry.
- In **Spring Boot**, the same analytics logic is exposed as a standalone HTTP service, which:
  - Honors the original component boundaries (it does not manipulate members/trainers directly).
  - Depends only on the data contracts (`DTO`s) and service URLs of other components.

For the purposes of the project report and presentation, this shows how my module can be:

- Reused across different architectures (monolithic GUI, OSGi, Spring Boot microservices).
- Demonstrated independently with good UIs and realistic sample data.

