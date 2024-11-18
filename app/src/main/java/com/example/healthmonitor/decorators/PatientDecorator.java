package com.example.healthmonitor.decorators;

import com.example.healthmonitor.IPatient;

/**
 * A decorator class that adds additional functionality to the `IPatient` object.
 * This class implements the `IPatient` interface and delegates the `getValue` method to the wrapped `patient` object.
 */
public class PatientDecorator implements IPatient {

    // The patient object being decorated
    public IPatient patient;

    /**
     * Constructs a `PatientDecorator` with the provided `IPatient` object.
     *
     * @param patient the `IPatient` object to be decorated
     */
    public PatientDecorator(IPatient patient) {
        this.patient = patient;
    }

    /**
     * Retrieves the value representing the patient's information.
     * This method delegates the call to the wrapped `patient` object.
     *
     * @return a string containing the patient's details
     */
    public String getValue() {
        return patient.getValue();
    }
}
