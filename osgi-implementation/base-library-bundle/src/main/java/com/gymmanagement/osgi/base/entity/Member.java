package com.gymmanagement.osgi.base.entity;

import java.io.Serializable;

/**
 * Member entity for OSGi Base Library Bundle
 */
public class Member extends Person implements Serializable {
    private double height;
    private double weight;
    private double bmi;
    private Payment memberPayment;
    private String fitnessGoal;
    
    public Member() {
        super();
        height = 0.0;
        weight = 0.0;
        bmi = 0.0;
        fitnessGoal = null;
    }
    
    public Member(int regId, String name, String gmail, String phoneNum, String address, 
                  Date joinDate, Date dateOfBirth, int age, String gender, 
                  double height, double weight, Payment memberPayment, String fitnessGoal) {
        super(regId, name, gmail, phoneNum, address, joinDate, dateOfBirth, age, gender);
        this.height = height;
        this.weight = weight;
        this.bmi = (weight / (height * height));
        this.memberPayment = memberPayment;
        this.fitnessGoal = fitnessGoal;
    }
    
    public void setHeight(double height) {
        this.height = height;
    }
    
    public double getHeight() {
        return height;
    }
    
    public void setWeight(double weight) {
        this.weight = weight;
    }
    
    public double getWeight() {
        return weight;
    }
    
    public double getBmi() {
        if (height > 0) {
            return (weight / (height * height));
        }
        return bmi;
    }
    
    public void setBmi(double bmi) {
        this.bmi = bmi;
    }
    
    public void setPayment(Payment memberPayment) {
        this.memberPayment = memberPayment;
    }
    
    public Payment getPayment() {
        return memberPayment;
    }
    
    public void setFitnessGoal(String fitnessGoal) {
        this.fitnessGoal = fitnessGoal;
    }
    
    public String getFitnessGoal() {
        return fitnessGoal;
    }
    
    @Override
    public String getFinancialReport() {
        if (memberPayment != null) {
            return "\n\t\tFinancial Report for " + name + " (ID: " + regId + ")\n" +
                    memberPayment.toString();
        }
        return "\n\t\tNo payment information available for " + name + " (ID: " + regId + ")\n";
    }
    
    @Override
    public String toString() {
        return super.toString() + "\n\n> Height : " + height + " m\n\n> Weight : " + weight + 
                " kg\n\n> BMI : " + String.format("%.2f", getBmi()) + 
                "\n\n> Fitness Goal : " + fitnessGoal + 
                (memberPayment != null ? "\n" + memberPayment.toString() : "") +
                "\n---------------------------------------------------------------\n";
    }
}

