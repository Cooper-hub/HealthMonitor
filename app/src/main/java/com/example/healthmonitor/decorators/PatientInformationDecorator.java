package com.example.healthmonitor.decorators;

import com.example.healthmonitor.IPatient;
import com.google.firebase.database.PropertyName;

public class PatientInformationDecorator extends PatientDecorator {
    public PatientInformationDecorator(IPatient patient){
        super(patient);
    }
    // Medical History
    private String chronicConditions = null;  // E.g., diabetes, hypertension
    private String allergies = null;  // E.g., food, medication, environmental
    private String medications = null;  // Name, dosage, frequency
    private String surgeries = null;  // Surgery descriptions with dates
    private String familyHistory = null;  // E.g., heart disease, cancer
    private String vaccinationRecords = null;

    @PropertyName("chronicConditions")
    public String getChronicConditions() {
        return chronicConditions;
    }

    @PropertyName("chronicConditions")
    public void setChronicConditions(String chronicConditions) {
        this.chronicConditions = chronicConditions;
    }

    @PropertyName("allergies")
    public String getAllergies() {
        return allergies;
    }

    @PropertyName("allergies")
    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    @PropertyName("medications")
    public String getMedications() {
        return medications;
    }

    @PropertyName("medications")

    public void setMedications(String medications) {
        this.medications = medications;
    }
    @PropertyName("surgeries")

    public String getSurgeries() {
        return surgeries;
    }
    @PropertyName("surgeries")

    public void setSurgeries(String surgeries) {
        this.surgeries = surgeries;
    }

    @PropertyName("familyHistory")

    public String getFamilyHistory() {
        return familyHistory;
    }
    @PropertyName("familyHistory")
    public void setFamilyHistory(String familyHistory) {
        this.familyHistory = familyHistory;
    }
    @PropertyName("vaccineRecords")
    public String getVaccinationRecords() {
        return vaccinationRecords;
    }
    @PropertyName("vaccineRecords")

    public void setVaccinationRecords(String vaccinationRecords) {
        this.vaccinationRecords = vaccinationRecords;
    }
    public String getValue() {
        StringBuilder value = new StringBuilder();

        value.append("Chronic Conditions: ").append(chronicConditions != null ? chronicConditions : "Not Available ").append("\n")
                .append("Allergies: ").append(allergies != null ? allergies : "Not Available ").append("\n")
                .append("Medications: ").append(medications != null ? medications : "Not Available ").append("\n")
                .append("Surgeries: ").append(surgeries != null ? surgeries : "Not Available ").append("\n")
                .append("Family History: ").append(familyHistory != null ? familyHistory : "Not Available ").append("\n")
                .append("Vaccination Records: ").append(vaccinationRecords != null ? vaccinationRecords : "Not Available ").append("\n");

        return patient.getValue() + value.toString();
    }
}
