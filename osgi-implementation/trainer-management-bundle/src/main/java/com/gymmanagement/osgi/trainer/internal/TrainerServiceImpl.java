package com.gymmanagement.osgi.trainer.internal;

import com.gymmanagement.osgi.base.entity.Trainer;
import com.gymmanagement.osgi.base.service.ITrainerService;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * OSGi service implementation for Trainer Management
 * 
 * Note: Service registration is handled via XML descriptor:
 * OSGI-INF/com.gymmanagement.osgi.trainer.service.xml
 */
public class TrainerServiceImpl implements ITrainerService {
    
    // In-memory storage for trainers
    private final ConcurrentHashMap<Integer, Trainer> trainers = new ConcurrentHashMap<>();
    private int nextRegId = 1;

    @Override
    public String addTrainer(Trainer trainer) {
        if (trainer == null) {
            return "Error: Trainer cannot be null";
        }

        if (trainer.getName() == null || trainer.getName().trim().isEmpty()) {
            return "Error: Trainer name is required";
        }

        if (trainer.getRegId() == 0) {
            trainer.setRegId(nextRegId++);
        }

        if (trainers.containsKey(trainer.getRegId())) {
            return "Error: Trainer with ID " + trainer.getRegId() + " already exists";
        }

        trainers.put(trainer.getRegId(), trainer);
        return "Trainer added successfully with ID: " + trainer.getRegId();
    }

    @Override
    public Trainer getTrainer(int regId) {
        return trainers.get(regId);
    }

    @Override
    public List<Trainer> getAllTrainers() {
        return new ArrayList<>(trainers.values());
    }

    @Override
    public String updateTrainer(Trainer trainer) {
        if (trainer == null) {
            return "Error: Trainer cannot be null";
        }

        if (!trainers.containsKey(trainer.getRegId())) {
            return "Error: Trainer with ID " + trainer.getRegId() + " not found";
        }

        trainers.put(trainer.getRegId(), trainer);
        return "Trainer updated successfully";
    }

    @Override
    public String deleteTrainer(int regId) {
        Trainer removed = trainers.remove(regId);
        if (removed != null) {
            return "Trainer with ID " + regId + " deleted successfully";
        }
        return "Error: Trainer with ID " + regId + " not found";
    }

    @Override
    public List<Trainer> searchTrainersByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return new ArrayList<>();
        }

        String searchName = name.toLowerCase().trim();
        return trainers.values().stream()
                .filter(t -> t.getName() != null && t.getName().toLowerCase().contains(searchName))
                .collect(Collectors.toList());
    }

    @Override
    public void assignMemberToTrainer(int trainerId, int memberId) {
        Trainer trainer = getTrainer(trainerId);
        if (trainer != null) {
            if (!trainer.getAssignedMemberIds().contains(memberId)) {
                trainer.getAssignedMemberIds().add(memberId);
            }
        }
    }

    @Override
    public String updatePerformance(int trainerId, double rating, boolean attended) {
        Trainer trainer = getTrainer(trainerId);
        if (trainer == null) {
            return "Error: Trainer not found";
        }

        if (rating >= 0 && rating <= 5) {
            trainer.setTotalRating(trainer.getTotalRating() + rating);
            trainer.setRatingCount(trainer.getRatingCount() + 1);
        } else if (rating > 0) {
            return "Error: Rating must be between 0 and 5";
        }

        if (attended) {
            trainer.setAttendanceDays(trainer.getAttendanceDays() + 1);
        }

        return "Performance updated successfully";
    }
}
