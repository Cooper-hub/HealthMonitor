package com.example.healthmonitor.decorators;

import com.example.healthmonitor.IPatient;
import com.google.firebase.database.PropertyName;

/**
 * A decorator class that extends the `PatientDecorator` to add additional medical history information.
 * This class includes fields for chronic conditions, allergies, medications, surgeries, family history,
 * and vaccination records. It also overrides the `getValue` method to include this additional information.
 */
public class PatientInformationDecorator extends PatientDecorator {

    // Medical History Information
    private String chronicConditions = null;  // E.g., diabetes, hypertension
    private String allergies = null;  // E.g., food, medication, environmental
    private String medications = null;  // Name, dosage, frequency
    private String surgeries = null;  // Surgery descriptions with dates
    private String familyHistory = null;  // E.g., heart disease, cancer
    private String vaccinationRecords = null;  // Vaccination history

    /**
     * Constructs a `PatientInformationDecorator` with the provided `IPatient` object.
     * This constructor initializes the decorated patient object.
     *
     * @param patient the `IPatient` object to be decorated
     */
    public PatientInformationDecorator(IPatient patient) {
        super(patient);
    }

    /**
     * Retrieves the chronic conditions information for the patient.
     *
     * @return a string representing the patient's chronic conditions
     */
    @PropertyName("chronicConditions")
    public String getChronicConditions() {
        return chronicConditions;
    }

    /**
     * Sets the chronic conditions information for the patient.
     *
     * @param chronicConditions the chronic conditions to set
     */
    @PropertyName("chronicConditions")
    public void setChronicConditions(String chronicConditions) {
        this.chronicConditions = chronicConditions;
    }

    /**
     * Retrieves the allergies information for the patient.
     *
     * @return a string representing the patient's allergies
     */
    @PropertyName("allergies")
    public String getAllergies() {
        return allergies;
    }

    /**
     * Sets the allergies information for the patient.
     *
     * @param allergies the allergies to set
     */
    @PropertyName("allergies")
    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    /**
     * Retrieves the medications information for the patient.
     *
     * @return a string representing the patient's medications
     */
    @PropertyName("medications")
    public String getMedications() {
        return medications;
    }

    /**
     * Sets the medications information for the patient.
     *
     * @param medications the medications to set
     */
    @PropertyName("medications")
    public void setMedications(String medications) {
        this.medications = medications;
    }

    /**
     * Retrieves the surgeries information for the patient.
     *
     * @return a string representing the patient's surgeries
     */
    @PropertyName("surgeries")
    public String getSurgeries() {
        return surgeries;
    }

    /**
     * Sets the surgeries information for the patient.
     *
     * @param surgeries the surgeries to set
     */
    @PropertyName("surgeries")
    public void setSurgeries(String surgeries) {
        this.surgeries = surgeries;
    }

    /**
     * Retrieves the family history information for the patient.
     *
     * @return a string representing the patient's family history
     */
    @PropertyName("familyHistory")
    public String getFamilyHistory() {
        return familyHistory;
    }

    /**
     * Sets the family history information for the patient.
     *
     * @param familyHistory the family history to set
     */
    @PropertyName("familyHistory")
    public void setFamilyHistory(String familyHistory) {
        this.familyHistory = familyHistory;
    }

    /**
     * Retrieves the vaccination records information for the patient.
     *
     * @return a string representing the patient's vaccination records
     */
    @PropertyName("vaccineRecords")
    public String getVaccinationRecords() {
        return vaccinationRecords;
    }

    /**
     * Sets the vaccination records information for the patient.
     *
     * @param vaccinationRecords the vaccination records to set
     */
    @PropertyName("vaccineRecords")
    public void setVaccinationRecords(String vaccinationRecords) {
        this.vaccinationRecords = vaccinationRecords;
    }

    /**
     * Retrieves the patient's full information, including basic information from the decorated `patient` object
     * and additional medical history details.
     *
     * @return a string containing all available patient information, including medical history
     */
    public String getValue() {
        StringBuilder value = new StringBuilder();

        // Append medical history details
        value.append("Chronic Conditions: ").append(chronicConditions != null ? chronicConditions : "Not Available ").append("\n")
                .append("Allergies: ").append(allergies != null ? allergies : "Not Available ").append("\n")
                .append("Medications: ").append(medications != null ? medications : "Not Available ").append("\n")
                .append("Surgeries: ").append(surgeries != null ? surgeries : "Not Available ").append("\n")
                .append("Family History: ").append(familyHistory != null ? familyHistory : "Not Available ").append("\n")
                .append("Vaccination Records: ").append(vaccinationRecords != null ? vaccinationRecords : "Not Available ").append("\n");

        // Append the base patient information from the decorated object
        return patient.getValue() + value.toString();
    }
}
