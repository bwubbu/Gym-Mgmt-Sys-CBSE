package com.gymmanagement.base.service;

import com.gymmanagement.base.entity.Member;
import java.util.List;

/**
 * IMemberService Interface
 * 
 * Defines the contract for Member Management operations.
 * This interface should be implemented by the Member Management Component.
 * 
 * Dependencies: Member, MemberPlan, BodyStats (from Base Library)
 */
public interface IMemberService {
    
    // Member CRUD Operations
    Member addMember(Member member);
    Member getMemberById(int regId);
    List<Member> getAllMembers();
    Member updateMember(int regId, Member member);
    boolean deleteMember(int regId);
    boolean memberExists(int regId);
    
    // Member Search Operations
    List<Member> searchMembersByName(String name);
    Member searchMemberByRegId(int regId);
    
    // Member Validation
    boolean validateMemberData(Member member);
}

