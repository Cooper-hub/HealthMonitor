package com.example.healthmonitor;

import java.time.LocalDate;

public abstract class Patient {
    // Basic Personal Information
    private String name;
    private LocalDate dateOfBirth;
    private String gender;
    private int height;  // Measured in cm
    private int weight;  // Measured in kg
    private String contactInformation;
    private String emergencyContact;
}

