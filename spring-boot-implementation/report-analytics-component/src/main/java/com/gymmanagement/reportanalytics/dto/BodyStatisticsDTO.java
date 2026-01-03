package com.gymmanagement.reportanalytics.dto;

import java.util.Map;

/**
 * DTO for Body Statistics Report
 */
public class BodyStatisticsDTO {
    private double averageHeight;
    private double averageWeight;
    private double averageBMI;
    private Map<String, Integer> bmiCategoryDistribution;
    private Map<String, Double> bmiCategoryPercentages;
    private int validRecords;
    private String generatedDate;

    public BodyStatisticsDTO() {
    }

    public BodyStatisticsDTO(double averageHeight, double averageWeight, double averageBMI,
                            Map<String, Integer> bmiCategoryDistribution,
                            Map<String, Double> bmiCategoryPercentages,
                            int validRecords, String generatedDate) {
        this.averageHeight = averageHeight;
        this.averageWeight = averageWeight;
        this.averageBMI = averageBMI;
        this.bmiCategoryDistribution = bmiCategoryDistribution;
        this.bmiCategoryPercentages = bmiCategoryPercentages;
        this.validRecords = validRecords;
        this.generatedDate = generatedDate;
    }

    public double getAverageHeight() {
        return averageHeight;
    }

    public void setAverageHeight(double averageHeight) {
        this.averageHeight = averageHeight;
    }

    public double getAverageWeight() {
        return averageWeight;
    }

    public void setAverageWeight(double averageWeight) {
        this.averageWeight = averageWeight;
    }

    public double getAverageBMI() {
        return averageBMI;
    }

    public void setAverageBMI(double averageBMI) {
        this.averageBMI = averageBMI;
    }

    public Map<String, Integer> getBmiCategoryDistribution() {
        return bmiCategoryDistribution;
    }

    public void setBmiCategoryDistribution(Map<String, Integer> bmiCategoryDistribution) {
        this.bmiCategoryDistribution = bmiCategoryDistribution;
    }

    public Map<String, Double> getBmiCategoryPercentages() {
        return bmiCategoryPercentages;
    }

    public void setBmiCategoryPercentages(Map<String, Double> bmiCategoryPercentages) {
        this.bmiCategoryPercentages = bmiCategoryPercentages;
    }

    public int getValidRecords() {
        return validRecords;
    }

    public void setValidRecords(int validRecords) {
        this.validRecords = validRecords;
    }

    public String getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(String generatedDate) {
        this.generatedDate = generatedDate;
    }
}

