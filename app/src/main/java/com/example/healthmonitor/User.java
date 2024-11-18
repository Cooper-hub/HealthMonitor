package com.example.healthmonitor;
import com.google.firebase.database.PropertyName;

import java.time.LocalDate;
import java.util.List;

/**
 * Represents a user in the health monitoring system. This class contains user information
 * such as name, contact details, password, emergency contact, and address. It also provides
 * getter and setter methods to access and modify these properties. The properties are mapped
 * to Firebase database fields using the {@link PropertyName} annotation.
 */
public class User {

    private String name;
    private String contactInformation;
    private String password;
    private String emergencyContact;
    private String address;

    // Getters

    /**
     * Gets the name of the user.
     *
     * @return the name of the user
     */
    @PropertyName("name")
    public String getName() {
        return name;
    }

    /**
     * Gets the contact information of the user.
     *
     * @return the contact information of the user
     */
    @PropertyName("contactInformation")
    public String getContactInformation() {
        return contactInformation;
    }

    /**
     * Gets the password of the user.
     *
     * @return the password of the user
     */
    @PropertyName("password")
    public String getPassword() {
        return password;
    }

    /**
     * Gets the emergency contact of the user.
     *
     * @return the emergency contact of the user
     */
    @PropertyName("emergencyContact")
    public String getEmergencyContact() {
        return emergencyContact;
    }

    /**
     * Gets the address of the user.
     *
     * @return the address of the user
     */
    @PropertyName("address")
    public String getAddress() {
        return address;
    }

    // Setters

    /**
     * Sets the name of the user.
     *
     * @param name the name of the user
     */
    @PropertyName("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the contact information of the user.
     *
     * @param contactInformation the contact information of the user
     */
    @PropertyName("contactInformation")
    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }

    /**
     * Sets the password of the user.
     *
     * @param password the password of the user
     */
    @PropertyName("password")
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets the emergency contact of the user.
     *
     * @param emergencyContact the emergency contact of the user
     */
    @PropertyName("emergencyContact")
    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    /**
     * Sets the address of the user.
     *
     * @param address the address of the user
     */
    @PropertyName("address")
    public void setAddress(String address) {
        this.address = address;
    }
}
