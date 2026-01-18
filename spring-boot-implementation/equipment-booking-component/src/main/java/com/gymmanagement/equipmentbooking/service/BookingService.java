package com.gymmanagement.equipmentbooking.service;

import com.gymmanagement.base.entity.Member;
import com.gymmanagement.base.entity.Machine;
import com.gymmanagement.base.entity.Maintenance;
import com.gymmanagement.equipmentbooking.repository.FileMachineRepository;
import com.gymmanagement.equipmentbooking.repository.FileMaintenanceRepository;
import com.gymmanagement.base.service.IBookingService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;

@Service
public class BookingService implements IBookingService {

    private final FileMachineRepository machineRepository;
    private final FileMaintenanceRepository maintenanceRepository;
    private final DataService dataService;

    public BookingService(FileMachineRepository machineRepository, 
                          FileMaintenanceRepository maintenanceRepository,
                          DataService dataService) {
        this.machineRepository = machineRepository;
        this.maintenanceRepository = maintenanceRepository;
        this.dataService = dataService;
    }

    // --- IBookingService Implementation ---

    @Override
    public String bookMachine(int memberRegId, int machineRegId) {
        Machine machine = machineRepository.findById(machineRegId).orElse(null);
        if (machine == null) {
            return "Machine not found.";
        }

        // Check for active maintenance
        List<Maintenance> activeMaintenance = maintenanceRepository.findActiveByMachineId(machineRegId, LocalDate.now());
        if (!activeMaintenance.isEmpty()) {
            return "Machine is currently under maintenance.";
        }

        Member member = dataService.getMember(memberRegId);
        if (member == null) {
            return "Member not found.";
        }

        // Delegate to the Base entity's logic. Note that it works in-memory on the array.
        String result = machine.bookMachine(member);
        
        // The Base library returns specific strings ("booked", "The machine is completely booked.", etc.)
        // We only persist the change if the operation was successful.
        if (result.equals("booked")) {
            machineRepository.save(machine);
        }
        
        return result;
    }

    @Override
    public String cancelBooking(int memberRegId, int machineRegId) {
        Machine machine = machineRepository.findById(machineRegId).orElse(null);
        if (machine == null) {
            return "Machine not found.";
        }
        
        // Delegate cancellation to the Base entity.
        String result = machine.cancelBooking(memberRegId);
        
        // Only persist if the cancellation was successful (Base library returns "Your Booking canceled").
        if (result.equals("Your Booking canceled")) {
            machineRepository.save(machine);
        }
        return result;
    }

    @Override
    public List<Member> getMachineBookings(int machineRegId) {
        Machine machine = machineRepository.findById(machineRegId).orElse(null);
        if (machine == null) {
            return new ArrayList<>();
        }
        // The Machine entity stores bookings as a fixed-size array (Member[8]).
        // We filter out null slots and return a clean List<Member>.
        List<Member> list = new ArrayList<>();
        for (Member m : machine.getBookings()) {
            if (m != null) {
                list.add(m);
            }
        }
        return list;
    }

    @Override
    public List<Machine> getMemberBookings(int memberRegId) {
        List<Machine> result = new ArrayList<>();
        for (Machine m : machineRepository.findAll()) {
            for (Member booking : m.getBookings()) {
                if (booking != null && booking.getRegId() == memberRegId) {
                    result.add(m);
                    break; 
                }
            }
        }
        return result;
    }

    @Override
    public String getAllBookings() {
        StringBuilder sb = new StringBuilder();
        List<Machine> all = machineRepository.findAll();
        for (Machine m : all) {
            sb.append(m.getAllBookings());
        }
        return sb.toString();
    }

    @Override
    public Machine addMachine(Machine machine) {
        return machineRepository.save(machine);
    }

    @Override
    public Machine getMachineById(int regId) {
        return machineRepository.findById(regId).orElse(null);
    }

    @Override
    public List<Machine> getAllMachines() {
        return machineRepository.findAll();
    }

    @Override
    public Machine updateMachine(int regId, Machine machine) {
        // Security/Consistency check: Ensure the payload ID matches the URL path ID.
        // If they don't match, set the machine's ID to the path ID to ensure the correct machine is updated.
        if (machine.getRegId() != regId) {
            machine.setRegId(regId);
        }
        return machineRepository.save(machine);
    }

    @Override
    public boolean deleteMachine(int regId) {
        if (machineRepository.existsById(regId)) {
            machineRepository.deleteById(regId);
            return true;
        }
        return false;
    }

    @Override
    public boolean machineExists(int regId) {
        return machineRepository.existsById(regId);
    }

    @Override
    public List<Machine> searchMachinesByName(String name) {
        return machineRepository.findAll().stream()
                .filter(m -> m.getName() != null && m.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public Machine searchMachineByRegId(int regId) {
        return getMachineById(regId);
    }

    @Override
    public boolean validateBooking(int memberRegId, int machineRegId) {
        return machineRepository.existsById(machineRegId);
    }

    @Override
    public boolean isMachineAvailable(int machineRegId) {
        Machine m = getMachineById(machineRegId);
        if (m == null) return false;
        
        // Availability is defined as having at least one empty (null) slot 
        // in the fixed-size bookings array.
        for (Member b : m.getBookings()) {
            if (b == null) return true;
        }
        return false;
    }

    @Override
    public String scheduleMaintenance(Maintenance maintenance) {
        if (!machineRepository.existsById(maintenance.getMachineId())) {
            return "Machine not found. Cannot schedule maintenance.";
        }
        maintenanceRepository.save(maintenance);
        return "Maintenance scheduled successfully.";
    }

    @Override
    public List<Maintenance> getMaintenanceByMachine(int machineId) {
        return maintenanceRepository.findByMachineId(machineId);
    }
}
