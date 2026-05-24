package com.hospital.hms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hospital.hms.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
}