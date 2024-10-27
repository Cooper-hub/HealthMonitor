package com.example.healthmonitor.decorators;

import com.example.healthmonitor.Patient;

public class BodyTemperatureDecorator extends HealthMetricDecorator<Double>{
    public BodyTemperatureDecorator(Patient patient){
        super(patient);
    }
    // add stuff for when we add a body temperature monitor

    public String getBodyTemperatureInfo() {
        return "Body Temperature: " + getMetricValue() + " °C";
    }
}
