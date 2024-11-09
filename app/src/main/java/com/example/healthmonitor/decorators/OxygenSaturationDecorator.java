package com.example.healthmonitor.decorators;

import com.example.healthmonitor.Patient;
import com.example.healthmonitor.decorators.HealthMetricDecorator;

public class OxygenSaturationDecorator extends HealthMetricDecorator<Double> {

    public OxygenSaturationDecorator(PatientDecorator decoratedPatient){
        super(decoratedPatient);
    }

    public String getHeartRateInfo() {
        return "Oxygen Saturation: " + getMetricValue() + "%";
    }

    // add stuff for when we add a O2 saturation monitor
}
