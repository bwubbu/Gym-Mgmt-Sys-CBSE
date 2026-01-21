package project_oop;

import java.io.*;
import java.util.ArrayList;

/**
 * Sample Data Generator for Gym Management System
 * 
 * This utility class generates diverse sample data for:
 * - Members (with various demographics, fitness goals, payment statuses)
 * - Trainers (with different specializations and experience levels)
 * - Machines (with different types and specifications)
 * - Machine bookings
 * 
 * Usage: Run this class to populate MemberFile, TrainerFile, and MachineFile
 */
public class SampleDataGenerator {
    
    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("Gym Management System - Sample Data Generator");
        System.out.println("=========================================\n");
        
        SampleDataGenerator generator = new SampleDataGenerator();
        
        try {
            // Generate and save members
            ArrayList<Member> members = generator.generateSampleMembers();
            generator.saveMembers(members);
            System.out.println("✅ Generated " + members.size() + " sample members");
            
            // Generate and save trainers
            ArrayList<Trainer> trainers = generator.generateSampleTrainers();
            generator.saveTrainers(trainers);
            System.out.println("✅ Generated " + trainers.size() + " sample trainers");
            
            // Generate and save machines
            ArrayList<Machine> machines = generator.generateSampleMachines();
            generator.saveMachines(machines);
            System.out.println("✅ Generated " + machines.size() + " sample machines");
            
            // Create some bookings
            generator.createSampleBookings(members, machines);
            generator.saveMachines(machines); // Save again with bookings
            System.out.println("✅ Created sample machine bookings");
            
            System.out.println("\n=========================================");
            System.out.println("Sample data generation completed successfully!");
            System.out.println("=========================================");
            System.out.println("\nFiles created:");
            System.out.println("  - MemberFile");
            System.out.println("  - TrainerFile");
            System.out.println("  - MachineFile");
            System.out.println("\nYou can now run the Gym Management System application.");
            
        } catch (IOException e) {
            System.err.println("❌ Error generating sample data: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Generate diverse sample members
     */
    private ArrayList<Member> generateSampleMembers() {
        ArrayList<Member> members = new ArrayList<>();
        
        // Member 1: Young male, Weight Loss, Zero balance, Joined Jan 2024
        Date joinDate1 = new Date(1, 15, 2024);
        Date dob1 = new Date(5, 20, 1995);
        Payment payment1 = new Payment(0.0, "John Doe", "1234-5678-9012-3456");
        Member member1 = new Member(1001, "John Doe", "john.doe@gmail.com", 
            "0300-1234567", "123 Main Street, City", joinDate1, dob1, 29, "Male", 
            1.75, 85.0, payment1, "Weight Loss");
        members.add(member1);
        
        // Member 2: Young female, Muscle Gain, Outstanding balance, Joined Feb 2024
        Date joinDate2 = new Date(2, 10, 2024);
        Date dob2 = new Date(8, 15, 1998);
        Payment payment2 = new Payment(150.0, "Sarah Smith", "2345-6789-0123-4567");
        Member member2 = new Member(1002, "Sarah Smith", "sarah.smith@gmail.com", 
            "0300-2345678", "456 Oak Avenue, City", joinDate2, dob2, 26, "Female", 
            1.65, 55.0, payment2, "Muscle Gain");
        members.add(member2);
        
        // Member 3: Middle-aged male, Endurance, Zero balance, Joined Mar 2024
        Date joinDate3 = new Date(3, 5, 2024);
        Date dob3 = new Date(3, 12, 1985);
        Payment payment3 = new Payment(0.0, "Mike Johnson", "3456-7890-1234-5678");
        Member member3 = new Member(1003, "Mike Johnson", "mike.johnson@gmail.com", 
            "0300-3456789", "789 Pine Road, City", joinDate3, dob3, 39, "Male", 
            1.80, 75.0, payment3, "Endurance");
        members.add(member3);
        
        // Member 4: Young female, Flexibility, Outstanding balance, Joined Jan 2024
        Date joinDate4 = new Date(1, 22, 2024);
        Date dob4 = new Date(11, 8, 2000);
        Payment payment4 = new Payment(75.0, "Emily Davis", "4567-8901-2345-6789");
        Member member4 = new Member(1004, "Emily Davis", "emily.davis@gmail.com", 
            "0300-4567890", "321 Elm Street, City", joinDate4, dob4, 24, "Female", 
            1.70, 60.0, payment4, "Flexibility");
        members.add(member4);
        
        // Member 5: Middle-aged female, General Fitness, Zero balance, Joined Apr 2024
        Date joinDate5 = new Date(4, 1, 2024);
        Date dob5 = new Date(7, 20, 1980);
        Payment payment5 = new Payment(0.0, "Lisa Anderson", "5678-9012-3456-7890");
        Member member5 = new Member(1005, "Lisa Anderson", "lisa.anderson@gmail.com", 
            "0300-5678901", "654 Maple Drive, City", joinDate5, dob5, 44, "Female", 
            1.68, 68.0, payment5, "General Fitness");
        members.add(member5);
        
        // Member 6: Young male, Muscle Gain, Outstanding balance, Joined Feb 2024
        Date joinDate6 = new Date(2, 18, 2024);
        Date dob6 = new Date(4, 3, 1997);
        Payment payment6 = new Payment(200.0, "David Wilson", "6789-0123-4567-8901");
        Member member6 = new Member(1006, "David Wilson", "david.wilson@gmail.com", 
            "0300-6789012", "987 Cedar Lane, City", joinDate6, dob6, 27, "Male", 
            1.82, 90.0, payment6, "Muscle Gain");
        members.add(member6);
        
        // Member 7: Middle-aged male, Weight Loss, Zero balance, Joined Mar 2024
        Date joinDate7 = new Date(3, 12, 2024);
        Date dob7 = new Date(9, 25, 1978);
        Payment payment7 = new Payment(0.0, "Robert Brown", "7890-1234-5678-9012");
        Member member7 = new Member(1007, "Robert Brown", "robert.brown@gmail.com", 
            "0300-7890123", "147 Birch Court, City", joinDate7, dob7, 46, "Male", 
            1.78, 95.0, payment7, "Weight Loss");
        members.add(member7);
        
        // Member 8: Young female, Endurance, Outstanding balance, Joined Apr 2024
        Date joinDate8 = new Date(4, 8, 2024);
        Date dob8 = new Date(12, 14, 1999);
        Payment payment8 = new Payment(125.0, "Alex Taylor", "8901-2345-6789-0123");
        Member member8 = new Member(1008, "Alex Taylor", "alex.taylor@gmail.com", 
            "0300-8901234", "258 Spruce Way, City", joinDate8, dob8, 25, "Female", 
            1.72, 58.0, payment8, "Endurance");
        members.add(member8);
        
        // Member 9: Middle-aged female, Flexibility, Zero balance, Joined Jan 2024
        Date joinDate9 = new Date(1, 30, 2024);
        Date dob9 = new Date(6, 18, 1982);
        Payment payment9 = new Payment(0.0, "Jennifer Martinez", "9012-3456-7890-1234");
        Member member9 = new Member(1009, "Jennifer Martinez", "jennifer.martinez@gmail.com", 
            "0300-9012345", "369 Willow Street, City", joinDate9, dob9, 42, "Female", 
            1.66, 62.0, payment9, "Flexibility");
        members.add(member9);
        
        // Member 10: Young male, General Fitness, Outstanding balance, Joined Feb 2024
        Date joinDate10 = new Date(2, 25, 2024);
        Date dob10 = new Date(10, 5, 1996);
        Payment payment10 = new Payment(50.0, "James White", "0123-4567-8901-2345");
        Member member10 = new Member(1010, "James White", "james.white@gmail.com", 
            "0300-0123456", "741 Ash Boulevard, City", joinDate10, dob10, 28, "Male", 
            1.79, 80.0, payment10, "General Fitness");
        members.add(member10);
        
        // Member 11: Senior male, General Fitness, Zero balance, Joined Mar 2024
        Date joinDate11 = new Date(3, 20, 2024);
        Date dob11 = new Date(2, 14, 1965);
        Payment payment11 = new Payment(0.0, "William Harris", "1234-5678-9012-3456");
        Member member11 = new Member(1011, "William Harris", "william.harris@gmail.com", 
            "0300-1234567", "852 Poplar Circle, City", joinDate11, dob11, 59, "Male", 
            1.76, 78.0, payment11, "General Fitness");
        members.add(member11);
        
        // Member 12: Young female, Weight Loss, Outstanding balance, Joined Apr 2024
        Date joinDate12 = new Date(4, 15, 2024);
        Date dob12 = new Date(1, 28, 2001);
        Payment payment12 = new Payment(100.0, "Olivia Clark", "2345-6789-0123-4567");
        Member member12 = new Member(1012, "Olivia Clark", "olivia.clark@gmail.com", 
            "0300-2345678", "963 Fir Avenue, City", joinDate12, dob12, 23, "Female", 
            1.64, 70.0, payment12, "Weight Loss");
        members.add(member12);
        
        return members;
    }
    
    /**
     * Generate diverse sample trainers
     */
    private ArrayList<Trainer> generateSampleTrainers() {
        ArrayList<Trainer> trainers = new ArrayList<>();
        
        // Trainer 1: Yoga specialist
        Date joinDate1 = new Date(1, 10, 2024);
        Date dob1 = new Date(5, 20, 1990);
        Trainer trainer1 = new Trainer(2001, "Jane Gym", "jane.gym@gmail.com", 
            "0300-7654321", "456 Fitness Avenue, City", joinDate1, dob1, 34, "Female", 
            "Yoga", 30.0, 20.0, "Intermediate");
        trainers.add(trainer1);
        
        // Trainer 2: Strength training specialist
        Date joinDate2 = new Date(2, 1, 2024);
        Date dob2 = new Date(8, 15, 1988);
        Trainer trainer2 = new Trainer(2002, "Mark Strong", "mark.strong@gmail.com", 
            "0300-8765432", "789 Power Street, City", joinDate2, dob2, 36, "Male", 
            "Strength Training", 45.0, 15.0, "Advanced");
        trainers.add(trainer2);
        
        // Trainer 3: Cardio specialist
        Date joinDate3 = new Date(3, 10, 2024);
        Date dob3 = new Date(3, 22, 1992);
        Trainer trainer3 = new Trainer(2003, "Amy Runner", "amy.runner@gmail.com", 
            "0300-9876543", "321 Cardio Avenue, City", joinDate3, dob3, 32, "Female", 
            "Cardio", 35.0, 18.0, "Intermediate");
        trainers.add(trainer3);
        
        // Trainer 4: Pilates specialist
        Date joinDate4 = new Date(4, 5, 2024);
        Date dob4 = new Date(11, 8, 1985);
        Trainer trainer4 = new Trainer(2004, "Sophie Core", "sophie.core@gmail.com", 
            "0300-0987654", "654 Flexibility Road, City", joinDate4, dob4, 39, "Female", 
            "Pilates", 40.0, 12.0, "Advanced");
        trainers.add(trainer4);
        
        // Trainer 5: CrossFit specialist
        Date joinDate5 = new Date(5, 20, 2024);
        Date dob5 = new Date(7, 14, 1987);
        Trainer trainer5 = new Trainer(2005, "Tom Cross", "tom.cross@gmail.com", 
            "0300-1098765", "147 Intensity Way, City", joinDate5, dob5, 37, "Male", 
            "CrossFit", 50.0, 10.0, "Expert");
        trainers.add(trainer5);
        
        // Trainer 6: General Fitness specialist
        Date joinDate6 = new Date(1, 25, 2024);
        Date dob6 = new Date(9, 30, 1991);
        Trainer trainer6 = new Trainer(2006, "Chris Fit", "chris.fit@gmail.com", 
            "0300-2109876", "258 Wellness Boulevard, City", joinDate6, dob6, 33, "Male", 
            "General Fitness", 38.0, 16.0, "Intermediate");
        trainers.add(trainer6);
        
        return trainers;
    }
    
    /**
     * Generate diverse sample machines
     */
    private ArrayList<Machine> generateSampleMachines() {
        ArrayList<Machine> machines = new ArrayList<>();
        
        // Machine 1: Treadmill (Cardio)
        Machine machine1 = new Machine(3001, "ProRunner Treadmill", "FitnessPro", 
            "PR-TM-2024", 150.0, 120.0, "Cardio");
        machines.add(machine1);
        
        // Machine 2: Bench Press (Strength)
        Machine machine2 = new Machine(3002, "PowerLift Bench", "IronGym", 
            "IG-BP-2024", 200.0, 45.0, "Strength");
        machines.add(machine2);
        
        // Machine 3: Elliptical (Cardio)
        Machine machine3 = new Machine(3003, "EcoElliptical Trainer", "CardioMax", 
            "CM-ET-2024", 120.0, 80.0, "Cardio");
        machines.add(machine3);
        
        // Machine 4: Leg Press (Strength)
        Machine machine4 = new Machine(3004, "MegaLeg Press", "PowerHouse", 
            "PH-LP-2024", 300.0, 150.0, "Strength");
        machines.add(machine4);
        
        // Machine 5: Rowing Machine (Cardio)
        Machine machine5 = new Machine(3005, "WaterRower Elite", "AquaFit", 
            "AF-RM-2024", 150.0, 30.0, "Cardio");
        machines.add(machine5);
        
        // Machine 6: Smith Machine (Strength)
        Machine machine6 = new Machine(3006, "Smith Rack System", "SteelGym", 
            "SG-SM-2024", 250.0, 200.0, "Strength");
        machines.add(machine6);
        
        // Machine 7: Stationary Bike (Cardio)
        Machine machine7 = new Machine(3007, "SpinCycle Pro", "CycleMax", 
            "CM-SB-2024", 130.0, 25.0, "Cardio");
        machines.add(machine7);
        
        // Machine 8: Cable Crossover (Functional)
        Machine machine8 = new Machine(3008, "MultiCable Station", "FlexGym", 
            "FG-CC-2024", 100.0, 180.0, "Functional");
        machines.add(machine8);
        
        return machines;
    }
    
    /**
     * Create sample bookings for machines
     * Note: Only members with zero outstanding balance can book machines
     */
    private void createSampleBookings(ArrayList<Member> members, ArrayList<Machine> machines) {
        // Find members with zero balance (indices: 0, 2, 3, 4, 6, 8, 9, 10)
        // Member 0: John Doe (zero balance)
        // Member 2: Mike Johnson (zero balance)
        // Member 3: Emily Davis (has balance - skip)
        // Member 4: Lisa Anderson (zero balance)
        // Member 6: Robert Brown (zero balance)
        // Member 8: Jennifer Martinez (zero balance)
        // Member 9: James White (has balance - skip)
        // Member 10: William Harris (zero balance)
        
        // Bookings for Machine 1 (Treadmill) - Cardio
        if (members.size() > 0 && machines.size() > 0) {
            machines.get(0).bookMachine(members.get(0)); // John Doe (zero balance)
            machines.get(0).bookMachine(members.get(2)); // Mike Johnson (zero balance)
            machines.get(0).bookMachine(members.get(4)); // Lisa Anderson (zero balance)
        }
        
        // Bookings for Machine 2 (Bench Press) - Strength
        if (members.size() > 0 && machines.size() > 1) {
            machines.get(1).bookMachine(members.get(0)); // John Doe
            machines.get(1).bookMachine(members.get(2)); // Mike Johnson
            machines.get(1).bookMachine(members.get(6)); // Robert Brown (zero balance)
        }
        
        // Bookings for Machine 3 (Elliptical) - Cardio
        if (members.size() > 2 && machines.size() > 2) {
            machines.get(2).bookMachine(members.get(2)); // Mike Johnson
            machines.get(2).bookMachine(members.get(4)); // Lisa Anderson
            machines.get(2).bookMachine(members.get(8)); // Jennifer Martinez (zero balance)
        }
        
        // Bookings for Machine 4 (Leg Press) - Strength
        if (members.size() > 0 && machines.size() > 3) {
            machines.get(3).bookMachine(members.get(0)); // John Doe
            machines.get(3).bookMachine(members.get(6)); // Robert Brown
        }
        
        // Bookings for Machine 5 (Rowing Machine) - Cardio
        if (members.size() > 2 && machines.size() > 4) {
            machines.get(4).bookMachine(members.get(2)); // Mike Johnson
            machines.get(4).bookMachine(members.get(10)); // William Harris (zero balance)
        }
        
        // Bookings for Machine 6 (Smith Machine) - Strength
        if (members.size() > 6 && machines.size() > 5) {
            machines.get(5).bookMachine(members.get(6)); // Robert Brown
            machines.get(5).bookMachine(members.get(8)); // Jennifer Martinez
        }
        
        // Bookings for Machine 7 (Stationary Bike) - Cardio
        if (members.size() > 4 && machines.size() > 6) {
            machines.get(6).bookMachine(members.get(4)); // Lisa Anderson
            machines.get(6).bookMachine(members.get(10)); // William Harris
        }
    }
    
    /**
     * Save members to MemberFile
     */
    private void saveMembers(ArrayList<Member> members) throws IOException {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("MemberFile"));
            for (Member m : members) {
                objectOutputStream.writeObject(m);
            }
            objectOutputStream.close();
        } catch (FileNotFoundException ex) {
            throw new IOException("Could not create MemberFile", ex);
        }
    }
    
    /**
     * Save trainers to TrainerFile
     */
    private void saveTrainers(ArrayList<Trainer> trainers) throws IOException {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("TrainerFile"));
            for (Trainer t : trainers) {
                objectOutputStream.writeObject(t);
            }
            objectOutputStream.close();
        } catch (FileNotFoundException ex) {
            throw new IOException("Could not create TrainerFile", ex);
        }
    }
    
    /**
     * Save machines to MachineFile
     */
    private void saveMachines(ArrayList<Machine> machines) throws IOException {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("MachineFile"));
            for (Machine m : machines) {
                objectOutputStream.writeObject(m);
            }
            objectOutputStream.close();
        } catch (FileNotFoundException ex) {
            throw new IOException("Could not create MachineFile", ex);
        }
    }
}
