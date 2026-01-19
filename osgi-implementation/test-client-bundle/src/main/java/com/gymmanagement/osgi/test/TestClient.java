package com.gymmanagement.osgi.test;

import com.gymmanagement.osgi.base.entity.*;
import com.gymmanagement.osgi.base.service.*;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import java.util.Map;

/**
 * Test Client Bundle Activator
 * Tests the OSGi services when the bundle starts
 */
public class TestClient implements BundleActivator {
    
    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("\n=========================================");
        System.out.println("OSGi Test Client - Starting Tests");
        System.out.println("=========================================\n");
        
        // Test IMemberService
        testMemberService(context);
        
        // Test ITrainerService
        testTrainerService(context);

        // Test IReportService
        testReportService(context);
        
        System.out.println("\n=========================================");
        System.out.println("OSGi Test Client - Tests Complete");
        System.out.println("=========================================\n");
    }
    
    private void testMemberService(BundleContext context) {
        System.out.println("--- Testing IMemberService ---");
        
        ServiceReference<IMemberService> memberRef = 
            context.getServiceReference(IMemberService.class);
        
        if (memberRef == null) {
            System.out.println("❌ IMemberService not found in service registry");
            return;
        }
        
        IMemberService memberService = context.getService(memberRef);
        if (memberService == null) {
            System.out.println("❌ Failed to get IMemberService instance");
            return;
        }
        
        System.out.println("✅ IMemberService found and retrieved");
        
        try {
            // Create a test member
            Date joinDate = new Date(1, 15, 2024);
            Date dob = new Date(5, 20, 1995);
            Payment payment = new Payment(0.0, "Test User", "1234-5678-9012-3456");
            Member member = new Member(1, "John Doe", "john@example.com", 
                "0300-1234567", "123 Main St", joinDate, dob, 29, "Male", 
                1.75, 70.0, payment, "Weight Loss");
            
            // Test adding member
            String result = memberService.addMember(member);
            System.out.println("✅ Add Member: " + result);
            
            // Test getting member
            Member retrieved = memberService.getMember(1);
            if (retrieved != null) {
                System.out.println("✅ Get Member: Found member " + retrieved.getName());
            } else {
                System.out.println("❌ Get Member: Member not found");
            }
            
            // Test getting all members
            int totalMembers = memberService.getAllMembers().size();
            System.out.println("✅ GetAllMembers: " + totalMembers + " member(s) found");
            
            // Test search
            var searchResults = memberService.searchMembersByName("John");
            System.out.println("✅ SearchMembers: " + searchResults.size() + " result(s)");
            
        } catch (Exception e) {
            System.out.println("❌ Error testing IMemberService: " + e.getMessage());
            e.printStackTrace();
        }
        
        // Release service
        context.ungetService(memberRef);
    }
    
    private void testTrainerService(BundleContext context) {
        System.out.println("\n--- Testing ITrainerService ---");
        
        ServiceReference<ITrainerService> trainerRef = 
            context.getServiceReference(ITrainerService.class);
        
        if (trainerRef == null) {
            System.out.println("❌ ITrainerService not found in service registry");
            return;
        }
        
        ITrainerService trainerService = context.getService(trainerRef);
        if (trainerService == null) {
            System.out.println("❌ Failed to get ITrainerService instance");
            return;
        }
        
        System.out.println("✅ ITrainerService found and retrieved");
        
        try {
            // Create a test trainer
            Date joinDate = new Date(1, 15, 2024);
            Date dob = new Date(5, 20, 1990);
            Trainer trainer = new Trainer(1, "Jane Gym", "jane@gmail.com", 
                "0300-7654321", "456 Fitness Ave", joinDate, dob, 34, "Female", 
                "Yoga", 30.0, 20.0, "Intermediate");
            
            // Test adding trainer
            String result = trainerService.addTrainer(trainer);
            System.out.println("✅ Add Trainer: " + result);
            
            // Test getting trainer
            Trainer retrieved = trainerService.getTrainer(1);
            if (retrieved != null) {
                System.out.println("✅ Get Trainer: Found trainer " + retrieved.getName());
            } else {
                System.out.println("❌ Get Trainer: Trainer not found");
            }
            
            // Test getting all trainers
            int totalTrainers = trainerService.getAllTrainers().size();
            System.out.println("✅ GetAllTrainers: " + totalTrainers + " trainer(s) found");
            
            // Test search
            var searchResults = trainerService.searchTrainersByName("Jane");
            System.out.println("✅ SearchTrainers: " + searchResults.size() + " result(s)");

            // Test member assignment (UC-7)
            trainerService.assignMemberToTrainer(1, 101);
            System.out.println("✅ AssignMember: Assigned member 101 to trainer 1");
            Trainer tAfterAssign = trainerService.getTrainer(1);
            System.out.println("   Assigned Members: " + tAfterAssign.getAssignedMemberIds());

            // Test performance update (UC-8)
            String perfResult = trainerService.updatePerformance(1, 4.5, true);
            System.out.println("✅ UpdatePerformance: " + perfResult);
            Trainer tAfterPerf = trainerService.getTrainer(1);
            System.out.println("   Avg Rating: " + tAfterPerf.getAverageRating());
            System.out.println("   Attendance Days: " + tAfterPerf.getAttendanceDays());
            
        } catch (Exception e) {
            System.out.println("❌ Error testing ITrainerService: " + e.getMessage());
            e.printStackTrace();
        }
        
        // Release service
        context.ungetService(trainerRef);
    }
    
    private void testReportService(BundleContext context) {
        System.out.println("\n--- Testing IReportService ---");
        
        ServiceReference<IReportService> reportRef = 
            context.getServiceReference(IReportService.class);
        
        if (reportRef == null) {
            System.out.println("❌ IReportService not found in service registry");
            return;
        }
        
        IReportService reportService = context.getService(reportRef);
        if (reportService == null) {
            System.out.println("❌ Failed to get IReportService instance");
            return;
        }
        
        System.out.println("✅ IReportService found and retrieved");
        
        try {
            // Test generating full report
            System.out.println("\n--- Testing Report Generation ---");
            String fullReport = reportService.generateFullReport();
            System.out.println("✅ GenerateFullReport: " + fullReport.length() + " characters");
            System.out.println("   Preview: " + fullReport.substring(0, Math.min(100, fullReport.length())) + "...");
            
            // Test individual reports
            String trainerReport = reportService.generateTrainerReport();
            System.out.println("✅ GenerateTrainerReport: " + trainerReport.length() + " characters");
            
            String memberReport = reportService.generateMemberReport();
            System.out.println("✅ GenerateMemberReport: " + memberReport.length() + " characters");
            
            String machineReport = reportService.generateMachineReport();
            System.out.println("✅ GenerateMachineReport: " + machineReport.length() + " characters");
            
            // Test analytics
            System.out.println("\n--- Testing Analytics ---");
            Map<String, Object> demographics = reportService.generateMemberDemographicsReport();
            System.out.println("✅ GenerateMemberDemographicsReport:");
            System.out.println("   Total Members: " + demographics.get("totalMembers"));
            System.out.println("   Gender Distribution: " + demographics.get("genderDistribution"));
            
            Map<String, Object> equipment = reportService.generateEquipmentUsageAnalyticsReport();
            System.out.println("✅ GenerateEquipmentUsageAnalyticsReport:");
            System.out.println("   Total Machines: " + equipment.get("totalMachines"));
            System.out.println("   Total Bookings: " + equipment.get("totalBookings"));
            
            Map<String, Object> bodyStats = reportService.generateBodyStatisticsReport();
            System.out.println("✅ GenerateBodyStatisticsReport:");
            System.out.println("   Valid Records: " + bodyStats.get("validRecords"));
            
            Map<String, Object> fitnessGoals = reportService.generateFitnessGoalDistributionReport();
            System.out.println("✅ GenerateFitnessGoalDistributionReport:");
            System.out.println("   Total Members: " + fitnessGoals.get("totalMembers"));
            
            // Test export
            System.out.println("\n--- Testing Export ---");
            byte[] txtExport = reportService.exportReportToTxt(fullReport, "Full Report");
            System.out.println("✅ ExportReportToTxt: " + txtExport.length + " bytes");
            
            byte[] jsonExport = reportService.exportReportToJson(demographics, "Demographics");
            System.out.println("✅ ExportReportToJson: " + jsonExport.length + " bytes");
            
            byte[] csvExport = reportService.exportReportToCsv(fullReport, "Full Report");
            System.out.println("✅ ExportReportToCsv: " + csvExport.length + " bytes");
            
        } catch (Exception e) {
            System.out.println("❌ Error testing IReportService: " + e.getMessage());
            e.printStackTrace();
        }
        
        // Release service
        context.ungetService(reportRef);
    }
    
    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("Test Client Bundle stopped");
    }
}