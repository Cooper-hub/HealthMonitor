package com.example.healthmonitor.decorators;

import com.example.healthmonitor.IPatient;

/**
 * @brief A decorator class that adds heart rate information to the patient details.
 *
 * This class extends the `PatientDecorator` and adds functionality to include the heart rate of the patient.
 * It overrides the `getValue` method to append heart rate information to the existing patient data.
 */
public class HeartRateDecorator extends PatientDecorator {

    /** Heart rate of the patient (e.g., "72 bpm") */
    String heartRate;

    /**
     * @brief Constructs a `HeartRateDecorator` with the provided `IPatient` object.
     *
     * This constructor initializes the decorated patient object with additional heart rate data.
     *
     * @param patient The `IPatient` object to be decorated with heart rate data.
     */
    public HeartRateDecorator(IPatient patient) {
        super(patient);
    }

    /**
     * @brief Retrieves the patient's full information including the heart rate.
     *
     * This method overrides the `getValue` method of the `PatientDecorator` to append the heart rate information
     * to the existing patient details.
     *
     * @return A string representing the patient's information along with the heart rate.
     */
    @Override
    public String getValue() {
        return patient.getValue() + "HeartRate: " + heartRate + " ";
    }
}
