package com.gymmanagement.osgi.member.internal;

import com.gymmanagement.osgi.base.entity.Member;
import com.gymmanagement.osgi.base.service.IMemberService;
// Note: @Component annotation removed - using XML-based component definition instead
// import org.osgi.service.component.annotations.Component;

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
        if (member == null) {
            return "Error: Member cannot be null";
        }
        
        // Validate member data
        if (member.getName() == null || member.getName().trim().isEmpty()) {
            return "Error: Member name is required";
        }
        
        // Assign registration ID if not set
        if (member.getRegId() == 0) {
            member.setRegId(nextRegId++);
        }
        
        // Check if member already exists
        if (members.containsKey(member.getRegId())) {
            return "Error: Member with ID " + member.getRegId() + " already exists";
        }
        
        members.put(member.getRegId(), member);
        return "Member added successfully with ID: " + member.getRegId();
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
    
    @Override
    public List<Member> searchMembersByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        String searchName = name.toLowerCase().trim();
        return members.values().stream()
                .filter(m -> m.getName() != null && 
                           m.getName().toLowerCase().contains(searchName))
                .collect(Collectors.toList());
    }
}

