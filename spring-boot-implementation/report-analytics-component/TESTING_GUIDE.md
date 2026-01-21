# Testing Guide - Report Analytics Component

## Overview

This guide explains how to use JUnit and Mockito for testing the Spring Boot Report Analytics Component.

## Prerequisites

- Java 17+
- Maven 3.6+
- JUnit 5 (included via `spring-boot-starter-test`)
- Mockito (included via `spring-boot-starter-test`)

## Test Structure

Tests are located in `src/test/java/` following the same package structure as the main code:

```
src/test/java/com/gymmanagement/reportanalytics/
├── service/
│   └── ReportServiceTest.java
└── controller/
    └── ReportControllerTest.java
```

## Running Tests

### Using Maven

Run all tests:
```bash
cd spring-boot-implementation/report-analytics-component
mvn test
```

Run a specific test class:
```bash
mvn test -Dtest=ReportServiceTest
```

Run a specific test method:
```bash
mvn test -Dtest=ReportServiceTest#testGenerateFullReport
```

### Using IDE (Eclipse/IntelliJ)

1. Right-click on the test file or test method
2. Select "Run As" → "JUnit Test"
3. View results in the JUnit view/panel

## Test Examples

### Service Layer Testing (ReportServiceTest)

**Purpose**: Test business logic in isolation using mocks

**Key Features**:
- Uses `@ExtendWith(MockitoExtension.class)` for Mockito integration
- Uses `@Mock` to create mock dependencies (DataService)
- Uses `@InjectMocks` to inject mocks into the service under test
- Tests verify both return values and interactions with dependencies

**Example Test**:
```java
@Test
void testGenerateFullReport() {
    // Arrange - setup mocks and test data
    when(dataService.getAllTrainers()).thenReturn(sampleTrainers);
    when(dataService.getAllMembers()).thenReturn(sampleMembers);
    
    // Act - call the method under test
    String report = reportService.generateFullReport();
    
    // Assert - verify results and interactions
    assertNotNull(report);
    assertTrue(report.contains("Date"));
    verify(dataService, times(1)).getAllTrainers();
}
```

### Controller Layer Testing (ReportControllerTest)

**Purpose**: Test REST endpoint behavior

**Key Features**:
- Tests HTTP response status codes
- Tests response content types (HTML vs Plain Text)
- Tests response body content
- Verifies service method calls

**Example Test**:
```java
@Test
void testGenerateFullReport_WithHtmlAccept() {
    // Arrange
    String htmlReport = "<html><body>Full Report</body></html>";
    when(reportServiceImpl.generateFullReportHtml()).thenReturn(htmlReport);
    
    // Act
    ResponseEntity<String> response = reportController.generateFullReport("text/html");
    
    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(MediaType.TEXT_HTML, response.getHeaders().getContentType());
    assertTrue(response.getBody().contains("Full Report"));
}
```

## Test Annotations

- `@Test`: Marks a method as a test case
- `@BeforeEach`: Method runs before each test (setup)
- `@ExtendWith(MockitoExtension.class)`: Enables Mockito for the test class
- `@Mock`: Creates a mock object for a dependency
- `@InjectMocks`: Injects mocks into the class under test

## Assertions

JUnit 5 provides various assertion methods:

- `assertNotNull(object)`: Verifies object is not null
- `assertEquals(expected, actual)`: Verifies equality
- `assertTrue(condition)`: Verifies condition is true
- `assertFalse(condition)`: Verifies condition is false
- `assertThrows(Exception.class, () -> method())`: Verifies exception is thrown

## Mockito Verification

Verify interactions with mocks:

- `verify(mock, times(n)).method()`: Verify method called n times
- `verify(mock, never()).method()`: Verify method never called
- `verify(mock, atLeastOnce()).method()`: Verify method called at least once

## Running Tests in CI/CD

Add to your CI/CD pipeline:

```yaml
# Example GitHub Actions
- name: Run Tests
  run: |
    cd spring-boot-implementation/report-analytics-component
    mvn test
```

## Test Coverage

To generate test coverage reports:

```bash
mvn test jacoco:report
```

Coverage report will be in: `target/site/jacoco/index.html`

## Best Practices

1. **Test Isolation**: Each test should be independent
2. **Arrange-Act-Assert**: Follow AAA pattern
3. **Descriptive Names**: Test method names should describe what they test
4. **Mock External Dependencies**: Don't test real database/network calls
5. **Test Edge Cases**: Test empty lists, null values, error conditions

## Next Steps

To add more tests:

1. Create test files in `src/test/java/` matching package structure
2. Use `@ExtendWith(MockitoExtension.class)` for Mockito support
3. Mock dependencies with `@Mock`
4. Write test methods with `@Test`
5. Run tests with `mvn test`
