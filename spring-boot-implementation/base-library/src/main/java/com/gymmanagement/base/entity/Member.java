package com.gymmanagement.base.entity;

import java.io.Serializable;

/**
 * Member Entity
 * Represents a gym member, extends Person
 */
public class Member extends Person implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private double height;
    private double weight;
    private double bmi; // Body Mass index
    private Payment memberPayment;
    private String fitnessGoal;
    
    public Member() {
        super();
        height = 0.0;
        weight = 0.0;
        bmi = 0.0;
        fitnessGoal = null;
    }
    
    public Member(int regId, String name, String gmail, String phoneNum, String address, Date joinDate,
                  Date dateOfBirth, int age, String gender, double height, double weight, 
                  Payment memberPayment, String fitnessGoal) {
        super(regId, name, gmail, phoneNum, address, joinDate, dateOfBirth, age, gender);
        this.height = height;
        this.weight = weight;
        this.bmi = (weight / (height * height));
        this.memberPayment = memberPayment;
        this.fitnessGoal = fitnessGoal;
    }
    
    // Getters and Setters
    public double getHeight() {
        return height;
    }
    
    public void setHeight(double height) {
        this.height = height;
    }
    
    public double getWeight() {
        return weight;
    }
    
    public void setWeight(double weight) {
        this.weight = weight;
    }
    
    public double getBmi() {
        if (height > 0 && weight > 0) {
            return (weight / (height * height));
        }
        return bmi;
    }
    
    public Payment getPayment() {
        return memberPayment;
    }
    
    public void setPayment(Payment memberPayment) {
        this.memberPayment = memberPayment;
    }
    
    public String getFitnessGoal() {
        return fitnessGoal;
    }
    
    public void setFitnessGoal(String fitnessGoal) {
        this.fitnessGoal = fitnessGoal;
    }
    
    @Override
    public String getFinancialReport() {
        return "Name: " + name + "\nReg id: " + regId + "\nAccount details:\n" + 
               (memberPayment != null ? memberPayment.getOutstandingBalance() : "N/A");
    }
    
    // Validation methods
    public boolean validateHeight(String height) {
        if (!checkNumberIsdouble(height)) {
            return false;
        }
        double height1 = Double.parseDouble(height);
        if (height1 > 0 && height1 < 10) {
            return true;
        }
        return false;
    }
    
    public boolean validateWeight(String weight) {
        if (!checkNumberIsdouble(weight)) {
            return false;
        }
        double weight1 = Double.parseDouble(weight);
        if (weight1 > 0 && weight1 < 300) {
            return true;
        }
        return false;
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
        return "\n\t\tMember Details : " + super.toString() +
                "\n\n> Height : " + height + " feet\n\n> Weight : " + weight
                + " Kg\n\n> BMI (Body Mass Index) : " + getBmi() +
                "\n\n> Payment Status : " + (memberPayment != null ? memberPayment.checkStatus() : "N/A") +
                "\n\n> Outstanding Balance : " + (memberPayment != null ? memberPayment.getOutstandingBalance() : "N/A")
                + "\n\n> Fitness Goal : " + (fitnessGoal != null ? fitnessGoal : "N/A") +
                "\n---------------------------------------------------------------\n";
    }
}

