package com.gymmanagement.base.service;

import com.gymmanagement.base.entity.Member;
import com.gymmanagement.base.entity.Machine;
import java.util.List;

/**
 * IBookingService Interface
 * 
 * Defines the contract for Equipment Booking operations.
 * This interface should be implemented by the Equipment Booking Component.
 * 
 * Dependencies: Member, Machine (from Base Library)
 */
public interface IBookingService {
    
    // Booking Operations
    String bookMachine(int memberRegId, int machineRegId);
    String cancelBooking(int memberRegId, int machineRegId);
    List<Member> getMachineBookings(int machineRegId);
    List<Machine> getMemberBookings(int memberRegId);
    String getAllBookings();
    
    // Machine Operations
    Machine addMachine(Machine machine);
    Machine getMachineById(int regId);
    List<Machine> getAllMachines();
    Machine updateMachine(int regId, Machine machine);
    boolean deleteMachine(int regId);
    boolean machineExists(int regId);
    
    // Machine Search Operations
    List<Machine> searchMachinesByName(String name);
    Machine searchMachineByRegId(int regId);
    
    // Validation
    boolean validateBooking(int memberRegId, int machineRegId);
    boolean isMachineAvailable(int machineRegId);
}

