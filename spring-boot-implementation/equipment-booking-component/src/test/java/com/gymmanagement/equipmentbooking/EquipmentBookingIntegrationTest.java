package com.gymmanagement.equipmentbooking;

import com.gymmanagement.base.entity.Machine;
import com.gymmanagement.base.entity.Maintenance;
import com.gymmanagement.base.entity.Member;
import com.gymmanagement.base.entity.Payment;
import com.gymmanagement.base.entity.Date;
import com.gymmanagement.base.entity.BodyStats;
import com.gymmanagement.base.entity.MemberPlan;
import com.gymmanagement.equipmentbooking.repository.FileMachineRepository;
import com.gymmanagement.equipmentbooking.repository.FileMaintenanceRepository;
import com.gymmanagement.equipmentbooking.service.DataService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EquipmentBookingIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileMachineRepository machineRepository;

    @MockBean
    private FileMaintenanceRepository maintenanceRepository;

    @MockBean
    private DataService dataService;

    @Autowired
    private ObjectMapper objectMapper;

    private Machine testMachine;
    private Member testMember;

    @BeforeEach
    void setUp() {
        System.out.println("\nSETUP: Initializing Test Data...");
        // Setup Test Machine
        testMachine = new Machine(101, "Treadmill X1", "Technogym", "X1", 150.0, 80.0, "Cardio");
        
        // Setup Test Member (Mocking DataService)
        Payment payment = new Payment(0.0, "Test User", "1234");
        BodyStats stats = new BodyStats();
        MemberPlan plan = new MemberPlan();
        testMember = new Member(
            1, "Test Member", "test@test.com", "12345", "Address",
            new Date(1, 1, 2023), new Date(1, 1, 1990), 30, "M", 6.0, 80.0,
            payment, "Fitness", stats, plan, null
        );

        // Default Mocks
        when(machineRepository.findById(101)).thenReturn(Optional.of(testMachine));
        when(machineRepository.existsById(101)).thenReturn(true);
        when(machineRepository.findAll()).thenReturn(new ArrayList<>(Collections.singletonList(testMachine)));
        when(machineRepository.save(any(Machine.class))).thenAnswer(i -> i.getArguments()[0]); // Return saved object
        
        when(dataService.getMember(1)).thenReturn(testMember);
    }

    // --- Feature 1: Manage Equipment Inventory ---
    
    @Test
    void testAddMachine() throws Exception {
        System.out.println("TESTING: Feature 1 - Manage Equipment Inventory (Add Machine)");
        Machine newMachine = new Machine(102, "Bench Press", "Hammer Strength", "BP-01", 300.0, 50.0, "Strength");
        when(machineRepository.save(any(Machine.class))).thenReturn(newMachine);

        mockMvc.perform(post("/api/equipment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newMachine)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Bench Press")))
                .andExpect(jsonPath("$.regId", is(102)));
        System.out.println("SUCCESS: Machine added successfully.");
    }

    @Test
    void testGetAllMachines() throws Exception {
        System.out.println("TESTING: Feature 1 - Manage Equipment Inventory (Get All Machines)");
        mockMvc.perform(get("/api/equipment"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Treadmill X1")));
        System.out.println("SUCCESS: Retrieved machine list.");
    }

    @Test
    void testDeleteMachine() throws Exception {
        System.out.println("TESTING: Feature 1 - Manage Equipment Inventory (Delete Machine)");
        when(machineRepository.existsById(101)).thenReturn(true);
        
        mockMvc.perform(delete("/api/equipment/101"))
                .andExpect(status().isOk())
                .andExpect(content().string("Machine Data Removed"));

        Mockito.verify(machineRepository).deleteById(101);
        System.out.println("SUCCESS: Machine deleted.");
    }

    // --- Feature 2: Book Machine Slot ---

    @Test
    void testBookMachineSuccess() throws Exception {
        System.out.println("TESTING: Feature 2 - Book Machine Slot (Success Case)");
        mockMvc.perform(post("/api/bookings/book")
                .param("memberId", "1")
                .param("machineId", "101"))
                .andExpect(status().isOk())
                .andExpect(content().string("booked"));
        System.out.println("SUCCESS: Machine slot booked.");
    }

    @Test
    void testBookMachine_MachineNotFound() throws Exception {
        System.out.println("TESTING: Feature 2 - Book Machine Slot (Error: Machine Not Found)");
        when(machineRepository.findById(999)).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/bookings/book")
                .param("memberId", "1")
                .param("machineId", "999"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("not found")));
        System.out.println("SUCCESS: Correct error returned for missing machine.");
    }

    @Test
    void testBookMachine_OutstandingBalance() throws Exception {
        System.out.println("TESTING: Edge Case - Book Machine (Outstanding Balance)");
        // Mock member with balance
        testMember.getPayment().setOutstandingBalance(50.0);
        
        mockMvc.perform(post("/api/bookings/book")
                .param("memberId", "1")
                .param("machineId", "101"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("outstanding balance")));
        System.out.println("SUCCESS: Booking blocked due to balance.");
    }

    @Test
    void testBookMachine_DoubleBooking() throws Exception {
        System.out.println("TESTING: Edge Case - Book Machine (Double Booking)");
        // Pre-book
        testMachine.bookMachine(testMember);
        
        mockMvc.perform(post("/api/bookings/book")
                .param("memberId", "1")
                .param("machineId", "101"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("already book")));
        System.out.println("SUCCESS: Double booking blocked.");
    }

    @Test
    void testBookMachine_FullCapacity() throws Exception {
        System.out.println("TESTING: Edge Case - Book Machine (Full Capacity)");
        // Fill all 8 slots
        for(int i=0; i<8; i++) {
             // We need unique members usually, but the machine check just looks for empty slots
             // However, to avoid "already booked" error, we need to bypass the equality check or use different members.
             // Based on Machine.java logic: it iterates to check equality FIRST. 
             // So we must use new Member objects that are NOT equal to testMember.
             Member dummy = new Member(); // default constructor
             dummy.setName("Dummy " + i);
             testMachine.getBookings()[i] = dummy; 
        }

        mockMvc.perform(post("/api/bookings/book")
                .param("memberId", "1")
                .param("machineId", "101"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("completely booked")));
        System.out.println("SUCCESS: Booking blocked due to full capacity.");
    }

    // --- Feature 3: Schedule Equipment Maintenance ---

    @Test
    void testScheduleMaintenance() throws Exception {
        System.out.println("TESTING: Feature 3 - Schedule Equipment Maintenance");
        Maintenance maintenance = new Maintenance(1, 101, new Date(1, 1, 2024), new Date(7, 1, 2024), "Repair", "SCHEDULED");
        when(maintenanceRepository.save(any(Maintenance.class))).thenReturn(maintenance);

        mockMvc.perform(post("/api/maintenance")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(maintenance)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Maintenance scheduled successfully")));
        System.out.println("SUCCESS: Maintenance scheduled.");
    }

    @Test
    void testBookingBlockedByMaintenance() throws Exception {
        System.out.println("TESTING: Feature 3 - Schedule Equipment Maintenance (Block Booking)");
        // Simulate active maintenance
        Maintenance activeMaint = new Maintenance(1, 101, new Date(1, 1, 2020), new Date(1, 1, 2030), "Repair", "SCHEDULED");
        when(maintenanceRepository.findActiveByMachineId(anyInt(), any(LocalDate.class)))
                .thenReturn(Collections.singletonList(activeMaint));

        mockMvc.perform(post("/api/bookings/book")
                .param("memberId", "1")
                .param("machineId", "101"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("under maintenance")));
        System.out.println("SUCCESS: Booking blocked due to active maintenance.");
    }


    // --- Feature 4: View Equipment Usage Statistics ---

    @Test
    void testGetUsageStatistics() throws Exception {
        System.out.println("TESTING: Feature 4 - View Equipment Usage Statistics");
        // Pre-fill booking in test machine
        testMachine.bookMachine(testMember);
        
        mockMvc.perform(get("/api/equipment/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].machineName", is("Treadmill X1")))
                .andExpect(jsonPath("$[0].bookedSlots", is(1))); // 1 booking made
        System.out.println("SUCCESS: Statistics retrieved correctly.");
    }
}
