package com.gymmanagement.trainer.repository;

import com.gymmanagement.trainer.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Integer> {
    // Basic CRUD is provided by JpaRepository
}
