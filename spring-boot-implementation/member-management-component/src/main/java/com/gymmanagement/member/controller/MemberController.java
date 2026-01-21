package com.gymmanagement.member.controller;

import java.util.List;

import com.gymmanagement.member.base.IMemberService;
import com.gymmanagement.member.dto.MemberProfileDTO;
import com.gymmanagement.base.entity.Member;
import com.gymmanagement.base.entity.BodyStats;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@CrossOrigin(origins = "*")
public class MemberController {

    private final IMemberService memberService;

    public MemberController(IMemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/add")
    public ResponseEntity<Member> addMember(@RequestBody Member member) {
        // Add the <Member> type hint
        return ResponseEntity.<Member>ok(memberService.addMember(member));
    }

    // List all members
    @GetMapping
    public ResponseEntity<List<Member>> getAllMembers() {
        List<Member> members = memberService.getAllMembers();
        // Explicitly tell ResponseEntity the type is List<Member>
        return ResponseEntity.<List<Member>>ok(members); 
    }

    // UC-1: Search Member
   @GetMapping("/{id}")
    public ResponseEntity<Member> getMember(@PathVariable int id) {
        Member member = memberService.getMember(id);
        // Explicitly tell ResponseEntity the type is Member
        return ResponseEntity.<Member>ok(member);
    }

    // UC-3: Update Body Stats
    @PutMapping("/{id}/update-stats")
    public ResponseEntity<String> updateStats(@PathVariable int id, @RequestBody BodyStats stats) { // CHANGED String to int
        if (stats.getWeight() <= 0 || stats.getHeight() <= 0) {
            return ResponseEntity.badRequest().body("Invalid Body Statistics"); // UC-3 Exception Flow
        }
        memberService.updateBodyStats(id, stats); // UC-3 Logic
        return ResponseEntity.ok("Body statistics updated successfully");
    }

    // @GetMapping("/{id}/profile")
    // public ResponseEntity<Member> viewProfile(@PathVariable String id) {
    //     return ResponseEntity.ok(memberService.getMemberProfile(id));
    // }

    /**
     * UC-4: View Member Profile & History
     * URL: GET http://localhost:8081/api/members/{id}/profile
     */
    @GetMapping("/{id}/profile")
    public ResponseEntity<MemberProfileDTO> getProfile(@PathVariable int id) {
        return ResponseEntity.ok(memberService.getMemberProfile(id));
    }
}