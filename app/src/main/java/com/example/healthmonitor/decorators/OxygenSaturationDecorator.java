package com.example.healthmonitor.decorators;

import com.example.healthmonitor.IPatient;
public class OxygenSaturationDecorator extends PatientDecorator {

    String oxygenSaturation;
    public OxygenSaturationDecorator(IPatient patient){
        super(patient);
    }
    public String getValue() {
        return patient.getValue() + "OxygenSaturation " + oxygenSaturation+" ";
    }
}
