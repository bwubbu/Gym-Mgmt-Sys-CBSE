package com.gymmanagement.trainer.service;

import com.gymmanagement.trainer.base.ITrainerService;
import com.gymmanagement.trainer.entity.Trainer;
import com.gymmanagement.trainer.repository.TrainerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * TrainerService Implementation
 * 
 * Implements ITrainerService and provides business logic for trainer management.
 */
@Service
@RequiredArgsConstructor
public class TrainerService implements ITrainerService {

    private final TrainerRepository trainerRepository;

    @Override
    public Trainer createTrainer(Trainer trainer) {
        return trainerRepository.save(trainer);
    }

    @Override
    public Trainer getTrainer(int id) {
        return trainerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Trainer not found with ID: " + id));
    }

    @Override
    public List<Trainer> getAllTrainers() {
        return trainerRepository.findAll();
    }

    @Override
    @Transactional
    public Trainer updateTrainer(int id, Trainer updatedTrainer) {
        Trainer existingTrainer = getTrainer(id);
        
        existingTrainer.setName(updatedTrainer.getName());
        existingTrainer.setGmail(updatedTrainer.getGmail());
        existingTrainer.setPhoneNum(updatedTrainer.getPhoneNum());
        existingTrainer.setAddress(updatedTrainer.getAddress());
        existingTrainer.setJoinDate(updatedTrainer.getJoinDate());
        existingTrainer.setDateOfBirth(updatedTrainer.getDateOfBirth());
        existingTrainer.setAge(updatedTrainer.getAge());
        existingTrainer.setGender(updatedTrainer.getGender());
        existingTrainer.setSpecialization(updatedTrainer.getSpecialization());
        existingTrainer.setHourlyRate(updatedTrainer.getHourlyRate());
        existingTrainer.setWeeklyWorkingHours(updatedTrainer.getWeeklyWorkingHours());
        existingTrainer.setExperienceLevel(updatedTrainer.getExperienceLevel());

        return trainerRepository.save(existingTrainer);
    }

    @Override
    public void deleteTrainer(int id) {
        if (!trainerRepository.existsById(id)) {
            throw new EntityNotFoundException("Trainer not found with ID: " + id);
        }
        trainerRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void assignMemberToTrainer(int trainerId, int memberId) {
        Trainer trainer = getTrainer(trainerId);
        if (!trainer.getAssignedMemberIds().contains(memberId)) {
            trainer.getAssignedMemberIds().add(memberId);
            trainerRepository.save(trainer);
        }
    }

    @Override
    @Transactional
    public Trainer updatePerformance(int trainerId, double rating, boolean attended) {
        Trainer trainer = getTrainer(trainerId);

        if (rating >= 0 && rating <= 5) {
            trainer.setTotalRating(trainer.getTotalRating() + rating);
            trainer.setRatingCount(trainer.getRatingCount() + 1);
        } else if (rating > 5 || rating < 0) {
             throw new IllegalArgumentException("Rating must be between 0 and 5");
        }

        if (attended) {
            trainer.setAttendanceDays(trainer.getAttendanceDays() + 1);
        }

        return trainerRepository.save(trainer);
    }
}