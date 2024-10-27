package com.example.healthmonitor;
import java.time.LocalDate;

import java.time.LocalDate;
import java.util.List;

public class User {
    private String name;
    private String contactInformation;
    private String password;
    private String emergencyContact;
    private String address;

    // Getters
    public String getName() {
        return name;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public String getPassword() {
        return password;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public String getAddress() {
        return address;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
