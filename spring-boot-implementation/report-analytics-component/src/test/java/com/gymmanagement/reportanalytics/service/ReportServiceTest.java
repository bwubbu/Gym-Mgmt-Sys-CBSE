package com.gymmanagement.reportanalytics.service;

import com.gymmanagement.reportanalytics.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for ReportService
 * 
 * Uses JUnit 5 and Mockito for testing
 */
@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @Mock
    private DataService dataService;

    @InjectMocks
    private ReportService reportService;

    private List<MemberDTO> sampleMembers;
    private List<TrainerDTO> sampleTrainers;
    private List<MachineDTO> sampleMachines;

    @BeforeEach
    void setUp() {
        // Setup sample data for testing
        sampleMembers = new ArrayList<>();
        sampleMembers.add(new MemberDTO(1001, "John Doe", "john@example.com", 
            "0300-1234567", "123 Main St", LocalDate.now(), LocalDate.of(1995, 5, 20),
            29, "Male", 1.75, 85.0, 27.8, "Weight Loss", 
            new PaymentDTO(0.0, "Paid", LocalDate.now())));

        sampleTrainers = new ArrayList<>();
        sampleTrainers.add(new TrainerDTO(2001, "Jane Trainer", "jane@example.com",
            "0300-7654321", "456 Fitness Ave", LocalDate.now(), LocalDate.of(1990, 1, 1),
            34, "Female", "Yoga", 30.0));

        sampleMachines = new ArrayList<>();
        sampleMachines.add(new MachineDTO(3001, "Treadmill X1", "FitnessPro", 
            "PR-TM-2024", 150.0, 120.0, "Cardio", new ArrayList<>()));
    }

    @Test
    void testGenerateFullReport() {
        // Arrange
        when(dataService.getAllTrainers()).thenReturn(sampleTrainers);
        when(dataService.getAllMembers()).thenReturn(sampleMembers);
        when(dataService.getAllMachines()).thenReturn(sampleMachines);
        when(dataService.getAllBookings()).thenReturn(new ArrayList<>());

        // Act
        String report = reportService.generateFullReport();

        // Assert
        assertNotNull(report);
        assertTrue(report.contains("Date"));
        assertTrue(report.contains("John Doe"));
        assertTrue(report.contains("Jane Trainer"));
        verify(dataService, times(1)).getAllTrainers();
        verify(dataService, times(3)).getAllMembers(); // Called for member report, outstanding balance, and zero balance reports
    }

    @Test
    void testGenerateMemberReport() {
        // Arrange
        when(dataService.getAllMembers()).thenReturn(sampleMembers);

        // Act
        String report = reportService.generateMemberReport();

        // Assert
        assertNotNull(report);
        assertTrue(report.contains("Members list"));
        assertTrue(report.contains("John Doe"));
        verify(dataService, times(1)).getAllMembers();
    }

    @Test
    void testGenerateMemberDemographicsReport() {
        // Arrange
        when(dataService.getAllMembers()).thenReturn(sampleMembers);

        // Act
        MemberDemographicsDTO report = reportService.generateMemberDemographicsReport();

        // Assert
        assertNotNull(report);
        assertEquals(1, report.getTotalMembers());
        assertNotNull(report.getGenderDistribution());
        assertTrue(report.getGenderDistribution().containsKey("male"));
        verify(dataService, times(1)).getAllMembers();
    }

    @Test
    void testGenerateBodyStatisticsReport() {
        // Arrange
        when(dataService.getAllMembers()).thenReturn(sampleMembers);

        // Act
        BodyStatisticsDTO report = reportService.generateBodyStatisticsReport();

        // Assert
        assertNotNull(report);
        assertTrue(report.getAverageHeight() > 0);
        assertTrue(report.getAverageWeight() > 0);
        assertTrue(report.getAverageBMI() > 0);
        assertEquals(1, report.getValidRecords());
        verify(dataService, times(1)).getAllMembers();
    }

    @Test
    void testExportReportToTxt() {
        // Arrange
        String reportContent = "Test Report Content";

        // Act
        byte[] result = reportService.exportReportToTxt(reportContent, "TestReport");

        // Assert
        assertNotNull(result);
        assertTrue(result.length > 0);
        String resultString = new String(result);
        assertEquals(reportContent, resultString);
    }

    @Test
    void testExportReportToJson() {
        // Arrange
        MemberDemographicsDTO report = new MemberDemographicsDTO(
            10, 
            new java.util.HashMap<>(), 
            new java.util.HashMap<>(),
            new java.util.HashMap<>(), 
            new java.util.HashMap<>(), 
            LocalDate.now().toString()
        );

        // Act
        byte[] result = reportService.exportReportToJson(report, "MemberDemographics");

        // Assert
        assertNotNull(result);
        assertTrue(result.length > 0);
        String jsonString = new String(result);
        assertTrue(jsonString.contains("totalMembers"));
    }
}
