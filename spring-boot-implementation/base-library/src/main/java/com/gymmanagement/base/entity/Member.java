package com.gymmanagement.base.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Member Entity
 * Represents a gym member, extends Person
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Member extends Person implements Serializable {
    private static final long serialVersionUID = 1L;

    private Payment memberPayment;
    private String fitnessGoal;    
    private BodyStats bodyStats;
    private MemberPlan currentPlan;
    private Integer assignedTrainerId; // Added for UC-1 Step 9.2
    
    public Member() {
        super();
        // height = 0.0;
        // weight = 0.0;
        // bmi = 0.0;
        fitnessGoal = null;
        this.bodyStats = new BodyStats();
        this.memberPayment = new Payment();
        this.currentPlan = new MemberPlan();
        this.assignedTrainerId = null;
    }
    
    public Member(int regId, String name, String gmail, String phoneNum, String address, Date joinDate,
                  Date dateOfBirth, int age, String gender, double height, double weight, 
                  Payment memberPayment, String fitnessGoal, BodyStats bodyStats, 
                  MemberPlan currentPlan, Integer assignedTrainerId) { // Added to parameter list
        super(regId, name, gmail, phoneNum, address, joinDate, dateOfBirth, age, gender);
        // this.height = height;
        // this.weight = weight;
        // this.bmi = (weight / (height * height));
        this.memberPayment = memberPayment;
        this.fitnessGoal = fitnessGoal;
        this.bodyStats = bodyStats;
        this.currentPlan = currentPlan;
        this.assignedTrainerId = assignedTrainerId;
    }
    
    // Getters and Setters
    // public double getHeight() {
    //     return height;
    // }
    
    // public void setHeight(double height) {
    //     this.height = height;
    // }
    
    // public double getWeight() {
    //     return weight;
    // }
    
    // public void setWeight(double weight) {
    //     this.weight = weight;
    // }

    // public void setBmi(double bmi) {
    //     this.bmi = bmi;
    // }
    
    // public double getBmi() {
    //     if (height > 0 && weight > 0) {
    //         return (weight / (height * height));
    //     }
    //     return bmi;
    // }
    
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

    // Logic for BMI now pulls from the BodyStats object
    public double getBmi() {
        return (bodyStats != null) ? bodyStats.getBmi() : 0.0;
    }

    // public Date getDateOfJoin() { 
    //     return dateOfJoin; 
    // }

    // public void setDateOfJoin(Date dateOfJoin) {
    //     this.dateOfJoin = dateOfJoin;
    // }

    // public Date getDateOfBirth() { 
    //     return dateOfBirth; 
    // }

    // public void setDateOfBirth(Date dateOfBirth) {
    //     this.dateOfBirth = dateOfBirth;
    // }

    public void setDateOfJoin(Date date) { this.joinDate = date; }
    public Date getDateOfJoin() { return this.joinDate; }
    
    @Override
    public void setDateOfBirth(Date date) { this.dateOfBirth = date; }
    @Override
    public Date getDateOfBirth() { return this.dateOfBirth; }

    public BodyStats getBodyStats() {
        return bodyStats;
    }

    public void setBodyStats(BodyStats bodyStats) {
        this.bodyStats = bodyStats;
    }

    public MemberPlan getCurrentPlan() {
        return currentPlan;
    }

    public void setCurrentPlan(MemberPlan currentPlan) {
        this.currentPlan = currentPlan;
    }

    public Integer getAssignedTrainerId() {
        return assignedTrainerId;
    }

    public void setAssignedTrainerId(Integer assignedTrainerId) {
        this.assignedTrainerId = assignedTrainerId;
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

    // @Override
    // public String toString() {
    //     return super.toString() + 
    //         "\n> Fitness Goal : " + (fitnessGoal != null ? fitnessGoal : "N/A") +
    //         "\n> BMI          : " + String.format("%.2f", getBmi()) +
    //         "\n> Current Plan : " + (currentPlan != null ? currentPlan.getPlanName() : "No Plan");
    // }
    
    @Override
    public String toString() {
        String planInfo = (currentPlan != null) ? currentPlan.getPlanName() : "No Plan";
        String machines = "N/A";
        
        if (currentPlan != null) {
            machines = currentPlan.isAccessAllMachines() ? "All Machines" : 
                (currentPlan.getAccessibleMachineIds() != null ? String.join(", ", currentPlan.getAccessibleMachineIds()) : "None");
        }
        return "\n\t\tMember Details : " + super.toString() +
                "\n\n> Height : " + (bodyStats != null ? bodyStats.getHeight() : 0.0) 
                + " m\n\n> Weight : " + (bodyStats != null ? bodyStats.getWeight() : 0.0) 
                + " Kg\n\n> BMI (Body Mass Index) : " + getBmi() +
                "\n\n> Payment Status : " + (memberPayment != null ? memberPayment.checkStatus() : "N/A") +
                "\n\n> Outstanding Balance : " + (memberPayment != null ? memberPayment.getOutstandingBalance() : "N/A")
                + "\n\n> Fitness Goal : " + (fitnessGoal != null ? fitnessGoal : "N/A") +
                "\n\n> Current Plan : " + planInfo +
                "\n\n> Assigned Trainer ID : " + (assignedTrainerId != null ? assignedTrainerId : "None") +
                "\n\n> Accessible Machine IDs : " + machines +
                "\n---------------------------------------------------------------\n";
    }
}