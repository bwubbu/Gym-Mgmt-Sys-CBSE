package com.gymmanagement.reportanalytics.dto;

import java.time.LocalDate;

/**
 * DTO for Trainer data (from Base Library/Trainer Management Component)
 */
public class TrainerDTO {
    private int regId;
    private String name;
    private String email;
    private String phoneNum;
    private String address;
    private LocalDate joinDate;
    private LocalDate dateOfBirth;
    private int age;
    private String gender;
    private String specialization;
    private double hourlyRate;

    public TrainerDTO() {
    }

    public TrainerDTO(int regId, String name, String email, String phoneNum, String address,
                     LocalDate joinDate, LocalDate dateOfBirth, int age, String gender,
                     String specialization, double hourlyRate) {
        this.regId = regId;
        this.name = name;
        this.email = email;
        this.phoneNum = phoneNum;
        this.address = address;
        this.joinDate = joinDate;
        this.dateOfBirth = dateOfBirth;
        this.age = age;
        this.gender = gender;
        this.specialization = specialization;
        this.hourlyRate = hourlyRate;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }
}

