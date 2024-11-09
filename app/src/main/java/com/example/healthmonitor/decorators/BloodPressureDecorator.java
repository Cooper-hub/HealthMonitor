package com.example.healthmonitor.decorators;

import com.example.healthmonitor.Patient;

public class BloodPressureDecorator extends HealthMetricDecorator<String>{

    public BloodPressureDecorator(PatientDecorator decoratedPatient){
       super(decoratedPatient);
    }
    public String getBloodSugarInfo() {
        return "Blood Pressure: " + getMetricValue() + " mmHg";
    }
}
