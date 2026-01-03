package com.gymmanagement.reportanalytics.dto;

import java.util.List;

/**
 * DTO for Machine data (from Base Library/Equipment Booking Component)
 */
public class MachineDTO {
    private int regId;
    private String name;
    private String brand;
    private String model;
    private double maxWeightCapacity;
    private double machineWeight;
    private String type;
    private List<MemberDTO> bookings; // List of members who booked this machine

    public MachineDTO() {
    }

    public MachineDTO(int regId, String name, String brand, String model,
                     double maxWeightCapacity, double machineWeight, String type,
                     List<MemberDTO> bookings) {
        this.regId = regId;
        this.name = name;
        this.brand = brand;
        this.model = model;
        this.maxWeightCapacity = maxWeightCapacity;
        this.machineWeight = machineWeight;
        this.type = type;
        this.bookings = bookings;
    }

    public int getRegId() {
        return regId;
    }

    public void setRegId(int regId) {
        this.regId = regId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getMaxWeightCapacity() {
        return maxWeightCapacity;
    }

    public void setMaxWeightCapacity(double maxWeightCapacity) {
        this.maxWeightCapacity = maxWeightCapacity;
    }

    public double getMachineWeight() {
        return machineWeight;
    }

    public void setMachineWeight(double machineWeight) {
        this.machineWeight = machineWeight;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<MemberDTO> getBookings() {
        return bookings;
    }

    public void setBookings(List<MemberDTO> bookings) {
        this.bookings = bookings;
    }
}

