package com.gymmanagement.reportanalytics.base;

import com.gymmanagement.reportanalytics.dto.*;

/**
 * IReportService Interface
 * 
 * This interface defines the contract for the Report Analytics Component.
 * According to the component architecture, this interface should be in the Base Library.
 * 
 * Dependencies: Report, Member, Trainer, Machine, Payment
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
    MemberDemographicsDTO generateMemberDemographicsReport();
    GrowthTrendsDTO generateGrowthTrendsReport();
    BodyStatisticsDTO generateBodyStatisticsReport();
    FitnessGoalDistributionDTO generateFitnessGoalDistributionReport();
    
    // Equipment Usage Analytics Method (NEW FEATURE)
    EquipmentUsageAnalyticsDTO generateEquipmentUsageAnalyticsReport();
    
    // Export Methods (NEW FEATURE)
    byte[] exportReportToTxt(String reportContent, String reportType);
    byte[] exportReportToJson(Object reportData, String reportType);
    byte[] exportReportToCsv(String reportContent, String reportType);
}

