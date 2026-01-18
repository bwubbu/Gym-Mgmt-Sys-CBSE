package com.gymmanagement.equipmentbooking.controller;

import com.gymmanagement.base.entity.Machine;
import com.gymmanagement.base.service.IBookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Equipment Management (UC-5).
 * 
 * Provides endpoints for adding, listing, updating, and removing gym machines.
 * Integrates directly with the `IBookingService` and uses `base-library` entities
 * (Machine) for data exchange, ensuring consistency with the legacy file-based persistence model.
 */
@RestController
@RequestMapping("/api/equipment")
public class EquipmentController {

    private final IBookingService bookingService;

    public EquipmentController(IBookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<Machine> addMachine(@RequestBody Machine machine) {
        return ResponseEntity.ok(bookingService.addMachine(machine));
    }

    @GetMapping
    public ResponseEntity<List<Machine>> getAllMachines() {
        return ResponseEntity.ok(bookingService.getAllMachines());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Machine> getMachine(@PathVariable int id) {
        Machine m = bookingService.getMachineById(id);
        if (m == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(m);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMachine(@PathVariable int id) {
        boolean deleted = bookingService.deleteMachine(id);
        if (!deleted) {
            return ResponseEntity.status(404).body("Machine with reg id " + id + " was not found in gym.");
        }
        return ResponseEntity.ok("Machine Data Removed");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Machine> updateMachine(@PathVariable int id, @RequestBody Machine machine) {
        Machine updated = bookingService.updateMachine(id, machine);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }
}
