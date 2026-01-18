package com.gymmanagement.base.entity;

import java.io.Serializable;

/**
 * Maintenance Entity
 * Represents a maintenance record for a machine.
 */
public class Maintenance implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int machineId;
    private Date startDate;
    private Date endDate;
    private String description;
    private String status; // "SCHEDULED", "IN_PROGRESS", "COMPLETED"

    public Maintenance() {
    }

    public Maintenance(int id, int machineId, Date startDate, Date endDate, String description, String status) {
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
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
        return "Maintenance [id=" + id + ", machineId=" + machineId + ", status=" + status + "]";
    }
}
