package com.gymmanagement.reportanalytics.dto;

import java.util.Map;

/**
 * DTO for Growth Trends Report
 */
public class GrowthTrendsDTO {
    private Map<String, Integer> monthlyRegistrations;
    private Map<Integer, Integer> yearlyRegistrations;
    private Double growthRate;
    private String growthRatePeriod;
    private String generatedDate;

    public GrowthTrendsDTO() {
    }

    public GrowthTrendsDTO(Map<String, Integer> monthlyRegistrations,
                          Map<Integer, Integer> yearlyRegistrations,
                          Double growthRate, String growthRatePeriod, String generatedDate) {
        this.monthlyRegistrations = monthlyRegistrations;
        this.yearlyRegistrations = yearlyRegistrations;
        this.growthRate = growthRate;
        this.growthRatePeriod = growthRatePeriod;
        this.generatedDate = generatedDate;
    }

    public Map<String, Integer> getMonthlyRegistrations() {
        return monthlyRegistrations;
    }

    public void setMonthlyRegistrations(Map<String, Integer> monthlyRegistrations) {
        this.monthlyRegistrations = monthlyRegistrations;
    }

    public Map<Integer, Integer> getYearlyRegistrations() {
        return yearlyRegistrations;
    }

    public void setYearlyRegistrations(Map<Integer, Integer> yearlyRegistrations) {
        this.yearlyRegistrations = yearlyRegistrations;
    }

    public Double getGrowthRate() {
        return growthRate;
    }

    public void setGrowthRate(Double growthRate) {
        this.growthRate = growthRate;
    }

    public String getGrowthRatePeriod() {
        return growthRatePeriod;
    }

    public void setGrowthRatePeriod(String growthRatePeriod) {
        this.growthRatePeriod = growthRatePeriod;
    }

    public String getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(String generatedDate) {
        this.generatedDate = generatedDate;
    }
}

