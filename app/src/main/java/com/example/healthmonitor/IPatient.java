package com.example.healthmonitor;

/**
 * This interface represents a contract for patient-related classes.
 * Any class that implements this interface should provide an implementation for retrieving patient information.
 */
public interface IPatient {

    /**
     * Retrieves the value representing the patient information.
     *
     * @return a string containing the patient's details
     */
    String getValue();
}
