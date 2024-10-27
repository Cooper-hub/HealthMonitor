package com.example.healthmonitor.decorators;

import com.example.healthmonitor.Patient;

import java.util.List;

public abstract class PatientInformationDecorator extends Patient {
    private Patient patient;
    public PatientInformationDecorator(Patient patient){
        this.patient = patient;
    }
    // Medical History
    private List<String> chronicConditions = null;  // E.g., diabetes, hypertension
    private List<String> allergies = null;  // E.g., food, medication, environmental
    private List<String> medications = null;  // Name, dosage, frequency
    private List<String> surgeries = null;  // Surgery descriptions with dates
    private List<String> familyHistory = null;  // E.g., heart disease, cancer
    private List<String> vaccinationRecords = null;

    public List<String> getChronicConditions() {
        return chronicConditions;
    }

    public void setChronicConditions(List<String> chronicConditions) {
        this.chronicConditions = chronicConditions;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }

    public List<String> getMedications() {
        return medications;
    }

    public void setMedications(List<String> medications) {
        this.medications = medications;
    }

    public List<String> getSurgeries() {
        return surgeries;
    }

    public void setSurgeries(List<String> surgeries) {
        this.surgeries = surgeries;
    }

    public List<String> getFamilyHistory() {
        return familyHistory;
    }

    public void setFamilyHistory(List<String> familyHistory) {
        this.familyHistory = familyHistory;
    }

    public List<String> getVaccinationRecords() {
        return vaccinationRecords;
    }

    public void setVaccinationRecords(List<String> vaccinationRecords) {
        this.vaccinationRecords = vaccinationRecords;
    }
}
