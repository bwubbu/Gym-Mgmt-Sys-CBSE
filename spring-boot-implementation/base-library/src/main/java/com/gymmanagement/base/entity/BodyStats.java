package com.gymmanagement.base.entity;
import java.io.Serializable;
import java.time.LocalDate;

public class BodyStats implements Serializable {
    private static final long serialVersionUID = 1L;
    private double height; // in m
    private double weight; // in kg
    private double bmi;
    private double bodyFatPercentage;
    private LocalDate recordDate;

    public BodyStats() {}

    public double getHeight() { return height; }
    // public void setHeight(double height) { this.height = height; }
    public double getWeight() { return weight; }
    // public void setWeight(double weight) { this.weight = weight; }
    public double getBmi() { return bmi; }
    public void setBmi(double bmi) { this.bmi = bmi; }
    public double getBodyFatPercentage() { return bodyFatPercentage; }
    public void setBodyFatPercentage(double bodyFatPercentage) { this.bodyFatPercentage = bodyFatPercentage; }

    public final void calculateBMI() {
        if (height > 0) {
            // Formula: weight (kg) / [height (m)]^2
            this.bmi = weight / (height * height);
        }
    }

    public void setHeight(double height) { 
        this.height = height; 
        calculateBMI(); 
    }
    
    public void setWeight(double weight) { 
        this.weight = weight; 
        calculateBMI(); 
    }
    
    public LocalDate getRecordDate() { return recordDate; }
}