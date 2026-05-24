package com.hospital.hms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.hms.model.*;
import com.hospital.hms.repository.*;

import java.util.List;
import java.util.Optional;

@Service
public class HospitalService {

    @Autowired
    private DoctorRepository doctorRepo;

    @Autowired
    private PatientRepository patientRepo;

    @Autowired
    private AppointmentRepository appointmentRepo;

    // BOOK APPOINTMENT
    public String bookAppointment(Doctor doctor, Patient patient, int slot, String time) {

        // Save doctor if not exists
        if (!doctorRepo.existsById(doctor.getId())) {
            doctorRepo.save(doctor);
        }

        // Save patient if not exists
        if (!patientRepo.existsById(patient.getId())) {
            patientRepo.save(patient);
        }

        // Create appointment
        Appointment apt = new Appointment(time);
        apt.setDoctor(doctor);
        apt.setPatient(patient);

        appointmentRepo.save(apt);

        return patient.getName() + " booked with " +
               doctor.getName() + " at slot " + slot;
    }

    // DISCHARGE PATIENT
    public String dischargePatient(String patientId, int hours) {

        List<Appointment> list = appointmentRepo.findAll();

        for (Appointment apt : list) {
            if (apt.getPatient() != null &&
                apt.getPatient().getId().equals(patientId)) {

                int fee = apt.getPatient().getFeePerHour() * hours;

                appointmentRepo.delete(apt);

                return "Patient discharged. Fee = Rs." + fee;
            }
        }
        return "Patient not found";
    }

    // VIEW ALL APPOINTMENTS
    public List<Appointment> getAllAppointments() {
        return appointmentRepo.findAll();
    }
}