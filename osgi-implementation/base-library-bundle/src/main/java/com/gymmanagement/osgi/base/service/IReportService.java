package com.gymmanagement.osgi.base.service;

import java.util.List;
import java.util.Map;

/**
 * OSGi service interface for Report and Analytics
 * Provides methods for generating reports and analytics
 */
public interface IReportService {
    
    // ========== System Report Generation Methods ==========
    
    /**
     * Generate a full system report
     * @return Full report as string
     */
    String generateFullReport();
    
    /**
     * Generate trainer report
     * @return Trainer report as string
     */
    String generateTrainerReport();
    
    /**
     * Generate member report
     * @return Member report as string
     */
    String generateMemberReport();
    
    /**
     * Generate machine report
     * @return Machine report as string
     */
    String generateMachineReport();
    
    /**
     * Generate booking report
     * @return Booking report as string
     */
    String generateBookingReport();
    
    /**
     * Generate outstanding balance report
     * @return Outstanding balance report as string
     */
    String generateReportOfOutstandingBalance();
    
    /**
     * Generate zero outstanding balance report
     * @return Zero outstanding balance report as string
     */
    String generateReportOfZeroOutstandingBalance();
    
    // ========== Member Analytics Methods ==========
    
    /**
     * Generate member demographics analytics
     * @return Map containing demographics data
     */
    Map<String, Object> generateMemberDemographicsReport();
    
    /**
     * Generate growth trends analytics
     * @return Map containing growth trends data
     */
    Map<String, Object> generateGrowthTrendsReport();
    
    /**
     * Generate body statistics analytics
     * @return Map containing body statistics data
     */
    Map<String, Object> generateBodyStatisticsReport();
    
    /**
     * Generate fitness goal distribution analytics
     * @return Map containing fitness goal distribution data
     */
    Map<String, Object> generateFitnessGoalDistributionReport();
    
    // ========== Equipment Usage Analytics Methods ==========
    
    /**
     * Generate equipment usage analytics
     * @return Map containing equipment usage analytics data
     */
    Map<String, Object> generateEquipmentUsageAnalyticsReport();
    
    // ========== Export Methods ==========
    
    /**
     * Export report to text format
     * @param reportContent Report content
     * @param reportType Type of report
     * @return Byte array of exported report
     */
    byte[] exportReportToTxt(String reportContent, String reportType);
    
    /**
     * Export report to JSON format
     * @param reportData Report data object
     * @param reportType Type of report
     * @return Byte array of exported report
     */
    byte[] exportReportToJson(Object reportData, String reportType);
    
    /**
     * Export report to CSV format
     * @param reportContent Report content
     * @param reportType Type of report
     * @return Byte array of exported report
     */
    byte[] exportReportToCsv(String reportContent, String reportType);
}

