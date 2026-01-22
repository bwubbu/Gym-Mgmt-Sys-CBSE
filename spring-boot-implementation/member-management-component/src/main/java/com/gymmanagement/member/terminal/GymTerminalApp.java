package com.gymmanagement.member.terminal;

import com.gymmanagement.base.entity.*;
import com.gymmanagement.member.base.IMemberService;
import com.gymmanagement.member.dto.MemberProfileDTO;
import com.gymmanagement.member.service.DataService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;
import com.gymmanagement.base.entity.Date;

@Component
public class GymTerminalApp implements CommandLineRunner {

    private final IMemberService memberService;
    private final DataService dataService;
    private final Scanner scanner = new Scanner(System.in);
    
    // Auth State
    private String adminUser = "admin";
    private String adminPass = "admin123";

    public GymTerminalApp(IMemberService memberService, DataService dataService) {
        this.memberService = memberService;
        this.dataService = dataService;
    }

    @Override
    public void run(String... args) {
        while (true) {
            System.out.println("\n========================================");
            System.out.println("   GYM MANAGEMENT SYSTEM - TERMINAL    ");
            System.out.println("========================================");
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.print("Select Option: ");

            String choice = scanner.nextLine();
            if (choice.equals("1")) handleLogin();
            else if (choice.equals("2")) System.exit(0);
        }
    }

    private void handleLogin() {
        System.out.print("Username/ID: ");
        String user = scanner.nextLine();
        System.out.print("Password: ");
        String pass = scanner.nextLine();

        if (user.equalsIgnoreCase(adminUser) && pass.equals(adminPass)) {
            adminMenu();
        } else {
            try {
                int id = Integer.parseInt(user);
                Member m = memberService.getMember(id);
                if (m != null) memberMenu(m);
                else System.out.println("[!] Member ID not found.");
            } catch (NumberFormatException e) {
                System.out.println("[!] Invalid Credentials!");
            }
        }
    }

    private Date promptForDate(String dateLabel) {
        while (true) {
            try {
                System.out.println("\n--- Entering " + dateLabel + " ---");
                Date d = new Date();

                System.out.print("Enter Year (e.g., 2024): ");
                int year = Integer.parseInt(scanner.nextLine());
                // Logic check for Year
                if (year < 1900 || year > 2100) {
                    System.out.println("[!] Step 11: Validation failure - Please enter a realistic year.");
                    continue;
                }
                d.setYear(year);

                System.out.print("Enter Month (1-12): ");
                int month = Integer.parseInt(scanner.nextLine());
                if (month < 1 || month > 12) {
                    System.out.println("[!] Step 11: Validation failure - Month must be 1-12.");
                    continue;
                }
                d.setMonth(month);

                System.out.print("Enter Day: ");
                int day = Integer.parseInt(scanner.nextLine());
                if (!d.checkDay(day, month)) {
                    System.out.println("[!] Step 11: Validation failure - Invalid day for this month.");
                    continue;
                }
                d.setDay(day);

                // FINAL CHECK: Cannot be in the future
                if (d.toLocalDate().isAfter(java.time.LocalDate.now())) {
                    System.out.println("[!] Step 11: Validation failure - Date cannot be in the future.");
                    continue;
                }

                return d;
            } catch (Exception e) {
                System.out.println("[!] Invalid input. Please enter numbers only.");
            }
        }
    }

    // --- ADMIN SECTION (UC-1, UC-2, UC-21) ---
    private void adminMenu() {
        while (true) {
            System.out.println("\n--- ADMIN DASHBOARD ---");
            System.out.println("1. Manage Members (UC-1)");
            System.out.println("2. Manage Member Plans (UC-2)");
            System.out.println("3. Change My Credentials (UC-21)");
            // System.out.println("4. Developer: Add Test Data (Machines/Trainers)");
            System.out.println("4. Logout");
            System.out.print("Choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> manageMembersMenu();
                case "2" -> managePlans();
                case "3" -> changeCredentials();
                // case "4" -> addTestDataFlow();
                case "4" -> {
                    System.out.println("[INFO] Logging out of Admin session...");
                    return; // Use return to exit the method and go back to main login
                }
                default -> {
                    // This handles any input that isn't 1, 2, 3, 4, or 5
                    System.out.println("[!] ERROR: Invalid choice '" + choice + "'. Please enter a number between 1 and 5.");
                }
            }
        }
    }

    private void changeCredentials() {
        System.out.println("\n--- UC-21: UPDATE CREDENTIALS ---");
        System.out.print("Enter New Username (or 'cancel'): ");
        String newUser = scanner.nextLine();
        if (newUser.equalsIgnoreCase("cancel")) return;

        System.out.print("Enter New Password (or 'cancel'): ");
        String newPass = scanner.nextLine();
        if (newPass.equalsIgnoreCase("cancel")) return;

        this.adminUser = newUser;
        this.adminPass = newPass;
        System.out.println("[SUCCESS] Admin credentials updated.");
    }

    // private void addTestDataFlow() {
    //     System.out.println("\n--- DEVELOPER: ADD TEST DATA ---");
    //     System.out.println("1. Add Test Machine");
    //     System.out.println("2. Add Test Trainer");
    //     System.out.print("Choice: ");
    //     String choice = scanner.nextLine();

