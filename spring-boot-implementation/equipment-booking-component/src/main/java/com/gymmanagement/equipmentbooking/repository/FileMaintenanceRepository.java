package com.gymmanagement.equipmentbooking.repository;

import com.gymmanagement.base.entity.Maintenance;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class FileMaintenanceRepository {

    private static final String FILE_NAME = "maintenance.dat";
    private List<Maintenance> maintenanceList = new ArrayList<>();

    public FileMaintenanceRepository() {
        loadMaintenanceFile();
    }

    public List<Maintenance> findAll() {
        return new ArrayList<>(maintenanceList);
    }

    public List<Maintenance> findByMachineId(int machineId) {
        return maintenanceList.stream()
                .filter(m -> m.getMachineId() == machineId)
                .collect(Collectors.toList());
    }

    public List<Maintenance> findActiveByMachineId(int machineId, java.time.LocalDate date) {
        return maintenanceList.stream()
                .filter(m -> m.getMachineId() == machineId)
                .filter(m -> !"COMPLETED".equalsIgnoreCase(m.getStatus()))
                .filter(m -> {
                    if (m.getStartDate() == null || m.getEndDate() == null) return false;
                    java.time.LocalDate start = m.getStartDate().toLocalDate();
                    java.time.LocalDate end = m.getEndDate().toLocalDate();
                    return !date.isBefore(start) && !date.isAfter(end);
                })
                .collect(Collectors.toList());
    }

    public Maintenance save(Maintenance maintenance) {
        if (maintenance.getId() == 0) {
            maintenance.setId(generateAutoId());
            maintenanceList.add(maintenance);
        } else {
            // Update existing
            for (int i = 0; i < maintenanceList.size(); i++) {
                if (maintenanceList.get(i).getId() == maintenance.getId()) {
                    maintenanceList.set(i, maintenance);
                    saveMaintenanceFile();
                    return maintenance;
                }
            }
            // If not found, treat as new
            maintenanceList.add(maintenance);
        }
        saveMaintenanceFile();
        return maintenance;
    }

    private int generateAutoId() {
        int maxId = maintenanceList.stream().mapToInt(Maintenance::getId).max().orElse(0);
        return maxId + 1;
    }

    @SuppressWarnings("unchecked")
    private void loadMaintenanceFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            maintenanceList = (List<Maintenance>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Start fresh if corrupt or old format
            maintenanceList = new ArrayList<>();
        }
    }

    private void saveMaintenanceFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(new ArrayList<>(maintenanceList));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
