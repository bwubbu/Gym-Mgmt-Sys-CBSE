package com.gymmanagement.osgi.base.entity;

import java.io.Serializable;

/**
 * Trainer entity for OSGi Base Library Bundle
 */
public class Trainer extends Person implements Serializable {
    private String specialization;
    private double hourlyRate;
    private double weeklyWorkingHours;
    private String experienceLevel;
    private static final long serialVersionUID = 2286502415981060413L;

    // --- Performance Tracking Fields ---
    private java.util.List<Integer> assignedMemberIds = new java.util.ArrayList<>();
    private double totalRating;
    private int ratingCount;
    private int attendanceDays;
    
    public Trainer() {
        super();
        specialization = null;
        hourlyRate = 0.0;
        weeklyWorkingHours = 0.0;
        experienceLevel = null;
    }
    
    public Trainer(int regId, String name, String gmail, String phoneNum, String address, 
                   Date joinDate, Date dateOfBirth, int age, String gender, 
                   String specialization, double hourlyRate, double weeklyWorkingHours, String experienceLevel) {
        super(regId, name, gmail, phoneNum, address, joinDate, dateOfBirth, age, gender);
        this.specialization = specialization;
        this.hourlyRate = hourlyRate;
        this.weeklyWorkingHours = weeklyWorkingHours;
        this.experienceLevel = experienceLevel;
    }
    
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
    
    public String getSpecialization() {
        return specialization;
    }
    
    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }
    
    public double getHourlyRate() {
        return hourlyRate;
    }
    
    public void setWeeklyWorkingHours(double weeklyWorkingHours) {
        this.weeklyWorkingHours = weeklyWorkingHours;
    }
    
    public double getWeeklyWorkingHours() {
        return weeklyWorkingHours;
    }
    
    public void setExperienceLevel(String experienceLevel) {
        this.experienceLevel = experienceLevel;
    }
    
    public String getExperienceLevel() {
        return experienceLevel;
    }

    public java.util.List<Integer> getAssignedMemberIds() {
        return assignedMemberIds;
    }

    public double getTotalRating() {
        return totalRating;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public int getAttendanceDays() {
        return attendanceDays;
    }

    public void setTotalRating(double totalRating) {
        this.totalRating = totalRating;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    public void setAttendanceDays(int attendanceDays) {
        this.attendanceDays = attendanceDays;
    }

    public double getAverageRating() {
        if (ratingCount == 0) return 0.0;
        return totalRating / ratingCount;
    }
    
    public double calculSalary() {
        return weeklyWorkingHours * hourlyRate;
    }
    
    @Override
    public String getFinancialReport() {
        return "\n\t\tFinancial Report for Trainer " + name + " (ID: " + regId + ")\n" +
                "\n> Specialization : " + specialization +
                "\n> Hourly Rate : $" + hourlyRate +
                "\n> Weekly Working Hours : " + weeklyWorkingHours +
                "\n> Weekly Salary : $" + String.format("%.2f", calculSalary()) +
                "\n> Experience Level : " + experienceLevel +
                "\n---------------------------------------------------------------\n";
    }
    
    @Override
    public String toString() {
        return super.toString() + "\n\n> Specialization : " + specialization +
                "\n\n> Hourly Rate : $" + hourlyRate +
                "\n\n> Weekly Working Hours : " + weeklyWorkingHours +
                "\n\n> Weekly Salary : $" + String.format("%.2f", calculSalary()) +
                "\n\n> Experience Level : " + experienceLevel +
                "\n---------------------------------------------------------------\n";
    }
}

