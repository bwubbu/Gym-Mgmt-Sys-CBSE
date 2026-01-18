package com.gymmanagement.equipmentbooking.repository;

import com.gymmanagement.base.entity.Machine;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class FileMachineRepository {

    private static final String FILE_NAME = "Machine";
    private List<Machine> machines = new ArrayList<>();

    public FileMachineRepository() {
        loadMachineFile();
    }

    public List<Machine> findAll() {
        return new ArrayList<>(machines); // Return copy
    }

    public Optional<Machine> findById(int regId) {
        return machines.stream().filter(m -> m.getRegId() == regId).findFirst();
    }

    public Machine save(Machine machine) {
        Optional<Machine> existing = findById(machine.getRegId());
        if (existing.isPresent()) {
            int index = machines.indexOf(existing.get());
            machines.set(index, machine);
        } else {
            // Generate ID if 0 (simple legacy logic simulation)
            if (machine.getRegId() == 0) {
                machine.setRegId(generateAutoRegId());
            }
            machines.add(machine);
        }
        saveMachineFile();
        return machine;
    }

    public void deleteById(int regId) {
        machines.removeIf(m -> m.getRegId() == regId);
        saveMachineFile();
    }

    public boolean existsById(int regId) {
        return machines.stream().anyMatch(m -> m.getRegId() == regId);
    }
    
    private int generateAutoRegId() {
        while(true){
            int autoRegId = (int)(1000 + Math.random() * 1100);
            boolean exists = false;
            for (Machine m : machines){
                if(m.getRegId() == autoRegId){
                    exists = true;
                    break;
                }
            }
            if(!exists) return autoRegId;
        }
    }

    private void loadMachineFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            while (true) {
                try {
                    Machine machine = (Machine) ois.readObject();
                    machines.add(machine);
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveMachineFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            for (Machine m : machines) {
                oos.writeObject(m);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
