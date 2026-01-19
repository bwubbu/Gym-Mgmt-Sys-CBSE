package com.gymmanagement.osgi.base.service;

import com.gymmanagement.osgi.base.dto.MachineUsageStats;
import com.gymmanagement.osgi.base.entity.Machine;
import com.gymmanagement.osgi.base.entity.Member;
import java.time.LocalDate;
import java.util.List;

/**
 * OSGi service interface for Machine Management
 */
public interface IMachineService {
    /**
     * Add a new machine
     *
     * @param machine The machine to add
     * @return Success message
     */
    String addMachine(Machine machine);

    /**
     * Get a machine by registration ID
     *
     * @param regId Registration ID
     * @return Machine object or null if not found
     */
    Machine getMachine(int regId);

    /**
     * Get all machines
     *
     * @return List of all machines
     */
    List<Machine> getAllMachines();

    /**
     * Update machine information
     *
     * @param machine Updated machine object
     * @return Success message
     */
    String updateMachine(Machine machine);

    /**
     * Delete a machine by registration ID
     *
     * @param regId Registration ID
     * @return Success message
     */
    String deleteMachine(int regId);

    /**
     * Book a machine for a member
     *
     * @param machineId Machine registration ID
     * @param member    Member to book for
     * @return Status message
     */
    String bookMachine(int machineId, Member member);

    /**
     * Cancel a booking
     *
     * @param machineId Machine registration ID
     * @param memberId  Member registration ID
     * @return Status message
     */
    String cancelBooking(int machineId, int memberId);

    /**
     * Get all bookings for a machine
     *
     * @param machineId Machine registration ID
     * @return String representation of bookings
     */
    String getAllBookings(int machineId);

    /**
     * Schedule maintenance for a machine
     *
     * @param machineId   Machine registration ID
     * @param startDate   Start date of maintenance
     * @param endDate     End date of maintenance
     * @param description Brief description
     * @return Status message
     */
    String scheduleMaintenance(int machineId, LocalDate startDate, LocalDate endDate, String description);

    /**
     * Get list of machines available on a specific date
     * 
     * @param date Date to check
     * @return List of available machines
     */
    List<Machine> getAvailableMachines(LocalDate date);

    /**
     * Get usage statistics for all machines
     * 
     * @return List of usage statistics
     */
    List<MachineUsageStats> getUsageStatistics();
}
