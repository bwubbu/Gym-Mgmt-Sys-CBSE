package com.gymmanagement.member.base;

import com.gymmanagement.base.entity.Member;
import com.gymmanagement.base.entity.BodyStats;
import com.gymmanagement.member.dto.MemberProfileDTO;

import java.util.List;

import com.gymmanagement.base.entity.MemberPlan;

public interface IMemberService {
    // UC-1: Manage Members
    Member addMember(Member member);
    Member getMember(int id); 
    List<Member> getAllMembers();
    void removeMember(int id); 
    Member updateMember(int id, Member member);

    // UC-2: Manage Member Plans (ADD THESE TWO LINES)
    MemberPlan addMemberPlan(MemberPlan plan);
    List<MemberPlan> getAllMemberPlans();
    void removeMemberPlan(String planId);

    // UC-3: Update Body Stats
    void updateBodyStats(int id, BodyStats stats);

    // UC-4: View Profile
    MemberProfileDTO getMemberProfile(int id);
}