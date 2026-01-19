package com.gymmanagement.financialmanagement.service;

import com.gymmanagement.base.entity.Member;
import com.gymmanagement.base.entity.Payment;
import com.gymmanagement.base.service.IFinanceService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * FinanceService Implementation
 * 
 * Implements IFinanceService interface for Financial Management operations.
 * 
 * Implements:
 * - UC-13: Process Financial Transaction
 * - UC-14: Manage Outstanding Balances
 * - UC-15: Generate Financial Reports
 * - UC-16: Analyze Payment Analytics
 */
@Service
public class FinanceService implements IFinanceService {
    
    private final DataService dataService;
    
    public FinanceService(DataService dataService) {
        this.dataService = dataService;
    }
    
    // ========== Payment Operations ==========
    
    @Override
    public Payment getPaymentByMemberId(int memberRegId) {
        Member member = dataService.getMember(memberRegId);
        if (member == null) {
            return null;
        }
        return member.getPayment();
    }
    
    @Override
    public Payment updatePayment(int memberRegId, Payment payment) {
        Member member = dataService.getMember(memberRegId);
        if (member == null) {
            return null;
        }
        member.setPayment(payment);
        dataService.updateMember(member);
        return payment;
    }
    
    // ========== Outstanding Balance Operations ==========
    
    @Override
    public double getOutstandingBalance(int memberRegId) {
        Payment payment = getPaymentByMemberId(memberRegId);
        return (payment != null) ? payment.getOutstandingBalance() : 0.0;
    }
    
    // UC-14 Flow B: Add Outstanding Balance to Specific Member
    @Override
    public String addOutstandingBalance(int memberRegId, double amount) {
        Member member = dataService.getMember(memberRegId);
        if (member == null) {
            return "Member not found.";
        }
        
        // Validate amount is positive number
        if (amount <= 0) {
            return "Balance should be in numbers.";
        }
        
        // Get or create payment object
        Payment payment = member.getPayment();
        if (payment == null) {
            payment = new Payment();
            member.setPayment(payment);
        }
        
        // Add balance
        payment.addOutstandingBalance(amount);
        dataService.updateMember(member);
        
        return "Balance Added Successfully.";
    }
    
    // UC-13: Process Financial Transaction (Pay Outstanding Balance)
    @Override
    public String payOutstandingBalance(int memberRegId, double amount) {
        Member member = dataService.getMember(memberRegId);
        if (member == null) {
            return "Member not found.";
        }
        
        // Get payment object
        Payment payment = member.getPayment();
        if (payment == null) {
            return "Member has no outstanding balance to pay.";
        }
        
        // Check if member has outstanding balance
        if (payment.getOutstandingBalance() == 0) {
            return "Member has no outstanding balance to pay.";
        }
        
        // Validate amount is positive
        if (amount <= 0) {
            return "Amount should be in numbers.";
        }
        
        // Process payment (Payment.payOutstandingBalance handles exceed limit check)
        String result = payment.payOutstandingBalance(amount);
        
        // If successful, update member in service
        if (result.equals("Amount Paid Successfully.")) {
            dataService.updateMember(member);
        }
        
        return result;
    }
    
    // UC-14 Flow A: Add Outstanding Balance to All Members
    @Override
    public String addOutstandingBalanceToAll(double amount) {
        List<Member> members = dataService.getAllMembers();
        
        // Check if there are members in the system
        if (members.isEmpty()) {
            return "No members found in the system.";
        }
        
        // Validate amount is positive number
        if (amount <= 0) {
            return "Balance should be in numbers.";
        }
        
        // Add balance to all members
        for (Member member : members) {
            Payment payment = member.getPayment();
            if (payment == null) {
                payment = new Payment();
                member.setPayment(payment);
            }
            payment.addOutstandingBalance(amount);
            dataService.updateMember(member);
        }
        
        return "Balance Added Successfully to All.";
    }
    
    // ========== Payment Status Operations ==========
    
    @Override
    public String checkPaymentStatus(int memberRegId) {
        Payment payment = getPaymentByMemberId(memberRegId);
        if (payment == null) {
            return "No payment information found.";
        }
        return payment.checkStatus();
    }
    
