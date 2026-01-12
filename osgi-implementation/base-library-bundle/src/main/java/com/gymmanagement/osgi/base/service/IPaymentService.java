package com.gymmanagement.osgi.base.service;

import com.gymmanagement.osgi.base.entity.Member;
import com.gymmanagement.osgi.base.entity.Payment;
import java.util.List;

/**
 * OSGi service interface for Payment Management
 */
public interface IPaymentService {
    /**
     * Add outstanding balance to a member
     * @param memberId Member registration ID
     * @param amount Amount to add
     * @return Success message
     */
    String addOutstandingBalance(int memberId, double amount);
    
    /**
     * Pay outstanding balance
     * @param memberId Member registration ID
     * @param amount Amount to pay
     * @return Success message
     */
    String payOutstandingBalance(int memberId, double amount);
    
    /**
     * Get payment information for a member
     * @param memberId Member registration ID
     * @return Payment object or null if not found
     */
    Payment getPayment(int memberId);
    
    /**
     * Get all members with outstanding balance
     * @return List of members with outstanding balance
     */
    List<Member> getMembersWithOutstandingBalance();
    
    /**
     * Get all members with zero balance
     * @return List of members with zero balance
     */
    List<Member> getMembersWithZeroBalance();
}

