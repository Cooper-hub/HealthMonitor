package com.example.healthmonitor.decorators;

import com.example.healthmonitor.Patient;
import com.example.healthmonitor.activities.IPatient;
import com.google.firebase.database.PropertyName;

import java.util.List;

public class PatientInformationDecorator extends PatientDecorator{
    private PatientDecorator decoratedPatient;
    public PatientInformationDecorator(PatientDecorator decoratedPatient){super(decoratedPatient);
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
}
