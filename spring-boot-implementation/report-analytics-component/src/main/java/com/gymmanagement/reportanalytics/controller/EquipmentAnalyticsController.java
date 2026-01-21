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

    /**
     * Simple HTML view for Equipment Usage Analytics
     */
    @GetMapping(value = "/usage/ui", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> getEquipmentUsageAnalyticsHtml() {
        EquipmentUsageAnalyticsDTO report = reportService.generateEquipmentUsageAnalyticsReport();
        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\">");
        html.append("<title>Equipment Usage Analytics</title>");
        html.append("<style>");
        html.append("body{font-family:'Segoe UI',sans-serif;background:#f4f5fb;padding:20px;}");
        html.append(".card{max-width:900px;margin:0 auto;background:#fff;border-radius:12px;box-shadow:0 8px 24px rgba(0,0,0,0.08);padding:24px;}");
        html.append("h1{margin-bottom:8px;color:#2d3e50;}");
        html.append(".subtitle{color:#6c757d;margin-bottom:20px;}");
        html.append("table{border-collapse:collapse;width:100%;margin-top:12px;}");
        html.append("th,td{padding:8px 12px;text-align:left;border-bottom:1px solid #e5e7eb;}");
        html.append("th{background:#f3f4ff;color:#374151;}");
        html.append("</style></head><body>");

        html.append("<div class=\"card\">");
        html.append("<h1>Equipment Usage Analytics</h1>");
        html.append("<div class=\"subtitle\">Generated on ").append(report.getGeneratedDate()).append("</div>");

        html.append("<h2>Overall Statistics</h2>");
        html.append("<ul>");
        html.append("<li>Total Machines: ").append(report.getTotalMachines()).append("</li>");
        html.append("<li>Total Capacity: ").append(report.getTotalCapacity()).append(" bookings</li>");
        html.append("<li>Total Bookings: ").append(report.getTotalBookings()).append("</li>");
        html.append("<li>Average Utilization Rate: ")
            .append(String.format("%.1f", report.getAverageUtilizationRate())).append("%</li>");
        html.append("</ul>");

        html.append("<h2 style=\"margin-top:24px;\">Capacity Distribution</h2>");
        html.append("<table><thead><tr><th>Category</th><th>Count</th><th>Percentage</th></tr></thead><tbody>");
        double withBookingsPct = report.getCapacityDistribution().getOrDefault("with_bookings", 0.0);
        double fullyBookedPct = report.getCapacityDistribution().getOrDefault("fully_booked", 0.0);
        double unusedPct = report.getCapacityDistribution().getOrDefault("unused", 0.0);
        html.append("<tr><td>With bookings</td><td>").append(report.getMachinesWithBookings())
            .append("</td><td>").append(String.format("%.1f", withBookingsPct)).append("%</td></tr>");
        html.append("<tr><td>Fully booked</td><td>").append(report.getMachinesFullyBooked())
            .append("</td><td>").append(String.format("%.1f", fullyBookedPct)).append("%</td></tr>");
        html.append("<tr><td>Unused</td><td>").append(report.getMachinesUnused())
            .append("</td><td>").append(String.format("%.1f", unusedPct)).append("%</td></tr>");
        html.append("</tbody></table>");

        html.append("<h2 style=\"margin-top:24px;\">Machine Utilization</h2>");
        html.append("<table><thead><tr><th>Machine</th><th>Bookings</th><th>Utilization</th></tr></thead><tbody>");
        report.getMachineUtilization().forEach((machine, utilization) -> {
            int bookings = report.getMachineBookingCount().getOrDefault(machine, 0);
            html.append("<tr><td>").append(machine).append("</td><td>").append(bookings)
                .append("</td><td>").append(String.format("%.1f", utilization)).append("%</td></tr>");
        });
        html.append("</tbody></table>");

        html.append("</div></body></html>");

        return ResponseEntity.ok(html.toString());
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

