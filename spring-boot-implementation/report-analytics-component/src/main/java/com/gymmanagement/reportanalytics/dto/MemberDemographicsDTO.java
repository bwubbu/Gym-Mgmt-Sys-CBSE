package com.gymmanagement.reportanalytics.dto;

import java.util.Map;

/**
 * DTO for Member Demographics Report
 */
public class MemberDemographicsDTO {
    private int totalMembers;
    private Map<String, Integer> genderDistribution;
    private Map<String, Integer> ageGroupDistribution;
    private Map<String, Double> genderPercentages;
    private Map<String, Double> ageGroupPercentages;
    private String generatedDate;

    public MemberDemographicsDTO() {
    }

    public MemberDemographicsDTO(int totalMembers, Map<String, Integer> genderDistribution, 
                                  Map<String, Integer> ageGroupDistribution, 
                                  Map<String, Double> genderPercentages, 
                                  Map<String, Double> ageGroupPercentages, 
                                  String generatedDate) {
        this.totalMembers = totalMembers;
        this.genderDistribution = genderDistribution;
        this.ageGroupDistribution = ageGroupDistribution;
        this.genderPercentages = genderPercentages;
        this.ageGroupPercentages = ageGroupPercentages;
        this.generatedDate = generatedDate;
    }

    public int getTotalMembers() {
        return totalMembers;
    }

    public void setTotalMembers(int totalMembers) {
        this.totalMembers = totalMembers;
    }

    public Map<String, Integer> getGenderDistribution() {
        return genderDistribution;
    }

    public void setGenderDistribution(Map<String, Integer> genderDistribution) {
        this.genderDistribution = genderDistribution;
    }

    public Map<String, Integer> getAgeGroupDistribution() {
        return ageGroupDistribution;
    }

    public void setAgeGroupDistribution(Map<String, Integer> ageGroupDistribution) {
        this.ageGroupDistribution = ageGroupDistribution;
    }

    public Map<String, Double> getGenderPercentages() {
        return genderPercentages;
    }

    public void setGenderPercentages(Map<String, Double> genderPercentages) {
        this.genderPercentages = genderPercentages;
    }

    public Map<String, Double> getAgeGroupPercentages() {
        return ageGroupPercentages;
    }

    public void setAgeGroupPercentages(Map<String, Double> ageGroupPercentages) {
        this.ageGroupPercentages = ageGroupPercentages;
    }

    public String getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(String generatedDate) {
        this.generatedDate = generatedDate;
    }
}

