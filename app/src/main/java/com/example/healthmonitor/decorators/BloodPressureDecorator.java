package com.example.healthmonitor.decorators;

import com.example.healthmonitor.activities.IPatient;
import com.example.healthmonitor.decorators.PatientDecorator;

public class BloodPressureDecorator extends PatientDecorator {

    String bloodPressure;
    public BloodPressureDecorator(IPatient patient){
       super(patient);
    }
    public String getValue() {
        return patient.getValue() + "BloodPressure: "+ bloodPressure;
    }
}
