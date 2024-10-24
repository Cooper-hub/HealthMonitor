package com.example.healthmonitor;

public class HeartRateDecorator extends HealthMetricDecorator<Integer>{

    public HeartRateDecorator(Patient patient){
        super(patient);
    }
    // add stuff for when we add a heart rate monitor
    public String getBloodSugarInfo() {
        return "Heart Rate: " + getMetricValue() + " BPM";
    }

}
