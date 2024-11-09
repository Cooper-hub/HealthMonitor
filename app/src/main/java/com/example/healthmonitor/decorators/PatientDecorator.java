package com.example.healthmonitor.decorators;

import com.example.healthmonitor.Patient;

public class PatientDecorator extends Patient {
    public Patient patient;
    public PatientDecorator(Patient patient) {
        this.patient = patient;
    }
    public Patient getDecoratedPatient() {
        return patient;
    }
}
