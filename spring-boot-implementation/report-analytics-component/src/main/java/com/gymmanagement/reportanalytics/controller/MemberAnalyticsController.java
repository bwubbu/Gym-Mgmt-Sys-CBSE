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
    
    @GetMapping("/growth-trends")
    public ResponseEntity<GrowthTrendsDTO> getGrowthTrends() {
        GrowthTrendsDTO report = reportService.generateGrowthTrendsReport();
        return ResponseEntity.ok(report);
    }
    
    @GetMapping("/body-statistics")
    public ResponseEntity<BodyStatisticsDTO> getBodyStatistics() {
        BodyStatisticsDTO report = reportService.generateBodyStatisticsReport();
        return ResponseEntity.ok(report);
    }
    
    @GetMapping("/fitness-goals")
    public ResponseEntity<FitnessGoalDistributionDTO> getFitnessGoalDistribution() {
        FitnessGoalDistributionDTO report = reportService.generateFitnessGoalDistributionReport();
        return ResponseEntity.ok(report);
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

