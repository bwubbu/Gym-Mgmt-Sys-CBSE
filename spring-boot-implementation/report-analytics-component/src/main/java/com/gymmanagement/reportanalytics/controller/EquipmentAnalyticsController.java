package com.gymmanagement.reportanalytics.controller;

import com.gymmanagement.reportanalytics.base.IReportService;
import com.gymmanagement.reportanalytics.dto.EquipmentUsageAnalyticsDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for Equipment Usage Analytics (NEW FEATURE)
 * 
 * Handles equipment usage analytics endpoints
 */
@RestController
@RequestMapping("/api/analytics/equipment")
@CrossOrigin(origins = "*")
public class EquipmentAnalyticsController {
    
    private final IReportService reportService;
    
    public EquipmentAnalyticsController(IReportService reportService) {
        this.reportService = reportService;
    }
    
    @GetMapping("/usage")
    public ResponseEntity<EquipmentUsageAnalyticsDTO> getEquipmentUsageAnalytics() {
        EquipmentUsageAnalyticsDTO report = reportService.generateEquipmentUsageAnalyticsReport();
        return ResponseEntity.ok(report);
    }
    
    // Export endpoints
    @GetMapping(value = "/usage/export/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<byte[]> exportUsageJson() {
        EquipmentUsageAnalyticsDTO report = reportService.generateEquipmentUsageAnalyticsReport();
        byte[] content = reportService.exportReportToJson(report, "EquipmentUsage");
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setContentDispositionFormData("attachment", "EquipmentUsage.json");
        
        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }
    
    @GetMapping(value = "/usage/export/txt", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<byte[]> exportUsageTxt() {
        EquipmentUsageAnalyticsDTO report = reportService.generateEquipmentUsageAnalyticsReport();
        String reportText = formatUsageAsText(report);
        byte[] content = reportService.exportReportToTxt(reportText, "EquipmentUsage");
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentDispositionFormData("attachment", "EquipmentUsage.txt");
        
        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }
    
    @GetMapping(value = "/usage/export/csv", produces = "text/csv")
    public ResponseEntity<byte[]> exportUsageCsv() {
        EquipmentUsageAnalyticsDTO report = reportService.generateEquipmentUsageAnalyticsReport();
        String reportText = formatUsageAsText(report);
        byte[] content = reportService.exportReportToCsv(reportText, "EquipmentUsage");
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv"));
        headers.setContentDispositionFormData("attachment", "EquipmentUsage.csv");
        
        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }
    
    private String formatUsageAsText(EquipmentUsageAnalyticsDTO report) {
        StringBuilder sb = new StringBuilder();
        sb.append("Date: ").append(report.getGeneratedDate()).append("\n\n");
        sb.append(">>> Equipment Usage Analytics Report\n\n");
        
        sb.append("Overall Statistics:\n");
        sb.append("  Total Machines: ").append(report.getTotalMachines()).append("\n");
        sb.append("  Total Capacity: ").append(report.getTotalCapacity()).append(" bookings\n");
        sb.append("  Total Bookings: ").append(report.getTotalBookings()).append("\n");
        sb.append("  Average Utilization Rate: ")
                .append(String.format("%.1f", report.getAverageUtilizationRate())).append("%\n\n");
        
        sb.append("Capacity Distribution:\n");
        sb.append("  Machines with Bookings: ").append(report.getMachinesWithBookings())
                .append(" (").append(String.format("%.1f", 
                        report.getCapacityDistribution().getOrDefault("with_bookings", 0.0))).append("%)\n");
        sb.append("  Machines Fully Booked: ").append(report.getMachinesFullyBooked())
                .append(" (").append(String.format("%.1f", 
                        report.getCapacityDistribution().getOrDefault("fully_booked", 0.0))).append("%)\n");
        sb.append("  Machines Unused: ").append(report.getMachinesUnused())
                .append(" (").append(String.format("%.1f", 
                        report.getCapacityDistribution().getOrDefault("unused", 0.0))).append("%)\n\n");
        
        sb.append("Machine Utilization Rates:\n");
        report.getMachineUtilization().forEach((machine, utilization) -> {
            int bookings = report.getMachineBookingCount().getOrDefault(machine, 0);
            sb.append("  ").append(machine).append(": ")
                    .append(String.format("%.1f", utilization)).append("% (")
                    .append(bookings).append("/8 bookings)\n");
        });
        
        return sb.toString();
    }
}

