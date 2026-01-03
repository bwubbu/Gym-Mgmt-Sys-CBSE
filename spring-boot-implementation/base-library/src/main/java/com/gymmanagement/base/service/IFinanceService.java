package com.gymmanagement.base.service;

import com.gymmanagement.base.entity.Payment;
import com.gymmanagement.base.entity.Member;
import java.util.List;

/**
 * IFinanceService Interface
 * 
 * Defines the contract for Financial Management operations.
 * This interface should be implemented by the Financial Management Component.
 * 
 * Dependencies: Payment (from Base Library)
 */
public interface IFinanceService {
    
    // Payment Operations
    Payment getPaymentByMemberId(int memberRegId);
    Payment updatePayment(int memberRegId, Payment payment);
    
    // Outstanding Balance Operations
    double getOutstandingBalance(int memberRegId);
    String addOutstandingBalance(int memberRegId, double amount);
    String payOutstandingBalance(int memberRegId, double amount);
    String addOutstandingBalanceToAll(double amount);
    
    // Payment Status Operations
    String checkPaymentStatus(int memberRegId);
    List<Member> getMembersWithOutstandingBalance();
    List<Member> getMembersWithZeroBalance();
    
    // Financial Reports
    double getTotalOutstandingBalance();
    int getCountOfMembersWithBalance();
    int getCountOfMembersWithZeroBalance();
    
    // Validation
    boolean validatePayment(Payment payment);
}

