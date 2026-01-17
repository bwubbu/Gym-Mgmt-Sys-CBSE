package com.gymmanagement.trainer.base;

import com.gymmanagement.trainer.entity.Trainer;
import java.util.List;

/**
 * ITrainerService Interface
 * 
 * Defines the contract for the Trainer Management Component.
 */
public interface ITrainerService {
    
    // CRUD Operations
    Trainer createTrainer(Trainer trainer);
    Trainer getTrainer(int id);
    List<Trainer> getAllTrainers();
    Trainer updateTrainer(int id, Trainer updatedTrainer);
    void deleteTrainer(int id);

    // Business Logic
    void assignMemberToTrainer(int trainerId, int memberId);
    Trainer updatePerformance(int trainerId, double rating, boolean attended);
}
