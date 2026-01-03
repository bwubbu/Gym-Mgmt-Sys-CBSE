package com.gymmanagement.base.service;

import com.gymmanagement.base.entity.Trainer;
import java.util.List;

/**
 * ITrainerService Interface
 * 
 * Defines the contract for Trainer Management operations.
 * This interface should be implemented by the Trainer Management Component.
 * 
 * Dependencies: Trainer (from Base Library)
 */
public interface ITrainerService {
    
    // Trainer CRUD Operations
    Trainer addTrainer(Trainer trainer);
    Trainer getTrainerById(int regId);
    List<Trainer> getAllTrainers();
    Trainer updateTrainer(int regId, Trainer trainer);
    boolean deleteTrainer(int regId);
    boolean trainerExists(int regId);
    
    // Trainer Search Operations
    List<Trainer> searchTrainersByName(String name);
    Trainer searchTrainerByRegId(int regId);
    List<Trainer> searchTrainersBySpecialization(String specialization);
    
    // Trainer Validation
    boolean validateTrainerData(Trainer trainer);
}

