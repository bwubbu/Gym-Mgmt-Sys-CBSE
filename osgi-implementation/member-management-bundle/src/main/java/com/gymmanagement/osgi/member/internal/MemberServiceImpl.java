package com.gymmanagement.osgi.member.internal;

// Note: @Component annotation removed - using XML-based component definition instead
// import org.osgi.service.component.annotations.Component;
import com.gymmanagement.osgi.base.entity.Member;
import com.gymmanagement.osgi.base.entity.BodyStats;
import com.gymmanagement.osgi.base.service.IMemberService;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * OSGi service implementation for Member Management
 * 
 * Note: Service registration is handled via XML descriptor:
 * OSGI-INF/com.gymmanagement.osgi.member.service.xml
 */
public class MemberServiceImpl implements IMemberService {
    
    // In-memory storage for members (in production, this would be a database)
    private final ConcurrentHashMap<Integer, Member> members = new ConcurrentHashMap<>();
    private int nextRegId = 1;
    
    @Override
    public String addMember(Member member) {
        // if (member == null) {
        //     return "Error: Member cannot be null";
        // }
        
        // // Validate member data
        // if (member.getName() == null || member.getName().trim().isEmpty()) {
        //     return "Error: Member name is required";
        // }
        
        // // Assign registration ID if not set
        // if (member.getRegId() == 0) {
        //     member.setRegId(nextRegId++);
        // }
        
        // // Check if member already exists
        // if (members.containsKey(member.getRegId())) {
        //     return "Error: Member with ID " + member.getRegId() + " already exists";
        // }
        
        // members.put(member.getRegId(), member);
        // return "Member added successfully with ID: " + member.getRegId();

        // UC-1 Step 11: Validation
        if (!member.validateName(member.getName())) return "Error: Invalid Name format.";
        if (!member.validateGmail(member.getGmail())) return "Error: Invalid Gmail format.";
        
        if (member.getRegId() == 0) member.setRegId(nextRegId++);

        // UC-1 Step 9 & 10: Plan Logic
        if (member.getCurrentPlan() != null) {
            double planPrice = member.getCurrentPlan().getPrice();
            member.setOutstandingBalance(member.getOutstandingBalance() + planPrice);
            
            // Set Start/End dates based on duration
            LocalDate start = LocalDate.now();
            member.setCurrentPlan(member.getCurrentPlan()); // Set reference
            // Simple logic: Quarterly = 3 months, Annual = 12 months
            if ("Annual".equalsIgnoreCase(member.getCurrentPlan().getDuration())) {
                // Logic for dates would go here
            }
        }

        members.put(member.getRegId(), member);
        return "Member " + member.getName() + " saved successfully with ID: " + member.getRegId();
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
        return "Member updated successfully";
    }
    
    @Override
    public String deleteMember(int regId) {
        Member removed = members.remove(regId);
        if (removed != null) {
            return "Member with ID " + regId + " deleted successfully";
        }
        return "Error: Member with ID " + regId + " not found";
    }
    
    // @Override
    // public List<Member> searchMembersByName(String name) {
    //     if (name == null || name.trim().isEmpty()) {
    //         return new ArrayList<>();
    //     }
        
    //     String searchName = name.toLowerCase().trim();
    //     return members.values().stream()
    //             .filter(m -> m.getName() != null && 
    //                        m.getName().toLowerCase().contains(searchName))
    //             .collect(Collectors.toList());
    // }

    // Standard CRUD
    // @Override public Member getMember(int regId) { return members.get(regId); }
    // @Override public List<Member> getAllMembers() { return new ArrayList<>(members.values()); }
    // @Override public String updateMember(Member m) { members.put(m.getRegId(), m); return "Updated."; }
    // @Override public String deleteMember(int id) { return members.remove(id) != null ? "Removed." : "Not found."; }
}

