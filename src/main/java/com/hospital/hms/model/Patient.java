package com.hospital.hms.model;

import jakarta.persistence.*;

@Entity
@Table(name = "patients")
public class Patient {

    @Id
    private String id;

    private String name;

    private String diagnosis;

    @Column(name = "fee_per_hour")
    private int feePerHour;

    public Patient() {}

    public Patient(String id, String name, String diagnosis, int feePerHour) {
        this.id = id;
        this.name = name;
        this.diagnosis = diagnosis;
        this.feePerHour = feePerHour;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }

    public int getFeePerHour() { return feePerHour; }
    public void setFeePerHour(int feePerHour) { this.feePerHour = feePerHour; }
}