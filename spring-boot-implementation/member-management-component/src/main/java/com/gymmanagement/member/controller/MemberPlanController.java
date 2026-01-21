// package com.gymmanagement.member.controller;

// import com.gymmanagement.base.entity.MemberPlan;
// import org.springframework.web.bind.annotation.*;
// import java.util.List;

// @RestController
// @RequestMapping("/api/plans")
// public class MemberPlanController {

//     @PostMapping
//     public String addPlan(@RequestBody MemberPlan plan) {
//         // Logic for UC-2: Save new plan
//         return "Member plan created successfully";
//     }

//     @GetMapping
//     public List<MemberPlan> getAllPlans() {
//         // Logic for UC-2: View All Plans
//         return List.of();
//     }
// }

package com.gymmanagement.member.controller;

import com.gymmanagement.base.entity.MemberPlan; // From Base Library
import com.gymmanagement.base.entity.Machine;    // From Base Library
import com.gymmanagement.member.service.MemberService;
import com.gymmanagement.member.service.DataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/member-plans")
@CrossOrigin(origins = "*")
public class MemberPlanController {

    private final MemberService memberService;
    private final DataService dataService;

    public MemberPlanController(MemberService memberService, DataService dataService) {
        this.memberService = memberService;
        this.dataService = dataService;
    }

    /**
     * UC-2 Step 6.1: Get list of all machines for selection
     */
    @GetMapping("/available-machines")
    public ResponseEntity<List<Machine>> getMachinesForPlan() {
        // This will now correctly infer List<Machine>
        List<Machine> machines = dataService.getAllMachines();
        return ResponseEntity.ok(machines);
    }

    /**
     * UC-2 Step 7: Save a new Member Plan
     */
    @PostMapping("/add")
    public ResponseEntity<MemberPlan> addPlan(@RequestBody MemberPlan plan) {
        return ResponseEntity.ok(memberService.addMemberPlan(plan));
    }

    @GetMapping
    public ResponseEntity<List<MemberPlan>> getAllPlans() {
        return ResponseEntity.ok(memberService.getAllMemberPlans());
    }
}