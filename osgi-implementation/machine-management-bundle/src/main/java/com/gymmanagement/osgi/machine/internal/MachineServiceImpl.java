package com.gymmanagement.osgi.machine.internal;

import com.gymmanagement.osgi.base.entity.Machine;
import com.gymmanagement.osgi.base.entity.Member;
import com.gymmanagement.osgi.base.service.IMachineService;
import java.util.ArrayList;
import java.util.List;

public class MachineServiceImpl implements IMachineService {

    private List<Machine> machines = new ArrayList<>();

    @Override
    public String addMachine(Machine machine) {
        if (machine == null) return "Invalid machine data.";
        for (Machine m : machines) {
            if (m.getRegId() == machine.getRegId()) {
                return "Machine with ID " + machine.getRegId() + " already exists.";
            }
        }
        machines.add(machine);
        return "Machine added successfully.";
    }

    @Override
    public Machine getMachine(int regId) {
        for (Machine m : machines) {
            if (m.getRegId() == regId) {
                return m;
            }
        }
        return null;
    }

    @Override
    public List<Machine> getAllMachines() {
        return new ArrayList<>(machines);
    }

    @Override
    public String updateMachine(Machine machine) {
        for (int i = 0; i < machines.size(); i++) {
            if (machines.get(i).getRegId() == machine.getRegId()) {
                machines.set(i, machine);
                return "Machine updated successfully.";
            }
        }
        return "Machine not found.";
    }

    @Override
    public String deleteMachine(int regId) {
        return machines.removeIf(m -> m.getRegId() == regId) ? "Machine deleted." : "Machine not found.";
    }

    @Override
    public String bookMachine(int machineId, Member member) {
        Machine m = getMachine(machineId);
        if (m == null) return "Machine not found.";
        return m.bookMachine(member);
    }

    @Override
    public String cancelBooking(int machineId, int memberId) {
        Machine m = getMachine(machineId);
        if (m == null) return "Machine not found.";
        return m.cancelBooking(memberId);
    }

    @Override
    public String getAllBookings(int machineId) {
        Machine m = getMachine(machineId);
        if (m == null) return "Machine not found.";
        return m.getAllBookings();
    }
}
