package com.gymmanagement.base.entity;

import java.io.Serializable;

/**
 * Trainer Entity
 * Represents a gym trainer, extends Person
 */
public class Trainer extends Person implements Serializable {
    private static final long serialVersionUID = 2286502415981060413L;
    
    private String specialization;
    private double hourlyRate;
    private double weeklyWorkingHours;
    private String experienceLevel;
    
    public Trainer() {
        super();
        specialization = null;
        hourlyRate = 0.0;
        weeklyWorkingHours = 0.0;
        experienceLevel = null;
    }
    
    public Trainer(int regId, String name, String gmail, String phoneNum, String address, Date joinDate, 
                   Date dateOfBirth, int age, String gender, String specialization, double hourlyRate, 
                   double weeklyWorkingHours, String experienceLevel) {
        super(regId, name, gmail, phoneNum, address, joinDate, dateOfBirth, age, gender);
        this.specialization = specialization;
        this.hourlyRate = hourlyRate;
        this.weeklyWorkingHours = weeklyWorkingHours;
        this.experienceLevel = experienceLevel;
    }
    
    // Getters and Setters
    public int getRegId() { return this.regId; }
    public void setRegId(int regId) { this.regId = regId; }

    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    public String getGender() { return this.gender; }
    public void setGender(String gender) { this.gender = gender; }
    
    public String getSpecialization() {
        return specialization;
    }
    
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
    
    public double getHourlyRate() {
        return hourlyRate;
    }
    
    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }
    
    public double getWeeklyWorkingHours() {
        return weeklyWorkingHours;
    }
    
    public void setWeeklyWorkingHours(double weeklyWorkingHours) {
        this.weeklyWorkingHours = weeklyWorkingHours;
    }
    
    public String getExperienceLevel() {
        return experienceLevel;
    }
    
    public void setExperienceLevel(String experienceLevel) {
        this.experienceLevel = experienceLevel;
    }
    
    // Business methods
    public double calculSalary() {
        return weeklyWorkingHours * hourlyRate;
    }
    
    @Override
    public String getFinancialReport() {
        return "Name: " + name + "\nReg id: " + regId + "\nWeekly Salary:\n" + calculSalary();
    }
    
    // Validation methods
    public boolean validateWeeklyWorkingHours(String hours) {
        if (!checkNumberIsdouble(hours)) {
            return false;
        }
        double number = Double.parseDouble(hours);
        if (number < 0.0 || number > 168.0) {
            return false;
        }
        return true;
    }
    
    public boolean validateHourlyRate(String rate) {
        if (!checkNumberIsdouble(rate)) {
            return false;
        }
        double rate1 = Double.parseDouble(rate);
        if (rate1 < 0.0) {
            return false;
        }
        return true;
    }
    
    public boolean checkNumberIsdouble(String number) {
        try {
            Double.parseDouble(number);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "\n\t\tTrainer Details : " + super.toString() + "\n\n> Specialization : " + specialization + 
               "\n\n> Hourly Rate : " + hourlyRate
                + "\n\n> Weekly Working Hours : " + weeklyWorkingHours + "\n\n> Salary : " + calculSalary() + " $" + 
               "\n\n> Experience Level : " + experienceLevel
                + "\n---------------------------------------------------------------\n";
    }
}

