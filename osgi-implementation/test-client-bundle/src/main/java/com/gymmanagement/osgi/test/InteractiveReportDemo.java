package com.gymmanagement.osgi.test;

import com.gymmanagement.osgi.base.service.IReportService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Scanner;

/**
 * Interactive Demo Client for Report Analytics Module
 * 
 * This class demonstrates how to interact with OSGi services
 * through a simple menu-driven interface.
 * 
 * To use this:
 * 1. Get the BundleContext (from BundleActivator or FrameworkUtil)
 * 2. Create an instance: InteractiveReportDemo demo = new InteractiveReportDemo(context);
 * 3. Call: demo.startInteractiveMenu();
 */
public class InteractiveReportDemo {
    
    private BundleContext context;
    private Scanner scanner;
    
    public InteractiveReportDemo(BundleContext context) {
        this.context = context;
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Start the interactive menu
     */
    public void startInteractiveMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("  Report Analytics - Interactive Demo");
        System.out.println("=".repeat(50));
        
        while (true) {
            printMenu();
            int choice = getChoice();
            
            if (choice == 0) {
                System.out.println("\nExiting interactive demo...");
                break;
            }
            
            handleChoice(choice);
        }
        
        scanner.close();
    }
    
    private void printMenu() {
        System.out.println("\n--- Report Analytics Menu ---");
        System.out.println("1. Generate Full Report");
        System.out.println("2. Generate Trainer Report");
        System.out.println("3. Generate Member Report");
        System.out.println("4. Generate Machine Report");
        System.out.println("5. Show Member Demographics");
        System.out.println("6. Show Equipment Usage Analytics");
        System.out.println("7. Show Body Statistics");
        System.out.println("8. Show Fitness Goal Distribution");
        System.out.println("9. Export Report to TXT");
        System.out.println("10. Export Report to JSON");
        System.out.println("11. Export Report to CSV");
        System.out.println("0. Exit");
        System.out.print("\nEnter your choice: ");
    }
    
