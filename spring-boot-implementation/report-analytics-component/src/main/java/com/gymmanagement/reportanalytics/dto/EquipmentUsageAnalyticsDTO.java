package com.gymmanagement.reportanalytics.dto;

import java.util.Map;

/**
 * DTO for Equipment Usage Analytics Report
 */
public class EquipmentUsageAnalyticsDTO {
    private int totalMachines;
    private int totalCapacity;
    private int totalBookings;
    private double averageUtilizationRate;
    private int machinesWithBookings;
    private int machinesFullyBooked;
    private int machinesUnused;
    private Map<String, Integer> machineBookingCount;
    private Map<String, Double> machineUtilization;
    private Map<String, Double> capacityDistribution;
    private String generatedDate;

    public EquipmentUsageAnalyticsDTO() {
    }

    public EquipmentUsageAnalyticsDTO(int totalMachines, int totalCapacity, int totalBookings,
                                      double averageUtilizationRate, int machinesWithBookings,
                                      int machinesFullyBooked, int machinesUnused,
                                      Map<String, Integer> machineBookingCount,
                                      Map<String, Double> machineUtilization,
                                      Map<String, Double> capacityDistribution,
                                      String generatedDate) {
        this.totalMachines = totalMachines;
        this.totalCapacity = totalCapacity;
        this.totalBookings = totalBookings;
        this.averageUtilizationRate = averageUtilizationRate;
        this.machinesWithBookings = machinesWithBookings;
        this.machinesFullyBooked = machinesFullyBooked;
        this.machinesUnused = machinesUnused;
        this.machineBookingCount = machineBookingCount;
        this.machineUtilization = machineUtilization;
        this.capacityDistribution = capacityDistribution;
        this.generatedDate = generatedDate;
    }

    public int getTotalMachines() {
        return totalMachines;
    }

    public void setTotalMachines(int totalMachines) {
        this.totalMachines = totalMachines;
    }

    public int getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalCapacity(int totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public int getTotalBookings() {
        return totalBookings;
    }

    public void setTotalBookings(int totalBookings) {
        this.totalBookings = totalBookings;
    }

    public double getAverageUtilizationRate() {
        return averageUtilizationRate;
    }

    public void setAverageUtilizationRate(double averageUtilizationRate) {
        this.averageUtilizationRate = averageUtilizationRate;
    }

    public int getMachinesWithBookings() {
        return machinesWithBookings;
    }

    public void setMachinesWithBookings(int machinesWithBookings) {
        this.machinesWithBookings = machinesWithBookings;
    }

    public int getMachinesFullyBooked() {
        return machinesFullyBooked;
    }

    public void setMachinesFullyBooked(int machinesFullyBooked) {
        this.machinesFullyBooked = machinesFullyBooked;
    }

    public int getMachinesUnused() {
        return machinesUnused;
    }

    public void setMachinesUnused(int machinesUnused) {
        this.machinesUnused = machinesUnused;
    }

    public Map<String, Integer> getMachineBookingCount() {
        return machineBookingCount;
    }

    public void setMachineBookingCount(Map<String, Integer> machineBookingCount) {
        this.machineBookingCount = machineBookingCount;
    }

    public Map<String, Double> getMachineUtilization() {
        return machineUtilization;
    }

    public void setMachineUtilization(Map<String, Double> machineUtilization) {
        this.machineUtilization = machineUtilization;
    }

    public Map<String, Double> getCapacityDistribution() {
        return capacityDistribution;
    }

    public void setCapacityDistribution(Map<String, Double> capacityDistribution) {
        this.capacityDistribution = capacityDistribution;
    }

    public String getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(String generatedDate) {
        this.generatedDate = generatedDate;
    }
}

