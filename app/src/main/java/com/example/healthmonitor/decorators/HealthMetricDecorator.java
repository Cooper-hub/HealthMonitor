package com.example.healthmonitor.decorators;

import com.example.healthmonitor.Patient;

public class HealthMetricDecorator<T> extends Patient {
    private Patient patient;
    private T metricValue;  // A generic metric (like blood sugar, temperature, etc.)

    public HealthMetricDecorator(Patient patient) {
        this.patient = patient;
    }

    // Setter for the metric value
    public void setMetricValue(T metricValue) {
        this.metricValue = metricValue;
    }

    // Getter for the metric value with a default message
    public String getMetricValue() {
        return (metricValue != null) ? metricValue.toString() : "No measurement available";
    }

    // Optional: Access patient data
    public Patient getPatient() {
        return patient;
    }
}
