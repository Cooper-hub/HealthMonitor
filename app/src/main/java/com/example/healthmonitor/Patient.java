package com.example.healthmonitor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Patient extends User implements ISubject{
    // Basic personal information collected from BC service card

    private LocalDate dateOfBirth;
    private String gender;
    private float height;  // Measured in cm
    private float weight;  // Measured in kg

    private final List<IObserver> observers = new ArrayList<>();

    // Getter for dateOfBirth
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    // Setter for dateOfBirth
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    // Getter for gender
    public String getGender() {
        return gender;
    }

    // Setter for gender
    public void setGender(String gender) {
        this.gender = gender;
    }

    // Getter for height
    public float getHeight() {
        return height;
    }

    // Setter for height
    public void setHeight(float height) {
        this.height = height;
    }

    // Getter for weight
    public float getWeight() {
        return weight;
    }

    // Setter for weight
    public void setWeight(float weight) {
        this.weight = weight;
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
            observer.update(this);
        }
    }
}

