package com.example.healthmonitor;

import java.time.LocalDate;

public class Patient extends User implements IPatient{
    // Basic personal information collected from BC service card
    private LocalDate dateOfBirth;
    private String gender;
    private float height;  // Measured in cm
    private float weight;  // Measured in kg

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

    public String getValue() {
        return "Patient Info: " + "DateOfBirth: " + dateOfBirth + " Gender: " + gender + " Height: " + height + " Weight: " + weight + " ";
    }
}

