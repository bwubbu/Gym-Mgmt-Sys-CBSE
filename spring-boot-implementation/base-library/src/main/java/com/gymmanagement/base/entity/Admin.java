package com.gymmanagement.base.entity;

import java.io.Serializable;

/**
 * Admin Entity
 * Represents a gym administrator
 * Note: Admin doesn't extend Person in the original code, but conceptually it should
 */
public class Admin implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String username;
    private String password;
    
    public Admin() {
        username = null;
        password = null;
    }
    
    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    // Getters and Setters
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    // Business methods
    public boolean validateLogin(String userName, String password) {
        boolean loginSuccessful = false;
        if (userName != null && userName.equals(this.username)) {
            if (password != null && password.equals(this.password)) {
                loginSuccessful = true;
            }
        }
        return loginSuccessful;
    }
    
    @Override
    public String toString() {
        return "\n\t\tAdmin Details : " + "\n\n> User Name : " + "****" + "\n\n> Password : " + "****"
                + "\n---------------------------------------------------------------\n";
    }
}

