package com.example.healthmonitor.decorators;

import com.example.healthmonitor.IPatient;

public class BloodPressureDecorator extends PatientDecorator {

    String bloodPressure;
    public BloodPressureDecorator(IPatient patient){
       super(patient);
    }
    public String getValue() {
        return patient.getValue() + "BloodPressure: "+ bloodPressure +" ";
    }
}
