package com.gymmanagement.reportanalytics.controller;

import com.gymmanagement.reportanalytics.base.IReportService;
import com.gymmanagement.reportanalytics.dto.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for Member Analytics (NEW FEATURE)
 * 
 * Handles all member analytics endpoints
 */
@RestController
@RequestMapping("/api/analytics/member")
@CrossOrigin(origins = "*")
public class MemberAnalyticsController {
    
    private final IReportService reportService;
    
    public MemberAnalyticsController(IReportService reportService) {
        this.reportService = reportService;
    }
    
    @GetMapping("/demographics")
    public ResponseEntity<MemberDemographicsDTO> getMemberDemographics() {
        MemberDemographicsDTO report = reportService.generateMemberDemographicsReport();
        return ResponseEntity.ok(report);
    }

    /**
     * Simple HTML view for Member Demographics (for presentation/demo)
     */
    @GetMapping(value = "/demographics/ui", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> getMemberDemographicsHtml() {
        MemberDemographicsDTO report = reportService.generateMemberDemographicsReport();
        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\">");
        html.append("<title>Member Demographics Analytics</title>");
        html.append("<style>");
        html.append("body{font-family:'Segoe UI',sans-serif;background:#f4f5fb;padding:20px;}");
        html.append(".card{max-width:900px;margin:0 auto;background:#fff;border-radius:12px;box-shadow:0 8px 24px rgba(0,0,0,0.08);padding:24px;}");
        html.append("h1{margin-bottom:8px;color:#2d3e50;}");
        html.append(".subtitle{color:#6c757d;margin-bottom:20px;}");
        html.append("table{border-collapse:collapse;width:100%;margin-top:12px;}");
        html.append("th,td{padding:8px 12px;text-align:left;border-bottom:1px solid #e5e7eb;}");
        html.append("th{background:#f3f4ff;color:#374151;}");
        html.append(".badge{display:inline-block;padding:3px 10px;border-radius:999px;background:#e5e7ff;color:#4f46e5;font-size:12px;margin-left:8px;}");
        html.append("</style></head><body>");

        html.append("<div class=\"card\">");
        html.append("<h1>Member Demographics</h1>");
        html.append("<div class=\"subtitle\">Generated on ").append(report.getGeneratedDate())
            .append(" · Total Members: ").append(report.getTotalMembers()).append("</div>");

        // Gender distribution
        html.append("<h2>Gender Distribution</h2>");
        html.append("<table><thead><tr><th>Gender</th><th>Count</th><th>Percentage</th></tr></thead><tbody>");
        report.getGenderDistribution().forEach((gender, count) -> {
            double percentage = report.getGenderPercentages().getOrDefault(gender, 0.0);
            html.append("<tr><td>").append(gender).append("</td><td>").append(count)
                .append("</td><td>").append(String.format("%.1f", percentage)).append("%</td></tr>");
        });
        html.append("</tbody></table>");

        // Age group distribution
        html.append("<h2 style=\"margin-top:24px;\">Age Group Distribution</h2>");
        html.append("<table><thead><tr><th>Age Group</th><th>Count</th><th>Percentage</th></tr></thead><tbody>");
        report.getAgeGroupDistribution().forEach((ageGroup, count) -> {
            double percentage = report.getAgeGroupPercentages().getOrDefault(ageGroup, 0.0);
            html.append("<tr><td>").append(ageGroup).append("</td><td>").append(count)
                .append("</td><td>").append(String.format("%.1f", percentage)).append("%</td></tr>");
        });
        html.append("</tbody></table>");

        html.append("</div></body></html>");

        return ResponseEntity.ok(html.toString());
    }
    
    @GetMapping("/growth-trends")
    public ResponseEntity<GrowthTrendsDTO> getGrowthTrends() {
        GrowthTrendsDTO report = reportService.generateGrowthTrendsReport();
        return ResponseEntity.ok(report);
    }

    /**
     * Simple HTML view for Growth Trends
     */
    @GetMapping(value = "/growth-trends/ui", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> getGrowthTrendsHtml() {
        GrowthTrendsDTO report = reportService.generateGrowthTrendsReport();
        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\">");
        html.append("<title>Member Growth Trends</title>");
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
        html.append("<h1>Member Growth Trends</h1>");
        html.append("<div class=\"subtitle\">Generated on ").append(report.getGeneratedDate()).append("</div>");

        html.append("<h2>Monthly Registrations</h2>");
        html.append("<table><thead><tr><th>Month/Year</th><th>Registrations</th></tr></thead><tbody>");
        report.getMonthlyRegistrations().forEach((monthYear, count) -> {
            html.append("<tr><td>").append(monthYear).append("</td><td>").append(count).append("</td></tr>");
        });
        html.append("</tbody></table>");

        html.append("<h2 style=\"margin-top:24px;\">Yearly Registrations</h2>");
        html.append("<table><thead><tr><th>Year</th><th>Registrations</th></tr></thead><tbody>");
        report.getYearlyRegistrations().forEach((year, count) -> {
            html.append("<tr><td>").append(year).append("</td><td>").append(count).append("</td></tr>");
        });
        html.append("</tbody></table>");

        if (report.getGrowthRate() != null) {
            html.append("<p style=\"margin-top:20px;\">Growth Rate (")
                .append(report.getGrowthRatePeriod()).append("): ")
                .append(String.format("%.1f", report.getGrowthRate())).append("%</p>");
        }

        html.append("</div></body></html>");

        return ResponseEntity.ok(html.toString());
    }
    
