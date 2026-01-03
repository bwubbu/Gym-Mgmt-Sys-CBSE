package com.gymmanagement.reportanalytics.controller;

import com.gymmanagement.reportanalytics.base.IReportService;
import com.gymmanagement.reportanalytics.service.ReportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for System Reports
 * 
 * Handles all system report generation endpoints
 */
@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
public class ReportController {
    
    private final IReportService reportService;
    private final ReportService reportServiceImpl;
    
    public ReportController(IReportService reportService, ReportService reportServiceImpl) {
        this.reportService = reportService;
        this.reportServiceImpl = reportServiceImpl;
    }
    
    @GetMapping(value = "/full", produces = {MediaType.TEXT_HTML_VALUE, MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> generateFullReport(@RequestHeader(value = "Accept", required = false) String accept) {
        // Check if browser is requesting HTML (or if Accept header includes text/html)
        // Default to HTML if accessed from browser
        if (accept == null || accept.contains("text/html") || accept.contains("*/*")) {
            String htmlReport = reportServiceImpl.generateFullReportHtml();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_HTML);
            return new ResponseEntity<>(htmlReport, headers, HttpStatus.OK);
        } else {
            // Return plain text for API consumers
            String report = reportService.generateFullReport();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            return new ResponseEntity<>(report, headers, HttpStatus.OK);
        }
    }
    
    @GetMapping("/trainer")
    public ResponseEntity<String> generateTrainerReport() {
        String report = reportService.generateTrainerReport();
        return ResponseEntity.ok(report);
    }
    
    @GetMapping("/member")
    public ResponseEntity<String> generateMemberReport() {
        String report = reportService.generateMemberReport();
        return ResponseEntity.ok(report);
    }
    
    @GetMapping("/machine")
    public ResponseEntity<String> generateMachineReport() {
        String report = reportService.generateMachineReport();
        return ResponseEntity.ok(report);
    }
    
    @GetMapping("/booking")
    public ResponseEntity<String> generateBookingReport() {
        String report = reportService.generateBookingReport();
        return ResponseEntity.ok(report);
    }
    
    @GetMapping("/outstanding-balance")
    public ResponseEntity<String> generateOutstandingBalanceReport() {
        String report = reportService.generateReportOfOutstandingBalance();
        return ResponseEntity.ok(report);
    }
    
    @GetMapping("/zero-outstanding-balance")
    public ResponseEntity<String> generateZeroOutstandingBalanceReport() {
        String report = reportService.generateReportOfZeroOutstandingBalance();
        return ResponseEntity.ok(report);
    }
    
    // Export endpoints
    @GetMapping(value = "/export/full/txt", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<byte[]> exportFullReportTxt() {
        String report = reportService.generateFullReport();
        byte[] content = reportService.exportReportToTxt(report, "FullReport");
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentDispositionFormData("attachment", "FullReport.txt");
        
        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }
}

