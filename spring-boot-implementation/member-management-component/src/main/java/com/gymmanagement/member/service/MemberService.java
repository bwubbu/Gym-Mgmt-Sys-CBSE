package com.gymmanagement.member.service;

import com.gymmanagement.member.base.IMemberService;
import com.gymmanagement.member.repository.MemberRepository;
import com.gymmanagement.member.repository.MemberPlanRepository;
import com.gymmanagement.member.dto.MemberProfileDTO;
import com.gymmanagement.base.entity.*;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class MemberService implements IMemberService {

    private final MemberRepository memberRepository;
    private final MemberPlanRepository memberPlanRepository;
    private final DataService dataService;

    public MemberService(MemberRepository memberRepository, 
                         MemberPlanRepository memberPlanRepository, 
                         DataService dataService) {
        this.memberRepository = memberRepository;
        this.memberPlanRepository = memberPlanRepository;
        this.dataService = dataService;
    }

    // UC-1: Manage Members
    @Override
    public Member addMember(Member member) {
        /// FIX: Only generate if ID is 0 or uninitialized
        if (member.getRegId() <= 0) {
            member.setRegId(new Random().nextInt(900000) + 100000);
        }

        if (member.getPayment() == null) {
            member.setPayment(new Payment());
        }
        
        // Step 10: If plan has price, add outstanding balance via Finance
        if (member.getCurrentPlan() != null && member.getCurrentPlan().getPrice() > 0 && member.getPayment() != null) {
            // Logic to tell finance to add balance would go here
            member.getPayment().setOutstandingBalance(member.getCurrentPlan().getPrice());
        }
        return memberRepository.save(member);
    }

    // UC-2: Add Member Plan
    @Override
    public MemberPlan addMemberPlan(MemberPlan plan) {
        // UC-2 Step 4: Generate unique Plan ID
        if (plan.getPlanId() == null) {
            plan.setPlanId("PLAN-" + java.util.UUID.randomUUID().toString().substring(0, 5).toUpperCase());
        }
        return (MemberPlan) memberPlanRepository.save(plan);
    }

    @Override
    public List<MemberPlan> getAllMemberPlans() {
        return (List<MemberPlan>) memberPlanRepository.findAll();
    }

    // @Override
    // public void removeMemberPlan(String planId) {
    //     // Implementation for UC-2 Step 5: Deletes from repository
    //     memberPlanRepository.deleteById(planId); 
    // }
    @Override
    public void removeMemberPlan(String planId) {
        // RULE: Check if any member is using this plan
        boolean isUsed = memberRepository.findAll().stream()
                .anyMatch(m -> m.getCurrentPlan() != null && m.getCurrentPlan().getPlanId().equals(planId));

        if (isUsed) {
            throw new RuntimeException("CANNOT REMOVE: This plan is currently assigned to members.");
        }

        memberPlanRepository.deleteById(planId); 
    }

    // UC-3: Update Body Stats & Calculate BMI
    @Override
    public void updateBodyStats(int id, BodyStats stats) {
        // 1. Find the member (casting to Member to avoid Object error)
        Member member = (Member) memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        
        // 2. Calculate BMI
        double heightM = stats.getHeight(); 
        double weightK = stats.getWeight();
        double bmi = weightK / (heightM * heightM);
        
        // 3. Set the data inside the stats object
        stats.setBmi(bmi);
        
        // 4. Update the member's bodyStats object
        member.setBodyStats(stats);
        
        // 5. Save the updated member
        memberRepository.save(member);
    }

    // UC-4: View Profile (Aggregating data)
    public Map<String, Object> getFullProfile(int id) {
        Member member = memberRepository.findById(id).orElseThrow();
        Map<String, Object> profile = new HashMap<>();
        
        profile.put("member", member);
        // Getting real data from teammates' components
        profile.put("currentBalance", dataService.getOutstandingBalance(id));
        profile.put("trainerName", dataService.getTrainerName(member.getAssignedTrainerId()));
        
        return profile;
    }

    @Override
    public MemberProfileDTO getMemberProfile(int id) {
        Member member = memberRepository.findById(id)
                .map(obj -> (Member) obj) 
                .orElseThrow(() -> new RuntimeException("Member not found"));

        MemberProfileDTO profile = new MemberProfileDTO();
        profile.setMember(member);

        // UC-4: Aggregate external data
        // profile.setOutstandingBalance(dataService.getOutstandingBalance(id));
        if (member.getPayment() != null) {
            profile.setOutstandingBalance(member.getPayment().getOutstandingBalance());
        } else {
            profile.setOutstandingBalance(0.0);
        }
        profile.setTrainerName(dataService.getTrainerName(member.getAssignedTrainerId()));
        
        // UC-4: List accessible machines from the Member's Plan
        if (member.getCurrentPlan() != null) {
            profile.setAccessibleMachines(member.getCurrentPlan().getAccessibleMachineIds());
        }

        return profile;
    }

    // Fix findById by ensuring the ID is treated as an Integer object
    @Override
    public Member getMember(int id) {
        return (Member) memberRepository.findById(id).orElse(null);
    }

    @Override
    public List<Member> getAllMembers() {
        return (List<Member>) memberRepository.findAll();
    }

    @Override
    public void removeMember(int id) { memberRepository.deleteById(id); }
    
    @Override
    public Member updateMember(int id, Member m) { m.setRegId(id); return memberRepository.save(m); }
}