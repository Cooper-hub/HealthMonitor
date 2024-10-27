package com.example.healthmonitor.decorators;

import com.example.healthmonitor.Patient;

public class BloodPressureDecorator extends HealthMetricDecorator<String>{

    public BloodPressureDecorator(Patient patient){
        super(patient);
    }
    public String getBloodSugarInfo() {
        return "Blood Pressure: " + getMetricValue() + " mmHg";
    }
}
