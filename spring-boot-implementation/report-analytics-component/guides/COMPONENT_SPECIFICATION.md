# Report Analytics Component - Component Specification

## Component Information

- **Component Name**: Report Analytics Component
- **Label**: gym report
- **Owner**: Rudzaidan
- **Port**: 8085

## Architecture Compliance

### Component Structure

According to the component architecture diagram:

1. **Internal Elements**:
   - `Report` (Entity)
   - `ReportManager` (implemented as `ReportService`)

2. **Provided Interface**:
   - `IReportService` (should be in Base Library according to architecture)

3. **Dependencies**:
   - **Base Library**: For entities (Member, Trainer, Machine, Payment, Report)
   - **Member Management Component**: For member data
   - **Trainer Management Component**: For trainer data
   - **Equipment Booking Component**: For machine and booking data
   - **Financial Management Component**: For payment data

### Dependency Depth

- **Dependency Depth**: 1
- The component depends on Base Library (depth=1) and other components for data access

## Features Implemented

### 1. Generate System Reports ✅
- Full Report
- Trainer Report
- Member Report
- Machine Report
- Booking Report
- Outstanding Balance Report
- Zero Outstanding Balance Report

### 2. Analyze Member Analytics (NEW FEATURE) ✅
- Member Demographics
- Growth Trends
- Body Statistics
- Fitness Goal Distribution

### 3. Analyze Equipment Usage Analytics (NEW FEATURE) ✅
- Overall utilization statistics
- Machine popularity
- Capacity distribution
- Utilization rates

### 4. Export Reports (NEW FEATURE) ✅
- TXT format
- JSON format
- CSV format

## API Endpoints Summary

### System Reports
- `/api/reports/*` - All system report generation endpoints

### Member Analytics
- `/api/analytics/member/*` - All member analytics endpoints

### Equipment Analytics
- `/api/analytics/equipment/*` - All equipment analytics endpoints

## Service Interface Methods

The `IReportService` interface defines the following methods:

### System Reports
1. `generateFullReport()`
2. `generateTrainerReport()`
3. `generateMemberReport()`
4. `generateMachineReport()`
5. `generateBookingReport()`
6. `generateReportOfOutstandingBalance()`
7. `generateReportOfZeroOutstandingBalance()`

### Member Analytics
8. `generateMemberDemographicsReport()`
9. `generateGrowthTrendsReport()`
10. `generateBodyStatisticsReport()`
11. `generateFitnessGoalDistributionReport()`

### Equipment Analytics
12. `generateEquipmentUsageAnalyticsReport()`

### Export
13. `exportReportToTxt()`
14. `exportReportToJson()`
15. `exportReportToCsv()`

## Data Flow

```
Other Components (Member, Trainer, Booking, Finance)
         ↓
    DataService (REST API calls)
         ↓
    ReportService (Business Logic)
         ↓
    Controllers (REST Endpoints)
         ↓
    Client Applications
```

## Integration Points

This component integrates with other components via REST APIs:

- **Member Service**: `http://localhost:8081/api/members`
- **Trainer Service**: `http://localhost:8082/api/trainers`
- **Booking Service**: `http://localhost:8083/api/bookings`
- **Finance Service**: `http://localhost:8084/api/payments`

## Testing Checklist

- [ ] Unit tests for ReportService
- [ ] Integration tests for REST controllers
- [ ] Test data fetching from other components
- [ ] Test export functionality
- [ ] Test analytics calculations
- [ ] Test error handling

## Deployment Notes

1. Ensure other components are running before starting this component
2. Configure service URLs in `application.yml`
3. Set up database (H2 for dev, PostgreSQL/MySQL for production)
4. Configure logging
5. Set up monitoring and health checks

## Future Enhancements

1. Add caching for frequently accessed reports
2. Add scheduled report generation
3. Add report history tracking
4. Add more export formats (PDF, Excel)
5. Add report templates
6. Add real-time analytics dashboard
7. Add authentication and authorization

