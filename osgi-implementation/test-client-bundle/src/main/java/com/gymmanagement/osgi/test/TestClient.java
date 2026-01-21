package com.gymmanagement.osgi.test;

import com.gymmanagement.osgi.base.entity.*;
import com.gymmanagement.osgi.base.service.*;
import java.util.List;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import java.util.Map;

import java.time.LocalDate;

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
        
        // Test IPaymentService (add outstanding balances)
        testPaymentService(context);

        // Test IReportService
        testReportService(context);

        // Test IMachineService
        testMachineService(context);

        System.out.println("\n=========================================");
        System.out.println("OSGi Test Client - Tests Complete");
        System.out.println("=========================================\n");

        // Register Gogo shell commands for interactive demos
        // This avoids conflict with the Gogo shell's own console handling
        try {
            java.util.Hashtable<String, Object> props = new java.util.Hashtable<>();
            props.put("osgi.command.scope", "gym");
            props.put("osgi.command.function", new String[] { "machine", "report" });
            context.registerService(Object.class, new GymCommand(context), props);

            System.out.println("\n✅ Interactive Demos Registered!");
            System.out.println("   Type 'gym:machine' to start the Machine Management Demo");
            System.out.println("   Type 'gym:report' to start the Report Analytics Demo");

        } catch (Exception e) {
            System.err.println("❌ Failed to register gym commands: " + e.getMessage());
        }
    }

    private void testMemberService(BundleContext context) {
        System.out.println("--- Testing IMemberService ---");

        ServiceReference<IMemberService> memberRef = context.getServiceReference(IMemberService.class);

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
            // Seed comprehensive sample data for analytics
            System.out.println("\n📊 Seeding sample data for analytics...");
            
            // Member 1: Young male, Weight Loss, Zero balance, Joined Jan 2024
            Date joinDate1 = new Date(1, 15, 2024);
            Date dob1 = new Date(5, 20, 1995);
            Payment payment1 = new Payment(0.0, "John Doe", "1234-5678-9012-3456");
            Member member1 = new Member(0, "John Doe", "john@example.com", 
                "0300-1234567", "123 Main St", joinDate1, dob1, 29, "Male", 
                1.75, 70.0, payment1, "Weight Loss");
            memberService.addMember(member1);
            System.out.println("  ✓ Added: John Doe (Male, 29, Weight Loss, $0 balance)");
            
            // Member 2: Young female, Muscle Gain, Outstanding balance, Joined Feb 2024
            Date joinDate2 = new Date(2, 10, 2024);
            Date dob2 = new Date(8, 15, 1998);
            Payment payment2 = new Payment(150.0, "Sarah Smith", "2345-6789-0123-4567");
            Member member2 = new Member(0, "Sarah Smith", "sarah@example.com", 
                "0300-2345678", "456 Oak Ave", joinDate2, dob2, 26, "Female", 
                1.65, 55.0, payment2, "Muscle Gain");
            memberService.addMember(member2);
            System.out.println("  ✓ Added: Sarah Smith (Female, 26, Muscle Gain, $150 balance)");
            
            // Member 3: Middle-aged male, Endurance, Zero balance, Joined Mar 2024
            Date joinDate3 = new Date(3, 5, 2024);
            Date dob3 = new Date(3, 12, 1985);
            Payment payment3 = new Payment(0.0, "Mike Johnson", "3456-7890-1234-5678");
            Member member3 = new Member(0, "Mike Johnson", "mike@example.com", 
                "0300-3456789", "789 Pine Rd", joinDate3, dob3, 39, "Male", 
                1.80, 85.0, payment3, "Endurance");
            memberService.addMember(member3);
            System.out.println("  ✓ Added: Mike Johnson (Male, 39, Endurance, $0 balance)");
            
            // Member 4: Middle-aged female, Flexibility, Outstanding balance, Joined Apr 2024
            Date joinDate4 = new Date(4, 20, 2024);
            Date dob4 = new Date(11, 8, 1988);
            Payment payment4 = new Payment(75.50, "Emily Davis", "4567-8901-2345-6789");
            Member member4 = new Member(0, "Emily Davis", "emily@example.com", 
                "0300-4567890", "321 Elm St", joinDate4, dob4, 36, "Female", 
                1.70, 62.0, payment4, "Flexibility");
            memberService.addMember(member4);
            System.out.println("  ✓ Added: Emily Davis (Female, 36, Flexibility, $75.50 balance)");
            
            // Member 5: Older male, General Fitness, Zero balance, Joined May 2024
            Date joinDate5 = new Date(5, 1, 2024);
            Date dob5 = new Date(7, 22, 1975);
            Payment payment5 = new Payment(0.0, "Robert Brown", "5678-9012-3456-7890");
            Member member5 = new Member(0, "Robert Brown", "robert@example.com", 
                "0300-5678901", "654 Maple Dr", joinDate5, dob5, 49, "Male", 
                1.78, 90.0, payment5, "General Fitness");
            memberService.addMember(member5);
            System.out.println("  ✓ Added: Robert Brown (Male, 49, General Fitness, $0 balance)");
            
            // Member 6: Young female, Weight Loss, Outstanding balance, Joined Jun 2024
            Date joinDate6 = new Date(6, 15, 2024);
            Date dob6 = new Date(2, 14, 2000);
            Payment payment6 = new Payment(200.0, "Lisa Wilson", "6789-0123-4567-8901");
            Member member6 = new Member(0, "Lisa Wilson", "lisa@example.com", 
                "0300-6789012", "987 Cedar Ln", joinDate6, dob6, 24, "Female", 
                1.60, 65.0, payment6, "Weight Loss");
            memberService.addMember(member6);
            System.out.println("  ✓ Added: Lisa Wilson (Female, 24, Weight Loss, $200 balance)");
            
            // Member 7: Middle-aged male, Muscle Gain, Zero balance, Joined Jul 2024
            Date joinDate7 = new Date(7, 10, 2024);
            Date dob7 = new Date(9, 30, 1982);
            Payment payment7 = new Payment(0.0, "David Lee", "7890-1234-5678-9012");
            Member member7 = new Member(0, "David Lee", "david@example.com", 
                "0300-7890123", "147 Birch Way", joinDate7, dob7, 42, "Male", 
                1.82, 88.0, payment7, "Muscle Gain");
            memberService.addMember(member7);
            System.out.println("  ✓ Added: David Lee (Male, 42, Muscle Gain, $0 balance)");
            
            // Member 8: Young male, Endurance, Outstanding balance, Joined Aug 2024
            Date joinDate8 = new Date(8, 25, 2024);
            Date dob8 = new Date(4, 5, 1997);
            Payment payment8 = new Payment(125.75, "Alex Taylor", "8901-2345-6789-0123");
            Member member8 = new Member(0, "Alex Taylor", "alex@example.com", 
                "0300-8901234", "258 Spruce Ct", joinDate8, dob8, 27, "Male", 
                1.77, 72.0, payment8, "Endurance");
            memberService.addMember(member8);
            System.out.println("  ✓ Added: Alex Taylor (Male, 27, Endurance, $125.75 balance)");
            
            // Member 9: Older female, General Fitness, Zero balance, Joined Sep 2024
            Date joinDate9 = new Date(9, 12, 2024);
            Date dob9 = new Date(12, 3, 1970);
            Payment payment9 = new Payment(0.0, "Maria Garcia", "9012-3456-7890-1234");
            Member member9 = new Member(0, "Maria Garcia", "maria@example.com", 
                "0300-9012345", "369 Willow Pl", joinDate9, dob9, 54, "Female", 
                1.68, 68.0, payment9, "General Fitness");
            memberService.addMember(member9);
            System.out.println("  ✓ Added: Maria Garcia (Female, 54, General Fitness, $0 balance)");
            
            // Member 10: Middle-aged female, Flexibility, Outstanding balance, Joined Oct 2024
            Date joinDate10 = new Date(10, 30, 2024);
            Date dob10 = new Date(6, 18, 1986);
            Payment payment10 = new Payment(50.0, "Jennifer Martinez", "0123-4567-8901-2345");
            Member member10 = new Member(0, "Jennifer Martinez", "jennifer@example.com", 
                "0300-0123456", "741 Ash Blvd", joinDate10, dob10, 38, "Female", 
                1.72, 60.0, payment10, "Flexibility");
            memberService.addMember(member10);
            System.out.println("  ✓ Added: Jennifer Martinez (Female, 38, Flexibility, $50 balance)");
            
            System.out.println("\n✅ Sample data seeding complete!");
            
            // Test getting all members
            int totalMembers = memberService.getAllMembers().size();
            System.out.println("✅ GetAllMembers: " + totalMembers + " member(s) found");

            // Test search
            var searchResults = memberService.searchMembersByName("John");
            System.out.println("✅ SearchMembers: " + searchResults.size() + " result(s) for 'John'");
            
        } catch (Exception e) {
            System.out.println("❌ Error testing IMemberService: " + e.getMessage());
            e.printStackTrace();
        }

        // Release service
        context.ungetService(memberRef);
    }

    private void testTrainerService(BundleContext context) {
        System.out.println("\n--- Testing ITrainerService ---");

        // Wait for service to become available (retry with delay)
        // Note: Trainer bundle may start after test client, so we wait longer
        ServiceReference<ITrainerService> trainerRef = null;
        int maxRetries = 30; // Increased from 10 to 30
        int retryDelay = 200; // milliseconds
        
        for (int i = 0; i < maxRetries; i++) {
            trainerRef = context.getServiceReference(ITrainerService.class);
            if (trainerRef != null) {
                break;
            }
            try {
                Thread.sleep(retryDelay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

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
            // Seed multiple trainers with different specializations
            System.out.println("\n💪 Seeding trainer data...");
            
            // Trainer 1: Yoga specialist
            Date joinDate1 = new Date(1, 15, 2024);
            Date dob1 = new Date(5, 20, 1990);
            Trainer trainer1 = new Trainer(0, "Jane Gym", "jane@gmail.com", 
                "0300-7654321", "456 Fitness Ave", joinDate1, dob1, 34, "Female", 
                "Yoga", 30.0, 20.0, "Intermediate");
            trainerService.addTrainer(trainer1);
            System.out.println("  ✓ Added: Jane Gym (Yoga, Female, 34)");
            
            // Trainer 2: Strength training specialist
            Date joinDate2 = new Date(2, 1, 2024);
            Date dob2 = new Date(8, 15, 1988);
            Trainer trainer2 = new Trainer(0, "Mark Strong", "mark@example.com", 
                "0300-8765432", "789 Power St", joinDate2, dob2, 36, "Male", 
                "Strength Training", 45.0, 15.0, "Advanced");
            trainerService.addTrainer(trainer2);
            System.out.println("  ✓ Added: Mark Strong (Strength Training, Male, 36)");
            
            // Trainer 3: Cardio specialist
            Date joinDate3 = new Date(3, 10, 2024);
            Date dob3 = new Date(3, 22, 1992);
            Trainer trainer3 = new Trainer(0, "Amy Runner", "amy@example.com", 
                "0300-9876543", "321 Cardio Ave", joinDate3, dob3, 32, "Female", 
                "Cardio", 35.0, 18.0, "Intermediate");
            trainerService.addTrainer(trainer3);
            System.out.println("  ✓ Added: Amy Runner (Cardio, Female, 32)");
            
            // Trainer 4: Pilates specialist
            Date joinDate4 = new Date(4, 5, 2024);
            Date dob4 = new Date(11, 8, 1985);
            Trainer trainer4 = new Trainer(0, "Sophie Core", "sophie@example.com", 
                "0300-0987654", "654 Flexibility Rd", joinDate4, dob4, 39, "Female", 
                "Pilates", 40.0, 12.0, "Advanced");
            trainerService.addTrainer(trainer4);
            System.out.println("  ✓ Added: Sophie Core (Pilates, Female, 39)");
            
            // Trainer 5: CrossFit specialist
            Date joinDate5 = new Date(5, 20, 2024);
            Date dob5 = new Date(7, 14, 1987);
            Trainer trainer5 = new Trainer(0, "Tom Cross", "tom@example.com", 
                "0300-1098765", "147 Intensity Way", joinDate5, dob5, 37, "Male", 
                "CrossFit", 50.0, 10.0, "Expert");
            trainerService.addTrainer(trainer5);
            System.out.println("  ✓ Added: Tom Cross (CrossFit, Male, 37)");
            
            System.out.println("\n✅ Trainer data seeding complete!");
            
            // Test getting all trainers
            int totalTrainers = trainerService.getAllTrainers().size();
            System.out.println("✅ GetAllTrainers: " + totalTrainers + " trainer(s) found");

            // Test search
            var searchResults = trainerService.searchTrainersByName("Jane");
            System.out.println("✅ SearchTrainers: " + searchResults.size() + " result(s) for 'Jane'");

            // Test member assignment (UC-7) - assign some members to trainers
            System.out.println("\n📋 Assigning members to trainers...");
            trainerService.assignMemberToTrainer(1, 1); // Jane -> John
            trainerService.assignMemberToTrainer(1, 2); // Jane -> Sarah
            trainerService.assignMemberToTrainer(2, 3); // Mark -> Mike
            trainerService.assignMemberToTrainer(2, 7); // Mark -> David
            trainerService.assignMemberToTrainer(3, 8); // Amy -> Alex
            System.out.println("✅ Assigned members to trainers");

            // Test performance update (UC-8) - update ratings for some trainers
            System.out.println("\n⭐ Updating trainer performance...");
            trainerService.updatePerformance(1, 4.5, true); // Jane: 4.5 rating, attended
            trainerService.updatePerformance(1, 4.8, true); // Jane: another session
            trainerService.updatePerformance(2, 4.2, true); // Mark: 4.2 rating
            trainerService.updatePerformance(3, 4.9, true); // Amy: 4.9 rating
            trainerService.updatePerformance(3, 5.0, true); // Amy: perfect rating
            System.out.println("✅ Updated trainer performance ratings");
            
            // Display trainer stats
            Trainer t1 = trainerService.getTrainer(1);
            Trainer t2 = trainerService.getTrainer(2);
            Trainer t3 = trainerService.getTrainer(3);
            System.out.println("\n📊 Trainer Performance Summary:");
            System.out.println("   " + t1.getName() + ": Rating " + t1.getAverageRating() + 
                             ", " + t1.getAttendanceDays() + " days, " + 
                             t1.getAssignedMemberIds().size() + " members");
            System.out.println("   " + t2.getName() + ": Rating " + t2.getAverageRating() + 
                             ", " + t2.getAttendanceDays() + " days, " + 
                             t2.getAssignedMemberIds().size() + " members");
            System.out.println("   " + t3.getName() + ": Rating " + t3.getAverageRating() + 
                             ", " + t3.getAttendanceDays() + " days, " + 
                             t3.getAssignedMemberIds().size() + " members");
            
        } catch (Exception e) {
            System.out.println("❌ Error testing ITrainerService: " + e.getMessage());
            e.printStackTrace();
        }

        // Release service
        context.ungetService(trainerRef);
    }
    
    private void testPaymentService(BundleContext context) {
        System.out.println("\n--- Testing IPaymentService ---");
        
        ServiceReference<IPaymentService> paymentRef = 
            context.getServiceReference(IPaymentService.class);
        
        if (paymentRef == null) {
            System.out.println("⚠️  IPaymentService not found in service registry (skipping payment tests)");
            return;
        }
        
        IPaymentService paymentService = context.getService(paymentRef);
        if (paymentService == null) {
            System.out.println("⚠️  Failed to get IPaymentService instance (skipping payment tests)");
            return;
        }
        
        System.out.println("✅ IPaymentService found and retrieved");
        
        try {
            // Note: Some members already have outstanding balances from creation
            // Let's add additional balances to demonstrate payment analytics
            System.out.println("\n💰 Setting up payment data for analytics...");
            
            // Member 2 (Sarah) already has $150, let's add more
            String result1 = paymentService.addOutstandingBalance(2, 50.0);
            System.out.println("  ✓ Added $50 to member 2 (Sarah): " + result1);
            
            // Member 6 (Lisa) already has $200, that's good for testing
            
            // Member 8 (Alex) already has $125.75, let's add more
            String result2 = paymentService.addOutstandingBalance(8, 25.0);
            System.out.println("  ✓ Added $25 to member 8 (Alex): " + result2);
            
            // Get payment analytics summary
            Map<String, Object> analytics = paymentService.generatePaymentAnalytics();
            System.out.println("\n📊 Payment Analytics Summary:");
            System.out.println("   Total Outstanding: $" + analytics.get("totalOutstandingBalance"));
            System.out.println("   Average Outstanding: $" + analytics.get("averageOutstandingBalance"));
            System.out.println("   Members with Balance: " + analytics.get("membersWithOutstandingBalance"));
            System.out.println("   Members with Zero Balance: " + analytics.get("membersWithZeroBalance"));
            System.out.println("   Total Members: " + analytics.get("totalMembers"));
            
        } catch (Exception e) {
            System.out.println("❌ Error testing IPaymentService: " + e.getMessage());
            e.printStackTrace();
        }
        
        // Release service
        context.ungetService(paymentRef);
    }
    
    private void testReportService(BundleContext context) {
        System.out.println("\n--- Testing IReportService ---");

        ServiceReference<IReportService> reportRef = context.getServiceReference(IReportService.class);

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

    private void testMachineService(BundleContext context) {
        System.out.println("\n--- Testing IMachineService ---");

        ServiceReference<IMachineService> machineRef = context.getServiceReference(IMachineService.class);

        if (machineRef == null) {
            System.out.println("❌ IMachineService not found in service registry");
            return;
        }

        IMachineService machineService = context.getService(machineRef);
        if (machineService == null) {
            System.out.println("❌ Failed to get IMachineService instance");
            return;
        }

        System.out.println("✅ IMachineService found and retrieved");

        try {
            // Create a test machine
            Machine machine = new Machine(101, "Treadmill X1", "Technogym", "X1-Pro", 150.0, 100.0, "Cardio");

            // Add machine
            String addResult = machineService.addMachine(machine);
            System.out.println("✅ Add Machine: " + addResult);

            // Schedule Maintenance
            LocalDate maintenanceDate = LocalDate.now().plusDays(7);
            String scheduleResult = machineService.scheduleMaintenance(101, maintenanceDate, maintenanceDate,
                    "Routine Check");
            System.out.println("✅ ScheduleMaintenance: " + scheduleResult);

            // Verify machine state if possible (Machine object is local copy, need to fetch
            // again)
            Machine fetched = machineService.getMachine(101);
            if (fetched != null && fetched.getMaintenanceSchedule() != null
                    && !fetched.getMaintenanceSchedule().isEmpty()) {
                System.out.println(
                        "✅ Verification: Machine Maintenance Scheduled: "
                                + fetched.getMaintenanceSchedule().get(0).getStartDate());
            } else {
                System.out.println("❌ Verification: Maintenance date not set correctly");
            }

            // --- Verify Blocking Booking during Maintenance ---
            System.out.println();
            System.out.println("--- Testing Maintenance Booking Restriction ---");

            // 1. Set maintenance to TODAY
            machineService.scheduleMaintenance(101, LocalDate.now(), LocalDate.now(), "Emergency Fix");

            // 2. Try to book (should fail)
            Payment dummyPayment = new Payment(0.0, "Test", "0000");
            Member testMember = new Member(999, "Test Member", "test@test.com", "0000", "Addr", new Date(1, 1, 2024),
                    new Date(1, 1, 1990), 20, "M", 1.8, 80.0, dummyPayment, "Goal");
            String bookingResultFail = machineService.bookMachine(101, testMember);
            if ("Machine is currently under maintenance.".equals(bookingResultFail)) {
                System.out.println("✅ Booking Restriction: Blocked correctly (" + bookingResultFail + ")");
            } else {
                System.out.println("❌ Booking Restriction: Failed to block. Result: " + bookingResultFail);
            }

            // Test getAvailableMachines
            List<Machine> availToday = machineService.getAvailableMachines(LocalDate.now());
            boolean is101Available = availToday.stream().anyMatch(m -> m.getRegId() == 101);
            if (!is101Available) {
                System.out.println("✅ GetAvailableMachines: Machine 101 correctly identified as unavailable today.");
            } else {
                System.out.println("❌ GetAvailableMachines: Machine 101 should be unavailable.");
            }

            // 3. Set maintenance to FUTURE (e.g. next week)
            // Note: In this simple implementation, the "TODAY" maintentance is still in the
            // list!
            // So we can't easily "remove" it unless we clear the list or update status.
            // For this test, let's just create a NEW machine to test the success case,
            // OR we update the status of the maintenance we just added?
            // But we don't have updateMaintenance exposed in service easily yet.
            // Let's create a fresh machine 102 for the success case.

            Machine machine2 = new Machine(102, "Bike Y1", "Fit", "Y-Pro", 100.0, 50.0, "Cardio");
            machineService.addMachine(machine2);
            machineService.scheduleMaintenance(102, LocalDate.now().plusDays(2), LocalDate.now().plusDays(2),
                    "Future Check");

            // 4. Try to book Machine 102 (should succeed as maintenance is in future)
            String bookingResultSuccess = machineService.bookMachine(102, testMember);
            if ("booked".equals(bookingResultSuccess)) {
                System.out.println("✅ Booking Restriction: Allowed correctly when not under maintenance ("
                        + bookingResultSuccess + ")");
            } else {
                System.out
                        .println("❌ Booking Restriction: Failed to book normal slot. Result: " + bookingResultSuccess);
            }

            // Test getAvailableMachines for 102
            List<Machine> availToday2 = machineService.getAvailableMachines(LocalDate.now());
            if (availToday2.stream().anyMatch(m -> m.getRegId() == 102)) {
                System.out.println("✅ GetAvailableMachines: Machine 102 correctly identified as available today.");
            } else {
                System.out.println("❌ GetAvailableMachines: Machine 102 should be available.");
            }

            // --- Test getUsageStatistics ---
            System.out.println();
            System.out.println("--- Testing Usage Statistics ---");
            List<com.gymmanagement.osgi.base.dto.MachineUsageStats> stats = machineService.getUsageStatistics();
            if (stats != null && !stats.isEmpty()) {
                System.out.println("✅ Usage Statistics retrieved: " + stats.size() + " records.");
                for (com.gymmanagement.osgi.base.dto.MachineUsageStats s : stats) {
                    System.out.println("   -> " + s.toString());
                }
            } else {
                System.out.println("❌ Usage Statistics: Returned empty or null.");
            }

        } catch (Exception e) {
            System.out.println("❌ Error testing IMachineService: " + e.getMessage());
            e.printStackTrace();
        }

        context.ungetService(machineRef);
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("Test Client Bundle stopped");
    }
}
