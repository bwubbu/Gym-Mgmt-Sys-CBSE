package com.gymmanagement.member.service;

import com.gymmanagement.base.entity.Machine; // From Base Library
import com.gymmanagement.base.entity.Trainer; // From Base Library
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

@Service
public class DataService {

    @Value("${services.finance.url:http://localhost:8084}")
    private String financeServiceUrl;

    @Value("${services.trainer.url:http://localhost:8082}")
    private String trainerServiceUrl;

    @Value("${services.booking.url:http://localhost:8083}")
    private String bookingServiceUrl;

    private final RestTemplate restTemplate;

    // Local lists to store testing data
    private List<Machine> mockMachines = new ArrayList<>();
    private List<Trainer> mockTrainers = new ArrayList<>();

    public void addTestMachine(Machine m) { mockMachines.add(m); }
    public void addTestTrainer(Trainer t) { mockTrainers.add(t); }

    public DataService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // UC-4: Fetch Balance from Finance Component
    public Double getOutstandingBalance(int memberId) {
        try {
            return restTemplate.getForObject(financeServiceUrl + "/api/payments/member/" + memberId + "/balance", Double.class);
        } catch (Exception e) {
            return 0.0; // Fallback if Finance service is down
        }
    }

    // UC-4: Fetch Trainer details from Trainer Component
    public String getTrainerName(Integer trainerId) {
        if (trainerId == null || trainerId == 0) return "No Trainer Assigned";
        try {
            Trainer trainer = restTemplate.getForObject(trainerServiceUrl + "/api/trainers/" + trainerId, Trainer.class);
            return (trainer != null) ? trainer.getName() : "Unknown Trainer";
        } catch (Exception e) {
            // FALLBACK: Use the mock list logic if service is down
            return getAvailableTrainers().stream()
                    .filter(t -> t.getRegId() == trainerId)
                    .map(Trainer::getName)
                    .findFirst()
                    .orElse("Trainer (" + trainerId + ")");
        }
    }

    // UC-1 Step 9.2.1: Get list of trainers to display to Admin
    public List<Trainer> getAvailableTrainers() {
        List<Trainer> combined = new ArrayList<>(mockTrainers); // Start with data you added manually
        try {
            Trainer[] trainers = restTemplate.getForObject(trainerServiceUrl + "/api/trainers", Trainer[].class);
            if (trainers != null && trainers.length > 0) {
                combined.addAll(Arrays.asList(trainers));
            } else if (combined.isEmpty()) {
                // API succeeded but returned nothing, and no manual data added
                combined.addAll(getMockTrainers());
            }
        } catch (Exception e) {
            // return new ArrayList<>();
            // List<Trainer> mock = new ArrayList<>();
            // mock.add(new Trainer(99, "Mock Coach John", "john@gym.com", "0123", "Main Gym", null, null, 30, "Male", "Yoga", 50, 40, "Expert"));
            // return mock;
            // System.out.println("[DEBUG] External Trainer API down. Using local/test data only.");
            // Optional: Add one hardcoded "Safety" mock if your local list is empty
            // if (combined.isEmpty()) {
            //     combined.add(new Trainer(99, "Emergency Coach John", "john@gym.com", "0123", "Main Gym", null, null, 30, "M", "Yoga", 50, 40, "Expert"));
            // }

            // return getMockTrainers();
            if (combined.isEmpty()) {
                combined.addAll(getMockTrainers());
            }
        }
        return combined;
    }

    /**
     * UC-2 Step 6.1: Fetch all machines from Equipment Booking Component
     * Corrected Endpoint: /api/equipment
     */
    public List<Machine> getAllMachines() {
        List<Machine> combined = new ArrayList<>(mockMachines); // Start with data you added manually
        try {
            // Call the teammate's endpoint: /api/equipment
            Machine[] machines = restTemplate.getForObject(bookingServiceUrl + "/api/equipment", Machine[].class);
            if (machines != null && machines.length > 0) {
                combined.addAll(Arrays.asList(machines));
                // return Arrays.asList(machines);
            } else if (combined.isEmpty()) {
                combined.addAll(getMockMachines());
            }
        } catch (Exception e) {
            // Log error here if needed
        //     System.out.println("[DEBUG] External Machine API down. Using local/test data only.");
        //     return new ArrayList<Machine>(); // Return empty list of correct type on error
        // }
            if (combined.isEmpty()) {
                combined.addAll(getMockMachines());
            }
        }
        return combined; // Return empty typed list instead of List.of() to avoid bounds issues
    }

    // --- MOCK DATA ---

    private List<Trainer> getMockTrainers() {
        List<Trainer> mock = new ArrayList<>();
        
        Trainer t1 = new Trainer();
        t1.setRegId(5001);
        t1.setName("Ahmad");
        t1.setGender("M");
        t1.setSpecialization("Bodybuilding");
        t1.setExperienceLevel("Expert");
        mock.add(t1);

        Trainer t2 = new Trainer();
        t2.setRegId(5002);
        t2.setName("Sarah");
        t2.setGender("F");
        t2.setSpecialization("Yoga");
        t2.setExperienceLevel("Senior");
        mock.add(t2);

        Trainer t3 = new Trainer();
        t3.setRegId(5003);
        t3.setName("Michael");
        t3.setGender("M");
        t3.setSpecialization("Weight Loss");
        t3.setExperienceLevel("Intermediate");
        mock.add(t3);

        return mock;
    }

    private List<Machine> getMockMachines() {
        List<Machine> mock = new ArrayList<>();

        Machine m1 = new Machine();
        m1.setRegId(1001);
        m1.setName("Treadmill");
        m1.setBrand("Matrix");
        m1.setModel("T70");
        m1.setType("Cardio");
        mock.add(m1);

        Machine m2 = new Machine();
        m2.setRegId(1002);
        m2.setName("Power Rack");
        m2.setBrand("Rogue");
        m2.setModel("RML-3");
        m2.setType("Strength");
        mock.add(m2);

        Machine m3 = new Machine();
        m3.setRegId(1003);
        m3.setName("Dumbbell Set");
        m3.setBrand("Harbinger");
        m3.setModel("DB-100");
        m3.setType("Strength");
        mock.add(m3);
    
        Machine m4 = new Machine();
        m4.setRegId(1004);
        m4.setName("Cable Machine");
        m4.setBrand("Technogym");
        m4.setModel("Cable-200");
        m4.setType("Strength");
        mock.add(m4);

        return mock;
    }
    // Existing helper methods...
    public String getMockTrainerName(Integer id) {
        if (id == null) return "No Trainer Assigned";
        return getAvailableTrainers().stream()
                .filter(t -> t.getRegId() == id)
                .map(Trainer::getName)
                .findFirst()
                .orElse("Trainer " + id);
    }

    public double getMockOutstandingBalance(int id) {
        return 0.0; // Fallback for demo
    }
}
