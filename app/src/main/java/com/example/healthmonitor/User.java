package com.example.healthmonitor;
import java.time.LocalDate;

import java.time.LocalDate;
import java.util.List;

public class User {
    // Basic Personal Information
    private String name;
    private LocalDate dateOfBirth;
    private String gender;
    private int height;  // Measured in cm
    private int weight;  // Measured in kg
    private String contactInformation;
    private String emergencyContact;

    // Medical History
    private List<String> chronicConditions;  // E.g., diabetes, hypertension
    private List<String> allergies;  // E.g., food, medication, environmental
    private List<String> medications;  // Name, dosage, frequency
    private List<String> surgeries;  // Surgery descriptions with dates
    private List<String> familyHistory;  // E.g., heart disease, cancer
    private List<String> vaccinationRecords;

    // Health Measurements
    private String bloodPressure;  // E.g., 120/80
    private int heartRate;  // Beats per minute
    private double bloodSugar;  // In mg/dL
    private double bodyTemperature;  // In Celsius
    private double oxygenSaturation;  // SpO2 in percentage
}
