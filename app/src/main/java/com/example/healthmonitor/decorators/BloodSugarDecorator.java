package com.example.healthmonitor.decorators;

import com.example.healthmonitor.IPatient;

public class BloodSugarDecorator extends PatientDecorator {

    String bloodSugar;
    public BloodSugarDecorator(IPatient patient){
        super(patient);
    }

    @Override
    public String getValue() {
        return patient.getValue() + "BloodSugar: " + bloodSugar +" ";
    }
}
