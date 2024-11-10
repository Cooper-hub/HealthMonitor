package com.example.healthmonitor.decorators;

import com.example.healthmonitor.activities.IPatient;
import com.example.healthmonitor.decorators.PatientDecorator;

public class HeartRateDecorator extends PatientDecorator {
    String heartRate;
    public HeartRateDecorator(IPatient patient){
        super(patient);
    }

    @Override
    public String getValue() {
        return patient.getValue() + "HeartRate: "+ heartRate+" ";
    }
}
