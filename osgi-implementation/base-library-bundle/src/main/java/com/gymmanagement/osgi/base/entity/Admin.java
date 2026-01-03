package com.gymmanagement.osgi.base.entity;

import java.io.*;
import java.util.Scanner;

/**
 * Admin entity for OSGi Base Library Bundle
 * Handles authentication and admin information
 */
public class Admin {
    private final String[] AdminInfo = new String[2];
    
    public Admin() throws IOException {
        super();
        updateAdminInfoArray();
    }
    
    @Override
    public String toString() {
        return "\n\t\tAdmin Details : " + "\n\n> User Name : " + "****" + "\n\n> Password : " + "****" +
                "\n---------------------------------------------------------------\n";
    }
    
    private void updateAdminInfoArray() throws IOException {
        while (true) {
            try {
                File a4 = new File("AdminInfo.txt");
                Scanner in = new Scanner(a4);
                String de = in.nextLine();
                String[] adInfo = de.split("#");
                for (int i = 0; i < AdminInfo.length; i++) {
                    AdminInfo[i] = adInfo[i];
                }
                in.close();
                break;
            } catch (FileNotFoundException e) {
                OutputStream os = new FileOutputStream("AdminInfo.txt");
                PrintWriter put = new PrintWriter(os);
                for (int i = 0; i < 2; i++) {
                    if (i == 0) {
                        put.print("admin#");
                    } else {
                        put.print("anything#");
                    }
                }
                put.close();
                continue;
            } catch (IndexOutOfBoundsException e) {
                // Continue to retry
            }
        }
    }
    
    public boolean validateLogin(String userName, String password) {
        boolean loginSuccessful = false;
        if (userName != null && password != null && userName.equals(AdminInfo[0])) {
            if (password.equals(AdminInfo[1])) {
                loginSuccessful = true;
            }
        }
        return loginSuccessful;
    }
    
    public void modifyData(String newUsername, String newPassword) {
        AdminInfo[0] = newUsername;
        AdminInfo[1] = newPassword;
        try {
            PrintWriter myFile = new PrintWriter("AdminInfo.txt");
            for (int i = 0; i < AdminInfo.length; i++) {
                myFile.print(AdminInfo[i] + "#");
            }
            myFile.close();
        } catch (FileNotFoundException e) {
            System.out.println("\n\t\tFile not found.... Sorry we are Closing Program\n");
            System.exit(1);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("\n\t\tIndex out of bound exception...Sorry we are Closing Program\n");
            System.exit(1);
        }
    }
}

