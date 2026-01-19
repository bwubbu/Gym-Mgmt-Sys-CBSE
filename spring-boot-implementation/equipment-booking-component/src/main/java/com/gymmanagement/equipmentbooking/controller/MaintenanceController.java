package com.gymmanagement.equipmentbooking.controller;

import com.gymmanagement.base.entity.Maintenance;
import com.gymmanagement.base.service.IBookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Equipment Maintenance.
 */
@RestController
@RequestMapping("/api/maintenance")
public class MaintenanceController {

    private final IBookingService bookingService;

    public MaintenanceController(IBookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<String> scheduleMaintenance(@RequestBody Maintenance maintenance) {
        String result = bookingService.scheduleMaintenance(maintenance);
        if (result.contains("not found")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/machine/{machineId}")
    public ResponseEntity<List<Maintenance>> getMaintenanceByMachine(@PathVariable int machineId) {
        return ResponseEntity.ok(bookingService.getMaintenanceByMachine(machineId));
    }
}
