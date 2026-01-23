package com.gymmanagement.member.service;

import com.gymmanagement.base.entity.*;
import com.gymmanagement.member.dto.MemberProfileDTO;
import com.gymmanagement.member.repository.MemberPlanRepository;
import com.gymmanagement.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private MemberPlanRepository memberPlanRepository;
    @Mock
    private DataService dataService;

    @InjectMocks
    private MemberService memberService;

    private Member testMember;
    private MemberPlan testPlan;

    @BeforeEach
    void setUp() {
        testPlan = new MemberPlan();
        testPlan.setPlanId("PLAN-001");
        testPlan.setPlanName("Elite Annual");
        testPlan.setPrice(1200.0);

        testMember = new Member();
        testMember.setRegId(1001);
        testMember.setName("Wendy Jane");
        testMember.setCurrentPlan(testPlan);
        testMember.setAssignedTrainerId(5002);
        testMember.setPayment(new Payment());
    }

    // --- UC-1: MEMBER MANAGEMENT TESTS ---

    @Test
    @DisplayName("TC-1: Add Member with existing ID should not regenerate ID")
    void testAddMemberExistingId() {
        testMember.setRegId(113503);
        when(memberRepository.save(any(Member.class))).thenReturn(testMember);
        Member result = memberService.addMember(testMember);
        assertEquals(113503, result.getRegId());
    }

    @Test
    @DisplayName("TC-2: Get Member should return null if ID does not exist")
    void testGetMemberNotFound() {
        when(memberRepository.findById(999)).thenReturn(Optional.empty());
        Member result = memberService.getMember(999);
        assertNull(result);
    }

    @Test
    @DisplayName("TC-3: Update Member should correctly persist changes")
    void testUpdateMember() {
        testMember.setName("Updated Wendy");
        when(memberRepository.save(any(Member.class))).thenReturn(testMember);
        Member result = memberService.updateMember(1001, testMember);
        assertEquals("Updated Wendy", result.getName());
    }

    @Test
    @DisplayName("TC-4: Get All Members should return empty list if none exist")
    void testGetAllMembersEmpty() {
        when(memberRepository.findAll()).thenReturn(new ArrayList<>());
        List<Member> results = memberService.getAllMembers();
        assertTrue(results.isEmpty());
    }

    // --- UC-2: PLAN MANAGEMENT TESTS ---

    @Test
    @DisplayName("TC-5: Add Plan should generate ID if not provided")
    void testAddPlanGeneratesId() {
        testPlan.setPlanId(null);
        when(memberPlanRepository.save(any(MemberPlan.class))).thenReturn(testPlan);
        MemberPlan result = memberService.addMemberPlan(testPlan);
        assertNotNull(result.getPlanId());
    }

    @Test
    @DisplayName("TC-6: Remove Plan should succeed if plan is NOT in use")
    void testRemovePlanNotInUse() {
        when(memberRepository.findAll()).thenReturn(new ArrayList<>());
        assertDoesNotThrow(() -> memberService.removeMemberPlan("PLAN-UNUSED"));
        verify(memberPlanRepository).deleteById("PLAN-UNUSED");
    }

    @Test
    @DisplayName("TC-7: Get All Plans should return full list from repository")
    void testGetAllPlans() {
        when(memberPlanRepository.findAll()).thenReturn(Arrays.asList(testPlan));
        List<MemberPlan> results = memberService.getAllMemberPlans();
        assertEquals(1, results.size());
    }

    // --- UC-3: BODY STATS TESTS ---

    @Test
    @DisplayName("TC-8: Update Body Stats should append to history list")
    void testUpdateStatsHistory() {
        BodyStats newStats = new BodyStats();
        newStats.setWeight(54.0);
        newStats.setHeight(1.62);
        when(memberRepository.findById(1001)).thenReturn(Optional.of(testMember));
        memberService.updateBodyStats(1001, newStats);
        assertFalse(testMember.getBodyStatsHistory().isEmpty());
    }

    @Test
    @DisplayName("TC-9: Update Body Stats should throw exception for invalid member")
    void testUpdateStatsInvalidMember() {
        when(memberRepository.findById(999)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> memberService.updateBodyStats(999, new BodyStats()));
    }

    // --- UC-4: PROFILE AGGREGATION TESTS ---

    @Test
    @DisplayName("TC-10: Get Profile should handle members with NO assigned plan")
    void testGetProfileNoPlan() {
        testMember.setCurrentPlan(null);
        when(memberRepository.findById(1001)).thenReturn(Optional.of(testMember));
        MemberProfileDTO dto = memberService.getMemberProfile(1001);
        assertTrue(dto.getAccessibleMachineDetails().isEmpty());
    }

    @Test
    @DisplayName("TC-11: Get Profile should show 'All Machines' if flag is set in plan")
    void testGetProfileAllMachineAccess() {
        testPlan.setAccessAllMachines(true);
        when(memberRepository.findById(1001)).thenReturn(Optional.of(testMember));
        MemberProfileDTO dto = memberService.getMemberProfile(1001);
        assertEquals("All Machines", dto.getAccessibleMachineDetails().get(0));
    }

    @Test
    @DisplayName("TC-12: Get Profile should handle missing payment object gracefully")
    void testGetProfileMissingPayment() {
        testPlan.setAccessibleMachineIds(new ArrayList<>()); 
        testMember.setPayment(null); // Force payment to null
        
        when(memberRepository.findById(1001)).thenReturn(Optional.of(testMember));
        
        MemberProfileDTO dto = memberService.getMemberProfile(1001);
        
        assertNotNull(dto);
        assertEquals(0.0, dto.getOutstandingBalance());
        verify(memberRepository).findById(1001);
    }
}