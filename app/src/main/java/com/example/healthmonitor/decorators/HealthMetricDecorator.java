package com.example.healthmonitor.decorators;

import com.example.healthmonitor.IObserver;
import com.example.healthmonitor.ISubject;
import com.example.healthmonitor.Patient;

import java.util.ArrayList;
import java.util.List;

public class HealthMetricDecorator<T> extends Patient implements ISubject {
    private Patient patient;
    private T metricValue;  // A generic metric (like blood sugar, temperature, etc.)

    private final List<IObserver> observers = new ArrayList<>();

    public HealthMetricDecorator(Patient patient) {
        this.patient = patient;
    }

    // Setter for the metric value
    public void setMetricValue(T metricValue) {
        this.metricValue = metricValue;
        notifyObservers();
    }

    // Getter for the metric value with a default message
    public String getMetricValue() {
        return (metricValue != null) ? metricValue.toString() : "No measurement available";
    }

    // Register an observer
    public void registerObserver(IObserver observer) {
        observers.add(observer);
    }

    // Unregister an observer
    public void unregisterObserver(IObserver observer) {
        observers.remove(observer);
    }

    // Notify all registered observers
    public void notifyObservers() {
        for (IObserver observer : observers) {
            observer.update(this.getMetricValue());
        }
    }
}