    //     if (choice.equals("1")) {
    //         Machine m = new Machine();
    //         System.out.print("Machine Name: "); m.setName(scanner.nextLine());
    //         System.out.print("Brand: "); m.setBrand(scanner.nextLine());
    //         System.out.print("Model: "); m.setModel(scanner.nextLine());
    //         System.out.print("Type: "); m.setType(scanner.nextLine());
    //         m.setRegId(new Random().nextInt(1000) + 1);
    //         dataService.addTestMachine(m);
    //         System.out.println("[SUCCESS] Mock Machine added for testing.");
    //     } else if (choice.equals("2")) {
    //         Trainer t = new Trainer();
    //         System.out.print("Trainer Name: "); t.setName(scanner.nextLine());
    //         t.setRegId(new Random().nextInt(500) + 500);
    //         dataService.addTestTrainer(t);
    //         System.out.println("[SUCCESS] Mock Trainer added for testing.");
    //     }
    // }

    private void manageMembersMenu() {
        while (true) {
            System.out.println("\n--- UC-1: MANAGE MEMBERS ---");
            System.out.println("1. Add Member\n2. Search Member\n3. Modify Member\n4. View All\n5. Remove Member\n6. Back");
            System.out.print("Choice: ");
            String choice = scanner.nextLine();

            List<Member> all = memberService.getAllMembers();
            if (!choice.equals("1") && !choice.equals("6") && (all == null || all.isEmpty())) {
                System.out.println("\n[!] ERROR: No member records found in the system. Please add a member first.");
                continue;
            }

            switch (choice) {
                case "1" -> addMemberFlow();
                case "2" -> searchMemberFlow();
                case "3" -> modifyMemberFlow();
                case "4" -> viewAllMembersFlow(all);
                case "5" -> removeMemberFlow();
                case "6" -> { return; }
                default -> System.out.println("[!] Invalid choice, try again.");
            }
        }
    }

    // --- UC-1: MAIN FLOW (ADD MEMBER) ---
    private void addMemberFlow() {
        System.out.println("\n--- [Add Member Form] (Type 'cancel' to exit) ---");
        
        System.out.println("Step 1: Generate Registration ID...");
        int regId = new Random().nextInt(900000) + 100000;
        System.out.println("Generated ID: " + regId);

        Member m = new Member();
        m.setRegId(regId);

        // Personal Details
        System.out.print("Name: "); String name = scanner.nextLine();
        if (name.equalsIgnoreCase("cancel")) return;
        if (name.isEmpty()) { System.out.println("[!] Step 11: Validation failure - Name is required."); return; }
        m.setName(name);

        System.out.print("Gmail: "); m.setGmail(scanner.nextLine());
        System.out.print("Phone no: "); m.setPhoneNum(scanner.nextLine());
        System.out.print("Address: "); m.setAddress(scanner.nextLine());

        System.out.print("Fitness Goal (e.g., Weight Loss, Muscle Gain): ");
        m.setFitnessGoal(scanner.nextLine());

        // Dates
        Date joinDate = promptForDate("Date of Join");
        if (joinDate == null) return; // Handle cancel
        m.setDateOfJoin(joinDate);

        Date birthDate = promptForDate("Date of Birth");
        if (birthDate == null) return; 
        m.setDateOfBirth(birthDate);
        m.setAge(m.caluAge(birthDate.getYear()));

        // Gender
        while (true) {
            System.out.print("Gender [M]ale / [F]emale: ");
            String g = scanner.nextLine().toUpperCase();
            if (g.equals("M")) { m.setGender("M"); break; }
            if (g.equals("F")) { m.setGender("F"); break; }
            System.out.println("[!] Invalid choice. Type M or F.");
        }

        // Body Statistics Input
        BodyStats stats = new BodyStats();
        System.out.print("Weight (kg): ");
        double w = Double.parseDouble(scanner.nextLine());
        stats.setWeight(w);

        System.out.print("Height (m): ");
        double h = Double.parseDouble(scanner.nextLine());
        stats.setHeight(h);

        System.out.print("Body Fat %: ");
        double fat = Double.parseDouble(scanner.nextLine());
        stats.setBodyFatPercentage(fat);

        // AUTO-CALCULATE BMI: weight / (height * height)
        if (h > 0) {
            double calculatedBmi = w / (h * h);
            stats.setBmi(calculatedBmi);
            System.out.printf("[INFO] Calculated BMI: %.2f\n", calculatedBmi);
        }
        
        m.setBodyStats(stats);

        // Step 9: Member Plan
        List<MemberPlan> plans = memberService.getAllMemberPlans();
        if (!plans.isEmpty()) {
            System.out.println("Available Plans (or type 'none'):");
            for (int i = 0; i < plans.size(); i++) System.out.println(i + ". " + plans.get(i).getPlanName());
            String pIn = scanner.nextLine();
            if (!pIn.equalsIgnoreCase("none") && !pIn.equalsIgnoreCase("cancel")) {
                MemberPlan selected = plans.get(Integer.parseInt(pIn));
                m.setCurrentPlan(selected);

                // Step 9.2: Personal Trainer included
                if (selected.isPersonalTrainerIncluded()) {
                    List<Trainer> trainers = dataService.getAvailableTrainers();
                    if (trainers == null || trainers.isEmpty()) {
                        System.out.println("[!] NOTE: No trainers available in the system. Skipping trainer assignment.");
                    } else {
                        System.out.println("Selecting trainer...");
                        for (int i = 0; i < trainers.size(); i++) System.out.println(i + ". " + trainers.get(i).getName());
                        m.setAssignedTrainerId(trainers.get(Integer.parseInt(scanner.nextLine())).getRegId());
                    }
                }
                
                // Step 10: Finance Information
                if (selected.getPrice() > 0) {
                    System.out.println("Adding Outstanding Balance: $" + selected.getPrice());
                    Payment pay = new Payment();
                    pay.setOutstandingBalance(selected.getPrice());
                    System.out.print("Credit card account holder: "); pay.setCreditCardAccountHolder(scanner.nextLine());
                    System.out.print("Credit card number: "); pay.setCreditCardNum(scanner.nextLine());
                    m.setPayment(pay);
                }
            }
        }

        memberService.addMember(m);
        System.out.println("[SUCCESS] Member record created and saved.");
    }

