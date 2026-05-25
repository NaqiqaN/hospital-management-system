package com.hospital.hms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.hms.model.*;
import com.hospital.hms.repository.*;

import java.util.List;

@Service
public class HospitalService {

    @Autowired
    private DoctorRepository doctorRepo;

    @Autowired
    private PatientRepository patientRepo;

    @Autowired
    private AppointmentRepository appointmentRepo;

    private final String[] availableSlots = {
            "09:00 AM",
            "10:00 AM",
            "11:00 AM",
            "12:00 PM",
            "01:00 PM",
            "02:00 PM",
            "03:00 PM",
            "04:00 PM",
            "05:00 PM",
            "06:00 PM",
            "07:00 PM",
            "08:00 PM",
            "09:00 PM"
    };

public String bookAppointment(Doctor doctor, Patient patient, int slot, String time) {

    if (!doctorRepo.existsById(doctor.getId())) {
        doctorRepo.save(doctor);
    }

    if (patientRepo.existsById(patient.getId())) {
        return "Patient ID already exists. Please use a new Patient ID.";
    }

    patientRepo.save(patient);

    String assignedTime = findAvailableSlot(time, doctor.getId());

    if (assignedTime == null) {
        return "No available slots for Dr. " + doctor.getName();
    }

    Appointment apt = new Appointment(assignedTime);

    apt.setDoctor(doctor);
    apt.setPatient(patient);

    appointmentRepo.save(apt);

    return patient.getName() + " booked with " +
            doctor.getName() + " at " + assignedTime;
}

private String findAvailableSlot(String requestedTime, String doctorId) {

    List<Appointment> existingAppointments = appointmentRepo.findAll();

    int startIndex = -1;

    for (int i = 0; i < availableSlots.length; i++) {

        if (availableSlots[i].equalsIgnoreCase(requestedTime)) {
            startIndex = i;
            break;
        }
    }

    if (startIndex == -1) {
        return null;
    }

    for (int i = startIndex; i < availableSlots.length; i++) {

        boolean isBooked = false;

        for (Appointment apt : existingAppointments) {

            if (
                apt.getAppointmentTime() != null &&
                apt.getDoctor() != null &&
                apt.getDoctor().getId().equals(doctorId) &&
                apt.getAppointmentTime().equalsIgnoreCase(availableSlots[i])
            ) {

                isBooked = true;
                break;
            }
        }

        if (!isBooked) {
            return availableSlots[i];
        }
    }

    return null;
}

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

    public String clearAllAppointments() {
        appointmentRepo.deleteAll();
        return "All appointments cleared.";
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepo.findAll();
    }
}