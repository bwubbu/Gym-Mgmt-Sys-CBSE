package com.gymmanagement.osgi.test;

import com.gymmanagement.osgi.base.dto.MachineUsageStats;
import com.gymmanagement.osgi.base.entity.Machine;
import com.gymmanagement.osgi.base.entity.Member;
import com.gymmanagement.osgi.base.service.IMachineService;
import com.gymmanagement.osgi.base.service.IMemberService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * Interactive Demo Client for Machine Management Module
 */
public class InteractiveMachineDemo {

    private BundleContext context;
    private Scanner scanner;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public InteractiveMachineDemo(BundleContext context) {
        this.context = context;
        this.scanner = new Scanner(System.in);
    }

    public void startInteractiveMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("  Machine Management - Interactive Demo");
        System.out.println("=".repeat(50));

        while (true) {
            printMenu();
            int choice = getChoice();

            if (choice == 0) {
                System.out.println("\nExiting machine demo...");
                break;
            }

            handleChoice(choice);
        }
    }

    private void printMenu() {
        System.out.println("\n--- Machine Management Menu ---");
        System.out.println("1. View All Machines (Inventory)");
        System.out.println("2. Add New Machine");
        System.out.println("3. Update Machine");
        System.out.println("4. Delete Machine");
        System.out.println("5. Book Machine Slot");
        System.out.println("6. Schedule Maintenance");
        System.out.println("7. View Equipment Usage Statistics");
        System.out.println("8. Check Available Machines for Date");
        System.out.println("0. Exit");
        System.out.print("\nEnter your choice: ");
    }

    private int getChoice() {
        try {
            String line = scanner.nextLine().trim();
            if (line.isEmpty())
                return -1;
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void handleChoice(int choice) {
        IMachineService machineService = getMachineService();
        if (machineService == null) {
            System.out.println("❌ IMachineService not available.");
            return;
        }

        try {
            switch (choice) {
                case 1:
                    viewInventory(machineService);
                    break;
                case 2:
                    addMachine(machineService);
                    break;
                case 3:
                    updateMachine(machineService);
                    break;
                case 4:
                    deleteMachine(machineService);
                    break;
                case 5:
                    bookMachine(machineService);
                    break;
                case 6:
                    scheduleMaintenance(machineService);
                    break;
                case 7:
                    viewUsageStats(machineService);
                    break;
                case 8:
                    checkAvailability(machineService);
                    break;
                default:
                    System.out.println("❌ Invalid choice.");
            }
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // 1. View Inventory
    private void viewInventory(IMachineService service) {
        System.out.println("\n📋 Current Machine Inventory:");
        List<Machine> machines = service.getAllMachines();
        if (machines.isEmpty()) {
            System.out.println("   No machines found.");
        } else {
            System.out.printf("   %-5s %-20s %-15s %-10s %-15s\n", "ID", "Name", "Type", "Status", "Maintenance");
            System.out.println("   " + "-".repeat(70));
            for (Machine m : machines) {
                String maintStatus = (m.getMaintenanceSchedule() != null && !m.getMaintenanceSchedule().isEmpty())
                        ? "Scheduled"
                        : "None";
                System.out.printf("   %-5d %-20s %-15s %-10s %-15s\n",
                        m.getRegId(),
                        truncate(m.getName(), 20),
                        truncate(m.getType(), 15),
                        "Active",
                        maintStatus);
            }
        }
    }

    // 2. Add Machine
    private void addMachine(IMachineService service) {
        System.out.println("\n➕ Add New Machine:");

        System.out.print("   Enter ID (e.g., 101): ");
        int id = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("   Enter Name (e.g., Treadmill X1): ");
        String name = scanner.nextLine().trim();

        System.out.print("   Enter Brand: ");
        String brand = scanner.nextLine().trim();

        System.out.print("   Enter Model: ");
        String model = scanner.nextLine().trim();

        System.out.print("   Enter Price: ");
        double price = Double.parseDouble(scanner.nextLine().trim());

        System.out.print("   Enter Weight limit/Load: ");
        double weight = Double.parseDouble(scanner.nextLine().trim());

        System.out.print("   Enter Type (e.g., Cardio): ");
        String type = scanner.nextLine().trim();

        Machine m = new Machine(id, name, brand, model, price, weight, type);
        String result = service.addMachine(m);
        System.out.println("✅ Result: " + result);
    }

    // 3. Update Machine
    private void updateMachine(IMachineService service) {
        System.out.println("\n✏️ Update Machine:");
        System.out.print("   Enter ID to update: ");
        int id = Integer.parseInt(scanner.nextLine().trim());

        Machine existing = service.getMachine(id);
        if (existing == null) {
            System.out.println("❌ Machine not found.");
            return;
        }

        System.out.println("   Current Name: " + existing.getName());
        System.out.print("   Enter New Name (Press Enter to keep): ");
        String name = scanner.nextLine().trim();
        if (!name.isEmpty())
            existing.setName(name);

        System.out.println("   Current Type: " + existing.getType());
        System.out.print("   Enter New Type (Press Enter to keep): ");
        String type = scanner.nextLine().trim();
        if (!type.isEmpty())
            existing.setType(type);

        // Simplified update for demo
        String result = service.updateMachine(existing);
        System.out.println("✅ Result: " + result);
    }

    // 4. Delete Machine
    private void deleteMachine(IMachineService service) {
        System.out.println("\n🗑️ Delete Machine:");
        System.out.print("   Enter ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        String result = service.deleteMachine(id);
        System.out.println("✅ Result: " + result);
    }

    // 5. Book Machine Slot
    private void bookMachine(IMachineService service) {
        System.out.println("\n📅 Book Machine Slot:");

        IMemberService memberService = getMemberService();
        if (memberService == null) {
            System.out.println("❌ Member service unavailable, cannot validte member.");
            return;
        }

        System.out.print("   Enter Member ID: ");
        int memberId = Integer.parseInt(scanner.nextLine().trim());
        Member member = memberService.getMember(memberId);
        if (member == null) {
            System.out.println("❌ Member not found.");
            return;
        }

        System.out.print("   Enter Machine ID to book: ");
        int machineId = Integer.parseInt(scanner.nextLine().trim());

        String result = service.bookMachine(machineId, member);
        System.out.println("✅ Result: " + result);
    }

    // 6. Schedule Maintenance
    private void scheduleMaintenance(IMachineService service) {
        System.out.println("\n🔧 Schedule Maintenance:");
        System.out.print("   Enter Machine ID: ");
        int id = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("   Enter Start Date (yyyy-MM-dd): ");
        LocalDate start = LocalDate.parse(scanner.nextLine().trim(), dateFormatter);

        System.out.print("   Enter End Date (yyyy-MM-dd): ");
        LocalDate end = LocalDate.parse(scanner.nextLine().trim(), dateFormatter);

        System.out.print("   Enter Description: ");
        String desc = scanner.nextLine().trim();

        String result = service.scheduleMaintenance(id, start, end, desc);
        System.out.println("✅ Result: " + result);
    }

    // 7. View Usage Statistics
    private void viewUsageStats(IMachineService service) {
        System.out.println("\n📊 Equipment Usage Statistics:");
        List<MachineUsageStats> stats = service.getUsageStatistics();

        if (stats == null || stats.isEmpty()) {
            System.out.println("   No statistics available.");
        } else {
            System.out.printf("   %-5s %-20s %-10s %-10s %-10s\n", "ID", "Name", "Slots", "Booked", "Usage%");
            System.out.println("   " + "-".repeat(60));
            for (MachineUsageStats s : stats) {
                double usage = (s.getTotalSlots() > 0)
                        ? ((double) s.getBookedSlots() / s.getTotalSlots()) * 100.0
                        : 0.0;
                System.out.printf("   %-5d %-20s %-10d %-10d %-9.1f%%\n",
                        s.getMachineId(),
                        truncate(s.getMachineName(), 20),
                        s.getTotalSlots(),
                        s.getBookedSlots(),
                        usage);
            }
        }
    }

    // 8. Check Availability
    private void checkAvailability(IMachineService service) {
        System.out.println("\n🔍 Check Availability:");
        System.out.print("   Enter Date (yyyy-MM-dd, Enter for Today): ");
        String dateStr = scanner.nextLine().trim();
        LocalDate date = dateStr.isEmpty() ? LocalDate.now() : LocalDate.parse(dateStr, dateFormatter);

        List<Machine> available = service.getAvailableMachines(date);
        System.out.println("   Available Machines on " + date + ":");
        for (Machine m : available) {
            System.out.println("   - [" + m.getRegId() + "] " + m.getName() + " (" + m.getType() + ")");
        }
    }

    // Helpers
    private String truncate(String str, int len) {
        if (str == null)
            return "";
        if (str.length() <= len)
            return str;
        return str.substring(0, len - 3) + "...";
    }

    private IMachineService getMachineService() {
        ServiceReference<IMachineService> ref = context.getServiceReference(IMachineService.class);
        return (ref != null) ? context.getService(ref) : null;
    }

    private IMemberService getMemberService() {
        ServiceReference<IMemberService> ref = context.getServiceReference(IMemberService.class);
        return (ref != null) ? context.getService(ref) : null;
    }
}
