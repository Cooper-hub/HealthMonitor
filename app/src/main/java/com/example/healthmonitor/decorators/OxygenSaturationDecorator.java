package com.example.healthmonitor.decorators;

import com.example.healthmonitor.activities.IPatient;
public class OxygenSaturationDecorator extends PatientDecorator {

    String oxygenSaturation;
    public OxygenSaturationDecorator(IPatient patient){
        super(patient);
    }
    public String getValue() {
        return patient.getValue() + "OxygenSaturation " + oxygenSaturation+" ";
    }
}
