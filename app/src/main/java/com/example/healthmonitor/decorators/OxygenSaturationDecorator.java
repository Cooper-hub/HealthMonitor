package com.example.healthmonitor.decorators;

import com.example.healthmonitor.IPatient;

/**
 * @brief A decorator class that adds oxygen saturation information to the patient details.
 *
 * This class extends the `PatientDecorator` and adds functionality to include the oxygen saturation level of the patient.
 * It overrides the `getValue` method to append oxygen saturation information to the existing patient data.
 */
public class OxygenSaturationDecorator extends PatientDecorator {

    /** Oxygen saturation level of the patient (e.g., "98%") */
    String oxygenSaturation;

    /**
     * @brief Constructs an `OxygenSaturationDecorator` with the provided `IPatient` object.
     *
     * This constructor initializes the decorated patient object with additional oxygen saturation data.
     *
     * @param patient The `IPatient` object to be decorated with oxygen saturation data.
     */
    public OxygenSaturationDecorator(IPatient patient) {
        super(patient);
    }

    /**
     * @brief Retrieves the patient's full information including the oxygen saturation level.
     *
     * This method overrides the `getValue` method of the `PatientDecorator` to append the oxygen saturation information
     * to the existing patient details.
     *
     * @return A string representing the patient's information along with the oxygen saturation level.
     */
    public String getValue() {
        return patient.getValue() + "OxygenSaturation " + oxygenSaturation + " ";
    }
}
