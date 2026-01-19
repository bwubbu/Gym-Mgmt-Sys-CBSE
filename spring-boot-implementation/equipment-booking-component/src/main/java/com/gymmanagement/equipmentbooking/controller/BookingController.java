package com.gymmanagement.equipmentbooking.controller;

import com.gymmanagement.base.service.IBookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



/**
 * REST Controller for Machine Bookings.
 * 
 * Handles requests for booking and canceling gym machines.
 * Relies on the `IBookingService` for business logic, which operates on the
 * legacy slot-based booking model (no specific dates/times).
 */
@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final IBookingService bookingService;

    public BookingController(IBookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/book")
    public ResponseEntity<String> bookMachine(
            @RequestParam int machineId,
            @RequestParam int memberId) {
        
        String result = bookingService.bookMachine(memberId, machineId);
        if (result.equals("booked")) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

    @PostMapping("/cancel")
    public ResponseEntity<String> cancelBooking(
            @RequestParam int machineId,
            @RequestParam int memberId) {
        
        String result = bookingService.cancelBooking(memberId, machineId);
        if (result.contains("canceled")) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }
}
