package com.example.healthmonitor.decorators;

import com.example.healthmonitor.activities.IPatient;
import com.example.healthmonitor.decorators.PatientDecorator;

public class BodyTemperatureDecorator extends PatientDecorator {
    String bodyTemperature;
    public BodyTemperatureDecorator(IPatient patient){
        super(patient);
    }
    // add stuff for when we add a body temperature monitor
    public String getValue() {
        return patient.getValue() + "BodyTemperature: "+ bodyTemperature+" ";
    }
}
