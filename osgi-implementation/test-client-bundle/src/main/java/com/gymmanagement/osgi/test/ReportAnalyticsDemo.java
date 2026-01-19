package com.gymmanagement.osgi.test;

import com.gymmanagement.osgi.base.service.*;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import java.util.Map;

/**
 * Focused Demo for Report Analytics Module
 * Use this for presentations to showcase Report Analytics features
 */
public class ReportAnalyticsDemo implements BundleActivator {

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("\n");
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║     REPORT ANALYTICS MODULE - DEMONSTRATION                  ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
        System.out.println();

        // Wait a moment for other services to be ready
        Thread.sleep(1000);

        // Get Report Service
        ServiceReference<IReportService> reportRef = context.getServiceReference(IReportService.class);
        if (reportRef == null) {
            System.out.println("❌ IReportService not found. Make sure report-analytics-bundle is running.");
            return;
        }

        IReportService reportService = context.getService(reportRef);
        if (reportService == null) {
            System.out.println("❌ Failed to get IReportService instance");
            return;
        }

        System.out.println("✅ Report Analytics Service Connected");
        System.out.println();

        try {
            // ========================================
            // 1. FULL SYSTEM REPORT
            // ========================================
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.println("📊 FEATURE 1: FULL SYSTEM REPORT");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            String fullReport = reportService.generateFullReport();
            System.out.println("Generated Full Report: " + fullReport.length() + " characters");
            System.out.println();
            System.out.println("Report Preview:");
            System.out.println("─────────────────────────────────────────────────────────────");
            // Show first 500 characters
            String preview = fullReport.length() > 500 
                ? fullReport.substring(0, 500) + "\n... (truncated)" 
                : fullReport;
            System.out.println(preview);
            System.out.println("─────────────────────────────────────────────────────────────");
            System.out.println();

            // ========================================
            // 2. INDIVIDUAL REPORTS
            // ========================================
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.println("📋 FEATURE 2: INDIVIDUAL REPORTS");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            
            String trainerReport = reportService.generateTrainerReport();
            System.out.println("✅ Trainer Report: " + trainerReport.length() + " characters");
            System.out.println("   Preview: " + trainerReport.substring(0, Math.min(150, trainerReport.length())) + "...");
            System.out.println();
            
            String memberReport = reportService.generateMemberReport();
            System.out.println("✅ Member Report: " + memberReport.length() + " characters");
            System.out.println();
            
            String machineReport = reportService.generateMachineReport();
            System.out.println("✅ Machine Report: " + machineReport.length() + " characters");
            System.out.println();

            // ========================================
            // 3. ANALYTICS REPORTS
            // ========================================
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.println("📈 FEATURE 3: ADVANCED ANALYTICS");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            
            // Member Demographics
            Map<String, Object> demographics = reportService.generateMemberDemographicsReport();
            System.out.println("📊 Member Demographics Analytics:");
            System.out.println("   • Total Members: " + demographics.get("totalMembers"));
            System.out.println("   • Gender Distribution: " + demographics.get("genderDistribution"));
            if (demographics.containsKey("ageGroups")) {
                System.out.println("   • Age Groups: " + demographics.get("ageGroups"));
            }
            System.out.println();

            // Equipment Usage
            Map<String, Object> equipment = reportService.generateEquipmentUsageAnalyticsReport();
            System.out.println("🏋️ Equipment Usage Analytics:");
            System.out.println("   • Total Machines: " + equipment.get("totalMachines"));
            System.out.println("   • Total Bookings: " + equipment.get("totalBookings"));
            if (equipment.containsKey("mostUsedMachines")) {
                System.out.println("   • Most Used Machines: " + equipment.get("mostUsedMachines"));
            }
            System.out.println();

            // Body Statistics
            Map<String, Object> bodyStats = reportService.generateBodyStatisticsReport();
            System.out.println("💪 Body Statistics Analytics:");
            System.out.println("   • Valid Records: " + bodyStats.get("validRecords"));
            if (bodyStats.containsKey("averageHeight")) {
                System.out.println("   • Average Height: " + bodyStats.get("averageHeight") + " m");
            }
            if (bodyStats.containsKey("averageWeight")) {
                System.out.println("   • Average Weight: " + bodyStats.get("averageWeight") + " kg");
            }
            System.out.println();

            // Fitness Goal Distribution
            Map<String, Object> fitnessGoals = reportService.generateFitnessGoalDistributionReport();
            System.out.println("🎯 Fitness Goal Distribution:");
            System.out.println("   • Total Members: " + fitnessGoals.get("totalMembers"));
            if (fitnessGoals.containsKey("goalDistribution")) {
                System.out.println("   • Goal Distribution: " + fitnessGoals.get("goalDistribution"));
            }
            System.out.println();

            // ========================================
            // 4. EXPORT FUNCTIONALITY
            // ========================================
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.println("💾 FEATURE 4: MULTI-FORMAT EXPORT");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            
            byte[] txtExport = reportService.exportReportToTxt(fullReport, "Full Report");
            System.out.println("✅ TXT Export: " + txtExport.length + " bytes");
            
            byte[] jsonExport = reportService.exportReportToJson(demographics, "Demographics");
            System.out.println("✅ JSON Export: " + jsonExport.length + " bytes");
            
            byte[] csvExport = reportService.exportReportToCsv(fullReport, "Full Report");
            System.out.println("✅ CSV Export: " + csvExport.length + " bytes");
            System.out.println();

            // ========================================
            // SUMMARY
            // ========================================
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.println("✅ REPORT ANALYTICS MODULE - ALL FEATURES DEMONSTRATED");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.println();
            System.out.println("Key Features Shown:");
            System.out.println("  ✓ Full System Report Generation");
            System.out.println("  ✓ Individual Report Types (Trainer, Member, Machine)");
            System.out.println("  ✓ Advanced Analytics (Demographics, Usage, Statistics)");
            System.out.println("  ✓ Multi-format Export (TXT, JSON, CSV)");
            System.out.println();
            System.out.println("╔═══════════════════════════════════════════════════════════════╗");
            System.out.println("║              DEMONSTRATION COMPLETE                           ║");
            System.out.println("╚═══════════════════════════════════════════════════════════════╝");
            System.out.println();

        } catch (Exception e) {
            System.out.println("❌ Error during demonstration: " + e.getMessage());
            e.printStackTrace();
        } finally {
            context.ungetService(reportRef);
        }
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("Report Analytics Demo stopped");
    }
}