    // --- UC-1: MODIFY MEMBER (Alternative Flow) ---
    private void modifyMemberFlow() {
        System.out.print("Enter Registration ID to Modify (or 'cancel'): ");
        // BodyStats stats = m.getBodyStats() != null ? m.getBodyStats() : new BodyStats();
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("cancel")) return;

        try {
            int id = Integer.parseInt(input);
            Member m = memberService.getMember(id);
            if (m == null) {
                System.out.println("[!] MODIFY FAILED: Member ID '" + id + "' not found.");
                return;
            }

            System.out.println("\n--- Modifying Member: " + m.getName() + " (Press Enter to skip) ---");

            // 1. Personal Details
            System.out.print("New Name (current: " + m.getName() + "): ");
            String name = scanner.nextLine(); if (!name.isEmpty()) m.setName(name);

            System.out.print("New Gmail (current: " + m.getGmail() + "): ");
            String gmail = scanner.nextLine(); if (!gmail.isEmpty()) m.setGmail(gmail);

            System.out.print("New Phone (current: " + m.getPhoneNum() + "): ");
            String phone = scanner.nextLine(); if (!phone.isEmpty()) m.setPhoneNum(phone);

            System.out.print("New Address (current: " + m.getAddress() + "): ");
            String addr = scanner.nextLine(); if (!addr.isEmpty()) m.setAddress(addr);

            // 2. Dates
            if (promptYesNo("Change Joining Date?")) m.setJoinDate(promptForDate("Date of Join"));
            if (promptYesNo("Change Date of Birth?")) {
                Date dob = promptForDate("Date of Birth");
                m.setDateOfBirth(dob);
                m.setAge(m.caluAge(dob.getYear()));
            }

            // 3. Gender (M/F)
            System.out.print("New Gender [M/F] (current: " + m.getGender() + "): ");
            String g = scanner.nextLine().toUpperCase();
            if (g.equals("M") || g.equals("F")) m.setGender(g);

            // 4. Body Stats (BMI Recalculated)
            BodyStats stats = m.getBodyStats() != null ? m.getBodyStats() : new BodyStats();
            System.out.print("New Weight kg (current: " + stats.getWeight() + "): ");
            String wIn = scanner.nextLine();
            if (!wIn.isEmpty()) stats.setWeight(Double.parseDouble(wIn));

            System.out.print("New Height m (current: " + stats.getHeight() + "): ");
            String hIn = scanner.nextLine();
            if (!hIn.isEmpty()) stats.setHeight(Double.parseDouble(hIn));

            System.out.print("New Body Fat % (current: " + stats.getBodyFatPercentage() + "): ");
            String fIn = scanner.nextLine();
            if (!fIn.isEmpty()) stats.setBodyFatPercentage(Double.parseDouble(fIn));
            
            if (stats.getHeight() > 0) stats.setBmi(stats.getWeight() / (stats.getHeight() * stats.getHeight()));
            m.setBodyStats(stats);

            // 5. Finance
            // if (m.getPayment() == null) m.setPayment(new Payment());
            // System.out.print("New Balance (current: $" + m.getPayment().getOutstandingBalance() + "): ");
            // String bal = scanner.nextLine();
            // if (!bal.isEmpty()) m.getPayment().setOutstandingBalance(Double.parseDouble(bal));
            if (m.getPayment() == null) m.setPayment(new Payment());
            Payment pay = m.getPayment();

            System.out.print("New Balance (current: $" + pay.getOutstandingBalance() + "): ");
            String bal = scanner.nextLine();
            if (!bal.isEmpty()) pay.setOutstandingBalance(Double.parseDouble(bal));

            System.out.print("New Card Holder (current: " + pay.getCreditCardAccountHolder() + "): ");
            String holder = scanner.nextLine();
            if (!holder.isEmpty()) pay.setCreditCardAccountHolder(holder);

            System.out.print("New Card Number (current: " + pay.getCreditCardNum() + "): ");
            String cardNum = scanner.nextLine();
            if (!cardNum.isEmpty()) pay.setCreditCardNum(cardNum);

            // 6. Fitness Goal
            System.out.print("New Fitness Goal (current: " + m.getFitnessGoal() + "): ");
            String goal = scanner.nextLine(); if (!goal.isEmpty()) m.setFitnessGoal(goal);

            // 7. Plan & Trainer
            if (promptYesNo("Change Member Plan?")) {
                List<MemberPlan> plans = memberService.getAllMemberPlans();
                for (int i = 0; i < plans.size(); i++) {
                    System.out.println(i + ". " + plans.get(i).getPlanId() + " (" + plans.get(i).getPlanName() + ")");
                }
                System.out.print("Select Plan No: ");
                m.setCurrentPlan(plans.get(Integer.parseInt(scanner.nextLine())));
            }
            // if (promptYesNo("Change Member Plan?")) {
            //     List<MemberPlan> plans = memberService.getAllMemberPlans();
            //     for (int i = 0; i < plans.size(); i++) System.out.println(i + ". " + plans.get(i).getPlanName());
            //     m.setCurrentPlan(plans.get(Integer.parseInt(scanner.nextLine())));
            // }

            if (m.getCurrentPlan() != null && m.getCurrentPlan().isPersonalTrainerIncluded()) {
                if (promptYesNo("Change Assigned Trainer?")) {
                    List<Trainer> trainers = dataService.getAvailableTrainers();
                    for (int i = 0; i < trainers.size(); i++) System.out.println(i + ". " + trainers.get(i).getName());
                    m.setAssignedTrainerId(trainers.get(Integer.parseInt(scanner.nextLine())).getRegId());
                }
            }

            memberService.updateMember(id, m);
            System.out.println("[SUCCESS] Member information updated fully.");
        } catch (Exception e) {
            System.out.println("[!] ERROR: Update failed. Check inputs.");
        }
    }

    // --- UC-1: SEARCH MEMBER (Alternative Flow) ---
    private void searchMemberFlow() {
        System.out.print("Enter Member Registration Number: ");
        String input = scanner.nextLine();
        try {
            int id = Integer.parseInt(input);
            MemberProfileDTO profile = memberService.getMemberProfile(id);
            Member m = profile.getMember();

            // REFRESH PLAN DATA: Fetch the latest version of the plan from the system
            if (m.getCurrentPlan() != null) {
                String currentPlanId = m.getCurrentPlan().getPlanId();
                memberService.getAllMemberPlans().stream()
                    .filter(p -> p.getPlanId().equals(currentPlanId))
                    .findFirst()
                    .ifPresent(m::setCurrentPlan); // Update member's plan snapshot to latest version
            }
            System.out.println("\n================ MEMBER PROFILE ================");
            System.out.println("Registration ID : " + m.getRegId());
            System.out.println("Name            : " + m.getName());
            System.out.println("Gender / Age    : " + m.getGender() + " / " + m.getAge() + " years old");
            System.out.println("Email           : " + m.getGmail());
            System.out.println("Address         : " + m.getAddress());
            System.out.println("Date of Birth   : " + (m.getDateOfBirth() != null ? m.getDateOfBirth() : "N/A"));
            System.out.println("Join Date       : " + (m.getJoinDate() != null ? m.getJoinDate() : "N/A"));
            System.out.println("Fitness Goal    : " + (m.getFitnessGoal() != null ? m.getFitnessGoal() : "N/A"));
            
            System.out.println("\n--- Body Statistics ---");
            System.out.printf("Weight: %.1f kg | Height: %.2f m | Body Fat: %.2f%% | BMI: %.2f\n", 
                m.getBodyStats().getWeight(), 
                m.getBodyStats().getHeight(), 
                m.getBodyStats().getBodyFatPercentage(), 
                m.getBodyStats().getBmi());

            System.out.println("\n--- Plan Details ---");
            if (m.getCurrentPlan() != null) {
                MemberPlan p = m.getCurrentPlan();
                System.out.println("Current Plan    : " + p.getPlanId() + " (" + p.getPlanName() + ")");
                
                // Fetch machine names from DataService
                List<Machine> allMachines = dataService.getAllMachines();
                System.out.print("Machine Access  : ");
                if (p.isAccessAllMachines()) {
                    System.out.println("ALL MACHINES");
                } else {
                    List<String> names = p.getAccessibleMachineIds().stream()
                        .map(mid -> allMachines.stream().filter(mac -> String.valueOf(mac.getRegId()).equals(mid))
                        .map(Machine::getName).findFirst().orElse("Unknown"))
                        .toList();
                    System.out.println(String.join(", ", names));
                }
            } else {
                System.out.println("Current Plan    : None");
            }
            System.out.println("\n--- Trainer  ---");
            System.out.println("Assigned Trainer: " + profile.getTrainerName());

            System.out.println("\n--- Finance ---");
            System.out.println("Pending Balance : $" + profile.getOutstandingBalance());
            // SHOW CREDIT CARD INFO
            if (m.getPayment() != null && m.getPayment().getCreditCardNum() != null) {
                Payment pay = m.getPayment();
                String rawNum = pay.getCreditCardNum();
                // Masking the card number for the demo (e.g., 1234-****-****-5678)
                // String masked = (rawNum.length() >= 16) ? 
                //     rawNum.substring(0, 4) + "-****-****-" + rawNum.substring(rawNum.length()-4) : "Invalid Number Format";
                
                System.out.println("Card Holder     : " + pay.getCreditCardAccountHolder());
                System.out.println("Card Number     : " + rawNum);
            } else {
                System.out.println("Payment Method  : No Card Linked");
            }
            System.out.println("================================================");

        } catch (Exception e) {
            System.out.println("[!] Member not found or invalid ID.");
        }
    }

    // --- UC-1: ALTERNATIVE FLOW (VIEW ALL) ---
    private void viewAllMembersFlow(List<Member> all) {
        System.out.println("\n--- View All Members Screen ---");
        List<MemberPlan> latestPlans = memberService.getAllMemberPlans();
        System.out.printf("%-10s | %-15s | %-10s | %-12s | %-28s | %-10s\n", 
                "Reg ID", "Name", "Gender", "Join Date", "Member Plan", "Trainer");
        System.out.println("---------------------------------------------------------------------------------------");
        for (Member m : all) {
            String planDisplay = "None";
                if (m.getCurrentPlan() != null) {
                String targetId = m.getCurrentPlan().getPlanId();
                
                // Find the latest name/details for this Plan ID
                String latestName = latestPlans.stream()
                    .filter(p -> p.getPlanId().equals(targetId))
                    .map(MemberPlan::getPlanName)
                    .findFirst()
                    .orElse(m.getCurrentPlan().getPlanName()); // Fallback to old name if plan was deleted

                planDisplay = targetId + " (" + latestName + ")";
            }
            
            // SAFE CHECK for Trainer ID
            String trainer = "N/A";
            if (m.getAssignedTrainerId() != null) {
                trainer = String.valueOf(m.getAssignedTrainerId());
            }

            String joinDateStr = (m.getJoinDate() != null) ? m.getJoinDate().toString() : "N/A";
            
            System.out.printf("%-10d | %-15s | %-10s | %-12s | %-28s | %-10s\n",
                    m.getRegId(), m.getName(), m.getGender(), joinDateStr, planDisplay, trainer);
        }
    }

    // --- UC-1: REMOVE MEMBER (Alternative Flow) ---
    private void removeMemberFlow() {
        System.out.print("Enter Member Registration Number to Remove: ");
        String input = scanner.nextLine();
        
        try {
            int id = Integer.parseInt(input);
            Member m = memberService.getMember(id);
            
            if (m == null) {
                System.out.println("[!] REMOVE FAILED: Member ID '" + id + "' not found.");
                return;
            }

            memberService.removeMember(id);
            System.out.println("[SUCCESS] Member successfully removed from the system.");
        } catch (NumberFormatException e) {
            System.out.println("[!] ERROR: ID must be numeric.");
        }
    }

    // --- UC-2: MANAGE PLANS ---
    private void managePlans() {
        while (true) {
            System.out.println("\n--- UC-2: MANAGE MEMBER PLANS ---");
            System.out.println("1. Add Member Plan");
            System.out.println("2. Search Plan");
            System.out.println("3. Modify Plan");
            System.out.println("4. View All Plans");
            System.out.println("5. Remove Plan");
            System.out.println("6. Back");
            System.out.print("Choice: ");
            String choice = scanner.nextLine();

            List<MemberPlan> all = memberService.getAllMemberPlans();
            if (!choice.equals("1") && !choice.equals("6") && all.isEmpty()) {
                System.out.println("[!] ERROR: No member plan records found. Please add a plan first.");
                continue;
            }

            switch (choice) {
                case "1" -> addPlanFlow();
                case "2" -> searchPlanFlow();
                case "3" -> modifyPlanFlow();
                case "4" -> viewAllPlansFlow(all);
                case "5" -> removePlanFlow();
                case "6" -> { return; }
            }
        }
    }

    // Add this helper method at the bottom of GymTerminalApp
    private boolean promptYesNo(String message) {
        while (true) {
            System.out.print(message + " (y/n): ");
            String in = scanner.nextLine().toLowerCase();
            if (in.equals("y")) return true;
            if (in.equals("n")) return false;
            System.out.println("[!] Please type 'y' for Yes or 'n' for No.");
        }
    }

    // --- UC-2: MAIN FLOW (ADD PLAN) ---
    private void addPlanFlow() {
        System.out.println("\n--- [Add Member Plan Form] (Type 'cancel' to exit) ---");
        
        System.out.println("Step 4: Generate unique Plan ID...");
        String planId = "PLAN-" + UUID.randomUUID().toString().substring(0, 5).toUpperCase();
        System.out.println("Generated ID: " + planId);

        MemberPlan p = new MemberPlan();
        p.setPlanId(planId);

        // Plan Details
        System.out.print("Plan Name (e.g., Basic, Premium, VIP): ");
        String name = scanner.nextLine();
        if (name.equalsIgnoreCase("cancel")) return;
        if (name.isEmpty()) { System.out.println("[!] Step 8: Validation Failed - Plan Name is required."); return; }
        p.setPlanName(name);

        // Duration Validation (M/Q/A)
        while (true) {
            System.out.print("Duration [M]onthly, [Q]uarterly, [A]nnual: ");
            String dur = scanner.nextLine().toUpperCase();
            if (dur.equals("M")) { p.setDuration("Monthly"); break; }
            if (dur.equals("Q")) { p.setDuration("Quarterly"); break; }
            if (dur.equals("A")) { p.setDuration("Annual"); break; }
            System.out.println("[!] Invalid choice. Type M, Q, or A.");
        }

        System.out.print("Plan Price: ");
        try {
            p.setPrice(Double.parseDouble(scanner.nextLine()));
        } catch (Exception e) { System.out.println("[!] Step 8: Validation Failed - Invalid Price format."); return; }

        System.out.print("Plan Description: ");
        p.setDescription(scanner.nextLine());

        // Yes/No Validation Helper
        // Capture the result in a local variable named 'allAccess'
        boolean allAccess = promptYesNo("Access to all machines?");
        
        // Pass that variable to the object
        p.setAccessAllMachines(allAccess);
        
        p.setPersonalTrainerIncluded(promptYesNo("Personal trainer included?"));
        p.setLockerAccess(promptYesNo("Locker access?"));

        // Step 6: Conditional Machine Access
        if (!allAccess) {
            System.out.println("\nStep 6.1: Display list of all machines...");
            List<Machine> machines = dataService.getAllMachines();
            if (machines.isEmpty()) {
                System.out.println("[!] No machines found in system to select.");
            } else {
                System.out.println("ID | Name | Brand | Model | Type");
                System.out.println("------------------------------------");
                for (int i = 0; i < machines.size(); i++) {
                    Machine m = machines.get(i);
                    System.out.printf("%d | %s | %s | %s | %s\n", i, m.getName(), m.getBrand(), m.getModel(), m.getType());
                }
                System.out.print("Select machines (enter indices comma-separated, e.g., 0,2,3): ");
                String[] indices = scanner.nextLine().split(",");
                List<String> selectedIds = new ArrayList<>();
                for (String idx : indices) {
                    // selectedIds.add(machines.get(Integer.parseInt(idx.trim())).getRegId());
                    // Fix: Use String.valueOf() to convert the int ID to a String for the List
                    selectedIds.add(String.valueOf(machines.get(Integer.parseInt(idx.trim())).getRegId()));
                }
                p.setAccessibleMachineIds(selectedIds);
            }
        }

        memberService.addMemberPlan(p);
        System.out.println("[SUCCESS] New member plan record created.");
    }

    // --- UC-2: ALTERNATIVE FLOW (SEARCH/VIEW DETAILS) ---
    private void searchPlanFlow() {
        System.out.print("Enter Plan ID or Plan Name to Search: ");
        String query = scanner.nextLine();
        
        Optional<MemberPlan> result = memberService.getAllMemberPlans().stream()
                .filter(p -> p.getPlanId().equalsIgnoreCase(query) || p.getPlanName().equalsIgnoreCase(query))
                .findFirst();

        if (result.isPresent()) {
            MemberPlan p = result.get();
            System.out.println("\n--- [VIEW PLAN INFORMATION] ---");
            System.out.println("Plan ID: " + p.getPlanId());
            System.out.println("Plan Name: " + p.getPlanName());
            System.out.println("Duration: " + p.getDuration());
            System.out.println("Price: $" + p.getPrice());
            System.out.println("Description: " + p.getDescription());
            System.out.println("Personal Trainer Included: " + (p.isPersonalTrainerIncluded() ? "Yes" : "No"));
            System.out.println("Locker Access: " + (p.isLockerAccess() ? "Yes" : "No"));
            System.out.println("Access All Machines: " + (p.isAccessAllMachines() ? "Yes" : "No"));
            // SHOW ACCESSIBLE MACHINES (ID + Name)
            System.out.print("Accessible Machines: ");
            if (p.isAccessAllMachines()) {
                System.out.println("ALL");
            } else if (p.getAccessibleMachineIds() == null || p.getAccessibleMachineIds().isEmpty()) {
                System.out.println("None Selected");
            } else {
                List<Machine> allMachines = dataService.getAllMachines();
                List<String> displayList = new ArrayList<>();
                for (String id : p.getAccessibleMachineIds()) {
                    String machineName = allMachines.stream()
                        .filter(m -> String.valueOf(m.getRegId()).equals(id))
                        .map(Machine::getName)
                        .findFirst().orElse("Unknown");
                    displayList.add(id + " (" + machineName + ")");
                }
                System.out.println(displayList.isEmpty() ? "None Selected" : String.join(", ", displayList));
            }
        } else {
            System.out.println("[!] Plan not found.");
        }
    }

    // --- UC-2: ALTERNATIVE FLOW (MODIFY) ---
    private void modifyPlanFlow() {
        System.out.print("Enter Plan ID to Modify: ");
        String id = scanner.nextLine();
        
        Optional<MemberPlan> existing = memberService.getAllMemberPlans().stream()
                .filter(p -> p.getPlanId().equalsIgnoreCase(id))
                .findFirst();

        if (existing.isEmpty()) {
            System.out.println("[!] SEARCH PLAN Failed: Not found.");
            return;
        }

        MemberPlan p = existing.get();
        boolean isPlanUsed = memberService.getAllMembers().stream()
                .anyMatch(m -> m.getCurrentPlan() != null && m.getCurrentPlan().getPlanId().equals(p.getPlanId()));

        System.out.println("--- Modifying Plan Details (Press Enter to keep current) ---");

        System.out.print("New Name (" + p.getPlanName() + "): ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) p.setPlanName(name);

        System.out.print("New Price ($" + p.getPrice() + "): ");
        String price = scanner.nextLine();
        if (!price.isEmpty()) p.setPrice(Double.parseDouble(price));

        System.out.print("New Description: ");
        String desc = scanner.nextLine();
        if (!desc.isEmpty()) p.setDescription(desc);

        // System.out.print("New Duration [M]onthly, [Q]uarterly, [A]nnual: ");
        // String duration = scanner.nextLine();
        // if (!duration.isEmpty()) p.setDuration(duration);
        while (true) {
            System.out.print("New Duration [M,Q,A] (" + p.getDuration() + "): ");
            String dur = scanner.nextLine().toUpperCase();
            if (dur.equals("M")) { p.setDuration("Monthly"); break; }
            if (dur.equals("Q")) { p.setDuration("Quarterly"); break; }
            if (dur.equals("A")) { p.setDuration("Annual"); break; }
            System.out.println("[!] Invalid choice. Type M, Q, or A.");
        }

        System.out.print("Locker Access (" + (p.isLockerAccess() ? "Yes" : "No") + ") [y/n]: ");
        String lockerIn = scanner.nextLine().toLowerCase();
        if (!lockerIn.isEmpty()) {
            p.setLockerAccess(lockerIn.equals("y"));
        }

        // Accessible Machines (Modifiable)
        System.out.println("Current machine access: " + (p.isAccessAllMachines() ? "ALL" : p.getAccessibleMachineIds()));
        boolean allAccess = promptYesNo("Access to all machines?");
        p.setAccessAllMachines(allAccess);

        if (!allAccess) {
            List<Machine> machines = dataService.getAllMachines();
            System.out.println("\n--- Available Machines ---");
            for (int i = 0; i < machines.size(); i++) {
                System.out.printf("%d | %s (%s)\n", i, machines.get(i).getName(), machines.get(i).getRegId());
            }
            System.out.print("Select machines (enter machine id(s): 0,2,3): ");
            String input = scanner.nextLine();
            if (!input.isEmpty()) {
                String[] indices = input.split(",");
                List<String> selectedIds = new ArrayList<>();
                for (String idx : indices) {
                    try {
                        selectedIds.add(String.valueOf(machines.get(Integer.parseInt(idx.trim())).getRegId()));
                    } catch (Exception e) {}
                }
                p.setAccessibleMachineIds(selectedIds);
            }
        } else {
            p.setAccessibleMachineIds(new ArrayList<>()); // Clear list if ALL access is granted
        }

        // STRICT FIELDS: Only if not used
        if (!isPlanUsed) {
            p.setPersonalTrainerIncluded(promptYesNo("Personal trainer included?"));
        } else {
            System.out.println("[NOTE] Feature \"PersonalTrainerIncluded\" can't be modified because this plan is currently in use by members.");
        }

        memberService.addMemberPlan(p); // Overwrites existing in file
        System.out.println("[SUCCESS] System updates Plan information successfully.");
    }

    // --- UC-2: ALTERNATIVE FLOW (VIEW ALL) ---
    private void viewAllPlansFlow(List<MemberPlan> all) {
        System.out.println("\n--- View All Plans Screen ---");
        System.out.printf("%-10s | %-15s | %-10s | %-8s | %-15s | %-10s\n", 
                "Plan ID", "Plan Name", "Duration", "Price", "All Machines", "Trainer");
        System.out.println("-----------------------------------------------------------------------------------");
        for (MemberPlan p : all) {
            System.out.printf("%-10s | %-15s | %-10s | $%-7.2f | %-15s | %-10s\n",
                    p.getPlanId(), p.getPlanName(), p.getDuration(), p.getPrice(), 
                    (p.isAccessAllMachines() ? "Yes" : "No"), 
                    (p.isPersonalTrainerIncluded() ? "Yes" : "No"));
        }
    }

    private void removePlanFlow() {
        System.out.println("\n--- UC-2 Step 5: Remove Member Plan ---");
        System.out.print("Enter Plan ID to Remove: ");
        String id = scanner.nextLine();

        // 1. Original Verification check
        boolean exists = memberService.getAllMemberPlans().stream()
                .anyMatch(p -> p.getPlanId().equalsIgnoreCase(id));

        if (exists) {
            try {
                // 2. Attempt removal (The Service will throw exception if plan is in use)
                memberService.removeMemberPlan(id);
                System.out.println("[SUCCESS] Successfully removed Plan.");
            } catch (RuntimeException e) {
                // 3. Catch the "In Use" error message from the service
                System.out.println("[!] ERROR: " + e.getMessage());
            }
        } else {
            System.out.println("[!] ERROR: Plan ID '" + id + "' not found.");
        }
    }

    // --- MEMBER SECTION (UC-3 & UC-4) ---
    private void memberMenu(Member m) {
        while (true) {
            System.out.println("\n--- MEMBER DASHBOARD (" + m.getName() + ") ---");
            System.out.println("1. Update Body Stats (UC-3)");
            System.out.println("2. View Profile & History (UC-4)");
            System.out.println("3. Logout");
            System.out.print("Choice: ");
            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                updateBodyStatsFlow(m);
            } else if (choice.equals("2")) {
                viewProfileFlow(m.getRegId());
            } else break;
        }
    }

    private void updateBodyStatsFlow(Member m) {
        System.out.println("\n--- UC-3: UPDATE BODY STATS ---");
        System.out.println("1. Update All Fields");
        System.out.println("2. Update Specific Field");
        System.out.print("Choice: ");
        String subChoice = scanner.nextLine();

        // Get a copy of latest stats to modify
        BodyStats latest = m.getLatestStats();
        BodyStats newEntry = new BodyStats(); 
        newEntry.setWeight(latest.getWeight());
        newEntry.setHeight(latest.getHeight());
        newEntry.setBodyFatPercentage(latest.getBodyFatPercentage());

        if (subChoice.equals("1")) {
            System.out.print("Enter Weight (kg): "); newEntry.setWeight(Double.parseDouble(scanner.nextLine()));
            System.out.print("Enter Height (m): "); newEntry.setHeight(Double.parseDouble(scanner.nextLine()));
            System.out.print("Enter Body Fat %: "); newEntry.setBodyFatPercentage(Double.parseDouble(scanner.nextLine()));
        } else {
            System.out.println("1. Weight | 2. Height | 3. Body Fat %");
            System.out.print("Select field to update: ");
            String field = scanner.nextLine();
            System.out.print("Enter new value: ");
            double val = Double.parseDouble(scanner.nextLine());
            
            if (field.equals("1")) newEntry.setWeight(val);
            else if (field.equals("2")) newEntry.setHeight(val);
            else if (field.equals("3")) newEntry.setBodyFatPercentage(val);
        }

        // Recalculate BMI and Save
        if (newEntry.getHeight() > 0) {
            newEntry.setBmi(newEntry.getWeight() / (newEntry.getHeight() * newEntry.getHeight()));
        }
        
        // This will append to the list in your Service/Repo
        memberService.updateBodyStats(m.getRegId(), newEntry);
        System.out.println("[SUCCESS] New record added to your history.");
    }

    private void viewProfileFlow(int id) {
        MemberProfileDTO profile = memberService.getMemberProfile(id);
        if (profile == null || profile.getMember() == null) {
            System.out.println("[!] ERROR: Could not retrieve profile.");
            return;
        }
        
        Member m = profile.getMember();

        System.out.println("\n==================== FULL MEMBER PROFILE ====================");
        
        // --- Section 1: Personal Details ---
        System.out.println("Registration ID : " + m.getRegId());
        System.out.println("Name            : " + m.getName());
        System.out.println("Email           : " + m.getGmail());
        System.out.println("Phone Number    : " + m.getPhoneNum());
        System.out.println("Address         : " + m.getAddress());
        System.out.println("Gender / Age    : " + m.getGender() + " / " + m.getAge() + " years old");
        System.out.println("Date of Birth   : " + (m.getDateOfBirth() != null ? m.getDateOfBirth() : "N/A"));
        System.out.println("Joining Date    : " + (m.getJoinDate() != null ? m.getJoinDate() : "N/A"));
        System.out.println("Fitness Goal    : " + (m.getFitnessGoal() != null ? m.getFitnessGoal() : "N/A"));

        // --- Section 2: Body Statistics History (UC-3/4) ---
        System.out.println("\n--- Body Stats History ---");
        System.out.printf("%-12s | %-8s | %-8s | %-10s | %-8s\n", "Date", "Weight", "Height", "Fat %", "BMI");
        System.out.println("------------------------------------------------------------");
        
        List<BodyStats> history = m.getBodyStatsHistory() != null ? m.getBodyStatsHistory() : new ArrayList<>();
        if (history.isEmpty()) {
            System.out.println("          No history records found.");
        } else {
            for (BodyStats s : history) {
                String dateStr = (s.getRecordDate() != null) ? s.getRecordDate().toString() : "N/A";
                System.out.printf("%-12s | %-8.1f | %-8.2f | %-10.1f | %-8.2f\n",
                    dateStr, s.getWeight(), s.getHeight(), s.getBodyFatPercentage(), s.getBmi());
            }
        }

        // --- Section 3: Plan & Machines (With Names) ---
        System.out.println("\n--- Plan Details ---");
        if (m.getCurrentPlan() != null) {
            System.out.println("Current Plan    : " + m.getCurrentPlan().getPlanId() + " (" + m.getCurrentPlan().getPlanName() + ")");
            System.out.print("Machine Access  : ");
            if (profile.getAccessibleMachineDetails() != null) {
                System.out.println(String.join(", ", profile.getAccessibleMachineDetails()));
            } else {
                System.out.println("No Access Details Found");
            }
        } else {
            System.out.println("Current Plan    : None Assigned");
        }

        // --- Section 4: Trainer & Finance ---
        System.out.println("\n--- Assignment & Finance ---");
        System.out.println("Assigned Trainer: " + profile.getTrainerName());
        System.out.println("Pending Balance : $" + profile.getOutstandingBalance());
        
        if (m.getPayment() != null) {
            Payment pay = m.getPayment();
            System.out.println("Card Holder     : " + (pay.getCreditCardAccountHolder() != null ? pay.getCreditCardAccountHolder() : "N/A"));
            System.out.println("Card Number     : " + (pay.getCreditCardNum() != null ? pay.getCreditCardNum() : "N/A"));
        } else {
            System.out.println("Payment Info    : No card on file");
        }
        
        System.out.println("=============================================================");
    }
}