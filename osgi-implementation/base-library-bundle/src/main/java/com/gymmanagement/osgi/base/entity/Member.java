package com.gymmanagement.osgi.base.entity;

// import java.time.LocalDate;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Member entity for OSGi Base Library Bundle
 */
public class Member extends Person implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Payment memberPayment;
    private String fitnessGoal;    
    private BodyStats bodyStats;
    private MemberPlan currentPlan;
    private int assignedTrainerId;
    private List<BodyStats> statsHistory = new ArrayList<>(); // UC-4 History

    // Finance info (UC-1 Step 10 & UC-4)
    private double outstandingBalance;
    private String ccHolder;
    private String ccNumber;
    
    public Member() {
        super();
        fitnessGoal = null;
        this.bodyStats = new BodyStats();
        this.memberPayment = new Payment();
        this.currentPlan = new MemberPlan();
        this.assignedTrainerId = 0;
    }

    public Member(int regId, String name, String gmail, String phoneNum, String address, 
                Date joinDate, Date dateOfBirth, int age, String gender, double height, 
                double weight, Payment payment, String fitnessGoal, BodyStats bodyStats, 
                MemberPlan plan, Integer trainerId) {
        
        super(regId, name, gmail, phoneNum, address, joinDate, dateOfBirth, age, gender);
        
        this.memberPayment = payment;
        this.bodyStats = bodyStats;
        this.currentPlan = plan;
        this.assignedTrainerId = trainerId;
        this.fitnessGoal = fitnessGoal;

        // CRITICAL: Copy data to Member fields so viewProfile can see them
        if (payment != null) {
            this.ccHolder = payment.getCreditCardAccountHolder();
            this.ccNumber = payment.getCreditCardNum();
            this.outstandingBalance = payment.getOutstandingBalance();
        }
    }

    // Add this to Member.java in com.gymmanagement.osgi.base.entity
    public Member(int regId, String name, String gmail, String phoneNum, String address, 
                Date joinDate, Date dateOfBirth, int age, String gender, 
                double height, double weight, Payment memberPayment, String fitnessGoal) {
        
        // 1. Initialize parent Person fields
        super(regId, name, gmail, phoneNum, address, joinDate, dateOfBirth, age, gender);
        
        // 2. Wrap the height/weight into the new BodyStats object
        this.bodyStats = new BodyStats();
        this.bodyStats.setHeight(height);
        this.bodyStats.setWeight(weight);
        this.bodyStats.calculateBMI();
        
        // 3. Set remaining fields
        this.memberPayment = memberPayment;
        this.fitnessGoal = fitnessGoal;
        
        // 4. Initialize defaults for other OSGi-specific fields
        this.statsHistory = new ArrayList<>();
        this.currentPlan = new MemberPlan();
    }

    // UC-3 Logic: Save old stats to history before updating current
    public void recordNewStats(BodyStats newStats) {
        if (this.statsHistory == null) {
            this.statsHistory = new ArrayList<>();
        }

        // CREATE A SNAPSHOT: Copy the values into a NEW object
        // This stops the "all records are the same" bug
        BodyStats snapshot = new BodyStats(
            newStats.getHeight(), 
            newStats.getWeight(), 
            newStats.getBodyFatPercentage()
        );
        snapshot.calculateBMI();
        
        // Add the independent snapshot to history
        this.statsHistory.add(snapshot);
        
        // Update current stats field
        this.bodyStats = snapshot; 
    }

    public double getOutstandingBalance() { return outstandingBalance; }
    public void setOutstandingBalance(double bal) { this.outstandingBalance = bal; }
    public String getCcHolder() { return ccHolder; }
    public void setCcHolder(String holder) { this.ccHolder = holder; }
    public String getCcNumber() { return ccNumber; }
    public void setCcNumber(String num) { this.ccNumber = num; }

    public List<BodyStats> getStatsHistory() { return statsHistory; }
    
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

    public int getAssignedTrainerId() {
        return assignedTrainerId;
    }

    public void setAssignedTrainerId(int assignedTrainerId) {
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

    // Logic to update history (UC-3 & UC-4)
    public void addStatsToHistory(BodyStats stats) {
        this.statsHistory.add(stats);
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
                "\n\n> Assigned Trainer ID : " + (assignedTrainerId != 0 ? assignedTrainerId : "None")  +
                "\n\n> Accessible Machine IDs : " + machines +
                "\n---------------------------------------------------------------\n";
    }
}