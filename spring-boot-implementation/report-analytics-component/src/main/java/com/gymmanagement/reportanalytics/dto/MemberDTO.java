package com.gymmanagement.reportanalytics.dto;

import java.time.LocalDate;

/**
 * DTO for Member data (from Base Library/Member Management Component)
 */
public class MemberDTO {
    private int regId;
    private String name;
    private String email;
    private String phoneNum;
    private String address;
    private LocalDate joinDate;
    private LocalDate dateOfBirth;
    private int age;
    private String gender;
    private double height;
    private double weight;
    private double bmi;
    private String fitnessGoal;
    private PaymentDTO payment;

    public MemberDTO() {
    }

    public MemberDTO(int regId, String name, String email, String phoneNum, String address,
                    LocalDate joinDate, LocalDate dateOfBirth, int age, String gender,
                    double height, double weight, double bmi, String fitnessGoal, PaymentDTO payment) {
        this.regId = regId;
        this.name = name;
        this.email = email;
        this.phoneNum = phoneNum;
        this.address = address;
        this.joinDate = joinDate;
        this.dateOfBirth = dateOfBirth;
        this.age = age;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.bmi = bmi;
        this.fitnessGoal = fitnessGoal;
        this.payment = payment;
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

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public String getFitnessGoal() {
        return fitnessGoal;
    }

    public void setFitnessGoal(String fitnessGoal) {
        this.fitnessGoal = fitnessGoal;
    }

    public PaymentDTO getPayment() {
        return payment;
    }

    public void setPayment(PaymentDTO payment) {
        this.payment = payment;
    }
}

