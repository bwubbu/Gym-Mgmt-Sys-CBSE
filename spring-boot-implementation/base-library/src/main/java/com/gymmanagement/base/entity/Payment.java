package com.gymmanagement.base.entity;

import java.io.Serializable;

/**
 * Payment Entity
 * Represents payment information for members
 */
public class Payment implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private double outStandingBalance;
    private String creditCardAccountHolder;
    private String creditCardNum;
    
    public Payment() {
        outStandingBalance = 0.0;
        creditCardAccountHolder = null;
        creditCardNum = null;
    }
    
    public Payment(double outStandingBalance, String creditCardAccountHolder, String creditCardNum) {
        this.outStandingBalance = outStandingBalance;
        this.creditCardAccountHolder = creditCardAccountHolder;
        this.creditCardNum = creditCardNum;
    }
    
    // Getters and Setters
    public double getOutstandingBalance() {
        return outStandingBalance;
    }
    
    public void setOutstandingBalance(double outStandingBalance) {
        this.outStandingBalance = outStandingBalance;
    }
    
    public String getCreditCardAccountHolder() {
        return creditCardAccountHolder;
    }
    
    public void setCreditCardAccountHolder(String creditCardAccountHolder) {
        this.creditCardAccountHolder = creditCardAccountHolder;
    }
    
    public String getCreditCardNum() {
        return creditCardNum;
    }
    
    public void setCreditCardNum(String creditCardNum) {
        this.creditCardNum = creditCardNum;
    }
    
    // Business methods
    public String checkStatus() {
        if (outStandingBalance == 0.0) {
            return "Paid";
        }
        return "UnPaid";
    }
    
    public void addOutstandingBalance(double balance) {
        this.outStandingBalance += balance;
    }
    
    public String payOutstandingBalance(double balance) {
        String result = "";
        if (this.outStandingBalance < balance) {
            return "Error.. Exceed paying limit. Because your outstanding balance is " + this.outStandingBalance;
        }
        this.outStandingBalance -= balance;
        return "Amount Paid Successfully.";
    }
    
    // Validation methods
    public Boolean validateBalance(String balance) {
        if (!checkNumberIsdouble(balance)) {
            return false;
        }
        double balance1 = Double.parseDouble(balance);
        if (balance1 < 0.0) {
            return false;
        }
        return true;
    }
    
    public boolean checkNumberIsdouble(String number) {
        try {
            Double.parseDouble(number);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }
    
    public boolean validateName(String name) {
        int count;
        if (name.length() > 30) {
            return false;
        }
        count = 0;
        for (int i = 0; i < name.length(); i++) {
            if (!(Character.isLetter(name.charAt(i)) || name.charAt(i) == '.' || name.charAt(i) == ' ')) {
                count++;
                break;
            }
        }
        if (count != 0) {
            return false;
        }
        return true;
    }
    
    public boolean validateCreditCard(String num) {
        boolean isValid = (num.length() == 19) &&
                (Character.isDigit(num.charAt(0))) &&
                (Character.isDigit(num.charAt(1))) &&
                (Character.isDigit(num.charAt(2))) &&
                (Character.isDigit(num.charAt(3))) &&
                (num.charAt(4) == '-') &&
                (Character.isDigit(num.charAt(5))) &&
                (Character.isDigit(num.charAt(6))) &&
                (Character.isDigit(num.charAt(7))) &&
                (Character.isDigit(num.charAt(8))) &&
                (num.charAt(9) == '-') &&
                (Character.isDigit(num.charAt(10))) &&
                (Character.isDigit(num.charAt(11))) &&
                (Character.isDigit(num.charAt(12))) &&
                (Character.isDigit(num.charAt(13))) &&
                (num.charAt(14) == '-') &&
                (Character.isDigit(num.charAt(15))) &&
                (Character.isDigit(num.charAt(16))) &&
                (Character.isDigit(num.charAt(17))) &&
                (Character.isDigit(num.charAt(18)));
        
        return isValid;
    }
    
    @Override
    public String toString() {
        if (!((creditCardNum == null || creditCardNum.equals("")) || 
              (creditCardAccountHolder == null || creditCardAccountHolder.equals("")))) {
            return "\n\n\t\tPayment Details" + "\n---------------------------------------------------------------\n" +
                    "\n> Payment Status : " + checkStatus() + "\n\n> Outstanding Balance : " +
                    String.valueOf(outStandingBalance) + "\n\n> Credit Card Account holder : " + creditCardAccountHolder +
                    "\n\n> Credit Card Number : " + creditCardNum.substring(0, 4) + "-****-****-" + creditCardNum.substring(15, 19) +
                    "\n---------------------------------------------------------------\n";
        }
        return "\n\n\t\tPayment Details" + "\n---------------------------------------------------------------\n" +
                "\n> Payment Status : " + checkStatus() + "\n\n> Outstanding Balance : " +
                String.valueOf(outStandingBalance) + "\n\n> Credit Card Account holder : No details Found " +
                "\n\n> Credit Card Number : No details Found" + "\n---------------------------------------------------------------\n";
    }
}

