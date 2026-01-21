package com.gymmanagement.reportanalytics.service;

import com.gymmanagement.reportanalytics.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * DataService
 * 
 * This service is responsible for fetching data from other components.
 * In a microservices architecture, this would call REST APIs of other components.
 * For now, we'll create a placeholder that can be replaced with actual service calls.
 */
@Service
public class DataService {
    
    @Value("${services.member.url:http://localhost:8081}")
    private String memberServiceUrl;
    
    @Value("${services.trainer.url:http://localhost:8082}")
    private String trainerServiceUrl;
    
    @Value("${services.booking.url:http://localhost:8083}")
    private String bookingServiceUrl;
    
    @Value("${services.finance.url:http://localhost:8084}")
    private String financeServiceUrl;
    
    private final RestTemplate restTemplate;
    
    public DataService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // =====================================================================
    // SAMPLE DATA FOR DEMO / PRESENTATION
    // ---------------------------------------------------------------------
    // For now, we return in-memory sample data so that analytics and reports
    // have something meaningful to show even if other components are offline.
    // =====================================================================

    private List<MemberDTO> sampleMembers;
    private List<TrainerDTO> sampleTrainers;
    private List<MachineDTO> sampleMachines;
    private List<BookingDTO> sampleBookings;

    private void initSampleDataIfNeeded() {
        if (sampleMembers != null) {
            return;
        }

        // -------- Payments --------
        PaymentDTO p1 = new PaymentDTO(0.0, "Paid", LocalDate.of(2024, 4, 1));
        PaymentDTO p2 = new PaymentDTO(150.0, "UnPaid", LocalDate.of(2024, 3, 20));
        PaymentDTO p3 = new PaymentDTO(0.0, "Paid", LocalDate.of(2024, 4, 5));
        PaymentDTO p4 = new PaymentDTO(75.0, "UnPaid", LocalDate.of(2024, 3, 28));
        PaymentDTO p5 = new PaymentDTO(0.0, "Paid", LocalDate.of(2024, 4, 10));
        PaymentDTO p6 = new PaymentDTO(200.0, "UnPaid", LocalDate.of(2024, 3, 15));
        PaymentDTO p7 = new PaymentDTO(0.0, "Paid", LocalDate.of(2024, 4, 3));
        PaymentDTO p8 = new PaymentDTO(125.0, "UnPaid", LocalDate.of(2024, 4, 6));
        PaymentDTO p9 = new PaymentDTO(0.0, "Paid", LocalDate.of(2024, 1, 25));
        PaymentDTO p10 = new PaymentDTO(50.0, "UnPaid", LocalDate.of(2024, 2, 18));
        PaymentDTO p11 = new PaymentDTO(0.0, "Paid", LocalDate.of(2024, 3, 22));
        PaymentDTO p12 = new PaymentDTO(100.0, "UnPaid", LocalDate.of(2024, 4, 12));

        // -------- Members (12) --------
        sampleMembers = List.of(
                new MemberDTO(1001, "John Doe", "john.doe@gmail.com", "0300-1234567",
                        "123 Main Street, City",
                        LocalDate.of(2024, 1, 15), LocalDate.of(1995, 5, 20),
                        29, "Male", 1.75, 85.0, 85.0 / (1.75 * 1.75),
                        "Weight Loss", p1),
                new MemberDTO(1002, "Sarah Smith", "sarah.smith@gmail.com", "0300-2345678",
                        "456 Oak Avenue, City",
                        LocalDate.of(2024, 2, 10), LocalDate.of(1998, 8, 15),
                        26, "Female", 1.65, 55.0, 55.0 / (1.65 * 1.65),
                        "Muscle Gain", p2),
                new MemberDTO(1003, "Mike Johnson", "mike.johnson@gmail.com", "0300-3456789",
                        "789 Pine Road, City",
                        LocalDate.of(2024, 3, 5), LocalDate.of(1985, 3, 12),
                        39, "Male", 1.80, 75.0, 75.0 / (1.80 * 1.80),
                        "Endurance", p3),
                new MemberDTO(1004, "Emily Davis", "emily.davis@gmail.com", "0300-4567890",
                        "321 Elm Street, City",
                        LocalDate.of(2024, 1, 22), LocalDate.of(2000, 11, 8),
                        24, "Female", 1.70, 60.0, 60.0 / (1.70 * 1.70),
                        "Flexibility", p4),
                new MemberDTO(1005, "Lisa Anderson", "lisa.anderson@gmail.com", "0300-5678901",
                        "654 Maple Drive, City",
                        LocalDate.of(2024, 4, 1), LocalDate.of(1980, 7, 20),
                        44, "Female", 1.68, 68.0, 68.0 / (1.68 * 1.68),
                        "General Fitness", p5),
                new MemberDTO(1006, "David Wilson", "david.wilson@gmail.com", "0300-6789012",
                        "987 Cedar Lane, City",
                        LocalDate.of(2024, 2, 18), LocalDate.of(1997, 4, 3),
                        27, "Male", 1.82, 90.0, 90.0 / (1.82 * 1.82),
                        "Muscle Gain", p6),
                new MemberDTO(1007, "Robert Brown", "robert.brown@gmail.com", "0300-7890123",
                        "147 Birch Court, City",
                        LocalDate.of(2024, 3, 12), LocalDate.of(1978, 9, 25),
                        46, "Male", 1.78, 95.0, 95.0 / (1.78 * 1.78),
                        "Weight Loss", p7),
                new MemberDTO(1008, "Alex Taylor", "alex.taylor@gmail.com", "0300-8901234",
                        "258 Spruce Way, City",
                        LocalDate.of(2024, 4, 8), LocalDate.of(1999, 12, 14),
                        25, "Female", 1.72, 58.0, 58.0 / (1.72 * 1.72),
                        "Endurance", p8),
                new MemberDTO(1009, "Jennifer Martinez", "jennifer.martinez@gmail.com", "0300-9012345",
                        "369 Willow Street, City",
                        LocalDate.of(2024, 1, 30), LocalDate.of(1982, 6, 18),
                        42, "Female", 1.66, 62.0, 62.0 / (1.66 * 1.66),
                        "Flexibility", p9),
                new MemberDTO(1010, "James White", "james.white@gmail.com", "0300-0123456",
                        "741 Ash Boulevard, City",
                        LocalDate.of(2024, 2, 25), LocalDate.of(1996, 10, 5),
                        28, "Male", 1.79, 80.0, 80.0 / (1.79 * 1.79),
                        "General Fitness", p10),
                new MemberDTO(1011, "William Harris", "william.harris@gmail.com", "0300-1234567",
                        "852 Poplar Circle, City",
                        LocalDate.of(2024, 3, 20), LocalDate.of(1965, 2, 14),
                        59, "Male", 1.76, 78.0, 78.0 / (1.76 * 1.76),
                        "General Fitness", p11),
                new MemberDTO(1012, "Olivia Clark", "olivia.clark@gmail.com", "0300-2345678",
                        "963 Fir Avenue, City",
                        LocalDate.of(2024, 4, 15), LocalDate.of(2001, 1, 28),
                        23, "Female", 1.64, 70.0, 70.0 / (1.64 * 1.64),
                        "Weight Loss", p12)
        );

        // Helper map for quick lookup
        Map<Integer, MemberDTO> memberById = sampleMembers.stream()
                .collect(Collectors.toMap(MemberDTO::getRegId, m -> m));

        // -------- Trainers (6) --------
        sampleTrainers = List.of(
                new TrainerDTO(2001, "Jane Gym", "jane.gym@gmail.com", "0300-7654321",
                        "456 Fitness Avenue, City",
                        LocalDate.of(2024, 1, 10), LocalDate.of(1990, 5, 20),
                        34, "Female", "Yoga", 30.0),
                new TrainerDTO(2002, "Mark Strong", "mark.strong@gmail.com", "0300-8765432",
                        "789 Power Street, City",
                        LocalDate.of(2024, 2, 1), LocalDate.of(1988, 8, 15),
                        36, "Male", "Strength Training", 45.0),
                new TrainerDTO(2003, "Amy Runner", "amy.runner@gmail.com", "0300-9876543",
                        "321 Cardio Avenue, City",
                        LocalDate.of(2024, 3, 10), LocalDate.of(1992, 3, 22),
                        32, "Female", "Cardio", 35.0),
                new TrainerDTO(2004, "Sophie Core", "sophie.core@gmail.com", "0300-0987654",
                        "654 Flexibility Road, City",
                        LocalDate.of(2024, 4, 5), LocalDate.of(1985, 11, 8),
                        39, "Female", "Pilates", 40.0),
                new TrainerDTO(2005, "Tom Cross", "tom.cross@gmail.com", "0300-1098765",
                        "147 Intensity Way, City",
                        LocalDate.of(2024, 5, 20), LocalDate.of(1987, 7, 14),
                        37, "Male", "CrossFit", 50.0),
                new TrainerDTO(2006, "Chris Fit", "chris.fit@gmail.com", "0300-2109876",
                        "258 Wellness Boulevard, City",
                        LocalDate.of(2024, 1, 25), LocalDate.of(1991, 9, 30),
                        33, "Male", "General Fitness", 38.0)
        );

        // -------- Machines (8) with bookings --------
        sampleMachines = List.of(
                new MachineDTO(3001, "ProRunner Treadmill", "FitnessPro", "PR-TM-2024",
                        150.0, 120.0, "Cardio",
                        List.of(memberById.get(1001), memberById.get(1003), memberById.get(1005))),
                new MachineDTO(3002, "PowerLift Bench", "IronGym", "IG-BP-2024",
                        200.0, 45.0, "Strength",
                        List.of(memberById.get(1001), memberById.get(1003), memberById.get(1007))),
                new MachineDTO(3003, "EcoElliptical Trainer", "CardioMax", "CM-ET-2024",
                        120.0, 80.0, "Cardio",
                        List.of(memberById.get(1003), memberById.get(1005), memberById.get(1009))),
                new MachineDTO(3004, "MegaLeg Press", "PowerHouse", "PH-LP-2024",
                        300.0, 150.0, "Strength",
                        List.of(memberById.get(1001), memberById.get(1007))),
                new MachineDTO(3005, "WaterRower Elite", "AquaFit", "AF-RM-2024",
                        150.0, 30.0, "Cardio",
                        List.of(memberById.get(1003), memberById.get(1011))),
                new MachineDTO(3006, "Smith Rack System", "SteelGym", "SG-SM-2024",
                        250.0, 200.0, "Strength",
                        List.of(memberById.get(1007), memberById.get(1009))),
                new MachineDTO(3007, "SpinCycle Pro", "CycleMax", "CM-SB-2024",
                        130.0, 25.0, "Cardio",
                        List.of(memberById.get(1005), memberById.get(1011))),
                new MachineDTO(3008, "MultiCable Station", "FlexGym", "FG-CC-2024",
                        100.0, 180.0, "Functional",
                        List.of(memberById.get(1001), memberById.get(1002), memberById.get(1008)))
        );

        // -------- Bookings (derived from machines) --------
        long bookingId = 1L;
        LocalDate baseDate = LocalDate.of(2024, 4, 1);
        sampleBookings = sampleMachines.stream()
                .flatMap(machine -> machine.getBookings().stream()
                        .map(member -> new BookingDTO(
                                null, // ID will be set later
                                member.getRegId(),
                                member.getName(),
                                machine.getRegId(),
                                machine.getName(),
                                baseDate.plusDays((member.getRegId() % 7)),
                                "CONFIRMED"
                        )))
                .collect(Collectors.toList());

        // Assign sequential IDs after creation (avoids non-final variable in lambda)
        for (BookingDTO booking : sampleBookings) {
            booking.setId(bookingId++);
        }
    }

    /**
     * Fetch all members from Member Management Component
     * For now, returns in-memory sample data for demo.
     */
    public List<MemberDTO> getAllMembers() {
        initSampleDataIfNeeded();
        return sampleMembers;
    }

    /**
     * Fetch all trainers from Trainer Management Component
     * For now, returns in-memory sample data for demo.
     */
    public List<TrainerDTO> getAllTrainers() {
        initSampleDataIfNeeded();
        return sampleTrainers;
    }

    /**
     * Fetch all machines from Equipment Booking Component
     * For now, returns in-memory sample data for demo.
     */
    public List<MachineDTO> getAllMachines() {
        initSampleDataIfNeeded();
        return sampleMachines;
    }

    /**
     * Fetch all bookings from Equipment Booking Component
     * For now, returns in-memory sample data for demo.
     */
    public List<BookingDTO> getAllBookings() {
        initSampleDataIfNeeded();
        return sampleBookings;
    }
}

