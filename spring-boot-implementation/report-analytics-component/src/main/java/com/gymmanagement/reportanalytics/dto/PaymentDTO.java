package com.gymmanagement.reportanalytics.dto;

import java.time.LocalDate;

/**
 * DTO for Payment data (from Base Library/Financial Management Component)
 */
public class PaymentDTO {
    private double outstandingBalance;
    private String status; // "Paid" or "UnPaid"
    private LocalDate lastPaymentDate;

    public PaymentDTO() {
    }

    public PaymentDTO(double outstandingBalance, String status, LocalDate lastPaymentDate) {
        this.outstandingBalance = outstandingBalance;
        this.status = status;
        this.lastPaymentDate = lastPaymentDate;
    }

    public double getOutstandingBalance() {
        return outstandingBalance;
    }

    public void setOutstandingBalance(double outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getLastPaymentDate() {
        return lastPaymentDate;
    }

    public void setLastPaymentDate(LocalDate lastPaymentDate) {
        this.lastPaymentDate = lastPaymentDate;
    }
}

