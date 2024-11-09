package com.example.healthmonitor.decorators;

import com.example.healthmonitor.Patient;
import com.example.healthmonitor.activities.IPatient;

public class PatientDecorator implements IPatient {
    public IPatient patient;
    public PatientDecorator(IPatient patient) {
        this.patient = patient;
    }

    public String getValue(){
        return patient.getValue();
    }
}
