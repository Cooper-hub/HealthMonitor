package com.example.healthmonitor;

import java.util.*;
class PatientCareRegistry{
    private Map<Patient, List<MedicalProfessional>> registry = new HashMap<>();

    public void registerObserver(Patient patient, MedicalProfessional observer) {
        registry.computeIfAbsent(patient, k -> new ArrayList<>()).add(observer);
    }

    public void unregisterObserver(Patient patient, MedicalProfessional observer) {
        List<MedicalProfessional> observers = registry.get(patient);
        if (observers != null) {
            observers.remove(observer);
            if (observers.isEmpty()) {
                registry.remove(patient);
            }
        }
    }

    public void notifyObservers(Patient patient, String status) {
        List<MedicalProfessional> observers = registry.get(patient);
        if (observers != null) {
            for (MedicalProfessional observer : observers) {
                observer.update(patient);
            }
        }
    }
}

