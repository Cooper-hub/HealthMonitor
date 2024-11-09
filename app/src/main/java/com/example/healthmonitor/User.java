package com.example.healthmonitor;
import com.google.firebase.database.PropertyName;

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
    @PropertyName("name")

    public String getName() {
        return name;
    }
    @PropertyName("contactInformation")

    public String getContactInformation() {
        return contactInformation;
    }
    @PropertyName("password")
    public String getPassword() {
        return password;
    }
    @PropertyName("emergencyContact")
    public String getEmergencyContact() {
        return emergencyContact;
    }
    @PropertyName("address")
    public String getAddress() {
        return address;
    }

    // Setters

    @PropertyName("name")
    public void setName(String name) {
        this.name = name;
    }
    @PropertyName("contactInformation")
    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }
    @PropertyName("password")
    public void setPassword(String password) {
        this.password = password;
    }
    @PropertyName("emergencyContact")
    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }
    @PropertyName("address")
    public void setAddress(String address) {
        this.address = address;
    }
}
