package com.gymmanagement.osgi.base.entity;

/**
 * GymMachine interface for machine booking functionality
 * Part of OSGi Base Library Bundle
 */
public interface GymMachine {
    /**
     * Book a machine for a member
     * @param m The member to book for
     * @return Status message
     */
    String bookMachine(Member m);
    
    /**
     * Cancel a booking by member registration ID
     * @param regId Member registration ID
     * @return Status message
     */
    String cancelBooking(int regId);
    
    /**
     * Get all bookings for this machine
     * @return String representation of all bookings
     */
    String getAllBookings();
}

