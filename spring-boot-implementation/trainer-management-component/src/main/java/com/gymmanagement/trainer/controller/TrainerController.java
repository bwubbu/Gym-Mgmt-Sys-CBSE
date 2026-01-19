package com.gymmanagement.trainer.controller;

import com.gymmanagement.trainer.base.ITrainerService;
import com.gymmanagement.trainer.dto.PerformanceUpdateDTO;
import com.gymmanagement.trainer.entity.Trainer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Trainer Management
 */
@RestController
@RequestMapping("/api/trainers")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TrainerController {

    private final ITrainerService trainerService;

    @PostMapping
    public ResponseEntity<Trainer> createTrainer(@RequestBody Trainer trainer) {
        return ResponseEntity.ok(trainerService.createTrainer(trainer));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trainer> getTrainer(@PathVariable int id) {
        return ResponseEntity.ok(trainerService.getTrainer(id));
    }

    @GetMapping
    public ResponseEntity<List<Trainer>> getAllTrainers() {
        return ResponseEntity.ok(trainerService.getAllTrainers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trainer> updateTrainer(@PathVariable int id, @RequestBody Trainer trainer) {
        return ResponseEntity.ok(trainerService.updateTrainer(id, trainer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrainer(@PathVariable int id) {
        trainerService.deleteTrainer(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/assign/{memberId}")
    public ResponseEntity<Void> assignMember(@PathVariable int id, @PathVariable int memberId) {
        trainerService.assignMemberToTrainer(id, memberId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/performance")
    public ResponseEntity<Trainer> updatePerformance(@PathVariable int id, @RequestBody PerformanceUpdateDTO dto) {
        return ResponseEntity.ok(trainerService.updatePerformance(id, dto.getRating(), dto.isAttended()));
    }
}