package com.gymmanagement.base.service;

/**
 * IReportService Interface
 * 
 * Defines the contract for Report Analytics operations.
 * This interface should be implemented by the Report Analytics Component.
 * 
 * Dependencies: Report, Member, Trainer, Machine, Payment (from Base Library)
 */
public interface IReportService {
    
    // System Report Generation Methods
    String generateFullReport();
    String generateTrainerReport();
    String generateMemberReport();
    String generateMachineReport();
    String generateBookingReport();
    String generateReportOfOutstandingBalance();
    String generateReportOfZeroOutstandingBalance();
    
    // Member Analytics Methods (NEW FEATURE)
    Object generateMemberDemographicsReport();
    Object generateGrowthTrendsReport();
    Object generateBodyStatisticsReport();
    Object generateFitnessGoalDistributionReport();
    
    // Equipment Usage Analytics Method (NEW FEATURE)
    Object generateEquipmentUsageAnalyticsReport();
    
    // Export Methods (NEW FEATURE)
    byte[] exportReportToTxt(String reportContent, String reportType);
    byte[] exportReportToJson(Object reportData, String reportType);
    byte[] exportReportToCsv(String reportContent, String reportType);
}

