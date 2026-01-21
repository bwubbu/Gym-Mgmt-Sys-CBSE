package com.gymmanagement.osgi.base.entity;

// import java.time.LocalDate;
import java.io.Serializable;

/**
 * Member entity for OSGi Base Library Bundle
 */
public class Member extends Person implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Payment memberPayment;
    private String fitnessGoal;    
    private BodyStats bodyStats;
    private MemberPlan currentPlan;
    private Integer assignedTrainerId;
    
    public Member() {
        super();
        fitnessGoal = null;
        this.bodyStats = new BodyStats();
        this.memberPayment = new Payment();
        this.currentPlan = new MemberPlan();
        this.assignedTrainerId = null;
    }
    
    // public Member(int regId, String name, String gmail, String phoneNum, String address, 
    //               Date joinDate, Date dateOfBirth, int age, String gender, 
    //               double height, double weight, Payment memberPayment, String fitnessGoal) {
    //     super(regId, name, gmail, phoneNum, address, joinDate, dateOfBirth, age, gender);
    //     this.memberPayment = memberPayment;
    //     this.fitnessGoal = fitnessGoal;
    // }

    public Member(int regId, String name, String gmail, String phoneNum, String address, Date joinDate,
                  Date dateOfBirth, int age, String gender, double height, double weight, 
                  Payment memberPayment, String fitnessGoal, BodyStats bodyStats, 
                  MemberPlan currentPlan, Integer assignedTrainerId) { // Added to parameter list
        super(regId, name, gmail, phoneNum, address, joinDate, dateOfBirth, age, gender);
        this.memberPayment = memberPayment;
        this.fitnessGoal = fitnessGoal;
        this.bodyStats = bodyStats;
        this.currentPlan = currentPlan;
        this.assignedTrainerId = assignedTrainerId;
    }
    
    // public void setHeight(double height) {
    //     this.height = height;
    // }
    
    // public double getHeight() {
    //     return height;
    // }
    
    // public void setWeight(double weight) {
    //     this.weight = weight;
    // }
    
    // public double getWeight() {
    //     return weight;
    // }
    
    // public void setBmi(double bmi) {
    //     this.bmi = bmi;
    // }
    
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

    // Logic for BMI now pulls from the BodyStats object
    public double getBmi() {
        return (bodyStats != null) ? bodyStats.getBmi() : 0.0;
    }

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
        if (memberPayment != null) {
            return "\n\t\tFinancial Report for " + name + " (ID: " + regId + ")\n" +
                    memberPayment.toString();
        }
        return "\n\t\tNo payment information available for " + name + " (ID: " + regId + ")\n";
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
    //     return super.toString() + "\n\n> Height : " + height + " m\n\n> Weight : " + weight + 
    //             " kg\n\n> BMI : " + String.format("%.2f", getBmi()) + 
    //             "\n\n> Fitness Goal : " + fitnessGoal + 
    //             (memberPayment != null ? "\n" + memberPayment.toString() : "") +
    //             "\n---------------------------------------------------------------\n";
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