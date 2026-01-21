package com.gymmanagement.osgi.base.entity;

import java.io.Serializable;
import java.util.List;

public class MemberPlan implements Serializable {
    private static final long serialVersionUID = 1L;
    private String planId;
    private String planName;
    private String duration; 
    private double price;
    private String description;
    private boolean personalTrainerIncluded;
    private boolean lockerAccess;
    private boolean accessAllMachines;
    private List<String> accessibleMachineIds;

    // Getters and Setters for all fields...
    public String getPlanId() { return planId; }
    public void setPlanId(String planId) { this.planId = planId; }
    public String getPlanName() { return planName; }
    public void setPlanName(String planName) { this.planName = planName; }
    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public boolean isPersonalTrainerIncluded() { return personalTrainerIncluded; }
    public void setPersonalTrainerIncluded(boolean val) { this.personalTrainerIncluded = val; }
    public boolean isLockerAccess() { return lockerAccess; }
    public void setLockerAccess(boolean val) { this.lockerAccess = val; }
    public boolean isAccessAllMachines() { return accessAllMachines; }
    public void setAccessAllMachines(boolean val) { this.accessAllMachines = val; }
    public List<String> getAccessibleMachineIds() { return accessibleMachineIds; }
    public void setAccessibleMachineIds(List<String> ids) { this.accessibleMachineIds = ids; }
}