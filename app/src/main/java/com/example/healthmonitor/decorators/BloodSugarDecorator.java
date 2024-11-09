package com.example.healthmonitor.decorators;

import com.example.healthmonitor.Patient;

public class BloodSugarDecorator extends HealthMetricDecorator<Double>{

    public BloodSugarDecorator(PatientDecorator decoratedPatient){
        super(decoratedPatient);
    }
    // add stuff for when we add a blood sugar monitor
    public String getBloodSugarInfo() {
        return "Blood Sugar: " + getMetricValue() + " mg/dL";
    }
}
