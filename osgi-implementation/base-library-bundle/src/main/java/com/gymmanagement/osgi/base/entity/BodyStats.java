package com.gymmanagement.osgi.base.entity;

import java.io.Serializable;
import java.time.LocalDate;

public class BodyStats implements Serializable {
    private static final long serialVersionUID = 1L;
    private double height; // in m
    private double weight; // in kg
    private double bodyFatPercentage;
    private double bmi;
    private String recordDate;

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
        calculateBMI();
        // Automatically set to current date
        this.recordDate = java.time.LocalDate.now().toString();
    }

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

    public String getRecordDate() { return recordDate; }
}