package com.gymmanagement.reportanalytics.dto;

import java.util.Map;

/**
 * DTO for Fitness Goal Distribution Report
 */
public class FitnessGoalDistributionDTO {
    private int totalMembers;
    private int membersWithGoals;
    private Map<String, Integer> goalDistribution;
    private Map<String, Double> goalPercentages;
    private String generatedDate;

    public FitnessGoalDistributionDTO() {
    }

    public FitnessGoalDistributionDTO(int totalMembers, int membersWithGoals,
                                      Map<String, Integer> goalDistribution,
                                      Map<String, Double> goalPercentages,
                                      String generatedDate) {
        this.totalMembers = totalMembers;
        this.membersWithGoals = membersWithGoals;
        this.goalDistribution = goalDistribution;
        this.goalPercentages = goalPercentages;
        this.generatedDate = generatedDate;
    }

    public int getTotalMembers() {
        return totalMembers;
    }

    public void setTotalMembers(int totalMembers) {
        this.totalMembers = totalMembers;
    }

    public int getMembersWithGoals() {
        return membersWithGoals;
    }

    public void setMembersWithGoals(int membersWithGoals) {
        this.membersWithGoals = membersWithGoals;
    }

    public Map<String, Integer> getGoalDistribution() {
        return goalDistribution;
    }

    public void setGoalDistribution(Map<String, Integer> goalDistribution) {
        this.goalDistribution = goalDistribution;
    }

    public Map<String, Double> getGoalPercentages() {
        return goalPercentages;
    }

    public void setGoalPercentages(Map<String, Double> goalPercentages) {
        this.goalPercentages = goalPercentages;
    }

    public String getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(String generatedDate) {
        this.generatedDate = generatedDate;
    }
}

