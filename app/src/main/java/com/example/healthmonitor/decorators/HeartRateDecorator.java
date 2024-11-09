package com.example.healthmonitor.decorators;

import com.example.healthmonitor.Patient;

public class HeartRateDecorator extends HealthMetricDecorator<Integer>{

    public HeartRateDecorator(PatientDecorator decoratedPatient){
        super(decoratedPatient);
    }
    // add stuff for when we add a heart rate monitor
    public String getBloodSugarInfo() {
        return "Heart Rate: " + getMetricValue() + " BPM";
    }

}
