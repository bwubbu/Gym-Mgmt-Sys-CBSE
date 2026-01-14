package com.gymmanagement.financialmanagement.controller;

import com.gymmanagement.base.entity.Member;
import com.gymmanagement.base.entity.Payment;
import com.gymmanagement.base.service.IFinanceService;
import com.gymmanagement.financialmanagement.service.FinanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST Controller for Financial Management
 * 
 * Handles all financial management endpoints
 * 
 * Implements:
 * - UC-13: Process Financial Transaction
 * - UC-14: Manage Outstanding Balances
 * - UC-15: Generate Financial Reports
 * - UC-16: Analyze Payment Analytics
 */
@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class FinanceController {
    
    private final IFinanceService financeService;
    private final FinanceService financeServiceImpl;
    
    public FinanceController(IFinanceService financeService, FinanceService financeServiceImpl) {
        this.financeService = financeService;
        this.financeServiceImpl = financeServiceImpl;
    }
    
    // ========== Payment Operations ==========
    
    @GetMapping("/member/{memberId}")
    public ResponseEntity<Payment> getPaymentByMemberId(@PathVariable int memberId) {
        Payment payment = financeService.getPaymentByMemberId(memberId);
        if (payment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(payment);
    }
    
    @PutMapping("/member/{memberId}")
    public ResponseEntity<Payment> updatePayment(@PathVariable int memberId, @RequestBody Payment payment) {
        Payment updatedPayment = financeService.updatePayment(memberId, payment);
        if (updatedPayment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedPayment);
    }
    
    @GetMapping("/member/{memberId}/balance")
    public ResponseEntity<Double> getOutstandingBalance(@PathVariable int memberId) {
        double balance = financeService.getOutstandingBalance(memberId);
        return ResponseEntity.ok(balance);
    }
    
    // ========== Outstanding Balance Operations ==========
    
    // UC-14 Flow B: Add Outstanding Balance to Specific Member
    @PostMapping("/member/{memberId}/add-balance")
    public ResponseEntity<String> addOutstandingBalance(
            @PathVariable int memberId,
            @RequestParam double amount) {
        String result = financeService.addOutstandingBalance(memberId, amount);
        return ResponseEntity.ok(result);
    }
    
    // UC-13: Process Financial Transaction (Pay Outstanding Balance)
    @PostMapping("/member/{memberId}/pay")
    public ResponseEntity<String> payOutstandingBalance(
            @PathVariable int memberId,
            @RequestParam double amount) {
        String result = financeService.payOutstandingBalance(memberId, amount);
        return ResponseEntity.ok(result);
    }
    
    // UC-14 Flow A: Add Outstanding Balance to All Members
    @PostMapping("/add-balance-to-all")
    public ResponseEntity<String> addOutstandingBalanceToAll(@RequestParam double amount) {
        String result = financeService.addOutstandingBalanceToAll(amount);
        return ResponseEntity.ok(result);
    }
    
    // ========== Payment Status Operations ==========
    
    @GetMapping("/member/{memberId}/status")
    public ResponseEntity<String> checkPaymentStatus(@PathVariable int memberId) {
        String status = financeService.checkPaymentStatus(memberId);
        return ResponseEntity.ok(status);
    }
    
    // ========== Financial Reports (UC-15) ==========
    
    @GetMapping("/members/outstanding-balance")
    public ResponseEntity<List<Member>> getMembersWithOutstandingBalance() {
        List<Member> members = financeService.getMembersWithOutstandingBalance();
        return ResponseEntity.ok(members);
    }
    
    @GetMapping("/members/zero-balance")
    public ResponseEntity<List<Member>> getMembersWithZeroBalance() {
        List<Member> members = financeService.getMembersWithZeroBalance();
        return ResponseEntity.ok(members);
    }
    
    @GetMapping("/total-outstanding-balance")
    public ResponseEntity<Double> getTotalOutstandingBalance() {
        double total = financeService.getTotalOutstandingBalance();
        return ResponseEntity.ok(total);
    }
    
    @GetMapping("/count/members-with-balance")
    public ResponseEntity<Integer> getCountOfMembersWithBalance() {
        int count = financeService.getCountOfMembersWithBalance();
        return ResponseEntity.ok(count);
    }
    
    @GetMapping("/count/members-with-zero-balance")
    public ResponseEntity<Integer> getCountOfMembersWithZeroBalance() {
        int count = financeService.getCountOfMembersWithZeroBalance();
        return ResponseEntity.ok(count);
    }
    
    // ========== Payment Analytics (UC-16) ==========
    
    @GetMapping("/analytics")
    public ResponseEntity<Map<String, Object>> getPaymentAnalytics() {
        Map<String, Object> analytics = financeServiceImpl.generatePaymentAnalytics();
        return ResponseEntity.ok(analytics);
    }
}

