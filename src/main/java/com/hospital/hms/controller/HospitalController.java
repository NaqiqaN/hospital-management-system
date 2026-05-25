package com.hospital.hms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hospital.hms.service.HospitalService;
import com.hospital.hms.model.*;
import com.hospital.hms.dto.BookingRequest;

import java.util.List;

@RestController
@RequestMapping("/hospital")
public class HospitalController {

    @Autowired
    private HospitalService service;

    // TEST API (just to check everything works)
    @GetMapping("/test")
    public String test() {
        return "Hospital API is working";
    }
    @GetMapping("/appointments")
    public List<Appointment> getAppointments() {
        return service.getAllAppointments();
    }

    @DeleteMapping("/appointments/clear")
    public String clearAppointments() {
        return service.clearAllAppointments();
    }
    
    @GetMapping("/book")
    public String bookAppointment() {

        Doctor doctor = new Doctor(
        "D01",
        "Sharma",
        "Cardiology"
        );

        Patient patient = new Patient(
        "P01",
        "Ravi",
        "Fever",
        300
        );

        return service.bookAppointment(
        doctor,
        patient,
        2,
        "09:00 AM"
        );
    }
    
    @PostMapping("/book")
    public String bookAppointmentFromJson(@RequestBody BookingRequest request) {

        Doctor doctor = new Doctor(
            request.getDoctorId(),
            request.getDoctorName(),
            request.getSpecialization()
        );

        Patient patient = new Patient(
            request.getPatientId(),
            request.getPatientName(),
            request.getDiagnosis(),
            request.getFeePerHour()
        );

        return service.bookAppointment(
            doctor,
            patient,
            0,
            request.getAppointmentTime()
        );
    }

    @GetMapping("/discharge/{patientId}/{hours}")
    public String dischargePatient(@PathVariable String patientId,
                               @PathVariable int hours) {
        return service.dischargePatient(patientId, hours);
    }
}