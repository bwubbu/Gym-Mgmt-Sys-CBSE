package com.gymmanagement.base.entity;

/**
 * GymMachine Interface
 * Defines the contract for machine booking functionality
 */
public interface GymMachine {
    /**
     * Books a machine for a member
     * @param member The member to book for
     * @return Status message
     */
    String bookMachine(Member member);
    
    /**
     * Cancels a booking for a member
     * @param regId The registration ID of the member
     * @return Status message
     */
    String cancelBooking(int regId);
    
    /**
     * Gets all bookings for this machine
     * @return String representation of all bookings
     */
    String getAllBookings();
}

