package com.gymmanagement.reportanalytics.controller;

import com.gymmanagement.reportanalytics.base.IReportService;
import com.gymmanagement.reportanalytics.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for ReportController
 * 
 * Uses JUnit 5 and Mockito for testing REST endpoints
 */
@ExtendWith(MockitoExtension.class)
class ReportControllerTest {

    @Mock
    private IReportService reportService;

    @Mock
    private ReportService reportServiceImpl;

    private ReportController reportController;

    @BeforeEach
    void setUp() {
        // Manually create controller with mocked dependencies
        reportController = new ReportController(reportService, reportServiceImpl);
    }

    @Test
    void testGenerateFullReport_WithHtmlAccept() {
        // Arrange
        String htmlReport = "<html><body>Full Report</body></html>";
        when(reportServiceImpl.generateFullReportHtml()).thenReturn(htmlReport);

        // Act
        ResponseEntity<String> response = reportController.generateFullReport("text/html");

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.TEXT_HTML, response.getHeaders().getContentType());
        assertTrue(response.getBody().contains("Full Report"));
        verify(reportServiceImpl, times(1)).generateFullReportHtml();
    }

    @Test
    void testGenerateFullReport_WithPlainTextAccept() {
        // Arrange
        String textReport = "Full Report Text";
        when(reportService.generateFullReport()).thenReturn(textReport);

        // Act
        ResponseEntity<String> response = reportController.generateFullReport("text/plain");

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getHeaders().getContentType());
        assertEquals(MediaType.TEXT_PLAIN, response.getHeaders().getContentType());
        assertNotNull(response.getBody());
        assertEquals(textReport, response.getBody());
        verify(reportService, times(1)).generateFullReport();
    }

    @Test
    void testGenerateTrainerReport() {
        // Arrange
        String trainerReport = "Trainer Report Content";
        when(reportService.generateTrainerReport()).thenReturn(trainerReport);

        // Act
        ResponseEntity<String> response = reportController.generateTrainerReport();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(trainerReport, response.getBody());
        verify(reportService, times(1)).generateTrainerReport();
    }

    @Test
    void testGenerateMemberReport() {
        // Arrange
        String memberReport = "Member Report Content";
        when(reportService.generateMemberReport()).thenReturn(memberReport);

        // Act
        ResponseEntity<String> response = reportController.generateMemberReport();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(memberReport, response.getBody());
        verify(reportService, times(1)).generateMemberReport();
    }

    @Test
    void testExportFullReportTxt() {
        // Arrange
        String reportContent = "Full Report";
        byte[] reportBytes = reportContent.getBytes();
        when(reportService.generateFullReport()).thenReturn(reportContent);
        when(reportService.exportReportToTxt(reportContent, "FullReport")).thenReturn(reportBytes);

        // Act
        ResponseEntity<byte[]> response = reportController.exportFullReportTxt();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.TEXT_PLAIN, response.getHeaders().getContentType());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
        verify(reportService, times(1)).generateFullReport();
        verify(reportService, times(1)).exportReportToTxt(reportContent, "FullReport");
    }
}
