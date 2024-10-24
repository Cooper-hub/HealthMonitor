package com.example.healthmonitor;

public class BloodSugarDecorator extends HealthMetricDecorator<Double>{

    public BloodSugarDecorator(Patient patient){
        super(patient);
    }
    // add stuff for when we add a blood sugar monitor
    public String getBloodSugarInfo() {
        return "Blood Sugar: " + getMetricValue() + " mg/dL";
    }
}
