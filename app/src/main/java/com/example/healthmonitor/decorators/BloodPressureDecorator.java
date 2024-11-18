package com.example.healthmonitor.decorators;

import com.example.healthmonitor.IPatient;

/**
 * @brief A decorator class that adds blood pressure information to the patient details.
 *
 * This class extends the `PatientDecorator` and adds functionality to include the blood pressure of the patient.
 * It overrides the `getValue` method to append blood pressure information to the existing patient data.
 */
public class BloodPressureDecorator extends PatientDecorator {

    /** Blood pressure level of the patient (e.g., "120/80 mmHg") */
    String bloodPressure;

    /**
     * @brief Constructs a `BloodPressureDecorator` with the provided `IPatient` object.
     *
     * This constructor initializes the decorated patient object with additional blood pressure data.
     *
     * @param patient The `IPatient` object to be decorated with blood pressure data.
     */
    public BloodPressureDecorator(IPatient patient) {
        super(patient);
    }

    /**
     * @brief Retrieves the patient's full information including the blood pressure level.
     *
     * This method overrides the `getValue` method of the `PatientDecorator` to append the blood pressure level
     * to the existing patient details.
     *
     * @return A string representing the patient's information along with the blood pressure level.
     */
    public String getValue() {
        return patient.getValue() + "BloodPressure: " + bloodPressure + " ";
    }
}
