package com.gymmanagement.osgi.base.service;

import com.gymmanagement.osgi.base.entity.Trainer;
import java.util.List;

/**
 * OSGi service interface for Trainer Management
 */
public interface ITrainerService {
    /**
     * Add a new trainer
     * @param trainer The trainer to add
     * @return Success message
     */
    String addTrainer(Trainer trainer);
    
    /**
     * Get a trainer by registration ID
     * @param regId Registration ID
     * @return Trainer object or null if not found
     */
    Trainer getTrainer(int regId);
    
    /**
     * Get all trainers
     * @return List of all trainers
     */
    List<Trainer> getAllTrainers();
    
    /**
     * Update trainer information
     * @param trainer Updated trainer object
     * @return Success message
     */
    String updateTrainer(Trainer trainer);
    
    /**
     * Delete a trainer by registration ID
     * @param regId Registration ID
     * @return Success message
     */
    String deleteTrainer(int regId);
    
    /**
     * Search trainers by name
     * @param name Name to search for
     * @return List of matching trainers
     */
    List<Trainer> searchTrainersByName(String name);
}

