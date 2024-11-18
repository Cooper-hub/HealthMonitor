package com.example.healthmonitor.decorators;

import com.example.healthmonitor.IPatient;

/**
 * @brief A decorator class that adds blood sugar information to the patient details.
 *
 * This class extends the `PatientDecorator` and adds functionality to include the blood sugar level of the patient.
 * It overrides the `getValue` method to append blood sugar information to the existing patient data.
 */
public class BloodSugarDecorator extends PatientDecorator {

    /** Blood sugar level of the patient (e.g., "110 mg/dL") */
    String bloodSugar;

    /**
     * @brief Constructs a `BloodSugarDecorator` with the provided `IPatient` object.
     *
     * This constructor initializes the decorated patient object with additional blood sugar data.
     *
     * @param patient The `IPatient` object to be decorated with blood sugar data.
     */
    public BloodSugarDecorator(IPatient patient) {
        super(patient);
    }

    /**
     * @brief Retrieves the patient's full information including the blood sugar level.
     *
     * This method overrides the `getValue` method of the `PatientDecorator` to append the blood sugar level
     * to the existing patient details.
     *
     * @return A string representing the patient's information along with the blood sugar level.
     */
    @Override
    public String getValue() {
        return patient.getValue() + "BloodSugar: " + bloodSugar + " ";
    }
}