    @GetMapping("/body-statistics")
    public ResponseEntity<BodyStatisticsDTO> getBodyStatistics() {
        BodyStatisticsDTO report = reportService.generateBodyStatisticsReport();
        return ResponseEntity.ok(report);
    }

    /**
     * Simple HTML view for Body Statistics
     */
    @GetMapping(value = "/body-statistics/ui", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> getBodyStatisticsHtml() {
        BodyStatisticsDTO report = reportService.generateBodyStatisticsReport();
        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\">");
        html.append("<title>Member Body Statistics</title>");
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
        html.append("<h1>Member Body Statistics</h1>");
        html.append("<div class=\"subtitle\">Generated on ").append(report.getGeneratedDate())
            .append(" · Records: ").append(report.getValidRecords()).append("</div>");

        html.append("<h2>Averages</h2>");
        html.append("<ul>");
        html.append("<li>Average Height: ").append(String.format("%.2f", report.getAverageHeight())).append(" m</li>");
        html.append("<li>Average Weight: ").append(String.format("%.1f", report.getAverageWeight())).append(" kg</li>");
        html.append("<li>Average BMI: ").append(String.format("%.1f", report.getAverageBMI())).append("</li>");
        html.append("</ul>");

        html.append("<h2 style=\"margin-top:24px;\">BMI Categories</h2>");
        html.append("<table><thead><tr><th>Category</th><th>Count</th><th>Percentage</th></tr></thead><tbody>");
        report.getBmiCategoryDistribution().forEach((category, count) -> {
            double percentage = report.getBmiCategoryPercentages().getOrDefault(category, 0.0);
            html.append("<tr><td>").append(category).append("</td><td>").append(count)
                .append("</td><td>").append(String.format("%.1f", percentage)).append("%</td></tr>");
        });
        html.append("</tbody></table>");

        html.append("</div></body></html>");

        return ResponseEntity.ok(html.toString());
    }
    
    @GetMapping("/fitness-goals")
    public ResponseEntity<FitnessGoalDistributionDTO> getFitnessGoalDistribution() {
        FitnessGoalDistributionDTO report = reportService.generateFitnessGoalDistributionReport();
        return ResponseEntity.ok(report);
    }

    /**
     * Simple HTML view for Fitness Goal Distribution
     */
    @GetMapping(value = "/fitness-goals/ui", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> getFitnessGoalDistributionHtml() {
        FitnessGoalDistributionDTO report = reportService.generateFitnessGoalDistributionReport();
        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\">");
        html.append("<title>Fitness Goal Distribution</title>");
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
        html.append("<h1>Fitness Goal Distribution</h1>");
        html.append("<div class=\"subtitle\">Generated on ").append(report.getGeneratedDate())
            .append(" · Members with goals: ").append(report.getMembersWithGoals())
            .append(" / ").append(report.getTotalMembers()).append("</div>");

        html.append("<table><thead><tr><th>Goal</th><th>Count</th><th>Percentage (of members with goals)</th></tr></thead><tbody>");
        report.getGoalDistribution().forEach((goal, count) -> {
            double percentage = report.getGoalPercentages().getOrDefault(goal, 0.0);
            html.append("<tr><td>").append(goal).append("</td><td>").append(count)
                .append("</td><td>").append(String.format("%.1f", percentage)).append("%</td></tr>");
        });
        html.append("</tbody></table>");

        html.append("</div></body></html>");

        return ResponseEntity.ok(html.toString());
    }
    
    // Export endpoints
    @GetMapping(value = "/demographics/export/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<byte[]> exportDemographicsJson() {
        MemberDemographicsDTO report = reportService.generateMemberDemographicsReport();
        byte[] content = reportService.exportReportToJson(report, "MemberDemographics");
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setContentDispositionFormData("attachment", "MemberDemographics.json");
        
        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }
    
    @GetMapping(value = "/demographics/export/txt", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<byte[]> exportDemographicsTxt() {
        MemberDemographicsDTO report = reportService.generateMemberDemographicsReport();
        String reportText = formatDemographicsAsText(report);
        byte[] content = reportService.exportReportToTxt(reportText, "MemberDemographics");
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentDispositionFormData("attachment", "MemberDemographics.txt");
        
        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }
    
    private String formatDemographicsAsText(MemberDemographicsDTO report) {
        StringBuilder sb = new StringBuilder();
        sb.append("Date: ").append(report.getGeneratedDate()).append("\n\n");
        sb.append(">>> Member Demographics Report\n\n");
        sb.append("Total Members: ").append(report.getTotalMembers()).append("\n\n");
        
        sb.append("Gender Distribution:\n");
        report.getGenderDistribution().forEach((gender, count) -> {
            double percentage = report.getGenderPercentages().getOrDefault(gender, 0.0);
            sb.append("  ").append(gender).append(": ").append(count)
                    .append(" (").append(String.format("%.1f", percentage)).append("%)\n");
        });
        
        sb.append("\nAge Group Distribution:\n");
        report.getAgeGroupDistribution().forEach((ageGroup, count) -> {
            double percentage = report.getAgeGroupPercentages().getOrDefault(ageGroup, 0.0);
            sb.append("  ").append(ageGroup).append(": ").append(count)
                    .append(" (").append(String.format("%.1f", percentage)).append("%)\n");
        });
        
        return sb.toString();
    }
}

