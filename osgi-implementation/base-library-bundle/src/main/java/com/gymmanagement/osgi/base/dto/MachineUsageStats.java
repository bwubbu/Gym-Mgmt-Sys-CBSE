package com.gymmanagement.osgi.base.dto;

import java.io.Serializable;

public class MachineUsageStats implements Serializable {
    private static final long serialVersionUID = 1L;

    private int machineId;
    private String machineName;
    private int totalSlots;
    private int bookedSlots;
    private double utilizationPercentage;

    public MachineUsageStats() {
    }

    public MachineUsageStats(int machineId, String machineName, int totalSlots, int bookedSlots) {
        this.machineId = machineId;
        this.machineName = machineName;
        this.totalSlots = totalSlots;
        this.bookedSlots = bookedSlots;
        if (totalSlots > 0) {
            this.utilizationPercentage = (double) bookedSlots / totalSlots * 100.0;
        } else {
            this.utilizationPercentage = 0.0;
        }
    }

    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public int getTotalSlots() {
        return totalSlots;
    }

    public void setTotalSlots(int totalSlots) {
        this.totalSlots = totalSlots;
    }

    public int getBookedSlots() {
        return bookedSlots;
    }

    public void setBookedSlots(int bookedSlots) {
        this.bookedSlots = bookedSlots;
    }

    public double getUtilizationPercentage() {
        return utilizationPercentage;
    }

    public void setUtilizationPercentage(double utilizationPercentage) {
        this.utilizationPercentage = utilizationPercentage;
    }

    @Override
    public String toString() {
        return "Machine: " + machineName + " (ID:" + machineId + ") - " +
                String.format("%.1f%%", utilizationPercentage) + " in use (" +
                bookedSlots + "/" + totalSlots + ")";
    }
}
