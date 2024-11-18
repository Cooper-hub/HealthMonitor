package com.example.healthmonitor.decorators;

import com.example.healthmonitor.IPatient;

/**
 * @brief A decorator class that adds body temperature information to the patient details.
 *
 * This class extends the `PatientDecorator` and adds functionality to include the body temperature of the patient.
 * It overrides the `getValue` method to append body temperature information to the existing patient data.
 */
public class BodyTemperatureDecorator extends PatientDecorator {

    /** Body temperature of the patient (e.g., "36.6°C") */
    String bodyTemperature;

    /**
     * @brief Constructs a `BodyTemperatureDecorator` with the provided `IPatient` object.
     *
     * This constructor initializes the decorated patient object with additional body temperature data.
     *
     * @param patient The `IPatient` object to be decorated with body temperature data.
     */
    public BodyTemperatureDecorator(IPatient patient) {
        super(patient);
    }

    /**
     * @brief Retrieves the patient's full information including the body temperature.
     *
     * This method overrides the `getValue` method of the `PatientDecorator` to append the body temperature information
     * to the existing patient details.
     *
     * @return A string representing the patient's information along with the body temperature.
     */
    public String getValue() {
        return patient.getValue() + "BodyTemperature: " + bodyTemperature + " ";
    }
}
