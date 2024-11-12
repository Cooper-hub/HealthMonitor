package com.example.healthmonitor.decorators;

import com.example.healthmonitor.IPatient;

public class PatientDecorator implements IPatient {
    public IPatient patient;
    public PatientDecorator(IPatient patient) {
        this.patient = patient;
    }

    public String getValue(){
        return patient.getValue();
    }
}
