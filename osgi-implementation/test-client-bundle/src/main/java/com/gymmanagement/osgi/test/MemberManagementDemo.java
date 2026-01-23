package com.gymmanagement.osgi.test;

import java.util.ArrayList;

import com.gymmanagement.osgi.base.entity.*;
import com.gymmanagement.osgi.base.service.*;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import java.util.List;
import java.util.Arrays;

public class MemberManagementDemo {
    private final BundleContext context;

    public MemberManagementDemo(BundleContext context) {
        this.context = context;
    }

    private <T> T getService(Class<T> serviceClass) {
        try {
            ServiceReference<T> ref = context.getServiceReference(serviceClass);
            if (ref == null) return null;
            T service = context.getService(ref);
            return service;
        } catch (Exception e) {
            return null;
        }
    }

    private IMachineService getMachineService() {
        ServiceReference<IMachineService> ref = context.getServiceReference(IMachineService.class);
        if (ref == null) {
            System.out.println("⚠️ Machine Service Reference not found in Registry!");
            return null;
        }
        return context.getService(ref);
    }
    
    public void seedMyPart() {
        IMachineService machineService = getService(IMachineService.class);
        ITrainerService trainerService = getService(ITrainerService.class);
        IMemberService memberService = getService(IMemberService.class);
        
        if (memberService == null || machineService == null || trainerService == null) {
            System.out.println("❌ Error: One or more services not found.");
            return;
        }

        // STEP 1: WIPE OLD DATA (UC-1 & UC-2 Reset)
        // memberService.getAllMembers().clear();
        // memberService.getAllMemberPlans().clear();

        System.out.println("Performing Data Reset...");
        memberService.deleteAllData();      // Wipes Members and Plans
        machineService.deleteAllMachines(); // Wipes Machines
        trainerService.deleteAllTrainers(); // Wipes Trainers

        // STEP 2: Seed Machines
        machineService.addMachine(new Machine(101, "Treadmill X1", "TechnoGym", "X1", 100, 50, "Cardio"));
        machineService.addMachine(new Machine(102, "Bench Press Pro", "Hammer Strength", "H2", 200, 80, "Strength"));
        machineService.addMachine(new Machine(103, "Leg Press Elite", "Life Fitness", "LP300", 250, 100, "Strength"));

        // STEP 3: Seed Trainers
        trainerService.addTrainer(new Trainer(201, "Master Yoda", "yoda@gmail.com", "012-1234567", "PJ", new Date(80,0,1), new Date(124,0,1), 900, "M", "Force", 50, 0, "Expert"));
        trainerService.addTrainer(new Trainer(202, "Michael Tan", "michael@gmail.com", "013-1234567", "KL", new Date(90,5,15), new Date(123,3,10), 850, "M", "Strength", 40, 5, "Advanced"));
        trainerService.addTrainer(new Trainer(203, "Sarah Lim", "sarah@gmail.com", "014-1234567", "KL", new Date(90,5,15), new Date(123,3,10), 850, "F", "Strength", 40, 5, "Intermediate"));

        // STEP 4: Seed a Plan (Crucial for the Member Constructor)
        MemberPlan vipPlan = new MemberPlan("VIP", "Exclusive VIP", "Annually", 999.0, "All Access", true, true, true, "");
        MemberPlan silverVipPlan = new MemberPlan("VIP-SILVER", "VIP Silver", "Quarterly", 599.0, "VIP Access", true, true, false, "101,102");
        memberService.addMemberPlan(vipPlan);
        memberService.addMemberPlan(silverVipPlan);

        // STEP 5: Seed Member using the FULL CONSTRUCTOR
        Date joinDate = new Date(126, 0, 22); // 2026-01-22
        Date dob = new Date(95, 4, 20);      // 1995-05-20
        BodyStats stats = new BodyStats(1.75, 70.0, 22.0);
        Payment payment = new Payment(0.0, "John Doe", "1234");

        // Constructor: regId, name, gmail, phone, address, joinDate, dob, age, gender, h, w, payment, goal, bodyStats, plan, trainerId
        Member member1 = new Member(
            9406, "John Doe", "john@gmail.com", "0300-1234567", "123 Main St",
            joinDate, dob, 29, "Male", 1.75, 70.0,
            payment, "Weight Loss", stats, vipPlan, 201
        );

        // INITIALIZE HISTORY (So Row 1 isn't empty)
        member1.recordNewStats(stats);

        memberService.addMember(member1);

        System.out.println("✅ System Seeded: 3 Machines, 3 Trainers, 2 Plan, and 1 Member.");
    }

