package com.gymmanagement.osgi.base.service;

import com.gymmanagement.osgi.base.entity.Member;
import java.util.List;

/**
 * OSGi service interface for Member Management
 */
public interface IMemberService {
    /**
     * Add a new member
     * @param member The member to add
     * @return Success message
     */
    String addMember(Member member);
    
    /**
     * Get a member by registration ID
     * @param regId Registration ID
     * @return Member object or null if not found
     */
    Member getMember(int regId);
    
    /**
     * Get all members
     * @return List of all members
     */
    List<Member> getAllMembers();
    
    /**
     * Update member information
     * @param member Updated member object
     * @return Success message
     */
    String updateMember(Member member);
    
    /**
     * Delete a member by registration ID
     * @param regId Registration ID
     * @return Success message
     */
    String deleteMember(int regId);
    
    /**
     * Search members by name
     * @param name Name to search for
     * @return List of matching members
     */
    List<Member> searchMembersByName(String name);
}

