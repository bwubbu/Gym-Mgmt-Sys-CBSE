package com.gymmanagement.osgi.base.entity;

import java.io.Serializable;
import java.time.LocalDate;

public class BodyStats implements Serializable {
    private static final long serialVersionUID = 1L;
    private double height; // in m
    private double weight; // in kg
    private double bodyFatPercentage;
    private double bmi;
    private LocalDate recordDate;

    public BodyStats() {}

    public double getHeight() { return height; }
    public double getWeight() { return weight; }
    public double getBmi() { return bmi; }
    public void setBmi(double bmi) { this.bmi = bmi; }
    public double getBodyFatPercentage() { return bodyFatPercentage; }
    public void setBodyFatPercentage(double bodyFatPercentage) { this.bodyFatPercentage = bodyFatPercentage; }

    public BodyStats(double height, double weight, double bodyFatPercentage) {
        this.height = height;
        this.weight = weight;
        this.bodyFatPercentage = bodyFatPercentage;
        this.recordDate = LocalDate.now();
        calculateBMI();
    }

    private void calculateBMI() {
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

    // Getters...
    public LocalDate getRecordDate() { return recordDate; }
}