    private int getChoice() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private void handleChoice(int choice) {
        IReportService reportService = getReportService();
        
        if (reportService == null) {
            System.out.println("❌ IReportService not available. Make sure the report bundle is started.");
            return;
        }
        
        try {
            switch (choice) {
                case 1:
                    System.out.println("\n📊 Generating Full Report...");
                    String fullReport = reportService.generateFullReport();
                    System.out.println("✅ Full Report Generated (" + fullReport.length() + " characters)");
                    System.out.println("\n--- Preview (first 500 chars) ---");
                    System.out.println(fullReport.substring(0, Math.min(500, fullReport.length())));
                    if (fullReport.length() > 500) {
                        System.out.println("\n... (truncated)");
                    }
                    break;
                    
                case 2:
                    System.out.println("\n👨‍🏫 Generating Trainer Report...");
                    String trainerReport = reportService.generateTrainerReport();
                    System.out.println("✅ Trainer Report Generated (" + trainerReport.length() + " characters)");
                    System.out.println("\n--- Preview ---");
                    System.out.println(trainerReport.substring(0, Math.min(500, trainerReport.length())));
                    break;
                    
                case 3:
                    System.out.println("\n👥 Generating Member Report...");
                    String memberReport = reportService.generateMemberReport();
                    System.out.println("✅ Member Report Generated (" + memberReport.length() + " characters)");
                    System.out.println("\n--- Preview ---");
                    System.out.println(memberReport.substring(0, Math.min(500, memberReport.length())));
                    break;
                    
                case 4:
                    System.out.println("\n🏋️ Generating Machine Report...");
                    String machineReport = reportService.generateMachineReport();
                    System.out.println("✅ Machine Report Generated (" + machineReport.length() + " characters)");
                    System.out.println("\n--- Full Report ---");
                    System.out.println(machineReport);
                    break;
                    
                case 5:
                    System.out.println("\n📈 Member Demographics:");
                    Map<String, Object> demographics = reportService.generateMemberDemographicsReport();
                    System.out.println("   Total Members: " + demographics.get("totalMembers"));
                    System.out.println("   Gender Distribution: " + demographics.get("genderDistribution"));
                    if (demographics.containsKey("ageGroups")) {
                        System.out.println("   Age Groups: " + demographics.get("ageGroups"));
                    }
                    break;
                    
                case 6:
                    System.out.println("\n📊 Equipment Usage Analytics:");
                    Map<String, Object> equipmentUsage = reportService.generateEquipmentUsageAnalyticsReport();
                    System.out.println("   Total Machines: " + equipmentUsage.get("totalMachines"));
                    System.out.println("   Total Bookings: " + equipmentUsage.get("totalBookings"));
                    if (equipmentUsage.containsKey("usageStatistics")) {
                        System.out.println("   Usage Statistics: " + equipmentUsage.get("usageStatistics"));
                    }
                    break;
                    
                case 7:
                    System.out.println("\n📏 Body Statistics:");
                    Map<String, Object> bodyStats = reportService.generateBodyStatisticsReport();
                    System.out.println("   Valid Records: " + bodyStats.get("validRecords"));
                    if (bodyStats.containsKey("averageWeight")) {
                        System.out.println("   Average Weight: " + bodyStats.get("averageWeight"));
                    }
                    if (bodyStats.containsKey("averageHeight")) {
                        System.out.println("   Average Height: " + bodyStats.get("averageHeight"));
                    }
                    break;
                    
                case 8:
                    System.out.println("\n🎯 Fitness Goal Distribution:");
                    Map<String, Object> fitnessGoals = reportService.generateFitnessGoalDistributionReport();
                    System.out.println("   Total Members: " + fitnessGoals.get("totalMembers"));
                    if (fitnessGoals.containsKey("goalDistribution")) {
                        System.out.println("   Goal Distribution: " + fitnessGoals.get("goalDistribution"));
                    }
                    break;
                    
                case 9:
                    System.out.println("\n💾 Exporting to TXT...");
                    String txtReport = reportService.generateFullReport();
                    byte[] txtData = reportService.exportReportToTxt(txtReport, "Full Report");
                    String txtFile = saveToFile(txtData, "report", "txt");
                    System.out.println("✅ Exported to TXT (" + txtData.length + " bytes)");
                    System.out.println("📁 Saved to: " + txtFile);
                    break;
                    
                case 10:
                    System.out.println("\n💾 Exporting to JSON...");
                    Map<String, Object> jsonDataMap = reportService.generateMemberDemographicsReport();
                    byte[] jsonData = reportService.exportReportToJson(jsonDataMap, "Demographics");
                    String jsonFile = saveToFile(jsonData, "demographics", "json");
                    System.out.println("✅ Exported to JSON (" + jsonData.length + " bytes)");
                    System.out.println("📁 Saved to: " + jsonFile);
                    System.out.println("\n--- JSON Preview ---");
                    System.out.println(new String(jsonData));
                    break;
                    
                case 11:
                    System.out.println("\n💾 Exporting to CSV...");
                    String csvReport = reportService.generateFullReport();
                    byte[] csvData = reportService.exportReportToCsv(csvReport, "Full Report");
                    String csvFile = saveToFile(csvData, "report", "csv");
                    System.out.println("✅ Exported to CSV (" + csvData.length + " bytes)");
                    System.out.println("📁 Saved to: " + csvFile);
                    break;
                    
                default:
                    System.out.println("❌ Invalid choice. Please try again.");
            }
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    /**
     * Get IReportService from the OSGi service registry
     */
    private IReportService getReportService() {
        ServiceReference<IReportService> ref = context.getServiceReference(IReportService.class);
        
        if (ref == null) {
            return null;
        }
        
        return context.getService(ref);
    }
    
    /**
     * Save byte array to a file in the Desktop/reports directory
     * @param data The data to save
     * @param baseName Base name for the file
     * @param extension File extension (without dot)
     * @return Full path to the saved file
     */
    private String saveToFile(byte[] data, String baseName, String extension) {
        try {
            // Create filename with timestamp
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String filename = baseName + "_" + timestamp + "." + extension;
            
            // Use Desktop/reports directory
            File outputDir = new File("C:\\Users\\kyrod\\OneDrive\\Desktop", "reports");
            
            // Create reports directory if it doesn't exist
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }
            
            // Write file
            File file = new File(outputDir, filename);
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(data);
            }
            
            return file.getAbsolutePath();
        } catch (IOException e) {
            System.err.println("❌ Error saving file: " + e.getMessage());
            return "Error: Could not save file";
        }
    }
}
