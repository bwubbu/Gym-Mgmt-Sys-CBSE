package com.gymmanagement.osgi.base.entity;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Maintenance entity for OSGi Base Library Bundle
 */
public class Maintenance implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int machineId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private String status; // "SCHEDULED", "IN_PROGRESS", "COMPLETED", "CANCELLED"

    public Maintenance() {
    }

    public Maintenance(int id, int machineId, LocalDate startDate, LocalDate endDate, String description,
            String status) {
        this.id = id;
        this.machineId = machineId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Maintenance{" +
                "id=" + id +
                ", machineId=" + machineId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status='" + status + '\'' +
                '}';
    }
}