    // Helper to convert "2026-01-21" to your custom Date(1, 21, 2026)
    private Date parseCustomDate(String dateStr) {
        try {
            String[] parts = dateStr.split("-");
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[2]);
            return new Date(month, day, year); // Matches your custom Date constructor
        } catch (Exception e) {
            return new Date(1, 1, 2000); // Fallback
        }
    }

    // --- UC-1: Manage Members (Admin Flows) ---

    // Date: must be in format "2026-01-21"
    // Usage: gym:addMember "Name" "Email" "Phone" "Addr" "YYYY-MM-DD(DOB)" "M/F" 1.75 80.0 "Goal" 150.0 "Holder" "CC#"
    // --- 1. ADD MEMBER (Integrated Flow) ---
    public void addMember(String name, String email, String phone, String addr, String dobStr, String joinDateStr,
                          String gender, double h, double w, double fat, String goal, 
                          String planId, String trainerId, String cardHolder, String cardNum) {
        
        IMemberService memberService = getService(IMemberService.class);
        // ITrainerService trainerService = getService(ITrainerService.class);
        IPaymentService paymentService = getService(IPaymentService.class);

        // A. Setup Member Entity
        Member m = new Member();
        m.setName(name);
        m.setGmail(email); // Ensure email ends in @gmail.com for your current validation!
        m.setPhoneNum(phone);
        m.setAddress(addr);
        m.setGender(gender);
        m.setFitnessGoal(goal);

        m.setJoinDate(parseCustomDate(joinDateStr));
        m.setDateOfBirth(parseCustomDate(dobStr));

        // BMI + Body Fat
        if (h <= 0 || w <= 0 || fat <= 0) {
            System.out.println("Invalid Body Statistics: System detects negative numbers or 0.");
            return;
        }

        // B. BMI Calculation (UC-1 Step 8)
        BodyStats initialStats = new BodyStats(h, w, fat);
        initialStats.calculateBMI();
        m.setBodyStats(initialStats);
        m.recordNewStats(initialStats); // THIS ensures body stat history is NOT empty at start

        // FIX 2: Link Plan Safely
        if (planId != null) {
            MemberPlan memberPlan = memberService.getAllMemberPlans().stream()
                .filter(p -> p.getPlanId() != null) // Avoid NPE
                .filter(p -> p.getPlanId().equalsIgnoreCase(planId))
                .findFirst().orElse(null);
            
            if (memberPlan != null) {
                m.setCurrentPlan(memberPlan);
                m.setOutstandingBalance(memberPlan.getPrice());
            } else {
                System.out.println("⚠️ Warning: Plan ID " + planId + " not found. Member added with No Plan.");
            }
        }

        if (trainerId != null) m.setAssignedTrainerId(Integer.parseInt(trainerId));

        // F. Credit Card Data
        m.setCcHolder(cardHolder);
        m.setCcNumber(cardNum);

        System.out.println(memberService.addMember(m));
    }

    // Usage: gym:searchMember 101
    public void searchMember(int id) {
        IMemberService service = getService(IMemberService.class);
        IMachineService machineService = getService(IMachineService.class);
        Member m = service.getMember(id);
        if (m == null) { System.out.println("❌ Member " + id + " not found."); return; }

        System.out.println("\n========= MEMBER FULL PROFILE =========");
        System.out.println("Personal Info:");
        System.out.println(" > Name:      " + m.getName() + " (" + m.getGender() + ")");
        System.out.println(" > Email:     " + m.getGmail());
        System.out.println(" > Birth:     " + m.getDateOfBirth());
        System.out.println(" > Joined:    " + m.getJoinDate());
        
        System.out.println("\nPhysical Stats:");
        System.out.println(" > Height:    " + m.getBodyStats().getHeight() + "m");
        System.out.println(" > Weight:    " + m.getBodyStats().getWeight() + "kg");
        System.out.println(" > BMI:       " + String.format("%.2f", m.getBodyStats().getBmi()));
        System.out.println(" > Body Fat:  " + String.format("%.2f", m.getBodyStats().getBodyFatPercentage()));
        
        System.out.println("\nGym Plan:");
        MemberPlan p = m.getCurrentPlan();
        if (p != null) {
            System.out.println(" > Name:      " + p.getPlanName());
            System.out.println(" > Locker:    " + (p.isLockerAccess() ? "YES" : "NO"));
            System.out.println(" > Trainer:   " + (m.getAssignedTrainerId() != 0 ? "ID " + m.getAssignedTrainerId() : "None"));
            // System.out.println(" > Machines:  " + (p.isAccessAllMachines() ? "ALL MACHINES" : "Limited List"));
            // Machine lookup logic
            if (m.getCurrentPlan() != null) {
                MemberPlan plan = m.getCurrentPlan();
                System.out.print(" > Accessible Machines: ");
                if (plan.isAccessAllMachines()) {
                    System.out.println("ALL MACHINES");
                } else if (plan.getAccessibleMachineIds() != null) {
                    List<String> names = new ArrayList<>();
                    for (String mid : plan.getAccessibleMachineIds()) {
                        Machine mac = (machineService != null) ? machineService.getMachine(Integer.parseInt(mid.trim())) : null;
                        names.add(mac != null ? mac.getName() : mid);
                    }
                    System.out.println(String.join(", ", names));
                }
            }
        }

        System.out.println("\nFinancial Info:");
        System.out.println(" > Balance:   $" + m.getOutstandingBalance());
        System.out.println(" > Card Info: " + m.getCcHolder() + " (" + m.getCcNumber() + ")");
        System.out.println("========================================");
    }

    // --- 2. VIEW ALL MEMBERS ---
    public void viewAllMembers() {
        IMemberService service = getService(IMemberService.class);
        if (service == null) {
            System.out.println("❌ Member Service not found.");
            return;
        }

        List<Member> allMembers = service.getAllMembers();
        System.out.println("\n--- [UC-1] MEMBER LIST ---");
        System.out.printf("%-5s | %-20s | %-10s | %-20s | %-15s%n", "ID", "NAME", "GENDER", "PLAN", "TRAINER");
        System.out.println("-".repeat(85));
        
        if (allMembers == null || allMembers.isEmpty()) {
            System.out.println("No members found.");
            return;
        }

        for (Member m : allMembers) {
            // Null-safe field extraction
            String name = (m.getName() != null) ? m.getName() : "Unknown";
            String gender = (m.getGender() != null) ? m.getGender() : "N/A";
            String planName = (m.getCurrentPlan() != null && m.getCurrentPlan().getPlanName() != null) 
                            ? m.getCurrentPlan().getPlanName() : "None";
            
            // Trainer logic using the int primitive check (0 means no trainer)
            String trainerInfo = (m.getAssignedTrainerId() != 0) 
                                ? "ID: " + m.getAssignedTrainerId() : "N/A";
            
            System.out.printf("%-5d | %-20s | %-10s | %-20s | %-15s%n",
                m.getRegId(), name, gender, planName, trainerInfo);
                // m.getRegId(), 
                // (name != null ? m.getName() : "N/A"), 
                // (gender != null ? m.getGender() : "N/A"), 
                // planName, trainerInfo);
        }
        System.out.println("=".repeat(85));
    }

    // Usage: gym:removeMember 101
    public void removeMember(int id) {
        System.out.println(getService(IMemberService.class).deleteMember(id));
    }

    // --- 1. MODIFY MEMBER ---
    // Usage: gym:modifyMember 888 "New Name" "new@gmail.com" "012-345" "Addr" 1.75 85.0 20.0 "2024-01-01" "2000-01-01" "VIP-PLAN" "99"
    public void modifyMember(int id, String name, String email, String phone, String addr, 
                             double h, double w, double fat, String joinDateStr, String dobStr, String planId, String trainerId) {
        IMemberService memberService = getService(IMemberService.class);
        IPaymentService paymentService = getService(IPaymentService.class);
        
        Member m = memberService.getMember(id);
        if (m == null) { System.out.println("❌ Member " + id + " not found."); return; }

        // Update basic fields
        m.setName(name);
        m.setGmail(email);
        m.setPhoneNum(phone);
        m.setAddress(addr);

        m.setJoinDate(parseCustomDate(joinDateStr));
        m.setDateOfBirth(parseCustomDate(dobStr));

        // Update Physicals + Recalculate BMI
        BodyStats newStats = new BodyStats(h, w, fat);
        newStats.calculateBMI();
        m.setBodyStats(newStats);
        m.recordNewStats(newStats);

        // Check for Plan Change
        // SAFE PLAN LINKING: Prevents the NPE crash
        MemberPlan newPlan = memberService.getAllMemberPlans().stream()
            .filter(p -> p != null && p.getPlanId() != null) // THE CRITICAL SAFETY CHECK
            .filter(p -> p.getPlanId().equalsIgnoreCase(planId))
            .findFirst()
            .orElse(null);

        if (newPlan != null) {
            m.setCurrentPlan(newPlan);
        } else {
            System.out.println("⚠️ Warning: Plan " + planId + " not found. Plan unchanged.");
        }

        if (trainerId != null) m.setAssignedTrainerId(Integer.parseInt(trainerId));
        memberService.updateMember(m);
        System.out.println("✅ Member " + id + " information successfully updated.");
    }

    // --- UC-2: Manage Member Plans (Admin Flows) ---
    // --- 1. ADD MEMBER PLAN (Integrated Flow) ---
    // Usage: gym:addPlan "P01" "Gold" "A" 500.0 "Desc" false(AllAccess) true(Trainer) true(Locker) "101,102"
    public void addPlan(String id, String name, String durCode, double price, String desc, 
                        boolean allAccess, boolean trainer, boolean locker, String machineIds) {
        IMemberService service = getService(IMemberService.class);
        MemberPlan p = new MemberPlan();
        p.setPlanId(id);
        p.setPlanName(name);
        p.setPrice(price);
        p.setDescription(desc);
        p.setAccessAllMachines(allAccess);
        p.setPersonalTrainerIncluded(trainer);
        p.setLockerAccess(locker);

        // Duration Logic
        if (durCode.equalsIgnoreCase("M")) p.setDuration("Monthly");
        else if (durCode.equalsIgnoreCase("Q")) p.setDuration("Quarterly");
        else p.setDuration("Annual");

        // Machine Logic (Step 6)
        if (!allAccess && machineIds != null) {
            p.setAccessibleMachineIds(Arrays.asList(machineIds.split(",")));
        }

        service.addMemberPlan(p);
        System.out.println("[SUCCESS] Plan " + id + " created.");
    }

    // --- 2. SEARCH PLAN BY ID ---
    public void searchPlan(String id) {
        IMemberService service = getService(IMemberService.class);
        IMachineService macService = getService(IMachineService.class);
        
        MemberPlan p = service.getAllMemberPlans().stream()
            .filter(plan -> plan.getPlanId().equalsIgnoreCase(id)).findFirst().orElse(null);

        if (p == null) { System.out.println("[!] Plan not found."); return; }

        System.out.println("\n--- [VIEW PLAN INFORMATION] ---");
        System.out.println("Plan ID: " + p.getPlanId() + " | Name: " + p.getPlanName());
        System.out.println("Price: $" + p.getPrice() + " | Duration: " + p.getDuration());
        System.out.println("Trainer: " + (p.isPersonalTrainerIncluded()?"Yes":"No") + " | Locker: " + (p.isLockerAccess()?"Yes":"No"));
        
        System.out.print("Accessible Machines: ");
        if (p.isAccessAllMachines()) System.out.println("ALL");
        else {
            List<String> display = new ArrayList<>();
            for(String mid : p.getAccessibleMachineIds()) {
                Machine m = macService.getMachine(Integer.parseInt(mid));
                display.add(mid + " (" + (m!=null?m.getName():"Unknown") + ")");
            }
            System.out.println(String.join(", ", display));
        }
    }

    // --- 3. VIEW ALL TRAINERS ---
    public void listTrainers() {
        ITrainerService service = getService(ITrainerService.class);
        List<Trainer> trainers = service.getAllTrainers();

        System.out.println("\n" + "=".repeat(70));
        System.out.printf("%-6s | %-20s | %-20s | %-10s%n", "ID", "TRAINER NAME", "SPECIALIZATION", "LEVEL");
        System.out.println("-".repeat(70));

        for (Trainer t : trainers) {
            System.out.printf("%-6d | %-20s | %-20s | %-10s%n", 
                t.getRegId(), t.getName(), t.getSpecialization(), t.getExperienceLevel());
        }
        System.out.println("=".repeat(70));
    }

    // --- 4. REMOVE PLAN (With Usage Check) ---
    public void removePlan(String id) {
        IMemberService service = getService(IMemberService.class);
        if (service == null || id == null) return;

        // Check if any member is using this plan safely
        boolean inUse = service.getAllMembers().stream()
            .anyMatch(m -> m.getCurrentPlan() != null 
                        && m.getCurrentPlan().getPlanId() != null // CRITICAL NULL CHECK
                        && m.getCurrentPlan().getPlanId().equalsIgnoreCase(id));

        if (inUse) {
            System.out.println("❌ Cannot delete: Plan " + id + " is currently assigned to members.");
        } else {
            // You MUST call the service to actually delete it from the .dat file
            String result = service.deleteMemberPlan(id); 
            System.out.println(result);
        }
    }

    // --- 2. MODIFY PLAN (With Protected Access) ---
    // Usage: gym:modifyPlan "MY-VIP" "Super VIP" "A" 1200.0 "Description" true(AllAccess) true(Trainer) true(Locker) ""
    public void modifyPlan(String id, String name, String dur, double price, String desc, 
                           boolean allAcc, boolean trainer, boolean locker, String machineIds) {
        IMemberService service = getService(IMemberService.class);
        if (service == null) return;
        
        // Use a null-safe stream to find the plan
        MemberPlan existingPlan = service.getAllMemberPlans().stream()
            .filter(p -> p != null && p.getPlanId() != null) // CRITICAL: Skip nulls
            .filter(p -> p.getPlanId().equalsIgnoreCase(id))
            .findFirst()
            .orElse(null);

        if (existingPlan == null) {
            System.out.println("❌ Error: Plan ID '" + id + "' not found.");
            return;
        }

        existingPlan.setPlanName(name);
        existingPlan.setPrice(price);
        existingPlan.setDescription(desc);
        existingPlan.setDuration(dur.equalsIgnoreCase("A") ? "Annual" : dur.equalsIgnoreCase("Q") ? "Quarterly" : "Monthly");
        existingPlan.setAccessAllMachines(allAcc);
        existingPlan.setPersonalTrainerIncluded(trainer);
        existingPlan.setLockerAccess(locker);

        if (!allAcc && machineIds != null) {
            existingPlan.setAccessibleMachineIds(Arrays.asList(machineIds.split(",")));
        }

        service.addMemberPlan(existingPlan);
        System.out.println("✅ Plan '" + id + "' updated successfully.");
    }

    public void viewPlans() {
        IMemberService service = getService(IMemberService.class);
        if (service == null) return;

        List<MemberPlan> plans = service.getAllMemberPlans();
        System.out.println("\n" + "=".repeat(65));
        System.out.printf("%-15s | %-20s | %-12s | %-10s%n", "PLAN ID", "PLAN NAME", "PRICE", "DURATION");
        System.out.println("-".repeat(65));

        if (plans == null || plans.isEmpty()) {
            System.out.println("No plans found in system.");
        } else {
            for (MemberPlan p : plans) {
                // Null-safe ID and Name
                String id = (p.getPlanId() != null) ? p.getPlanId() : "N/A";
                String name = (p.getPlanName() != null) ? p.getPlanName() : "Unnamed";
                
                System.out.printf("%-15s | %-20s | $%-11.2f | %-10s%n", 
                    id, name, p.getPrice(), p.getDuration());
            }
        }
        System.out.println("=".repeat(65));
    }

    public void listMachines() {
        IMachineService service = getMachineService();
        
        // Safety Check: If the Machine bundle isn't started or the service isn't registered
        if (service == null) {
            System.out.println("❌ IMachineService is NULL. Please ensure Bundle 14 is ACTIVE.");
            return;
        }

        try {
            List<Machine> machineList = service.getAllMachines();
            System.out.println("\n--- [UC-2] GYM MACHINE INVENTORY ---");
            // System.out.printf("%-5s | %-15s | %-12s | %-10s%n", "ID", "Name", "Brand", "Type");
            System.out.printf("%-6s | %-20s | %-20s | %-12s%n", "ID", "MACHINE NAME", "BRAND", "TYPE");
            System.out.println("-".repeat(70));

            if (machineList == null || machineList.isEmpty()) {
                System.out.println("⚠️ No machines found.");
            } else {
                for (Machine m : machineList) {
                    System.out.printf("%-5d | %-15s | %-12s | %-10s%n", 
                        m.getRegId(), m.getName(), m.getBrand(), m.getType());
                }
            }
            System.out.println("=".repeat(70));
        } catch (Exception e) {
            System.out.println("❌ Error retrieving machine data: " + e.getMessage());
        }
    }

    // --- UC-3: Update Body Stats (Member Flow) ---

    // Usage: gym:updateStats 101 1.76 82.0 15.5(BodyFat)
    public void updateStats(int id, double h, double w, double fat) {
        // Step 8: Validation
        if (h <= 0 || w <= 0 || fat <= 0) {
            System.out.println("❌ Invalid Body Statistics: System detects negative numbers or 0.");
            return;
        }

        IMemberService service = getService(IMemberService.class);
        BodyStats stats = new BodyStats(h, w, fat);
        String result = service.updateBodyStats(id, stats); // Service handles BMI calc
        System.out.println("✅ " + result);
    }

    public void updateMemberStat(int id, String statType, double newValue) {
        IMemberService service = getService(IMemberService.class);
        Member m = (service != null) ? service.getMember(id) : null;

        if (m == null) {
            System.out.println("❌ Member ID " + id + " not found.");
            return;
        }

        // Get current stats to maintain other values
        BodyStats current = m.getBodyStats();
        double h = (current != null) ? current.getHeight() : 1.7;
        double w = (current != null) ? current.getWeight() : 70.0;
        double f = (current != null) ? current.getBodyFatPercentage() : 20.0;

        // Determine what to update
        String type = statType.toLowerCase();
        switch (type) {
            case "weight": w = newValue; break;
            case "height": h = newValue; break;
            case "fat":    f = newValue; break;
            default:
                System.out.println("❌ Invalid stat type. Use 'weight', 'height', or 'fat'.");
                return;
        }

        // Create fresh snapshot for history tracking
        BodyStats updatedStats = new BodyStats(h, w, f);
        updatedStats.calculateBMI();

        // Use your snapshot logic to ensure history rows are independent
        m.recordNewStats(updatedStats);
        
        service.updateMember(m);
        System.out.printf("✅ %s updated for %s. New Value: %.2f (New BMI: %.2f)%n", 
                        statType.toUpperCase(), m.getName(), newValue, updatedStats.getBmi());
    }

    // --- UC-4: View Profile & History (Member Flow) ---

    // Usage: gym:viewProfile 101
    public void viewProfile(int id) {
        IMemberService service = getService(IMemberService.class);
        ITrainerService trainerService = getService(ITrainerService.class);
        IMachineService machineService = getMachineService();
        
        Member m = service.getMember(id);
        if (m == null) { System.out.println("❌ Member not found."); return; }

        System.out.println("\n" + "=".repeat(50));
        System.out.println("       MEMBER PROFILE: " + m.getName().toUpperCase());
        System.out.println("=".repeat(50));
        
        // 1. Personal Profile
        // 1. Personal Profile
        System.out.println("[PERSONAL INFO]");
        System.out.printf("Email: %-20s | Phone: %s%n", m.getGmail(), m.getPhoneNum());
        System.out.printf("Address: %-18s | Join Date: %s%n", m.getAddress(), m.getJoinDate());
        System.out.printf("DOB: %-24s | Gender: %-10s%n", m.getDateOfBirth(), m.getGender()); 
        // Line deleted here
        System.out.printf("Balance: $%-18.2f | Card: %s (%s)%n", 
            m.getOutstandingBalance(), m.getCcHolder(), m.getCcNumber());
        // System.out.println("[PERSONAL INFO]");
        // System.out.printf("Email: %-20s | Phone: %s%n", m.getGmail(), m.getPhoneNum());
        // System.out.printf("Address: %-18s | Join Date: %s%n", m.getAddress(), m.getJoinDate());
        // System.out.printf("DOB: %-20s | Gender: %s%n", m.getDateOfBirth(), m.getGender());
        // System.out.printf("Join Date: %-20s | Gender: %s%n", m.getJoinDate(), m.getGender());
        // System.out.printf("Balance: $%-18.2f | Card: %s (%s)%n", 
        //     m.getOutstandingBalance(), m.getCcHolder(), m.getCcNumber());

        // 2. Member Plan Details
        System.out.println("\n[PLAN DETAILS]");
        MemberPlan p = m.getCurrentPlan();
        if (p != null) {
            System.out.println("Plan: " + p.getPlanName() + " (" + p.getDuration() + ")");
            
            // Trainer Logic
            // String trainerName = "No";
            // if (p.isPersonalTrainerIncluded()) {
            //     Trainer t = trainerService.getTrainer(m.getAssignedTrainerId());
            //     trainerName = (t != null) ? "Yes (" + t.getName() + ")" : "Yes (Unassigned)";
            // }
            String trainerDisplay = "No";
            if (m.getAssignedTrainerId() != 0) {
                // Fetch trainer name from the Trainer Service
                ITrainerService ts = getService(ITrainerService.class);
                Trainer t = (ts != null) ? ts.getTrainer(m.getAssignedTrainerId()) : null;
                trainerDisplay = (t != null) ? "Yes (" + t.getName() + ")" : "Yes (ID: " + m.getAssignedTrainerId() + ")";
            }
            System.out.println("Trainer: " + trainerDisplay);
            System.out.println("Locker Access: " + (p.isLockerAccess() ? "Yes" : "No"));

            // Machine Logic
            // Inside viewProfile
            System.out.print("Accessible Machines: ");
            if (p.isAccessAllMachines()) {
                System.out.println("ALL MACHINES");
            } else {
                List<String> machineIds = p.getAccessibleMachineIds();
                if (machineIds == null || machineIds.isEmpty()) {
                    System.out.println("None Selected");
                } else {
                    List<String> names = new ArrayList<>();
                    for (String mid : machineIds) {
                        // trim() handles accidental spaces in "101, 102"
                        int cleanId = Integer.parseInt(mid.trim());
                        Machine mac = (machineService != null) ? machineService.getMachine(cleanId) : null;
                        names.add(mac != null ? mac.getName() : "ID: " + mid);
                    }
                    System.out.println(String.join(", ", names));
                }
            }
        }

        // 3. Body Stats History (Table View)
        System.out.println("\n[BODY STATS HISTORY]");
        System.out.printf("%-12s | %-8s | %-8s | %-6s | %-10s%n", "Date", "Weight", "Height", "BMI", "Body Fat %");
        System.out.println("-".repeat(50));
        
        // Assuming your Member entity has a getStatsHistory() list
        for (BodyStats s : m.getStatsHistory()) {
            System.out.printf("%-12s | %-8.1f | %-8.2f | %-6.2f | %-10.1f%%%n", 
                s.getRecordDate(), s.getWeight(), s.getHeight(), s.getBmi(), s.getBodyFatPercentage());
        }
        System.out.println("=".repeat(50));
    }
}