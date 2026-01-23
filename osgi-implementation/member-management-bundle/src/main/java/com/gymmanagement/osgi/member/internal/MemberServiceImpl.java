package com.gymmanagement.osgi.member.internal;

// Note: @Component annotation removed - using XML-based component definition instead
// import org.osgi.service.component.annotations.Component;
import com.gymmanagement.osgi.base.entity.Member;
import com.gymmanagement.osgi.base.entity.BodyStats;
import com.gymmanagement.osgi.base.service.IMemberService;

import java.io.*;
import java.util.*;
// import java.util.concurrent.ConcurrentHashMap;
// import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
// import java.util.concurrent.ConcurrentHashMap;
// import java.util.stream.Collectors;

import com.gymmanagement.osgi.base.entity.MemberPlan;

/**
 * OSGi service implementation for Member Management
 * 
 * Note: Service registration is handled via XML descriptor:
 * OSGI-INF/com.gymmanagement.osgi.member.service.xml
 */
public class MemberServiceImpl implements IMemberService {
    
    private int nextRegId = 1;

    private Map<Integer, Member> members = new HashMap<>();
    private Map<String, MemberPlan> plans = new HashMap<>();

    // The file will be created in your Felix root folder
    // private static final String DATA_FILE = "gym.dat";
    private final String MEMBER_FILE = "members_data.dat";
    private final String PLAN_FILE = "plans_data.dat";

    public MemberServiceImpl() {
        loadMemberPlan();
        loadMember();
    }

    private synchronized void saveMember() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(MEMBER_FILE))) {
            oos.writeObject(new ArrayList<>(members.values()));
            System.out.println("[IO] Members written to file.");
        } catch (IOException e) { System.err.println("Save Member Error: " + e.getMessage()); }
    }

    // --- 2. SAVE MEMBER PLAN ---
    private synchronized void saveMemberPlan() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PLAN_FILE))) {
            oos.writeObject(new ArrayList<>(plans.values()));
            System.out.println("[IO] Plans written to file.");
        } catch (IOException e) { System.err.println("Save Plan Error: " + e.getMessage()); }
    }

    // --- 3. LOAD MEMBER ---
    @SuppressWarnings("unchecked")
    private void loadMember() {
        File file = new File(MEMBER_FILE);
        if (!file.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<Member> list = (List<Member>) ois.readObject();
            for (Member m : list) members.put(m.getRegId(), m);
            System.out.println("[IO] Members loaded.");
        } catch (Exception e) { System.err.println("Load Member Error"); }
    }

    // --- 4. LOAD MEMBER PLAN ---
    @SuppressWarnings("unchecked")
    private void loadMemberPlan() {
        File file = new File(PLAN_FILE);
        if (!file.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<MemberPlan> list = (List<MemberPlan>) ois.readObject();
            for (MemberPlan p : list) plans.put(p.getPlanId(), p);
            System.out.println("[IO] Plans loaded.");
        } catch (Exception e) { System.err.println("Load Plan Error"); }
    }

    @Override
    public void addMemberPlan(MemberPlan plan) {
        if (plan != null) {
            // Fix: Use Random/Timestamp if ID is missing to prevent Nulls
            if (plan.getPlanId() == null || plan.getPlanId().isEmpty()) {
                plan.setPlanId("P-" + (100 + new java.util.Random().nextInt(900)));
            }
            // if (plan.getPlanId() == null || plan.getPlanId().isEmpty()) {
            //     plan.setPlanId("P-" + System.currentTimeMillis() % 10000);
            // }
            plans.put(plan.getPlanId(), plan);
            saveMemberPlan();
        }
    }
    
    @Override
    public List<MemberPlan> getAllMemberPlans() {
        return new ArrayList<>(plans.values());
    }

    @Override
    public String addMember(Member member) {

        if (member.getRegId() == 0) {
            // Generate a random ID between 1000 and 9999
            int randomId = 1000 + new java.util.Random().nextInt(9000);
            member.setRegId(randomId);
        }

        // UC-1 Step 9 & 10: Plan & Finance Logic
        if (member.getCurrentPlan() != null && member.getCurrentPlan().getPlanName() != null) {
            MemberPlan plan = member.getCurrentPlan();
            double planPrice = member.getCurrentPlan().getPrice();
            
            // Step 10: Add plan price to outstanding balance
            member.setOutstandingBalance(member.getOutstandingBalance() + planPrice);
        }

        members.put(member.getRegId(), member);
        // saveDataToFile(); // SAVE AFTER ADD
        saveMember();
        return "Member added with ID: " + member.getRegId() + " and Plan: " + 
           (member.getCurrentPlan() != null ? member.getCurrentPlan().getPlanName() : "None");
    }

    @Override
    public String updateBodyStats(int regId, BodyStats newStats) {
        Member m = members.get(regId);
        if (m == null) return "Error: Member not found.";

        // UC-3 Step 8: Validation
        if (newStats.getHeight() <= 0 || newStats.getWeight() <= 0) {
            return "Exception: Invalid Body Statistics (Negative or Zero).";
        }

        newStats.calculateBMI();
        m.recordNewStats(newStats); // Moves current to history
        saveMember();
        return "Body stats updated. New BMI: " + String.format("%.2f", newStats.getBmi());
    }

    @Override
    public List<Member> searchMembersByName(String name) {
        List<Member> results = new ArrayList<>();
        for (Member m : members.values()) {
            if (m.getName().toLowerCase().contains(name.toLowerCase())) {
                results.add(m);
            }
        }
        return results;
    }
    
    @Override
    public Member getMember(int regId) {
        return members.get(regId);
    }
    
    @Override
    public List<Member> getAllMembers() {
        return new ArrayList<>(members.values());
    }
    
    @Override
    public String updateMember(Member member) {
        if (member == null) {
            return "Error: Member cannot be null";
        }
        
        if (!members.containsKey(member.getRegId())) {
            return "Error: Member with ID " + member.getRegId() + " not found";
        }
    
        members.put(member.getRegId(), member);
        saveMember();
        return "Member updated successfully";
    }
    
    @Override
    public String deleteMember(int regId) {
        Member removed = members.remove(regId);
        if (removed != null) {
            // saveDataToFile(); // SAVE AFTER DELETE
            saveMember();
            return "Member with ID " + regId + " deleted successfully";
        }
        return "Error: Member with ID " + regId + " not found";
    }

    @Override
    public String deleteMemberPlan(String planId) {
        if (planId == null) return "❌ Invalid Plan ID.";
        if (plans.containsKey(planId)) {
            plans.remove(planId);
            saveMemberPlan(); // Important: Resave the .dat file
            return "✅ Plan " + planId + " removed successfully.";
        }

        // Use Objects.equals or check for null to prevent crashes
        boolean inUse = members.values().stream()
            .anyMatch(m -> m.getCurrentPlan() != null && 
                    planId.equalsIgnoreCase(m.getCurrentPlan().getPlanId()));

        if (inUse) return "❌ Cannot delete: Plan is assigned to members.";
        
        plans.remove(planId);
        saveMemberPlan();
        return "✅ Plan removed.";
    }

    @Override
    public void deleteAllData() {
        // 1. Clear memory
        this.members.clear();
        this.plans.clear();

        // 2. Physical File Wipe (Hard Delete)
        String[] files = {"members_data.dat", "plans_data.dat"};
        for (String fileName : files) {
            java.io.File file = new java.io.File(fileName);
            if (file.exists()) {
                if (file.delete()) {
                    System.out.println("[IO] Physically deleted: " + fileName);
                }
            }
        }
        
        // 3. Initialize fresh files
        saveMember();
        saveMemberPlan();
    }
}

