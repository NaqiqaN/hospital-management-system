package com.hospital.hms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hospital.hms.service.HospitalService;
import com.hospital.hms.model.*;

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

    @GetMapping("/discharge/{patientId}/{hours}")
    public String dischargePatient(@PathVariable String patientId,
                               @PathVariable int hours) {
        return service.dischargePatient(patientId, hours);
    }
}