package com.gymmanagement.trainer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for Performance Update
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceUpdateDTO {
    private double rating;
    private boolean attended;
}
