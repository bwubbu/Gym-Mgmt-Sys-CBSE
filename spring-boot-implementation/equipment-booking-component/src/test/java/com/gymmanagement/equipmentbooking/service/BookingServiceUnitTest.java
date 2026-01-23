package com.gymmanagement.equipmentbooking.service;

import com.gymmanagement.base.entity.Machine;
import com.gymmanagement.base.entity.Member;
import com.gymmanagement.base.entity.Payment;
import com.gymmanagement.base.entity.Maintenance;
import com.gymmanagement.base.entity.Date;
import com.gymmanagement.equipmentbooking.repository.FileMachineRepository;
import com.gymmanagement.equipmentbooking.repository.FileMaintenanceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceUnitTest {

    @Mock
    private FileMachineRepository machineRepository;

    @Mock
    private FileMaintenanceRepository maintenanceRepository;

    @Mock
    private DataService dataService;

    @InjectMocks
    private BookingService bookingService;

    private Machine testMachine;
    private Member testMember;

    @BeforeEach
    void setUp() {
        testMachine = new Machine(101, "Test Machine", "Brand", "Model", 100.0, 50.0, "Cardio");
        testMember = new Member();
        testMember.setRegId(1);
        testMember.setName("Test Member");
        testMember.setMemberPayment(new Payment(0.0, "Test", "123"));
    }

    @Test
    void testBookMachine_Success() {
        System.out.println("UNIT TEST: bookMachine [Success Path] - Verifying valid booking updates repository.");
        when(machineRepository.findById(101)).thenReturn(Optional.of(testMachine));
        when(maintenanceRepository.findActiveByMachineId(anyInt(), any(LocalDate.class))).thenReturn(Collections.emptyList());
        when(dataService.getMember(1)).thenReturn(testMember);
        
        // Save must be called if success
        String result = bookingService.bookMachine(1, 101);
        
        assertEquals("booked", result);
        verify(machineRepository, times(1)).save(testMachine);
        System.out.println("   -> SUCCESS: Booking saved and 'booked' returned.");
    }
    
    @Test
    void testBookMachine_MachineNotFound() {
        System.out.println("UNIT TEST: bookMachine [NotFound Path] - Verifying invalid machine ID returns error string.");
        when(machineRepository.findById(101)).thenReturn(Optional.empty());
        
        String result = bookingService.bookMachine(1, 101);
        
        assertEquals("Machine not found.", result);
        System.out.println("   -> SUCCESS: Correct error message returned.");
    }

    @Test
    void testBookMachine_UnderMaintenance() {
        System.out.println("UNIT TEST: bookMachine [Maintenance Path] - Verifying maintenance blocks booking.");
        when(machineRepository.findById(101)).thenReturn(Optional.of(testMachine));
        when(maintenanceRepository.findActiveByMachineId(anyInt(), any(LocalDate.class)))
            .thenReturn(Collections.singletonList(new Maintenance()));
            
        String result = bookingService.bookMachine(1, 101);
        
        assertEquals("Machine is currently under maintenance.", result);
        System.out.println("   -> SUCCESS: Maintenance blocking confirmed.");
    }

    @Test
    void testIsMachineAvailable_Yes() {
        System.out.println("UNIT TEST: isMachineAvailable [True] - Verifying availability with empty slots.");
        when(machineRepository.findById(101)).thenReturn(Optional.of(testMachine));
        // New machine has empty slots
        assertTrue(bookingService.isMachineAvailable(101));
        System.out.println("   -> SUCCESS: Machine correctly identified as available.");
    }

    @Test
    void testIsMachineAvailable_No() {
        System.out.println("UNIT TEST: isMachineAvailable [False] - Verifying unavailability when full.");
        // Fill Slots
        for (int i=0; i<8; i++) {
            testMachine.getBookings()[i] = new Member();
        }
        when(machineRepository.findById(101)).thenReturn(Optional.of(testMachine));
        
        assertFalse(bookingService.isMachineAvailable(101));
        System.out.println("   -> SUCCESS: Machine correctly identified as unavailable.");
    }
}
