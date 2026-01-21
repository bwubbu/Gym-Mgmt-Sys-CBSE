// package com.gymmanagement.member.dto;

// import com.gymmanagement.base.entity.*;
// import lombok.Data;
// import java.util.List;
// import java.util.Map;

// @Data
// public class MemberProfileDTO {
//     // Basic Member Info
//     private Member member;
    
//     // Aggregated Info from other services
//     private Double currentOutstandingBalance;
//     private String assignedTrainerName;
    
//     // History
//     private List<BodyStats> statsHistory;
// }

package com.gymmanagement.member.dto;

import com.gymmanagement.base.entity.Member;
import com.gymmanagement.base.entity.BodyStats;
import lombok.Data;
import java.util.List;

@Data
public class MemberProfileDTO {
    // Personal & Plan info from local DB
    private Member member;
    
    // Real-time info from other components
    private Double outstandingBalance;
    private String trainerName;
    private List<String> accessibleMachines;
    
    // History (Simulated or from a separate history table)
    private List<BodyStats> bodyStatsHistory;

    public Member getMember() { return member; }
    public void setMember(Member member) { this.member = member; }

    public Double getOutstandingBalance() { return outstandingBalance; }
    public void setOutstandingBalance(Double outstandingBalance) { this.outstandingBalance = outstandingBalance; }

    public String getTrainerName() { return trainerName; }
    public void setTrainerName(String trainerName) { this.trainerName = trainerName; }

    public List<String> getAccessibleMachines() { return accessibleMachines; }
    public void setAccessibleMachines(List<String> accessibleMachines) { this.accessibleMachines = accessibleMachines; }

    public List<BodyStats> getBodyStatsHistory() { return bodyStatsHistory; }
    public void setBodyStatsHistory(List<BodyStats> bodyStatsHistory) { this.bodyStatsHistory = bodyStatsHistory; }
}