package com.gymmanagement.reportanalytics.dto;

import java.time.LocalDate;

/**
 * DTO for Booking data (from Equipment Booking Component)
 */
public class BookingDTO {
    private Long id;
    private int memberRegId;
    private String memberName;
    private int machineRegId;
    private String machineName;
    private LocalDate bookingDate;
    private String status;

    public BookingDTO() {
    }

    public BookingDTO(Long id, int memberRegId, String memberName, int machineRegId,
                     String machineName, LocalDate bookingDate, String status) {
        this.id = id;
        this.memberRegId = memberRegId;
        this.memberName = memberName;
        this.machineRegId = machineRegId;
        this.machineName = machineName;
        this.bookingDate = bookingDate;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getMemberRegId() {
        return memberRegId;
    }

    public void setMemberRegId(int memberRegId) {
        this.memberRegId = memberRegId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public int getMachineRegId() {
        return machineRegId;
    }

    public void setMachineRegId(int machineRegId) {
        this.machineRegId = machineRegId;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

