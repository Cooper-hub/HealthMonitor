package com.example.healthmonitor;

import java.time.LocalDate;

/**
 * Represents a patient in the health monitoring system. The Patient class extends the User class
 * and implements the IPatient interface. It stores additional personal information about the patient,
 * including date of birth, gender, height, and weight.
 */
public class Patient extends User implements IPatient {

    // Basic personal information collected from BC service card
    private LocalDate dateOfBirth;
    private String gender;
    private float height;  // Measured in cm
    private float weight;  // Measured in kg

    /**
     * Gets the date of birth of the patient.
     *
     * @return the date of birth of the patient
     */
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets the date of birth for the patient.
     *
     * @param dateOfBirth the date of birth to be set
     */
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Gets the gender of the patient.
     *
     * @return the gender of the patient
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the gender for the patient.
     *
     * @param gender the gender to be set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Gets the height of the patient.
     *
     * @return the height of the patient in centimeters
     */
    public float getHeight() {
        return height;
    }

    /**
     * Sets the height for the patient.
     *
     * @param height the height to be set in centimeters
     */
    public void setHeight(float height) {
        this.height = height;
    }

    /**
     * Gets the weight of the patient.
     *
     * @return the weight of the patient in kilograms
     */
    public float getWeight() {
        return weight;
    }

    /**
     * Sets the weight for the patient.
     *
     * @param weight the weight to be set in kilograms
     */
    public void setWeight(float weight) {
        this.weight = weight;
    }

    /**
     * Returns a string representation of the patient's information, including
     * date of birth, gender, height, and weight.
     *
     * @return a string containing the patient's information
     */
    public String getValue() {
        return "Patient Info: " + "DateOfBirth: " + dateOfBirth + " Gender: " + gender + " Height: " + height + " Weight: " + weight + " ";
    }
}
