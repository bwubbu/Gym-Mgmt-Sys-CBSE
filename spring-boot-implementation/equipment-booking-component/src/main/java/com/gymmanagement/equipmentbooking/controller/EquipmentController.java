package com.gymmanagement.equipmentbooking.controller;

import com.gymmanagement.base.entity.Machine;
import com.gymmanagement.base.service.IBookingService;
import com.gymmanagement.base.dto.MachineUsageStats;
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
    public ResponseEntity<List<MachineWithStatus>> getAllMachines() {
        List<Machine> machines = bookingService.getAllMachines();
        List<MachineWithStatus> enriched = machines.stream().map(m -> {
            List<com.gymmanagement.base.entity.Maintenance> maintenance = bookingService.getMaintenanceByMachine(m.getRegId());
            // Filter for active/future maintenance only to ensure UI badge is accurate
            java.time.LocalDate today = java.time.LocalDate.now();
            List<com.gymmanagement.base.entity.Maintenance> active = maintenance.stream().filter(r -> {
                if (r.getEndDate() == null) return false;
                java.time.LocalDate end = r.getEndDate().toLocalDate();
                return !end.isBefore(today);
            }).collect(java.util.stream.Collectors.toList());
            
            return new MachineWithStatus(m, active);
        }).collect(java.util.stream.Collectors.toList());
        
        return ResponseEntity.ok(enriched);
    }

    // DTO Helper
    public static class MachineWithStatus extends Machine {
        private List<com.gymmanagement.base.entity.Maintenance> maintenanceSchedule;

        public MachineWithStatus(Machine m, List<com.gymmanagement.base.entity.Maintenance> maintenanceSchedule) {
            super(m.getRegId(), m.getName(), m.getBrand(), m.getModel(), m.getMaxWeightCapacity(), m.getMachineWeight(), m.getType());
            this.setBookings(m.getBookings());
            this.maintenanceSchedule = maintenanceSchedule;
        }

        public List<com.gymmanagement.base.entity.Maintenance> getMaintenanceSchedule() {
            return maintenanceSchedule;
        }
    }

    @GetMapping("/stats")
    public ResponseEntity<List<MachineUsageStats>> getUsageStats() {
        return ResponseEntity.ok(bookingService.getUsageStatistics());
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