    // UC-15: Generate Financial Reports - Members with Outstanding Balance
    @Override
    public List<Member> getMembersWithOutstandingBalance() {
        return dataService.getAllMembers().stream()
                .filter(m -> m.getPayment() != null &&
                        m.getPayment().getOutstandingBalance() > 0)
                .collect(Collectors.toList());
    }
    
    // UC-15: Generate Financial Reports - Members with Zero Balance
    @Override
    public List<Member> getMembersWithZeroBalance() {
        return dataService.getAllMembers().stream()
                .filter(m -> m.getPayment() != null &&
                        m.getPayment().getOutstandingBalance() == 0)
                .collect(Collectors.toList());
    }
    
    // ========== Financial Reports ==========
    
    @Override
    public double getTotalOutstandingBalance() {
        return dataService.getAllMembers().stream()
                .mapToDouble(m -> {
                    Payment payment = m.getPayment();
                    return (payment != null) ? payment.getOutstandingBalance() : 0.0;
                })
                .sum();
    }
    
    @Override
    public int getCountOfMembersWithBalance() {
        return (int) dataService.getAllMembers().stream()
                .filter(m -> m.getPayment() != null &&
                        m.getPayment().getOutstandingBalance() > 0)
                .count();
    }
    
    @Override
    public int getCountOfMembersWithZeroBalance() {
        return (int) dataService.getAllMembers().stream()
                .filter(m -> m.getPayment() != null &&
                        m.getPayment().getOutstandingBalance() == 0)
                .count();
    }
    
    // ========== Validation ==========
    
    @Override
    public boolean validatePayment(Payment payment) {
        if (payment == null) {
            return false;
        }
        // Add validation logic as needed
        return payment.getOutstandingBalance() >= 0;
    }
    
    // ========== UC-16: Analyze Payment Analytics (Additional Method) ==========
    
    /**
     * Generate payment analytics data (UC-16)
     * This is an additional method not in IFinanceService interface
     * 
     * @return Map containing payment analytics
     */
    public Map<String, Object> generatePaymentAnalytics() {
        Map<String, Object> analytics = new LinkedHashMap<>();
        
        List<Member> members = dataService.getAllMembers();
        
        // Calculate totals
        double totalOutstanding = 0.0;
        int membersWithBalance = 0;
        int membersWithZeroBalance = 0;
        int totalMembers = members.size();
        
        // Member details list for table (sorted by outstanding balance descending)
        List<Map<String, Object>> memberDetails = new ArrayList<>();
        
        for (Member member : members) {
            Payment payment = member.getPayment();
            double balance = (payment != null) ? payment.getOutstandingBalance() : 0.0;
            String status = (payment != null) ? payment.checkStatus() : "Paid";
            
            totalOutstanding += balance;
            if (balance > 0) {
                membersWithBalance++;
            } else {
                membersWithZeroBalance++;
            }
            
            Map<String, Object> memberInfo = new LinkedHashMap<>();
            memberInfo.put("name", member.getName());
            memberInfo.put("regId", member.getRegId());
            memberInfo.put("outstandingBalance", balance);
            memberInfo.put("status", status);
            memberDetails.add(memberInfo);
        }
        
        // Sort by outstanding balance descending (highest balance first)
        memberDetails.sort((a, b) ->
                Double.compare((Double) b.get("outstandingBalance"),
                        (Double) a.get("outstandingBalance")));
        
        // Calculate average
        double averageOutstanding = totalMembers > 0 ?
                totalOutstanding / totalMembers : 0.0;
        
        // Populate analytics map
        analytics.put("totalOutstandingBalance", totalOutstanding);
        analytics.put("averageOutstandingBalance", averageOutstanding);
        analytics.put("membersWithOutstandingBalance", membersWithBalance);
        analytics.put("membersWithZeroBalance", membersWithZeroBalance);
        analytics.put("totalMembers", totalMembers);
        analytics.put("memberDetails", memberDetails);
        analytics.put("generatedDate", LocalDate.now().toString());
        
        return analytics;
    }
}

