package com.gymmanagement.trainer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trainers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer regId; // Use Integer for JPA compatibility, though int in legacy

    // --- Fields from Person ---
    @Column(nullable = false)
    private String name;

    private String gmail;

    private String phoneNum;

    private String address;

    private LocalDate joinDate;

    private LocalDate dateOfBirth;

    private int age;

    private String gender;

    // --- Fields from Trainer ---
    private String specialization;

    private double hourlyRate;

    private double weeklyWorkingHours;

    private String experienceLevel;

    // --- New Fields ---
    
    @ElementCollection
    @CollectionTable(name = "trainer_assigned_members", joinColumns = @JoinColumn(name = "trainer_id"))
    @Column(name = "member_id")
    @Builder.Default
    private List<Integer> assignedMemberIds = new ArrayList<>();

    private double totalRating;

    private int ratingCount;

    private int attendanceDays;
    
    // Method to calculate average rating
    public double getAverageRating() {
        if (ratingCount == 0) return 0.0;
        return totalRating / ratingCount;
    }
}
