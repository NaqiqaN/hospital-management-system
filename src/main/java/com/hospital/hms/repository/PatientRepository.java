package com.hospital.hms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hospital.hms.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, String> {
}