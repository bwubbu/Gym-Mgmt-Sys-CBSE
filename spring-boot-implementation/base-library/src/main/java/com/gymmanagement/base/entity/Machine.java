package com.gymmanagement.base.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Machine Entity
 * Represents gym equipment/machine
 * Implements GymMachine interface for booking functionality
 */
public class Machine implements Serializable, GymMachine {
    private static final long serialVersionUID = -7664452136969625334L;
    
    private int regId;
    private String name;
    private String brand;
    private String model;
    private double maxWeightCapacity;
    private double machineWeight;
    private String type;
    private Member[] bookings = new Member[8]; // Maximum 8 bookings per machine per day
    
    public Machine() {
        regId = 0;
        name = null;
        brand = null;
        model = null;
        maxWeightCapacity = 0.0;
        machineWeight = 0.0;
        type = null;
        for (int i = 0; i < bookings.length; i++) {
            bookings[i] = null;
        }
    }
    
    public Machine(int regId, String name, String brand, String model, double maxWeightCapacity, 
                   double machineWeight, String type) {
        this.regId = regId;
        this.name = name;
        this.brand = brand;
        this.model = model;
        this.maxWeightCapacity = maxWeightCapacity;
        this.machineWeight = machineWeight;
        this.type = type;
        for (int i = 0; i < bookings.length; i++) {
            bookings[i] = null;
        }
    }
    
    // Getters and Setters
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
    
    public Member[] getBookings() {
        return bookings;
    }
    
    public void setBookings(Member[] bookings) {
        this.bookings = bookings;
    }
    
    // Get bookings as List (helper method)
    public List<Member> getBookingsList() {
        List<Member> bookingList = new ArrayList<>();
        for (Member booking : bookings) {
            if (booking != null) {
                bookingList.add(booking);
            }
        }
        return bookingList;
    }
    
    // GymMachine interface implementation
    @Override
    public String bookMachine(Member member) {
        if (member.getPayment() != null && member.getPayment().getOutstandingBalance() != 0.0) {
            return "Sadly Can't book this machine because you have outstanding balance";
        }
        for (int i = 0; i < bookings.length; i++) {
            if (bookings[i] != null && bookings[i].equals(member)) {
                return "You already book this machine";
            }
        }
        for (int i = 0; i < bookings.length; i++) {
            if (bookings[i] == null) {
                bookings[i] = member;
                return "booked";
            }
        }
        return "The machine is completely booked.";
    }
    
    @Override
    public String cancelBooking(int regId) {
        for (int i = 0; i < bookings.length; i++) {
            if (bookings[i] != null) {
                if (bookings[i].getRegId() == regId) {
                    bookings[i] = null;
                    return "Your Booking canceled";
                }
            }
        }
        return "No booking found against the given member reg id " + regId;
    }
    
    @Override
    public String getAllBookings() {
        String getAllBookingsString = "";
        for (int i = 0; i < bookings.length; i++) {
            if (bookings[i] != null) {
                getAllBookingsString += "> " + name + "\t\t" + bookings[i].getName() + " ( " + bookings[i].getRegId() + " )\n";
            }
        }
        return getAllBookingsString;
    }
    
    @Override
    public String toString() {
        return "\n\t\tMachine Details : " + "\n---------------------------------------------------------------\n"
                + "\n\n> Registration Id : " + regId + "\n\n> Name : " + name + "\n\n> Brand : " + brand + "\n\n> Model : " + model
                + "\n\n> Maximum Weight Capacity : " + maxWeightCapacity + " Kg\n\n> Machine Weight : " + machineWeight + " Kg\n\n> Machine Type : "
                + type + "\n---------------------------------------------------------------\n";
    }
}

